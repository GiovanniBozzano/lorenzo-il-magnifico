package it.polimi.ingsw.lim.client.network.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerSession extends UnicastRemoteObject implements IServerSession
{
	ServerSession() throws RemoteException
	{
		super();
	}

	@Override
	public void sendDisconnect() throws RemoteException
	{
		Client.getInstance().disconnect(false, false);
	}

	@Override
	public void sendHeartbeat() throws RemoteException
	{
		// This method is empty because it is only called to check the connection.
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
	public void sendRoomTimer(int timer) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleRoomTimer(timer);
	}

	@Override
	public void sendGameStarted(RoomInformations roomInformations) throws RemoteException
	{
		Client.getInstance().getConnectionHandler().handleGameStarted(roomInformations);
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
