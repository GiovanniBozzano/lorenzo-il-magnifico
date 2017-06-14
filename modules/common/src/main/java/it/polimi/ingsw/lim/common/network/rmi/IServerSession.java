package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.player.PlayerData;
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

	void sendLogMessage(String text) throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;

	void sendGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, PlayerData> playersData) throws RemoteException;

	void sendGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<AvailableAction> availableActions) throws RemoteException;

	void sendGameUpdateExpectedAction(GameInformations gameInformations, List<PlayerInformations> playersInformations, ExpectedAction expectedAction) throws RemoteException;

	void sendGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations) throws RemoteException;
}
