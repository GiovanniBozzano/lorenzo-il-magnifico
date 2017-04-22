package it.polimi.ingsw.lim.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

@FunctionalInterface public interface IHandshake extends Remote
{
	IClientSession send(String name, String version, IServerSession serverSession) throws RemoteException;
}