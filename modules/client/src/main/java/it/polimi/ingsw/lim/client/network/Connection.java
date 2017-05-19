package it.polimi.ingsw.lim.client.network;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.gui.ControllerAuthentication;
import it.polimi.ingsw.lim.client.gui.ControllerLobby;
import it.polimi.ingsw.lim.client.gui.ControllerRoom;
import it.polimi.ingsw.lim.client.gui.ControllerRoomCreation;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketLogin;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRegistration;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomCreation;
import it.polimi.ingsw.lim.common.network.socket.packets.client.PacketRoomEntry;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;

public class Connection
{
	private Connection()
	{
	}

	/**
	 * Tries to register with username, password and Client version.
	 *
	 * @param username the username.
	 * @param password the password.
	 */
	public static synchronized void sendRegistration(String username, String password)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandler().setClientSession(Client.getInstance().getConnectionHandler().getLogin().sendRegistration(username, CommonUtils.encrypt(password), CommonUtils.VERSION, Client.getInstance().getConnectionHandler().getServerSession()));
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
				return;
			}
			if (Client.getInstance().getConnectionHandler().getClientSession() == null) {
				Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
				return;
			}
			Client.getInstance().setUsername(username);
			CommonUtils.setNewWindow(Utils.SCENE_LOBBY, null, null, new Thread(Connection::sendRequestRoomList));
		} else {
			new PacketRegistration(username, CommonUtils.encrypt(password)).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}

	private static synchronized void sendDisconnectionAcknowledgement()
	{
		if (Client.getInstance().getConnectionType() == ConnectionType.SOCKET) {
			new Packet(PacketType.DISCONNECTION_ACKNOWLEDGEMENT).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}

	/**
	 * Tries to send an heartbeat to check the connection status.
	 */
	public static synchronized void sendHeartbeat()
	{
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				if (Client.getInstance().getConnectionHandler().getClientSession() != null) {
					Client.getInstance().getConnectionHandler().getClientSession().sendHeartbeat();
				} else {
					Client.getInstance().getConnectionHandler().getLogin().sendHeartbeat();
				}
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
			}
		} else {
			new Packet(PacketType.HEARTBEAT).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}

	/**
	 * Sends a request to the Server asking for a room list refresh.
	 */
	public static synchronized void sendRequestRoomList()
	{
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandler().getClientSession().sendRoomListRequest();
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
			}
		} else {
			new Packet(PacketType.ROOM_LIST_REQUEST).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}

	/**
	 * Sends to the Server the newly created room to be verified.
	 *
	 * @param name the name of the newly created room.
	 */
	public static synchronized void sendRoomCreation(String name)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandler().getClientSession().sendRoomCreation(name);
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
			}
		} else {
			new PacketRoomCreation(name).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}

	public static synchronized void sendRoomEntry(int id)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandler().getClientSession().sendRoomEntry(id);
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
			}
		} else {
			new PacketRoomEntry(id).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}

	public static synchronized void sendRoomExit()
	{
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandler().getClientSession().sendRoomExit();
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
			}
		} else {
			new Packet(PacketType.ROOM_EXIT).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
		CommonUtils.setNewWindow(Utils.SCENE_LOBBY, null, null, new Thread(Connection::sendRequestRoomList));
	}

	public static synchronized void sendChatMessage(String text)
	{
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandler().getClientSession().sendChatMessage(text);
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
			}
		} else {
			new PacketChatMessage(text).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}


}
