package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerSession extends UnicastRemoteObject implements IServerSession
{
	public ServerSession() throws RemoteException
	{
	}

	@Override
	public void sendDisconnect() throws RemoteException
	{
		Client.getInstance().disconnect(false, true);
	}

	@Override
	public void sendRoomList(List<RoomInformations> rooms) throws RemoteException
	{
		Connection.handleRoomList(rooms);
	}

	@Override
	public void sendRoomCreationFailure()
	{
		Connection.handleRoomCreationFailure();
	}

	@Override
	public void sendRoomEntryConfirmation(RoomInformations roomInformations) throws RemoteException
	{
		Connection.handleRoomEntryConfirmation(roomInformations);
	}

	@Override
	public void sendRoomEntryOther(String name) throws RemoteException
	{
		Connection.handleRoomEntryOther(name);
	}

	@Override
	public void sendRoomExitOther(String name) throws RemoteException
	{
		Connection.handleRoomExitOther(name);
	}

	@Override
	public void sendLogMessage(String text) throws RemoteException
	{
		Connection.handleLogMessage(text);
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		Connection.handleChatMessage(text);
	}
}
