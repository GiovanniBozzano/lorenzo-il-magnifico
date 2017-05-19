package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.network.rmi.IClientSession;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class ClientSession extends UnicastRemoteObject implements IClientSession
{
	private final transient ConnectionRMI connectionRmi;

	ClientSession(ConnectionRMI connectionRmi) throws RemoteException
	{
		super();
		this.connectionRmi = connectionRmi;
	}

	@Override
	public void sendDisconnect() throws RemoteException
	{
		this.connectionRmi.disconnect(false, null);
	}

	@Override
	public void sendHeartbeat() throws RemoteException
	{
		// This method is empty because it is only called to check the connection.
	}

	@Override
	public void sendRoomListRequest() throws RemoteException
	{
		this.connectionRmi.handleRoomListRequest();
	}

	@Override
	public void sendRoomCreation(String name) throws RemoteException
	{
		this.connectionRmi.handleRoomCreation(name);
	}

	@Override
	public void sendRoomEntry(int id) throws RemoteException
	{
		this.connectionRmi.handleRoomEntry(id);
	}

	@Override
	public void sendRoomExit() throws RemoteException
	{
		this.connectionRmi.handleRoomExit();
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		this.connectionRmi.handleChatMessage(text);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		ClientSession that = (ClientSession) o;
		return Objects.equals(this.connectionRmi, that.connectionRmi);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.connectionRmi);
	}

	ConnectionRMI getConnectionRMI()
	{
		return this.connectionRmi;
	}
}
