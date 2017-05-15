package it.polimi.ingsw.lim.server.network.rmi;

import it.polimi.ingsw.lim.common.network.rmi.IAuthentication;
import it.polimi.ingsw.lim.common.network.rmi.IClientSession;
import it.polimi.ingsw.lim.common.network.rmi.IServerSession;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.database.Database;
import it.polimi.ingsw.lim.server.enums.QueryRead;
import it.polimi.ingsw.lim.server.enums.QueryValueType;
import it.polimi.ingsw.lim.server.enums.QueryWrite;
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

public class Authentication extends UnicastRemoteObject implements IAuthentication
{
	private final transient List<ClientSession> clientSessions = new ArrayList<>();

	public Authentication() throws RemoteException
	{
		super();
	}

	@Override
	public IClientSession sendLogin(String username, String password, String version, IServerSession serverSession) throws RemoteException
	{
		String trimmedUsername = username.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		int connectionId = Server.getInstance().getConnectionId();
		if (!version.equals(CommonUtils.VERSION)) {
			serverSession.sendLogMessage("Client version not compatible with the Server.");
			return null;
		}
		if (!trimmedUsername.matches(CommonUtils.REGEX_USERNAME)) {
			serverSession.sendLogMessage("Incorrect username.");
			return null;
		}
		String decryptedPassword = CommonUtils.decrypt(password);
		if (decryptedPassword == null || decryptedPassword.length() < 1) {
			serverSession.sendLogMessage("Incorrect password.");
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
		try (ResultSet resultSet = Utils.sqlRead(QueryRead.GET_PASSWORD_AND_SALT_WITH_USERNAME, queryArguments)) {
			if (!resultSet.next()) {
				serverSession.sendLogMessage("Incorrect username.");
				resultSet.getStatement().close();
				return null;
			}
			Server.getLogger().log(Level.INFO, decryptedPassword);
			if (!Utils.sha512Encrypt(decryptedPassword, resultSet.getBytes(Database.TABLE_PLAYERS_COLUMN_SALT)).equals(resultSet.getString(Database.TABLE_PLAYERS_COLUMN_PASSWORD))) {
				serverSession.sendLogMessage("Incorrect password.");
				resultSet.getStatement().close();
				return null;
			}
			resultSet.getStatement().close();
		} catch (SQLException | NoSuchAlgorithmException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			serverSession.sendLogMessage("Server error.");
			return null;
		}
		try {
			Utils.displayToLog("RMI Player " + UnicastRemoteObject.getClientHost() + " logged in as: " + trimmedUsername);
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
	public IClientSession sendRegistration(String username, String password, String version, IServerSession serverSession) throws RemoteException
	{
		String trimmedUsername = username.replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		int connectionId = Server.getInstance().getConnectionId();
		if (!version.equals(CommonUtils.VERSION)) {
			serverSession.sendLogMessage("Client version not compatible with the Server.");
			return null;
		}
		if (!trimmedUsername.matches(CommonUtils.REGEX_USERNAME)) {
			serverSession.sendLogMessage("Incorrect username.");
			return null;
		}
		String decryptedPassword = CommonUtils.decrypt(password);
		if (decryptedPassword == null || decryptedPassword.length() < 1) {
			serverSession.sendLogMessage("Incorrect password.");
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
		try (ResultSet resultSet = Utils.sqlRead(QueryRead.CHECK_EXISTING_USERNAME, queryArguments)) {
			if (resultSet.next()) {
				serverSession.sendLogMessage("Username already taken.");
				resultSet.getStatement().close();
				return null;
			}
			resultSet.getStatement().close();
			byte[] salt = Utils.getSalt();
			String encryptedPassword = Utils.sha512Encrypt(decryptedPassword, salt);
			queryArguments.clear();
			queryArguments.add(new QueryArgument(QueryValueType.STRING, trimmedUsername));
			queryArguments.add(new QueryArgument(QueryValueType.STRING, encryptedPassword));
			queryArguments.add(new QueryArgument(QueryValueType.BYTES, salt));
			Server.getInstance().getDatabaseSaver().execute(() -> {
				try {
					Utils.sqlWrite(QueryWrite.INSERT_USERNAME_AND_PASSWORD_AND_SALT, queryArguments);
				} catch (SQLException exception) {
					Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
			});
		} catch (SQLException | NoSuchAlgorithmException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			serverSession.sendLogMessage("Server error.");
			return null;
		}
		try {
			Utils.displayToLog("RMI Player " + UnicastRemoteObject.getClientHost() + " registerd as: " + trimmedUsername);
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
	public void sendHeartbeat() throws RemoteException
	{
		// This method is empty because it is only called to check the connection.
	}

	@Override
	public boolean equals(Object object)
	{
		return object instanceof Authentication && this.clientSessions.equals(((Authentication) object).getClientSessions());
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
