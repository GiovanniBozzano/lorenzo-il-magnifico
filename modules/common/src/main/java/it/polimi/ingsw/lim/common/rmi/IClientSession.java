package it.polimi.ingsw.lim.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientSession extends Remote
{
	void requestRoomList() throws RemoteException;

	void enterRoom(int id) throws RemoteException;

	void exitRoom() throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;
}
