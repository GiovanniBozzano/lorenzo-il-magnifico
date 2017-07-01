package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformations;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientSession extends Remote
{
	void sendDisconnect() throws RemoteException;

	void sendHeartbeat() throws RemoteException;

	void sendRoomTimerRequest() throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;

	@SuppressWarnings("squid:S1160")
	void sendGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex) throws RemoteException, GameActionFailedException;

	@SuppressWarnings("squid:S1160")
	void sendGameLeaderCardPlayerChoice(int leaderCardIndex) throws RemoteException, GameActionFailedException;

	@SuppressWarnings("squid:S1160")
	void sendGameExcommunicationPlayerChoice(boolean excommunicated) throws RemoteException, GameActionFailedException;

	@SuppressWarnings("squid:S1160")
	void sendGameAction(ActionInformations action) throws RemoteException, GameActionFailedException;

	@SuppressWarnings("squid:S1160")
	void sendGoodGame(int playerIndex) throws RemoteException, GameActionFailedException;
}
