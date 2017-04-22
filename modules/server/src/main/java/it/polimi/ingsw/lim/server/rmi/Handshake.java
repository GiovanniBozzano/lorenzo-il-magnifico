package it.polimi.ingsw.lim.server.rmi;

import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.common.rmi.IHandshake;
import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class Handshake extends UnicastRemoteObject implements IHandshake
{
	private transient int id;

	public Handshake() throws RemoteException
	{
	}

	@Override
	public IClientSession send(String name, String version, IServerSession serverSession) throws RemoteException
	{
		RMIConnection rmiConnection = new RMIConnection(++this.id, name, serverSession);
		Server.getInstance().getConnections().add(rmiConnection);
		return new ClientSession(rmiConnection);
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof Handshake && this.id == ((Handshake) object).id;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.toString());
	}
}