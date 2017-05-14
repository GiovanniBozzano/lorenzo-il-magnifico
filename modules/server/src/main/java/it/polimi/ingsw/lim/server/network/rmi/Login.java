package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
import it.polimi.ingsw.lim.common.network.rmi.ILogin;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.database.Database;
import it.polimi.ingsw.lim.server.enums.QueryRead;
import it.polimi.ingsw.lim.server.enums.QueryValueType;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.QueryArgument;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public IClientSession sendLogin(String username, String password, String version, IServerSession serverSession) throws RemoteException
	{
		String trimmedUsername = username.replaceAll("^\\s+|\\s+$", "");
		int connectionId = Server.getInstance().getConnectionId();
		if (!version.equals(CommonUtils.VERSION)) {
			serverSession.sendLogMessage("Client version not compatible with the Server.");
			return null;
		}
		if (!trimmedUsername.matches("^[\\w\\-]{4,16}$")) {
			serverSession.sendLogMessage("Incorrect username.");
			return null;
		}
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getUsername().equals(trimmedUsername)) {
				serverSession.sendLogMessage("Already logged in.");
				return null;
			}
		}
		List<QueryArgument> queryArguments = new ArrayList<>();
		queryArguments.add(new QueryArgument(QueryValueType.STRING, trimmedUsername));
		try (ResultSet resultSet = Utils.sqlRead(QueryRead.GET_SALT_AND_PASSWORD_WITH_USERNAME, queryArguments)) {
			if (!resultSet.next()) {
				serverSession.sendLogMessage("Incorrect username.");
				return null;
			}
			if (!Utils.sha1Encrypt(password, resultSet.getBytes(Database.TABLE_PLAYERS_COLUMN_SALT)).equals(resultSet.getString(Database.TABLE_PLAYERS_COLUMN_PASSWORD))) {
				serverSession.sendLogMessage("Incorrect password.");
				return null;
			}
		} catch (SQLException | NoSuchAlgorithmException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			serverSession.sendLogMessage("Server error.");
			return null;
		}
		try {
			Utils.displayToLog("RMI Connection accepted from: " + getClientHost() + " - " + connectionId);
		} catch (ServerNotActiveException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		ConnectionRMI connectionRmi = new ConnectionRMI(connectionId, trimmedUsername, serverSession);
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

	List<ClientSession> getClientSessions()
	{
		return this.clientSessions;
	}
}
