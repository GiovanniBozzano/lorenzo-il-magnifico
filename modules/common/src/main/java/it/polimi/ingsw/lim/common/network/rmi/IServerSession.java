package it.polimi.ingsw.lim.common.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerSession extends Remote
{
	void sendDisconnect() throws RemoteException;

	void sendHeartbeat() throws RemoteException;

	void sendRoomEntryOther(String name) throws RemoteException;

	void sendRoomExitOther(String name) throws RemoteException;

	void sendLogMessage(String text) throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;
}
