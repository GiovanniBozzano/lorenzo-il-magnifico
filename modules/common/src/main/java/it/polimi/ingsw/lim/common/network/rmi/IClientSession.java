package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.game.actions.ActionInformations;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientSession extends Remote
{
	void sendDisconnect() throws RemoteException;

	void sendHeartbeat() throws RemoteException;

	void sendRoomTimerRequest() throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;

	void sendGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex) throws RemoteException;

	void sendGameLeaderCardPlayerChoice(int leaderCardIndex) throws RemoteException;

	void sendGameExcommunicationPlayerChoice(boolean excommunicated) throws RemoteException;

	void sendGameAction(ActionInformations action) throws RemoteException;
}
