package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;

import javax.naming.AuthenticationException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthentication extends Remote
{
	IClientSession sendLogin(String name, String password, String version, IServerSession serverSession) throws RemoteException, AuthenticationFailedException;

	IClientSession sendRegistration(String name, String password, String version, IServerSession serverSession) throws RemoteException, AuthenticationFailedException;

	void sendHeartbeat() throws RemoteException;
}