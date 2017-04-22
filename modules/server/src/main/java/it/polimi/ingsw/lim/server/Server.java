package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.server.rmi.Handshake;
import it.polimi.ingsw.lim.server.socket.ConnectionListener;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Instance
{
	private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName().toUpperCase());
	private static Server instance;
	private final Stage stage;
	private final int port;
	private ServerSocket serverSocket;
	private ConnectionListener connectionListener;
	private final ConcurrentLinkedQueue<IConnection> connections = new ConcurrentLinkedQueue<>();

	Server(Stage stage, int port)
	{
		super(Side.SERVER);
		this.side = Side.SERVER;
		this.stage = stage;
		this.port = port;
		if (Server.instance != null) {
			return;
		}
		Server.setInstance(this);
		Server.LOGGER.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		Server.LOGGER.addHandler(consoleHandler);
		try {
			Registry registry = LocateRegistry.createRegistry(port + 1);
			registry.rebind("lorenzo-il-magnifico", new Handshake());
			this.serverSocket = new ServerSocket(port);
			this.displayToLog("Server waiting on port: " + port, FontType.BOLD);
			URL myIP = new URL("http://checkip.amazonaws.com");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myIP.openStream()));
			try {
				this.displayToLog("Your external IP address is: " + bufferedReader.readLine(), FontType.BOLD);
			} catch (IOException exception) {
				Server.getLogger().log(Level.INFO, "Cannot retrieve IP address...", exception);
			}
			this.connectionListener = new ConnectionListener();
			this.connectionListener.start();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public void stop()
	{
		this.broadcastChatMessage("Server shutting down...");
		if (this.connectionListener != null) {
			this.connectionListener.close();
			try (Socket socket = new Socket("localhost", this.port)) {
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
		return Server.instance;
	}

	private static void setInstance(Server instance)
	{
		Server.instance = instance;
	}

	public Stage getStage()
	{
		return this.stage;
	}

	public int getPort()
	{
		return this.port;
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
}
