package it.polimi.ingsw.lim.client.network;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.ActionInformations;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

public abstract class ConnectionHandler extends Thread
{
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();

	@Override
	public abstract void run();

	public void disconnect(boolean notifyServer)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		this.getHeartbeat().shutdownNow();
	}

	/**
	 * <p>Tries to send an heartbeat to check the connection status.
	 */
	public synchronized void sendHeartbeat()
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * <p>Tries to login with username, password and Client version, sending the
	 * desired {@link RoomType}.
	 *
	 * @param username the username.
	 * @param password the password.
	 * @param roomType the desired {@link RoomType}.
	 */
	public synchronized void sendLogin(String username, String password, RoomType roomType)
	{
		this.checkInitialization();
	}

	/**
	 * <p>Tries to register with username, password and Client version, sending
	 * the desired {@link RoomType}.
	 *
	 * @param username the username.
	 * @param password the password.
	 * @param roomType the desired {@link RoomType}.
	 */
	public synchronized void sendRegistration(String username, String password, RoomType roomType)
	{
		this.checkInitialization();
	}

	public synchronized void sendRoomTimerRequest()
	{
		this.checkInitialization();
	}

	public synchronized void sendChatMessage(String text)
	{
		this.checkInitialization();
	}

	public synchronized void sendGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex)
	{
		this.checkInitialization();
	}

	public synchronized void sendGameLeaderCardPlayerChoice(int leaderCardIndex)
	{
		this.checkInitialization();
	}

	public synchronized void sendGameExcommunicationPlayerChoice(boolean excommunicated)
	{
		this.checkInitialization();
	}

	public synchronized void sendGameAction(ActionInformations action)
	{
		this.checkInitialization();
	}

	public synchronized void sendGoodGame(int playerIndex)
	{
		this.checkInitialization();
	}

	public void handleRoomEntryOther(String name)
	{
		Client.getInstance().getInterfaceHandler().handleRoomEntryOther(name);
	}

	public void handleRoomExitOther(String name)
	{
		Client.getInstance().getInterfaceHandler().handleRoomExitOther(name);
	}

	public void handleRoomTimer(int timer)
	{
		Client.getInstance().getInterfaceHandler().handleRoomTimer(timer);
	}

	public void handleDisconnectionLogMessage(String text)
	{
		Client.getInstance().getInterfaceHandler().handleDisconnectionLogMessage(text);
	}

	public void handleChatMessage(String text)
	{
		Client.getInstance().getInterfaceHandler().handleChatMessage(text);
	}

	public void handleGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, List<ResourceAmount>> councilPrivilegeRewards, Map<Integer, PlayerIdentification> playersIdentifications, int ownPlayerIndex)
	{
		GameStatus.getInstance().setCurrentExcommunicationTiles(excommunicationTiles);
		GameStatus.getInstance().setCurrentCouncilPrivilegeRewards(councilPrivilegeRewards);
		Map<Integer, PlayerData> playersData = new HashMap<>();
		for (Entry<Integer, PlayerIdentification> entry : playersIdentifications.entrySet()) {
			playersData.put(entry.getKey(), new PlayerData(entry.getValue().getUsername(), entry.getValue().getColor()));
		}
		GameStatus.getInstance().setCurrentPlayerData(playersData);
		GameStatus.getInstance().setOwnPlayerIndex(ownPlayerIndex);
		Client.getInstance().getInterfaceHandler().handleGameStarted();
	}

	public void handleGameLogMessage(String text)
	{
		Client.getInstance().getInterfaceHandler().handleGameLogMessage(text);
	}

	public void handleGameTimer(int timer)
	{
		Client.getInstance().getInterfaceHandler().handleGameTimer(timer);
	}

	public void handleGameDisconnectionOther(int playerIndex)
	{
		Client.getInstance().getInterfaceHandler().handleGameDisconnectionOther(playerIndex);
	}

	public void handleGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles)
	{
		GameStatus.getInstance().setAvailablePersonalBonusTiles(availablePersonalBonusTiles);
		Client.getInstance().getInterfaceHandler().handleGamePersonalBonusTileChoiceRequest();
	}

	public void handleGamePersonalBonusTileChoiceOther(int choicePlayerIndex)
	{
		Client.getInstance().getInterfaceHandler().handleGamePersonalBonusTileChoiceOther(choicePlayerIndex);
	}

	public void handleGamePersonalBonusTileChosen(int choicePlayerIndex)
	{
		Client.getInstance().getInterfaceHandler().handleGamePersonalBonusTileChosen(choicePlayerIndex);
	}

	public void handleGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards)
	{
		GameStatus.getInstance().setAvailableLeaderCards(availableLeaderCards);
		Client.getInstance().getInterfaceHandler().handleGameLeaderCardChoiceRequest();
	}

	public void handleGameExcommunicationChoiceRequest(Period period)
	{
		Client.getInstance().getInterfaceHandler().handleGameExcommunicationChoiceRequest(period);
	}

	public void handleGameExcommunicationChoiceOther()
	{
		Client.getInstance().getInterfaceHandler().handleGameExcommunicationChoiceOther();
	}

	public void handleGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, Map<ActionType, List<Serializable>> availableActions)
	{
		if (GameStatus.getInstance().getCurrentTurnPlayerIndex() != GameStatus.getInstance().getOwnPlayerIndex()) {
			Client.getInstance().getInterfaceHandler().handleGameUpdateLog();
			GameStatus.getInstance().setCurrentTurnPlayerIndex(GameStatus.getInstance().getOwnPlayerIndex());
		}
		GameStatus.getInstance().updateGameStatus(gameInformations, playersInformations, ownLeaderCardsHand);
		GameStatus.getInstance().setCurrentAvailableActions(availableActions);
		Client.getInstance().getInterfaceHandler().handleGameUpdate();
	}

	public void handleGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, ExpectedAction expectedAction)
	{
		GameStatus.getInstance().setCurrentTurnPlayerIndex(GameStatus.getInstance().getOwnPlayerIndex());
		GameStatus.getInstance().updateGameStatus(gameInformations, playersInformations, ownLeaderCardsHand);
		Client.getInstance().getInterfaceHandler().handleGameUpdateExpectedAction(expectedAction);
	}

	public void handleGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, int turnPlayerIndex)
	{
		if (turnPlayerIndex != GameStatus.getInstance().getCurrentTurnPlayerIndex()) {
			Client.getInstance().getInterfaceHandler().handleGameUpdateOtherTurnLog(turnPlayerIndex);
			GameStatus.getInstance().setCurrentTurnPlayerIndex(turnPlayerIndex);
		}
		GameStatus.getInstance().updateGameStatus(gameInformations, playersInformations, ownLeaderCardsHand);
		Client.getInstance().getInterfaceHandler().handleGameUpdateOther();
	}

	public void handleGameEnded(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord)
	{
		Client.getInstance().getInterfaceHandler().handleGameEnded(playersScores, playerIndexesVictoryPointsRecord);
	}

	private void checkInitialization()
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	protected ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}
}
