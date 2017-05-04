package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;

public class ConnectionRMI extends Connection
{
	private IServerSession serverSession;

	ConnectionRMI(int id, String name, IServerSession serverSession)
	{
		super(id, name);
		this.serverSession = serverSession;
	}

	@Override
	public void disconnect(boolean kick)
	{
		if (this.serverSession == null) {
			return;
		}
		super.disconnect(kick);
		for (ClientSession clientSession : Server.getInstance().getConnectionListener().getHandshake().getClientSessions()) {
			if (clientSession.getConnectionRMI() == this) {
				try {
					UnicastRemoteObject.unexportObject(clientSession, true);
				} catch (NoSuchObjectException exception) {
					Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
				break;
			}
		}
		this.serverSession = null;
		System.gc();
		System.runFinalization();
		Utils.displayToLog("RMI Client: " + this.getId() + ":" + this.getName() + " disconnected.");
	}

	@Override
	public void sendRoomList(List<RoomInformations> rooms)
	{
		try {
			this.serverSession.sendRoomList(rooms);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendRoomCreationFailure()
	{
		try {
			this.serverSession.sendRoomCreationFailure();
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendRoomEntryConfirmation(RoomInformations roomInformations)
	{
		try {
			this.serverSession.sendRoomEntryConfirmation(roomInformations);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendRoomEntryOther(String name)
	{
		try {
			this.serverSession.sendRoomEntryOther(name);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendRoomExitOther(String name)
	{
		try {
			this.serverSession.sendRoomExitOther(name);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendLogMessage(String text)
	{
		try {
			this.serverSession.sendLogMessage(text);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendChatMessage(String text)
	{
		String trimmedText = text.replaceAll("^\\s+|\\s+$", "");
		try {
			this.serverSession.sendChatMessage(trimmedText);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public IServerSession getServerSession()
	{
		return this.serverSession;
	}
}
