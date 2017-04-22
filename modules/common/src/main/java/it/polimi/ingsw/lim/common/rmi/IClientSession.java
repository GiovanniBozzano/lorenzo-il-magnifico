package it.polimi.ingsw.lim.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientSession extends Remote
{
	void getMyName() throws RemoteException;

	void sendChatMessage(String text) throws RemoteException;
}
