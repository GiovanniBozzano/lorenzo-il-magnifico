package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.rmi.Handshake;
import it.polimi.ingsw.lim.server.socket.ConnectionListener;
import it.polimi.ingsw.lim.server.utils.Utils;
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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Instance
{
	private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName().toUpperCase());
	private static final Server INSTANCE = new Server();
	private Stage stage;
	private int rmiPort;
	private int socketPort;
	private Registry registry;
	private ServerSocket serverSocket;
	private ConnectionListener connectionListener;
	private final ConcurrentLinkedQueue<IConnection> connections = new ConcurrentLinkedQueue<>();
	private int connectionId;

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

	public void start(Stage stage, int rmiPort, int socketPort)
	{
		this.side = Side.SERVER;
		this.stage = stage;
		this.rmiPort = rmiPort;
		this.socketPort = socketPort;
		try {
			this.registry = LocateRegistry.createRegistry(rmiPort);
			this.registry.rebind("lorenzo-il-magnifico", new Handshake());
			this.serverSocket = new ServerSocket(socketPort);
			this.displayToLog("Server waiting on RMI port " + rmiPort + " and Socket port " + socketPort, FontType.BOLD);
			this.displayToLog("Your external IP address is: " + Utils.getExternalIpAddress(), FontType.BOLD);
			this.connectionListener = new ConnectionListener();
			this.connectionListener.start();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			this.stage.getScene().getRoot().setDisable(false);
			return;
		}
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneMain.fxml"));
			this.stage.close();
			this.stage = new Stage();
			this.stage.setScene(new Scene(root));
			this.stage.sizeToScene();
			this.stage.setResizable(false);
			this.stage.show();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
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

	public void disconnectAll()
	{
		for (IConnection connection : this.connections) {
			connection.disconnect();
		}
		this.connections.clear();
	}

	private void broadcastChatMessage(String text)
	{
		for (IConnection connection : this.connections) {
			connection.sendLogMessage(text);
		}
	}

	public void displayToLog(String text, FontType fontType)
	{
		Server.LOGGER.log(Level.INFO, text);
	}

	public static Logger getLogger()
	{
		return Server.LOGGER;
	}

	public static Server getInstance()
	{
		return Server.INSTANCE;
	}

	public Stage getStage()
	{
		return this.stage;
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
}
