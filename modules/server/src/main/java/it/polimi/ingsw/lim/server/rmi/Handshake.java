package it.polimi.ingsw.lim.server.rmi;

import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.common.rmi.IHandshake;
import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.Constants;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.IConnection;
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
		if (!version.equals(Constants.VERSION)) {
			serverSession.sendLogMessage("Client version not compatible with the Server.");
			try {
				Server.getInstance().displayToLog("RMI Connection refused from: " + getClientHost() + " - " + connectionId);
			} catch (ServerNotActiveException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			return null;
		}
		if (name.length() == 0) {
			serverSession.sendLogMessage("Client name is empty.");
			try {
				Server.getInstance().displayToLog("RMI Connection refused from: " + getClientHost() + " - " + connectionId);
			} catch (ServerNotActiveException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			return null;
		}
		for (IConnection connection : Server.getInstance().getConnections()) {
			if (connection.getName().equals(name)) {
				serverSession.sendLogMessage("Client name is already taken.");
				try {
					Server.getInstance().displayToLog("RMI Connection refused from: " + getClientHost() + " - " + connectionId);
				} catch (ServerNotActiveException exception) {
					Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
				return null;
			}
		}
		serverSession.sendLogMessage("Connected to Server.");
		try {
			Server.getInstance().displayToLog("RMI Connection accepted from: " + getClientHost() + " - " + connectionId);
		} catch (ServerNotActiveException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		ConnectionRMI connectionRmi = new ConnectionRMI(connectionId, name, serverSession);
		Server.getInstance().getConnections().add(connectionRmi);
		return new ClientSession(connectionRmi);
	}
}
