package it.polimi.ingsw.lim.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerSession extends Remote
{
	void disconnect() throws RemoteException;

	void sendLogMessage(String text) throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;
}
