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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionRMI extends Connection
{
	private final IServerSession serverSession;
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();

	ConnectionRMI(int id, String name, IServerSession serverSession)
	{
		super(id, name);
		this.serverSession = serverSession;
		this.heartbeat.scheduleAtFixedRate(this::sendHeartbeat, 0L, 3000L, TimeUnit.MILLISECONDS);
	}

	@Override
	public void disconnect(boolean isBeingKicked)
	{
		super.disconnect(isBeingKicked);
		this.heartbeat.shutdown();
		for (ClientSession clientSession : Server.getInstance().getConnectionHandler().getHandshake().getClientSessions()) {
			if (clientSession.getConnectionRMI() == this) {
				try {
					UnicastRemoteObject.unexportObject(clientSession, true);
				} catch (NoSuchObjectException exception) {
					Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
				break;
			}
		}
		if (isBeingKicked) {
			try {
				this.serverSession.sendDisconnect();
			} catch (RemoteException exception) {
				Server.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			}
		}
		Utils.displayToLog("RMI Client: " + this.getId() + ":" + this.getName() + " disconnected.");
	}

	private void sendHeartbeat()
	{
		try {
			this.serverSession.sendHeartbeat();
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			this.disconnect(false);
		}
	}

	@Override
	public void sendRoomList(List<RoomInformations> rooms)
	{
		try {
			this.serverSession.sendRoomList(rooms);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			this.disconnect(false);
		}
	}

	@Override
	public void sendRoomCreationFailure()
	{
		try {
			this.serverSession.sendRoomCreationFailure();
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			this.disconnect(false);
		}
	}

	@Override
	public void sendRoomEntryConfirmation(RoomInformations roomInformations)
	{
		try {
			this.serverSession.sendRoomEntryConfirmation(roomInformations);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			this.disconnect(false);
		}
	}

	@Override
	public void sendRoomEntryOther(String name)
	{
		try {
			this.serverSession.sendRoomEntryOther(name);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			this.disconnect(false);
		}
	}

	@Override
	public void sendRoomExitOther(String name)
	{
		try {
			this.serverSession.sendRoomExitOther(name);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			this.disconnect(false);
		}
	}

	@Override
	public void sendLogMessage(String text)
	{
		try {
			this.serverSession.sendLogMessage(text);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			this.disconnect(false);
		}
	}

	@Override
	public void sendChatMessage(String text)
	{
		try {
			this.serverSession.sendChatMessage(text);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
			this.disconnect(false);
		}
	}
}
