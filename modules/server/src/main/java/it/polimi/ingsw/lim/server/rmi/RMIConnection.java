package it.polimi.ingsw.lim.server.rmi;

import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.IConnection;
import it.polimi.ingsw.lim.server.Server;

import java.rmi.RemoteException;
import java.util.logging.Level;

public class RMIConnection implements IConnection
{
	private final int id;
	private final String name;
	private final IServerSession serverSession;

	RMIConnection(int id, String name, IServerSession serverSession)
	{
		this.id = id;
		this.name = name;
		this.serverSession = serverSession;
	}

	@Override
	public void disconnect()
	{
		Server.getInstance().getConnections().remove(this);
		try {
			this.serverSession.disconnect();
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
		try {
			this.serverSession.sendChatMessage(text);
		} catch (RemoteException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void handleChatMessage(String text)
	{
		for (IConnection otherConnection : Server.getInstance().getConnections()) {
			if (otherConnection != this) {
				otherConnection.sendChatMessage(this.name + ": " + text);
			}
		}
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public IServerSession getServerSession()
	{
		return this.serverSession;
	}
}
