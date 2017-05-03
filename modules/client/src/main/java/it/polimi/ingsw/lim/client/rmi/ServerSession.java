package it.polimi.ingsw.lim.client.rmi;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.gui.ControllerLobby;
import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;
import java.util.List;
import java.util.logging.Level;

public class ServerSession extends UnicastRemoteObject implements IServerSession, Unreferenced
{
	public ServerSession() throws RemoteException
	{
	}

	@Override
	public void unreferenced()
	{
		Client.getInstance().disconnect();
	}

	@Override
	public void disconnect() throws RemoteException
	{
		Client.getInstance().disconnect();
	}

	@Override
	public void sendRoomList(List<RoomInformations> rooms) throws RemoteException
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerLobby)) {
			return;
		}
		for (RoomInformations roomInformations : rooms) {
			((ControllerLobby) Client.getInstance().getWindowInformations().getController()).getRoomsListView().getItems().add(roomInformations);
		}
	}

	@Override
	public void sendRoomEntryConfirmation(RoomInformations roomInformations) throws RemoteException
	{
	}

	@Override
	public void sendRoomCreationConfirmation(RoomInformations roomInformations) throws RemoteException
	{
	}

	@Override
	public void sendLogMessage(String text) throws RemoteException
	{
		Client.getLogger().log(Level.INFO, text);
	}

	@Override
	public void sendChatMessage(String text) throws RemoteException
	{
		Client.getLogger().log(Level.INFO, text);
	}
}
