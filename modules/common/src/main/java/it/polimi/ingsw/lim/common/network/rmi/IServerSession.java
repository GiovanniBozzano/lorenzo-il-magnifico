package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerSession extends Remote
{
	void sendDisconnect() throws RemoteException;

	void sendHeartbeat() throws RemoteException;

	void sendRoomEntryOther(String name) throws RemoteException;

	void sendRoomExitOther(String name) throws RemoteException;

	void sendRoomTimer(int timer) throws RemoteException;

	void sendGameStarted(RoomInformations roomInformations) throws RemoteException;

	void sendLogMessage(String text) throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;
}
