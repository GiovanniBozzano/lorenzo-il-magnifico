package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Authentication extends UnicastRemoteObject implements IAuthentication
{
	private final transient List<ClientSession> clientSessions = new ArrayList<>();

	public Authentication() throws RemoteException
	{
		super();
	}

	@Override
	public IClientSession sendLogin(String username, String password, String version, IServerSession serverSession) throws RemoteException, AuthenticationFailedException
	{
		String trimmedUsername = username.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		Utils.checkLogin(version, trimmedUsername, password);
		try {
			Utils.displayToLog("RMI Player " + UnicastRemoteObject.getClientHost() + " logged in as: " + trimmedUsername);
		} catch (ServerNotActiveException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		ConnectionRMI connectionRmi = new ConnectionRMI(Server.getInstance().getConnectionId(), trimmedUsername, serverSession);
		Server.getInstance().getConnections().add(connectionRmi);
		ClientSession clientSession = new ClientSession(connectionRmi);
		this.clientSessions.add(clientSession);
		return clientSession;
	}

	@Override
	public IClientSession sendRegistration(String username, String password, String version, IServerSession serverSession) throws RemoteException, AuthenticationFailedException
	{
		String trimmedUsername = username.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		Utils.checkRegistration(version, trimmedUsername, password);
		try {
			Utils.displayToLog("RMI Player " + UnicastRemoteObject.getClientHost() + " registerd as: " + trimmedUsername);
		} catch (ServerNotActiveException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		ConnectionRMI connectionRmi = new ConnectionRMI(Server.getInstance().getConnectionId(), trimmedUsername, serverSession);
		Server.getInstance().getConnections().add(connectionRmi);
		ClientSession clientSession = new ClientSession(connectionRmi);
		this.clientSessions.add(clientSession);
		return clientSession;
	}

	@Override
	public void sendHeartbeat() throws RemoteException
	{
		// This method is empty because it is only called to check the connection.
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) {
			return true;
		}
		if (o == null || this.getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		Authentication that = (Authentication) o;
		return Objects.equals(this.clientSessions, that.clientSessions);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.clientSessions);
	}

	List<ClientSession> getClientSessions()
	{
		return this.clientSessions;
	}
}
