package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerSession extends UnicastRemoteObject implements IServerSession
{
	ServerSession() throws RemoteException
	{
		super();
	}

	@Override
	public void sendDisconnect() throws RemoteException
	{
		Client.getInstance().disconnect(false, true);
	}

	@Override
	public void sendHeartbeat() throws RemoteException
	{
		// This method is empty because it is only called to check the connection.
	}

	@Override
	public void sendRoomList(List<RoomInformations> rooms) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomList(rooms);
	}

	@Override
	public void sendRoomCreationFailure()
	{
		Client.getInstance().getConnectionHandler().handleRoomCreationFailure();
	}

	@Override
	public void sendRoomEntryConfirmation(RoomInformations roomInformations) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomEntryConfirmation(roomInformations);
	}

	@Override
	public void sendRoomEntryOther(String name) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomEntryOther(name);
	}

	@Override
	public void sendRoomExitOther(String name) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomExitOther(name);
	}

	@Override
	public void sendLogMessage(String text) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleLogMessage(text);
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleChatMessage(text);
	}
}
