package it.polimi.ingsw.lim.server.rmi;

import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;
import java.util.Objects;

public class ClientSession extends UnicastRemoteObject implements IClientSession, Unreferenced
{
	private final transient RMIConnection rmiConnection;

	ClientSession(RMIConnection rmiConnection) throws RemoteException
	{
		this.rmiConnection = rmiConnection;
	}

	@Override
	public void unreferenced()
	{
		Server.getInstance().getConnections().remove(this.rmiConnection);
		Server.getInstance().displayToLog("RMI Client: " + this.rmiConnection.getId() + ":" + this.rmiConnection.getName() + " disconnected.", FontType.NORMAL);
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		this.rmiConnection.handleChatMessage(text);
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof ClientSession && this.rmiConnection == ((ClientSession) object).rmiConnection;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.rmiConnection.getId());
	}

	public RMIConnection getRmiConnection()
	{
		return this.rmiConnection;
	}
}
