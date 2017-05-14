package it.polimi.ingsw.lim.common.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthentication extends Remote
{
	IClientSession sendLogin(String name, String password, String version, IServerSession serverSession) throws RemoteException;

	IClientSession sendRegistration(String name, String password, String version, IServerSession serverSession) throws RemoteException;

	void sendHeartbeat() throws RemoteException;
}