package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.utils.AuthenticationInformations;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthentication extends Remote
{
	AuthenticationInformations sendLogin(String version, String name, String password, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException;

	AuthenticationInformations sendRegistration(String version, String name, String password, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException;

	void sendHeartbeat() throws RemoteException;
}