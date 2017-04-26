package it.polimi.ingsw.lim.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

@FunctionalInterface public interface IClientSession extends Remote
{
	void sendChatMessage(String text) throws RemoteException;
}
