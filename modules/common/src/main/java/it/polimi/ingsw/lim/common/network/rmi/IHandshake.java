package it.polimi.ingsw.lim.common.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

@FunctionalInterface public interface IHandshake extends Remote
{
	IClientSession sendLogin(String name, String version, IServerSession serverSession) throws RemoteException;
}