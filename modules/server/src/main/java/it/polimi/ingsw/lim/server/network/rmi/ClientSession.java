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
	public void sendRoomTimerRequest() throws RemoteException
	{
		this.connectionRmi.handleRoomTimerRequest();
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		this.connectionRmi.handleChatMessage(text);
	}

	@Override
	public void sendGamePersonalBonusTilePlayerChoice(int personalBonusTileIndex) throws RemoteException
	{
		this.connectionRmi.handleGamePersonalBonusTilePlayerChoice(personalBonusTileIndex);
	}

	@Override
	public boolean equals(Object object)
	{
		if (this == object) {
			return true;
		}
		if (object == null || this.getClass() != object.getClass()) {
			return false;
		}
		if (!super.equals(object)) {
			return false;
		}
		ClientSession that = (ClientSession) object;
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
