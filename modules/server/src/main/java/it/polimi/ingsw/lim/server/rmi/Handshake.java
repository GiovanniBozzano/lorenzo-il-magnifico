package it.polimi.ingsw.lim.server.rmi;

import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.common.rmi.IHandshake;
import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

public class Handshake extends UnicastRemoteObject implements IHandshake
{
	public Handshake() throws RemoteException
	{
	}

	@Override
	public IClientSession send(String name, String version, IServerSession serverSession) throws RemoteException
	{
		int connectionId = Server.getInstance().getConnectionId();
		try {
			Server.getInstance().displayToLog("RMI Connection accepted from: " + getClientHost() + " - " + connectionId, FontType.NORMAL);
		} catch (ServerNotActiveException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		RMIConnection rmiConnection = new RMIConnection(connectionId, name, serverSession);
		Server.getInstance().getConnections().add(rmiConnection);
		return new ClientSession(rmiConnection);
	}
}