package it.polimi.ingsw.lim.client.network;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.gui.ControllerAuthentication;
import it.polimi.ingsw.lim.client.gui.ControllerLobby;
import it.polimi.ingsw.lim.client.gui.ControllerRoom;
import it.polimi.ingsw.lim.client.gui.ControllerRoomCreation;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import javafx.application.Platform;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;

public abstract class ConnectionHandler extends Thread
{
	private final ScheduledExecutorService heartbeat = Executors.newSingleThreadScheduledExecutor();

	@Override
	public abstract void run();

	public void disconnect(boolean notifyServer)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getLogger().log(Level.INFO, LogFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		this.getHeartbeat().shutdownNow();
	}

	/**
	 * Tries to login with username, password and Client version.
	 *
	 * @param username the username.
	 * @param password the password.
	 */
	public synchronized void sendLogin(String username, String password)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
	}

	/**
	 * Tries to register with username, password and Client version.
	 *
	 * @param username the username.
	 * @param password the password.
	 */
	public synchronized void sendRegistration(String username, String password)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
	}

	/**
	 * Tries to send an heartbeat to check the connection status.
	 */
	public abstract void sendHeartbeat();

	/**
	 * Sends a request to the Server asking for a room list refresh.
	 */
	public abstract void sendRequestRoomList();

	/**
	 * Sends to the Server the newly created room to be verified.
	 *
	 * @param name the name of the newly created room.
	 */
	public synchronized void sendRoomCreation(String name)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
	}

	public synchronized void sendRoomEntry(int id)
	{
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(true);
	}

	public synchronized void sendRoomExit()
	{
		CommonUtils.setNewWindow(Utils.SCENE_LOBBY, null, null, new Thread(this::sendRequestRoomList));
	}

	public abstract void sendChatMessage(String text);

	public void handleAuthenticationConfirmation(String username)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerAuthentication)) {
			return;
		}
		Client.getInstance().setUsername(username);
		CommonUtils.setNewWindow(Utils.SCENE_LOBBY, null, null, new Thread(this::sendRequestRoomList));
	}

	public void handleAuthenticationFailure(String text)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerAuthentication)) {
			return;
		}
		Client.getLogger().log(Level.INFO, text);
		Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
	}

	public void handleRoomList(List<RoomInformations> rooms)
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

	public void handleRoomCreationFailure()
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

	public void handleRoomEntryConfirmation(RoomInformations roomInformations)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerRoomCreation) && !(Client.getInstance().getWindowInformations().getController() instanceof ControllerLobby)) {
			return;
		}
		if (Client.getInstance().getWindowInformations().getController() instanceof ControllerRoomCreation) {
			Platform.runLater(((ControllerRoomCreation) Client.getInstance().getWindowInformations().getController())::close);
		}
		CommonUtils.setNewWindow("/fxml/SceneRoom.fxml", null, new Thread(() -> Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).setRoomInformations(roomInformations.getName(), roomInformations.getPlayerNames()))), null);
	}

	public void handleRoomEntryOther(String name)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerRoom)) {
			return;
		}
		Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).getPlayersListView().getItems().add(name));
	}

	public void handleRoomExitOther(String name)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerRoom)) {
			return;
		}
		Platform.runLater(() -> ((ControllerRoom) Client.getInstance().getWindowInformations().getController()).getPlayersListView().getItems().remove(name));
	}

	public void handleChatMessage(String text)
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

	public void handleLogMessage(String text)
	{
		Client.getLogger().log(Level.INFO, text);
	}

	protected ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}
}
