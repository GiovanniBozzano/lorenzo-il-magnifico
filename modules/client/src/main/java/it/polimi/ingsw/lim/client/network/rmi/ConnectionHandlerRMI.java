package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformations;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.rmi.AuthenticationInformationsGameRMI;
import it.polimi.ingsw.lim.common.network.rmi.AuthenticationInformationsLobbyRMI;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionHandlerRMI extends ConnectionHandler
{
	private final ExecutorService rmiExecutor = Executors.newSingleThreadExecutor();
	private ServerSession serverSession;
	private IAuthentication login;
	private IClientSession clientSession;

	@Override
	public void run()
	{
		try {
			this.serverSession = new ServerSession();
			this.login = (IAuthentication) Naming.lookup("rmi://" + Client.getInstance().getIp() + ":" + Client.getInstance().getPort() + "/lorenzo-il-magnifico");
		} catch (NotBoundException | MalformedURLException | RemoteException | IllegalArgumentException exception) {
			Client.getDebugger().log(Level.OFF, "Could not connect to host.", exception);
			Client.getInstance().getInterfaceHandler().handleConnectionError();
			return;
		}
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		Client.getInstance().getInterfaceHandler().handleConnectionSuccess();
	}

	@Override
	public synchronized void disconnect(boolean notifyServer)
	{
		super.disconnect(notifyServer);
		this.rmiExecutor.shutdownNow();
		if (notifyServer && this.clientSession != null) {
			try {
				this.clientSession.sendDisconnect();
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		try {
			UnicastRemoteObject.unexportObject(this.serverSession, false);
		} catch (NoSuchObjectException exception) {
			Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public synchronized void sendHeartbeat()
	{
		super.sendHeartbeat();
		try {
			if (this.clientSession != null) {
				this.clientSession.sendHeartbeat();
			} else {
				this.login.sendHeartbeat();
			}
		} catch (RemoteException exception) {
			Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
			Client.getInstance().disconnect(false, false);
		}
	}

	@Override
	public synchronized void sendLogin(String username, String password, RoomType roomType)
	{
		super.sendLogin(username, password, roomType);
		this.rmiExecutor.execute(() -> {
			try {
				this.finalizeAuthentication(username, this.login.sendLogin(CommonUtils.VERSION, username, CommonUtils.encrypt(password), roomType, this.serverSession));
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			} catch (AuthenticationFailedException exception) {
				Client.getDebugger().log(Level.OFF, exception.getLocalizedMessage(), exception);
				Client.getInstance().getInterfaceHandler().handleAuthenticationFailed(exception.getLocalizedMessage());
			}
		});
	}

	@Override
	public synchronized void sendRegistration(String username, String password, RoomType roomType)
	{
		super.sendRegistration(username, password, roomType);
		this.rmiExecutor.execute(() -> {
			try {
				this.finalizeAuthentication(username, this.login.sendRegistration(CommonUtils.VERSION, username, CommonUtils.encrypt(password), roomType, this.serverSession));
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			} catch (AuthenticationFailedException exception) {
				Client.getDebugger().log(Level.OFF, exception.getLocalizedMessage(), exception);
				Client.getInstance().getInterfaceHandler().handleAuthenticationFailed(exception.getLocalizedMessage());
			}
		});
	}

	@Override
	public synchronized void sendRoomTimerRequest()
	{
		super.sendRoomTimerRequest();
		this.rmiExecutor.execute(() -> {
			try {
				this.clientSession.sendRoomTimerRequest();
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			}
		});
	}

	@Override
	public synchronized void sendChatMessage(String text)
	{
		super.sendChatMessage(text);
		this.rmiExecutor.execute(() -> {
			try {
				this.clientSession.sendChatMessage(text);
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			}
		});
	}

	@Override
	public synchronized void sendGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex)
	{
		super.sendGamePersonalBonusTilePlayerChoice(personalBonusTileIndex);
		this.rmiExecutor.execute(() -> {
			try {
				this.clientSession.sendGamePersonalBonusTilePlayerChoice(personalBonusTileIndex);
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			} catch (GameActionFailedException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				Client.getInstance().getInterfaceHandler().handleGameActionFailed(exception.getLocalizedMessage());
			}
		});
	}

	@Override
	public synchronized void sendGameLeaderCardPlayerChoice(int leaderCardIndex)
	{
		super.sendGameLeaderCardPlayerChoice(leaderCardIndex);
		this.rmiExecutor.execute(() -> {
			try {
				this.clientSession.sendGameLeaderCardPlayerChoice(leaderCardIndex);
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			} catch (GameActionFailedException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				Client.getInstance().getInterfaceHandler().handleGameActionFailed(exception.getLocalizedMessage());
			}
		});
	}

	@Override
	public synchronized void sendGameExcommunicationPlayerChoice(boolean excommunicated)
	{
		super.sendGameExcommunicationPlayerChoice(excommunicated);
		this.rmiExecutor.execute(() -> {
			try {
				this.clientSession.sendGameExcommunicationPlayerChoice(excommunicated);
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			} catch (GameActionFailedException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				Client.getInstance().getInterfaceHandler().handleGameActionFailed(exception.getLocalizedMessage());
			}
		});
	}

	@Override
	public synchronized void sendGameAction(ActionInformations action)
	{
		super.sendGameAction(action);
		this.rmiExecutor.execute(() -> {
			try {
				this.clientSession.sendGameAction(action);
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			} catch (GameActionFailedException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				Client.getInstance().getInterfaceHandler().handleGameActionFailed(exception.getLocalizedMessage());
			}
		});
	}

	@Override
	public synchronized void sendGoodGame(int playerIndex)
	{
		super.sendGoodGame(playerIndex);
		this.rmiExecutor.execute(() -> {
			try {
				this.clientSession.sendGoodGame(playerIndex);
			} catch (RemoteException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			} catch (GameActionFailedException exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				Client.getInstance().getInterfaceHandler().handleGameActionFailed(exception.getLocalizedMessage());
			}
		});
	}

	private void finalizeAuthentication(String username, AuthenticationInformations authenticationInformations)
	{
		GameStatus.getInstance().setup(authenticationInformations.getDevelopmentCardsBuildingInformations(), authenticationInformations.getDevelopmentCardsCharacterInformations(), authenticationInformations.getDevelopmentCardsTerritoryInformations(), authenticationInformations.getDevelopmentCardsVentureInformations(), authenticationInformations.getLeaderCardsInformations(), authenticationInformations.getExcommunicationTilesInformations(), authenticationInformations.getPersonalBonusTilesInformations());
		if (!authenticationInformations.isGameStarted()) {
			this.clientSession = ((AuthenticationInformationsLobbyRMI) authenticationInformations).getClientSession();
			Client.getInstance().setUsername(username);
			Client.getInstance().getInterfaceHandler().handleAuthenticationSuccess(authenticationInformations);
		} else {
			this.clientSession = ((AuthenticationInformationsGameRMI) authenticationInformations).getClientSession();
			GameStatus.getInstance().setCurrentExcommunicationTiles(((AuthenticationInformationsGameRMI) authenticationInformations).getExcommunicationTiles());
			GameStatus.getInstance().setCurrentCouncilPrivilegeRewards(((AuthenticationInformationsGameRMI) authenticationInformations).getCouncilPrivilegeRewards());
			Map<Integer, PlayerData> playersData = new HashMap<>();
			for (Entry<Integer, PlayerIdentification> entry : ((AuthenticationInformationsGameRMI) authenticationInformations).getPlayersIdentifications().entrySet()) {
				playersData.put(entry.getKey(), new PlayerData(entry.getValue().getUsername(), entry.getValue().getColor()));
			}
			GameStatus.getInstance().setCurrentPlayerData(playersData);
			GameStatus.getInstance().setOwnPlayerIndex(((AuthenticationInformationsGameRMI) authenticationInformations).getOwnPlayerIndex());
			Client.getInstance().getInterfaceHandler().handleAuthenticationSuccessGameStarted((AuthenticationInformationsGameRMI) authenticationInformations);
		}
	}
}
