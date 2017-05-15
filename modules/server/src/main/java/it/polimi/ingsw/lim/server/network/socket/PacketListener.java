package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketLogin;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRegistration;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomCreation;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomEntry;
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

import java.io.IOException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private final ConnectionSocket connectionSocket;
	private volatile boolean keepGoing = true;

	PacketListener(ConnectionSocket connectionSocket)
	{
		this.connectionSocket = connectionSocket;
	}

	@Override
	public void run()
	{
		if (!this.waitLogin()) {
			return;
		}
		this.connectionSocket.sendAuthenticationConfirmation();
		while (this.keepGoing) {
			Packet packet;
			try {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				Server.getLogger().log(Level.INFO, "Socket Client " + this.connectionSocket.getId() + (this.connectionSocket.getUsername() != null ? " : " + this.connectionSocket.getUsername() : "") + " disconnected.", exception);
				if (!this.keepGoing) {
					return;
				}
				this.connectionSocket.disconnect(false, null);
				return;
			}
			if (packet == null) {
				this.connectionSocket.disconnect(false, null);
				return;
			}
			switch (packet.getPacketType()) {
				case ROOM_LIST_REQUEST:
					this.connectionSocket.handleRequestRoomList();
					break;
				case ROOM_CREATION:
					this.connectionSocket.handleRoomCreation(((PacketRoomCreation) packet).getName());
					break;
				case ROOM_ENTRY:
					this.connectionSocket.handleRoomEntry(((PacketRoomEntry) packet).getId());
					break;
				case ROOM_EXIT:
					this.connectionSocket.handleRoomExit();
					break;
				case CHAT_MESSAGE:
					this.connectionSocket.handleChatMessage(((PacketChatMessage) packet).getText());
					break;
				default:
			}
		}
	}

	private boolean waitLogin()
	{
		while (this.connectionSocket.getUsername() == null) {
			Packet packet;
			try {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				Server.getLogger().log(Level.INFO, "Socket Client " + this.connectionSocket.getSocket().getInetAddress().getHostAddress() + " : " + this.connectionSocket.getId() + " disconnected.", exception);
				if (!this.keepGoing) {
					return false;
				}
				this.connectionSocket.disconnect(false, null);
				return false;
			}
			if (packet.getPacketType() == PacketType.LOGIN) {
				this.login((PacketLogin) packet);
			} else if (packet.getPacketType() == PacketType.REGISTRATION) {
				this.register((PacketRegistration) packet);
			}
		}
		return true;
	}

	private void login(PacketLogin packetLogin)
	{
		if (!packetLogin.getVersion().equals(CommonUtils.VERSION)) {
			this.connectionSocket.sendAuthenticationFailure("Client version not compatible with the Server.");
			return;
		}
		String trimmedUsername = packetLogin.getUsername().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (!trimmedUsername.matches(CommonUtils.REGEX_USERNAME)) {
			this.connectionSocket.sendAuthenticationFailure("Incorrect username.");
			return;
		}
		String decryptedPassword = CommonUtils.decrypt(packetLogin.getPassword());
		if (decryptedPassword == null || decryptedPassword.length() < 1) {
			this.connectionSocket.sendAuthenticationFailure("Incorrect password.");
			return;
		}
		boolean alreadyLoggedIn = false;
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getUsername() != null && connection.getUsername().equals(trimmedUsername)) {
				this.connectionSocket.sendAuthenticationFailure("Already logged in.");
				alreadyLoggedIn = true;
				break;
			}
		}
		if (alreadyLoggedIn) {
			return;
		}
		List<QueryArgument> queryArguments = new ArrayList<>();
		queryArguments.add(new QueryArgument(QueryValueType.STRING, packetLogin.getUsername().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "")));
		try (ResultSet resultSet = Utils.sqlRead(QueryRead.GET_PASSWORD_AND_SALT_WITH_USERNAME, queryArguments)) {
			if (!resultSet.next()) {
				this.connectionSocket.sendAuthenticationFailure("Incorrect username.");
				resultSet.getStatement().close();
				return;
			}
			if (!Utils.sha512Encrypt(decryptedPassword, resultSet.getBytes(Database.TABLE_PLAYERS_COLUMN_SALT)).equals(resultSet.getString(Database.TABLE_PLAYERS_COLUMN_PASSWORD))) {
				this.connectionSocket.sendAuthenticationFailure("Incorrect password.");
				resultSet.getStatement().close();
				return;
			}
			resultSet.getStatement().close();
		} catch (SQLException | NoSuchAlgorithmException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			this.connectionSocket.sendAuthenticationFailure("Server error.");
			return;
		}
		this.connectionSocket.setUsername(trimmedUsername);
		Utils.displayToLog("Socket Player " + this.connectionSocket.getSocket().getInetAddress().getHostAddress() + " : " + this.connectionSocket.getId() + " logged in as: " + trimmedUsername);
	}

	private void register(PacketRegistration packetRegistration)
	{
		if (!packetRegistration.getVersion().equals(CommonUtils.VERSION)) {
			this.connectionSocket.sendAuthenticationFailure("Client version not compatible with the Server.");
			return;
		}
		String trimmedUsername = packetRegistration.getUsername().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (!trimmedUsername.matches(CommonUtils.REGEX_USERNAME)) {
			this.connectionSocket.sendAuthenticationFailure("Incorrect username.");
			return;
		}
		String decryptedPassword = CommonUtils.decrypt(packetRegistration.getPassword());
		if (decryptedPassword == null || decryptedPassword.length() < 1) {
			this.connectionSocket.sendAuthenticationFailure("Incorrect password.");
			return;
		}
		boolean alreadyLoggedIn = false;
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getUsername() != null && connection.getUsername().equals(trimmedUsername)) {
				this.connectionSocket.sendAuthenticationFailure("Already logged in.");
				alreadyLoggedIn = true;
				break;
			}
		}
		if (alreadyLoggedIn) {
			return;
		}
		List<QueryArgument> queryArguments = new ArrayList<>();
		queryArguments.add(new QueryArgument(QueryValueType.STRING, packetRegistration.getUsername().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "")));
		try (ResultSet resultSet = Utils.sqlRead(QueryRead.CHECK_EXISTING_USERNAME, queryArguments)) {
			if (resultSet.next()) {
				this.connectionSocket.sendAuthenticationFailure("Username already taken.");
				resultSet.getStatement().close();
				return;
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
			this.connectionSocket.sendAuthenticationFailure("Server error.");
			return;
		}
		this.connectionSocket.setUsername(trimmedUsername);
		Utils.displayToLog("Socket Player " + this.connectionSocket.getSocket().getInetAddress().getHostAddress() + " : " + this.connectionSocket.getId() + " registerd as: " + trimmedUsername);
	}

	synchronized void end()
	{
		this.keepGoing = false;
	}
}
