package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.common.rmi.IHandshake;
import it.polimi.ingsw.lim.common.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Handshake extends UnicastRemoteObject implements IHandshake
{
	private final transient List<ClientSession> clientSessions = new ArrayList<>();

	public Handshake() throws RemoteException
	{
	}

	@Override
	public IClientSession sendLogin(String name, String version, IServerSession serverSession) throws RemoteException
	{
		String trimmedName = name.replaceAll("^\\s+|\\s+$", "");
		int connectionId = Server.getInstance().getConnectionId();
		if (!version.equals(CommonUtils.VERSION)) {
			serverSession.sendLogMessage("Client version not compatible with the Server.");
			try {
				Utils.displayToLog("RMI Connection refused from: " + getClientHost() + " - " + connectionId);
			} catch (ServerNotActiveException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			return null;
		}
		if (!trimmedName.matches("^[\\w\\-]{4,16}$")) {
			try {
				Utils.displayToLog("RMI Connection refused from: " + getClientHost() + " - " + connectionId);
			} catch (ServerNotActiveException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
			return null;
		}
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getName().equals(trimmedName)) {
				serverSession.sendLogMessage("Client name is already taken.");
				try {
					Utils.displayToLog("RMI Connection refused from: " + getClientHost() + " - " + connectionId);
				} catch (ServerNotActiveException exception) {
					Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
				return null;
			}
		}
		serverSession.sendLogMessage("Connected to Server.");
		try {
			Utils.displayToLog("RMI Connection accepted from: " + getClientHost() + " - " + connectionId);
		} catch (ServerNotActiveException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		ConnectionRMI connectionRmi = new ConnectionRMI(connectionId, trimmedName, serverSession);
		Server.getInstance().getConnections().add(connectionRmi);
		ClientSession clientSession = new ClientSession(connectionRmi);
		this.clientSessions.add(clientSession);
		return clientSession;
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof Handshake && this.clientSessions.equals(((Handshake) object).getClientSessions());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.getClientSessions());
	}

	public List<ClientSession> getClientSessions()
	{
		return this.clientSessions;
	}
}
