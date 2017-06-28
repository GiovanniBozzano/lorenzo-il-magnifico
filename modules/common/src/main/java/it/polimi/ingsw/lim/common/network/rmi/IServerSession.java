package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IServerSession extends Remote
{
	void sendDisconnect() throws RemoteException;

	void sendHeartbeat() throws RemoteException;

	void sendRoomEntryOther(String name) throws RemoteException;

	void sendRoomExitOther(String name) throws RemoteException;

	void sendRoomTimer(int timer) throws RemoteException;

	void sendDisconnectionLogMessage(String text) throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;

	void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerIdentification> playersData, int ownPlayerIndex) throws RemoteException;

	void sendGameLogMessage(String text) throws RemoteException;

	void sendGameTimer(int timer) throws RemoteException;

	void sendGameDisconnectionOther(int playerIndex) throws RemoteException;

	void sendGamePersonalBonusTileChoiceRequest(List<Integer> availablePersonalBonusTiles) throws RemoteException;

	void sendGamePersonalBonusTileChoiceOther(int choicePlayerIndex) throws RemoteException;

	void sendGamePersonalBonusTileChosen(int choicePlayerIndex) throws RemoteException;

	void sendGameLeaderCardChoiceRequest(List<Integer> availableLeaderCards) throws RemoteException;

	void sendGameLeaderCardChosen() throws RemoteException;

	void sendGameExcommunicationChoiceRequest(Period period) throws RemoteException;

	void sendGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, Map<ActionType, List<AvailableAction>> availableActions) throws RemoteException;

	void sendGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, ExpectedAction expectedAction) throws RemoteException;

	void sendGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, Map<Integer, Boolean> ownLeaderCardsHand, int turnPlayerIndex) throws RemoteException;

	void sendGameEnded(Map<Integer, Integer> playersScores, Map<Integer, Integer> playerIndexesVictoryPointsRecord) throws RemoteException;
}
