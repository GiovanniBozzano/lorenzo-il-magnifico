package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.cli.CLIListenerClient;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.gui.ControllerAuthentication;
import it.polimi.ingsw.lim.client.gui.ControllerRoom;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.network.rmi.AuthenticationInformationsRMI;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.application.Platform;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
			Client.getDebugger().log(Level.INFO, "Could not connect to host.", exception);
			ConnectionHandler.printConnectionError();
			return;
		}
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_AUTHENTICATION, true);
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
			Client.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
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
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
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
				Client.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			} catch (AuthenticationFailedException exception) {
				Client.getDebugger().log(Level.INFO, exception.getLocalizedMessage(), exception);
				WindowFactory.getInstance().enableWindow();
				if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE) {
					Platform.runLater(() -> ((ControllerAuthentication) WindowFactory.getInstance().getCurrentWindow()).showDialog(exception.getLocalizedMessage()));
				} else {
					Client.getLogger().log(Level.INFO, exception.getLocalizedMessage());
				}
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
				Client.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			} catch (AuthenticationFailedException exception) {
				Client.getDebugger().log(Level.INFO, exception.getLocalizedMessage(), exception);
				WindowFactory.getInstance().enableWindow();
				if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE) {
					Platform.runLater(() -> ((ControllerAuthentication) WindowFactory.getInstance().getCurrentWindow()).showDialog(exception.getLocalizedMessage()));
				} else {
					Client.getLogger().log(Level.INFO, exception.getLocalizedMessage());
				}
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
				Client.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
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
				Client.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
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
				Client.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
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
				Client.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, false);
			}
		});
	}

	private void finalizeAuthentication(String username, AuthenticationInformationsRMI authenticationInformations)
	{
		GameStatus.getInstance().setup(authenticationInformations.getDevelopmentCardsBuildingInformations(), authenticationInformations.getDevelopmentCardsCharacterInformations(), authenticationInformations.getDevelopmentCardsTerritoryInformations(), authenticationInformations.getDevelopmentCardsVentureInformations(), authenticationInformations.getLeaderCardsInformations(), authenticationInformations.getExcommunicationTilesInformations(), authenticationInformations.getCouncilPalaceRewardsInformations(), authenticationInformations.getPersonalBonusTilesInformations());
		this.clientSession = authenticationInformations.getClientSession();
		Client.getInstance().setUsername(username);
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_ROOM, true, () -> Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow()).setRoomInformations(authenticationInformations.getRoomInformations().getRoomType(), authenticationInformations.getRoomInformations().getPlayerNames())));
	}
}
