package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthentication extends Remote
{
	@SuppressWarnings("squid:S1160")
	AuthenticationInformations sendLogin(String version, String name, String password, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException;

	@SuppressWarnings("squid:S1160")
	AuthenticationInformations sendRegistration(String version, String name, String password, RoomType roomType, IServerSession serverSession) throws RemoteException, AuthenticationFailedException;

	void sendHeartbeat() throws RemoteException;
}