package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
import it.polimi.ingsw.lim.common.network.rmi.ILogin;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
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

public class Login extends UnicastRemoteObject implements ILogin
{
	private final transient List<ClientSession> clientSessions = new ArrayList<>();

	public Login() throws RemoteException
	{
		super();
	}

	@Override
	public IClientSession sendLogin(String name, String password, String version, IServerSession serverSession) throws RemoteException
	{
		String trimmedName = name.replaceAll("^\\s+|\\s+$", "");
		int connectionId = Server.getInstance().getConnectionId();
		if (!version.equals(CommonUtils.VERSION)) {
			serverSession.sendLogMessage("Client version not compatible with the Server.");
			return null;
		}
		if (!trimmedName.matches("^[\\w\\-]{4,16}$")) {
			serverSession.sendLogMessage("Incorrect username.");
			return null;
		}
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getUsername().equals(trimmedName)) {
				serverSession.sendLogMessage("Already logged in.");
				return null;
			}
		}
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
		return object instanceof Login && this.clientSessions.equals(((Login) object).getClientSessions());
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
