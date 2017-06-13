package it.polimi.ingsw.lim.client.network;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.cli.CLIListenerClient;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.gui.ControllerRoom;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.application.Platform;

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
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		this.getHeartbeat().shutdownNow();
	}

	/**
	 * <p>Tries to send an heartbeat to check the connection status.
	 */
	public synchronized void sendHeartbeat()
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * <p>Tries to login with username, password and Client version, sending the
	 * desired {@link RoomType}.
	 *
	 * @param username the username.
	 * @param password the password.
	 * @param roomType the desired {@link RoomType}.
	 */
	public synchronized void sendLogin(String username, String password, RoomType roomType)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		WindowFactory.getInstance().getCurrentWindow().getController().setDisable(true);
	}

	/**
	 * <p>Tries to register with username, password and Client version, sending
	 * the desired {@link RoomType}.
	 *
	 * @param username the username.
	 * @param password the password.
	 * @param roomType the desired {@link RoomType}.
	 */
	public synchronized void sendRegistration(String username, String password, RoomType roomType)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		WindowFactory.getInstance().getCurrentWindow().getController().setDisable(true);
	}

	public synchronized void sendRoomTimerRequest()
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	public synchronized void sendChatMessage(String text)
	{
		try {
			this.join();
		} catch (InterruptedException exception) {
			Client.getDebugger().log(Level.INFO, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
	}

	public void handleRoomEntryOther(String name)
	{
		if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
		if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow().getController()).getPlayersListView().getItems().add(name));
		} else {
			Client.getLogger().log(Level.INFO, name + " connected");
		}
	}

	public void handleRoomExitOther(String name)
	{
		if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
		if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> {
				((ControllerRoom) WindowFactory.getInstance().getCurrentWindow().getController()).getPlayersListView().getItems().remove(name);
				if (((ControllerRoom) WindowFactory.getInstance().getCurrentWindow().getController()).getPlayersListView().getItems().size() < 2) {
					((ControllerRoom) WindowFactory.getInstance().getCurrentWindow().getController()).getTimerLabel().setText("Waiting for other players...");
				}
			});
		} else {
			Client.getLogger().log(Level.INFO, name + " disconnected");
		}
	}

	public void handleRoomTimer(int timer)
	{
		if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
		if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE) {
			Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow().getController()).getTimerLabel().setText("Game starts in: " + timer));
		} else {
			Client.getLogger().log(Level.INFO, "Game starts in: " + timer);
		}
	}

	public void handleGameStarted(RoomInformations roomInformations)
	{
		if (((CLIListenerClient) Client.getCliListener()).getStatus() == CLIStatus.NONE && !WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_GAME, true);
	}

	public void handleChatMessage(String text)
	{
		if (!WindowFactory.getInstance().isWindowOpen(ControllerRoom.class)) {
			return;
		}
		if (((ControllerRoom) WindowFactory.getInstance().getCurrentWindow().getController()).getChatTextArea().getText().length() < 1) {
			Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow().getController()).getChatTextArea().appendText(text));
		} else {
			Platform.runLater(() -> ((ControllerRoom) WindowFactory.getInstance().getCurrentWindow().getController()).getChatTextArea().appendText("\n" + text));
		}
	}

	public void handleLogMessage(String text)
	{
		Client.getDebugger().log(Level.INFO, text);
	}

	protected ScheduledExecutorService getHeartbeat()
	{
		return this.heartbeat;
	}
}
