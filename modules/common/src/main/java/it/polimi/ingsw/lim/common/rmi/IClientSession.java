package it.polimi.ingsw.lim.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientSession extends Remote
{
	void sendRequestRoomList() throws RemoteException;

	void sendRoomEntry(int id) throws RemoteException;

	void sendRoomExit() throws RemoteException;

	void sendRoomCreation(String name) throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;
}
