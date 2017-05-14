package it.polimi.ingsw.lim.common.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

@FunctionalInterface public interface ILogin extends Remote
{
	IClientSession sendLogin(String name, String password, String version, IServerSession serverSession) throws RemoteException;
}