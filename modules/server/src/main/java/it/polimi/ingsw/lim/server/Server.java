package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.WindowInformations;
import it.polimi.ingsw.lim.server.rmi.Handshake;
import it.polimi.ingsw.lim.server.socket.ConnectionListener;
import it.polimi.ingsw.lim.server.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Instance
{
	private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName().toUpperCase());
	private static final Server INSTANCE = new Server();
	private WindowInformations windowInformations;
	private int rmiPort;
	private int socketPort;
	private Registry registry;
	private ServerSocket serverSocket;
	private ConnectionListener connectionListener;
	private final ConcurrentLinkedQueue<IConnection> connections = new ConcurrentLinkedQueue<>();
	private int connectionId;
	private final ConcurrentLinkedQueue<Room> rooms = new ConcurrentLinkedQueue<>();
	private int roomId;

	static {
		Server.LOGGER.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		Server.LOGGER.addHandler(consoleHandler);
	}

	private Server()
	{
		super(Side.SERVER);
	}

	public void setupConnections(int rmiPort, int socketPort)
	{
		this.side = Side.SERVER;
		this.rmiPort = rmiPort;
		this.socketPort = socketPort;
		this.connectionId = 0;
		this.roomId = 0;
		this.windowInformations.getStage().getScene().getRoot().setDisable(true);
		try {
			this.registry = LocateRegistry.createRegistry(rmiPort);
			this.registry.rebind("lorenzo-il-magnifico", new Handshake());
			this.serverSocket = new ServerSocket(socketPort);
			this.displayToLog("Server waiting on RMI port " + rmiPort + " and Socket port " + socketPort);
			this.displayToLog("Your external IP address is: " + Utils.getExternalIpAddress());
			this.connectionListener = new ConnectionListener();
			this.connectionListener.start();
			this.setNewWindow("/fxml/SceneMain.fxml");
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			this.windowInformations.getStage().getScene().getRoot().setDisable(false);
		}
	}

	public void stop()
	{
		this.broadcastChatMessage("Server shutting down...");
		if (this.registry != null) {
			try {
				this.registry.unbind("lorenzo-il-magnifico");
				UnicastRemoteObject.unexportObject(this.registry, true);
			} catch (RemoteException | NotBoundException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		if (this.connectionListener != null) {
			this.connectionListener.close();
			try (Socket socket = new Socket("localhost", this.socketPort)) {
				socket.close();
			} catch (IOException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
	}

	public void setNewWindow(String fxmlFileLocation)
	{
		this.setNewWindow(fxmlFileLocation, null);
	}

	public void setNewWindow(String fxmlFileLocation, Thread thread)
	{
		Platform.runLater(() -> {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(fxmlFileLocation));
				Parent parent = fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(parent));
				stage.sizeToScene();
				stage.setResizable(false);
				if (this.windowInformations != null) {
					this.windowInformations.getStage().close();
				}
				this.windowInformations = new WindowInformations(fxmlLoader.getController(), stage);
				stage.show();
				if (thread != null) {
					thread.start();
				}
			} catch (IOException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		});
	}

	public void disconnectAll()
	{
		for (IConnection connection : this.connections) {
			connection.disconnect();
		}
		this.connections.clear();
	}

	public void broadcastRoomsUpdate()
	{
		for (IConnection connection : this.getPlayersInLobby()) {
			connection.sendRoomList();
		}
	}

	public void broadcastChatMessage(String text)
	{
		for (IConnection connection : this.connections) {
			connection.sendLogMessage(text);
		}
	}

	public void displayToLog(String text)
	{
		Server.LOGGER.log(Level.INFO, text);
	}

	private List<IConnection> getPlayersInLobby()
	{
		List<IConnection> playersInLobby = new ArrayList<>();
		for (IConnection connection : this.connections) {
			boolean isInLobby = true;
			for (Room room : this.rooms) {
				if (room.getPlayers().contains(connection)) {
					isInLobby = false;
					break;
				}
			}
			if (isInLobby) {
				playersInLobby.add(connection);
			}
		}
		return playersInLobby;
	}

	public static Logger getLogger()
	{
		return Server.LOGGER;
	}

	public static Server getInstance()
	{
		return Server.INSTANCE;
	}

	public WindowInformations getWindowInformations()
	{
		return this.windowInformations;
	}

	public int getRmiPort()
	{
		return this.rmiPort;
	}

	public int getSocketPort()
	{
		return this.socketPort;
	}

	public ServerSocket getServerSocket()
	{
		return this.serverSocket;
	}

	public ConnectionListener getConnectionListener()
	{
		return this.connectionListener;
	}

	public Queue<IConnection> getConnections()
	{
		return this.connections;
	}

	public int getConnectionId()
	{
		return this.connectionId++;
	}

	public Queue<Room> getRooms()
	{
		return this.rooms;
	}

	public int getRoomId()
	{
		return this.roomId++;
	}
}
