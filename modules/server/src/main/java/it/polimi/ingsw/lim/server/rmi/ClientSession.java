package it.polimi.ingsw.lim.server.rmi;

import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;
import java.util.Objects;

public class ClientSession extends UnicastRemoteObject implements IClientSession, Unreferenced
{
	private final transient ConnectionRMI connectionRmi;

	ClientSession(ConnectionRMI connectionRmi) throws RemoteException
	{
		this.connectionRmi = connectionRmi;
	}

	@Override
	public void unreferenced()
	{
		Server.getInstance().getConnections().remove(this.connectionRmi);
		Server.getInstance().displayToLog("RMI Client: " + this.connectionRmi.getId() + ":" + this.connectionRmi.getName() + " disconnected.");
	}

	@Override
	public void requestRoomList() throws RemoteException
	{
		this.connectionRmi.handleRequestRoomList();
	}

	@Override
	public void enterRoom(int id) throws RemoteException
	{
		this.connectionRmi.handleRoomEntry(id);
	}

	@Override
	public void exitRoom(int id) throws RemoteException
	{
		this.connectionRmi.handleRoomExit(id);
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		this.connectionRmi.handleChatMessage(text);
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof ClientSession && this.connectionRmi == ((ClientSession) object).connectionRmi;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.connectionRmi.getId());
	}

	public ConnectionRMI getConnectionRMI()
	{
		return this.connectionRmi;
	}
}
