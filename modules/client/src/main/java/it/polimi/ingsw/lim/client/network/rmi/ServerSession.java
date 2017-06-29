package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class ServerSession extends UnicastRemoteObject implements IServerSession
{
	ServerSession() throws RemoteException
	{
		super();
	}

	@Override
	public void sendDisconnect() throws RemoteException
	{
		Client.getInstance().disconnect(false, false);
	}

	@Override
	public void sendHeartbeat() throws RemoteException
	{
		// This method is empty because it is only called to check the connection.
	}

	@Override
	public void sendRoomEntryOther(String name) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomEntryOther(name);
	}

	@Override
	public void sendRoomExitOther(String name) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomExitOther(name);
	}

	@Override
	public void sendRoomTimer(int timer) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomTimer(timer);
	}

	@Override
	public void sendDisconnectionLogMessage(String text) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleDisconnectionLogMessage(text);
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleChatMessage(text);
	}

	@Override
	public void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameStarted(excommunicationTiles, playersData, ownPlayerIndex);
	}

	@Override
	public void sendGameLogMessage(String text) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameLogMessage(text);
	}

	@Override
	public void sendGameTimer(int timer) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameTimer(timer);
	}

	@Override
	public void sendGameDisconnectionOther(int playerIndex)
	{
		Client.getInstance().getConnectionHandler().handleGameDisconnectionOther(playerIndex);
	}

	@Override
	public void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGamePersonalBonusTileChoiceRequest(availablePersonalBonusTiles);
	}

	@Override
	public void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGamePersonalBonusTileChoiceOther(choicePlayerIndex);
	}

	@Override
	public void sendGamePersonalBonusTileChosen(int choicePlayerIndex) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGamePersonalBonusTileChosen(choicePlayerIndex);
	}

	@Override
	public void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameLeaderCardChoiceRequest(availableLeaderCards);
	}

	@Override
	public void sendGameLeaderCardChosen() throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameLeaderCardChosen();
	}

	@Override
	public void sendGameExcommunicationChoiceRequest(Period period) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameExcommunicationChoiceRequest(period);
	}

	@Override
	public void sendGameExcommunicationChoiceOther() throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameExcommunicationChoiceOther();
	}

	@Override
	public void sendGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, Map<ActionType, List<AvailableAction>> availableActions) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameUpdate(gameInformations, playersInformations, ownLeaderCardsHand, availableActions);
	}

	@Override
	public void sendGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, ExpectedAction expectedAction) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameUpdateExpectedAction(gameInformations, playersInformations, ownLeaderCardsHand, expectedAction);
	}

	@Override
	public void sendGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, int turnPlayerIndex) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameUpdateOtherTurn(gameInformations, playersInformations, ownLeaderCardsHand, turnPlayerIndex);
	}

	@Override
	public void sendGameEnded(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameEnded(playersScores, playerIndexesVictoryPointsRecord);
	}
}
