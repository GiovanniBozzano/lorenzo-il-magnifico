package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionRMI extends Connection
{
	private final ExecutorService rmiExecutor = Executors.newSingleThreadExecutor();
	private final IServerSession serverSession;

	ConnectionRMI(String name, IServerSession serverSession)
	{
		super(name);
		this.serverSession = serverSession;
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
	}

	ConnectionRMI(String name, IServerSession serverSession, Player player)
	{
		super(name);
		this.serverSession = serverSession;
		this.setPlayer(player);
		this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3L, TimeUnit.SECONDS);
	}

	@Override
	public void disconnect(boolean notifyClient, String message)
	{
		super.disconnect(notifyClient, null);
		for (ClientSession clientSession : Server.getInstance().getConnectionHandler().getLogin().getClientSessions()) {
			if (clientSession.getConnectionRMI() == this) {
				try {
					UnicastRemoteObject.unexportObject(clientSession, true);
				} catch (NoSuchObjectException exception) {
					Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				}
				Server.getInstance().getConnectionHandler().getLogin().getClientSessions().remove(clientSession);
				break;
			}
		}
		if (notifyClient) {
			if (message != null) {
				this.sendDisconnectionLogMessage(message);
			}
			try {
				this.serverSession.sendDisconnect();
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
			}
		}
		this.rmiExecutor.shutdownNow();
		Utils.displayToLog("RMI Player: " + this.getUsername() + " disconnected.");
	}

	@Override
	public void sendHeartbeat()
	{
		try {
			this.serverSession.sendHeartbeat();
		} catch (RemoteException exception) {
			Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
			this.disconnect(false, null);
		}
	}

	@Override
	public void sendRoomEntryOther(String name)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendRoomEntryOther(name);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendRoomExitOther(String name)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendRoomExitOther(name);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendRoomTimer(int timer)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendRoomTimer(timer);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendDisconnectionLogMessage(String text)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendDisconnectionLogMessage(text);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendChatMessage(String text)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendChatMessage(text);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameStarted(excommunicationTiles, playersData, ownPlayerIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameLogMessage(String text)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameLogMessage(text);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameTimer(int timer)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameTimer(timer);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameDisconnectionOther(int playerIndex)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameDisconnectionOther(playerIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGamePersonalBonusTileChoiceRequest(availablePersonalBonusTiles);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGamePersonalBonusTileChoiceOther(choicePlayerIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGamePersonalBonusTileChosen(int choicePlayerIndex, int choicePersonalBonusTileIndex)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGamePersonalBonusTileChosen(choicePlayerIndex, choicePersonalBonusTileIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameLeaderCardChoiceRequest(availableLeaderCards);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameLeaderCardChosen()
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameLeaderCardChosen();
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameExcommunicationChoiceRequest(Period period)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameExcommunicationChoiceRequest(period);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, Map<ActionType, List<AvailableAction>> availableActions)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameUpdate(gameInformations, playersInformations, ownLeaderCardsHand, availableActions);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, ExpectedAction expectedAction)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameUpdateExpectedAction(gameInformations, playersInformations, ownLeaderCardsHand, expectedAction);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}

	@Override
	public void sendGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, int turnPlayerIndex)
	{
		this.rmiExecutor.execute(() -> {
			try {
				this.serverSession.sendGameUpdateOtherTurn(gameInformations, playersInformations, ownLeaderCardsHand, turnPlayerIndex);
			} catch (RemoteException exception) {
				Server.getDebugger().log(Level.INFO, DebuggerFormatter.RMI_ERROR, exception);
				this.disconnect(false, null);
			}
		});
	}
}

