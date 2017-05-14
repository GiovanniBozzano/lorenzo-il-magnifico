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
	 * Tries to login with username, password and Client version.
	 * @param username the username.
	 * @param password the password.
	 */
	public static synchronized void sendLogin(String username, String password)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandlerRMI().setClientSession(Client.getInstance().getConnectionHandlerRMI().getLogin().sendLogin(username, CommonUtils.encrypt(password), CommonUtils.VERSION, Client.getInstance().getConnectionHandlerRMI().getServerSession()));
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
				return;
			}
			if (Client.getInstance().getConnectionHandlerRMI().getClientSession() == null) {
				Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
				return;
			}
			Client.getInstance().setUsername(username);
			CommonUtils.setNewWindow(Utils.SCENE_LOBBY, null, null, new Thread(Connection::sendRequestRoomList));
		} else {
			new PacketLogin(username, CommonUtils.encrypt(password)).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}

	/**
	 * Tries to register with username, password and Client version.
	 * @param username the username.
	 * @param password the password.
	 */
	public static synchronized void sendRegistration(String username, String password)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandlerRMI().setClientSession(Client.getInstance().getConnectionHandlerRMI().getLogin().sendRegistration(username, CommonUtils.encrypt(password), CommonUtils.VERSION, Client.getInstance().getConnectionHandlerRMI().getServerSession()));
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
				return;
			}
			if (Client.getInstance().getConnectionHandlerRMI().getClientSession() == null) {
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

	public static synchronized void sendHeartbeat()
	{
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				if (Client.getInstance().getConnectionHandlerRMI().getClientSession() != null) {
					Client.getInstance().getConnectionHandlerRMI().getClientSession().sendHeartbeat();
				} else {
					Client.getInstance().getConnectionHandlerRMI().getLogin().sendHeartbeat();
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
				Client.getInstance().getConnectionHandlerRMI().getClientSession().sendRequestRoomList();
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
	 * @param name the name of the newly created room.
	 */
	public static synchronized void sendRoomCreation(String name)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandlerRMI().getClientSession().sendRoomCreation(name);
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
				Client.getInstance().getConnectionHandlerRMI().getClientSession().sendRoomEntry(id);
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
				Client.getInstance().getConnectionHandlerRMI().getClientSession().sendRoomExit();
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
				Client.getInstance().getConnectionHandlerRMI().getClientSession().sendChatMessage(text);
			} catch (RemoteException exception) {
				Client.getLogger().log(Level.INFO, LogFormatter.RMI_ERROR, exception);
				Client.getInstance().disconnect(false, true);
			}
		} else {
			new PacketChatMessage(text).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}

	public static void handleAuthenticationConfirmation()
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerAuthentication)) {
			return;
		}
		CommonUtils.setNewWindow(Utils.SCENE_LOBBY, null, null, new Thread(Connection::sendRequestRoomList));
	}

	public static void handleAuthenticationFailure(String text)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerAuthentication)) {
			return;
		}
		Client.getLogger().log(Level.INFO, text);
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
	}

	public static void handleDisconnectionLogMessage(String text)
	{
		Client.getLogger().log(Level.INFO, text);
		Connection.sendDisconnectionAcknowledgement();
	}

	public static void handleRoomList(List<RoomInformations> rooms)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerLobby)) {
			return;
		}
		Platform.runLater(() -> {
			((ControllerLobby) Client.getInstance().getWindowInformations().getController()).getRoomsListView().getItems().clear();
			((ControllerLobby) Client.getInstance().getWindowInformations().getController()).getPlayerListView().getItems().clear();
			for (RoomInformations roomInformations : rooms) {
				((ControllerLobby) Client.getInstance().getWindowInformations().getController()).getRoomsListView().getItems().add(roomInformations);
			}
		});
	}

	public static void handleRoomCreationFailure()
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerRoomCreation)) {
			return;
		}
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
		Platform.runLater(() -> {
			((ControllerRoomCreation) Client.getInstance().getWindowInformations().getController()).getNameTextField().setPromptText("Name already taken");
			((ControllerRoomCreation) Client.getInstance().getWindowInformations().getController()).getNameTextField().clear();
		});
	}

	public static void handleRoomEntryConfirmation(RoomInformations roomInformations)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerRoomCreation) && !(Client.getInstance().getWindowInformations().getController() instanceof ControllerLobby)) {
			return;
		}
		if (Client.getInstance().getWindowInformations().getController() instanceof ControllerRoomCreation) {
			Platform.runLater(((ControllerRoomCreation) Client.getInstance().getWindowInformations().getController())::close);
		}
		CommonUtils.setNewWindow("/fxml/SceneRoom.fxml", null, new Thread(() -> Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).setRoomInformations(roomInformations.getName(), roomInformations.getPlayerNames()))), null);
	}

	public static void handleRoomEntryOther(String name)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerRoom)) {
			return;
		}
		Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).getPlayersListView().getItems().add(name));
	}

	public static void handleRoomExitOther(String name)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerRoom)) {
			return;
		}
		Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).getPlayersListView().getItems().remove(name));
	}

	public static void handleChatMessage(String text)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerRoom)) {
			return;
		}
		if (((ControllerRoom) Client.getInstance().getWindowInformations().getController()).getChatTextArea().getText().length() < 1) {
			Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).getChatTextArea().appendText(text));
		} else {
			Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).getChatTextArea().appendText("\n" + text));
		}
	}

	public static void handleLogMessage(String text)
	{
		Client.getLogger().log(Level.INFO, text);
	}
}
