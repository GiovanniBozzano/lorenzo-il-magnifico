package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;
import java.util.List;

public class ServerSession extends UnicastRemoteObject implements IServerSession, Unreferenced
{
	public ServerSession() throws RemoteException
	{
	}

	@Override
	public void unreferenced()
	{
		if (Client.getInstance().isConnected()) {
			Client.getInstance().disconnect(false);
		}
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
