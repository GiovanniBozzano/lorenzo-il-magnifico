package it.polimi.ingsw.lim.client.network;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.gui.ControllerLobby;
import it.polimi.ingsw.lim.client.gui.ControllerRoom;
import it.polimi.ingsw.lim.client.gui.ControllerRoomCreation;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.socket.packets.client.PacketHandshake;
import it.polimi.ingsw.lim.common.socket.packets.client.PacketRoomCreation;
import it.polimi.ingsw.lim.common.socket.packets.client.PacketRoomEntry;
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
	 * Sends an handshake packet to the Server with chosen name and Client version.
	 */
	public static void sendHandshake()
	{
		if (Client.getInstance().getConnectionType() == ConnectionType.SOCKET) {
			new PacketHandshake(Client.getInstance().getName()).send(Client.getInstance().getConnectionHandlerSocket().getOut());
		}
	}

	public static void sendHeartbeat()
	{
		if (Client.getInstance().getConnectionType() == ConnectionType.RMI) {
			try {
				Client.getInstance().getConnectionHandlerRMI().getClientSession().sendHeartbeat();
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
	public static void sendRequestRoomList()
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
	public static void sendRoomCreation(String name)
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

	public static void sendRoomEntry(int id)
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

	public static void sendRoomExit()
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
		CommonUtils.setNewWindow("/fxml/SceneLobby.fxml", null, null, new Thread(Connection::sendRequestRoomList));
	}

	public static void sendChatMessage(String text)
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

	public static void handleHandshakeConfirmation()
	{
		CommonUtils.setNewWindow("/fxml/SceneLobby.fxml", null, null, new Thread(Connection::sendRequestRoomList));
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
		Platform.runLater(() -> {
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			((ControllerRoomCreation) Client.getInstance().getWindowInformations().getController()).getNameTextField().setPromptText("Name already taken");
			((ControllerRoomCreation) Client.getInstance().getWindowInformations().getController()).getNameTextField().clear();
		});
	}

	public static void handleRoomEntryConfirmation(RoomInformations roomInformations)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerRoomCreation) && !(Client.getInstance().getWindowInformations().getController() instanceof ControllerLobby)) {
			return;
		}
		Platform.runLater(() -> {
			if (Client.getInstance().getWindowInformations().getController() instanceof ControllerRoomCreation) {
				((ControllerRoomCreation) Client.getInstance().getWindowInformations().getController()).close();
			}
			CommonUtils.setNewWindow("/fxml/SceneRoom.fxml", null, new Thread(() -> Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).setRoomInformations(roomInformations.getName(), roomInformations.getPlayerNames()))), null);
		});
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
