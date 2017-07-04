package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformation;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientSession extends Remote
{
	void sendDisconnect() throws RemoteException;

	void sendHeartbeat() throws RemoteException;

	void sendRoomTimerRequest() throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;

	void sendGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex) throws RemoteException, GameActionFailedException;

	void sendGameLeaderCardPlayerChoice(int leaderCardIndex) throws RemoteException, GameActionFailedException;

	void sendGameExcommunicationPlayerChoice(boolean excommunicated) throws RemoteException, GameActionFailedException;

	void sendGameAction(ActionInformation action) throws RemoteException, GameActionFailedException;

	void sendGoodGame(int playerIndex) throws RemoteException, GameActionFailedException;
}
