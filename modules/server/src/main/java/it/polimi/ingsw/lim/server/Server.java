package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Instance
{
	private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName().toUpperCase());
	private static Server instance;
	private Stage stage;
	private int port;
	private ServerSocket serverSocket;
	private ConnectionListener connectionListener;
	private final List<ClientConnection> clientConnections = new ArrayList<>();

	Server(Stage stage, int port)
	{
		if (Server.instance != null) {
			return;
		}
		Server.instance = this;
		Server.LOGGER.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		Server.LOGGER.addHandler(consoleHandler);
		this.side = Side.SERVER;
		this.stage = stage;
		this.port = port;
		Server.LOGGER.log(Level.INFO, "FIRST PERIOD:");
		Server.LOGGER.log(Level.INFO, "BLUE:   {0}", Arrays.toString(Period.FIRST.getBlueCards()));
		Server.LOGGER.log(Level.INFO, "GREEN:  {0}", Arrays.toString(Period.FIRST.getGreenCards()));
		Server.LOGGER.log(Level.INFO, "PURPLE: {0}", Arrays.toString(Period.FIRST.getPurpleCards()));
		Server.LOGGER.log(Level.INFO, "YELLOW: {0}", Arrays.toString(Period.FIRST.getYellowCards()));
		Server.LOGGER.log(Level.INFO, "----------");
		Server.LOGGER.log(Level.INFO, "SECOND PERIOD:");
		Server.LOGGER.log(Level.INFO, "BLUE:   {0}", Arrays.toString(Period.SECOND.getBlueCards()));
		Server.LOGGER.log(Level.INFO, "GREEN:  {0}", Arrays.toString(Period.SECOND.getGreenCards()));
		Server.LOGGER.log(Level.INFO, "PURPLE: {0}", Arrays.toString(Period.SECOND.getPurpleCards()));
		Server.LOGGER.log(Level.INFO, "YELLOW: {0}", Arrays.toString(Period.SECOND.getYellowCards()));
		Server.LOGGER.log(Level.INFO, "----------");
		Server.LOGGER.log(Level.INFO, "THIRD PERIOD:");
		Server.LOGGER.log(Level.INFO, "BLUE:   {0}", Arrays.toString(Period.THIRD.getBlueCards()));
		Server.LOGGER.log(Level.INFO, "GREEN:  {0}", Arrays.toString(Period.THIRD.getGreenCards()));
		Server.LOGGER.log(Level.INFO, "PURPLE: {0}", Arrays.toString(Period.THIRD.getPurpleCards()));
		Server.LOGGER.log(Level.INFO, "YELLOW: {0}", Arrays.toString(Period.THIRD.getYellowCards()));
		try {
			this.serverSocket = new ServerSocket(this.port);
			this.displayToLog("Server in attesa sulla porta " + this.port + ".", FontType.BOLD);
			URL myIP = new URL("http://checkip.amazonaws.com");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myIP.openStream()));
			this.displayToLog("Il tuo IP esterno: " + bufferedReader.readLine(), FontType.BOLD);
			this.connectionListener = new ConnectionListener();
			this.connectionListener.start();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public void stop()
	{
		this.broadcastChatMessage("Spegnimento Server...", FontType.BOLD);
		this.disconnectAll();
		if (this.connectionListener != null) {
			this.connectionListener.close();
			try (Socket socket = new Socket("localhost", port)) {
				socket.close();
			} catch (IOException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		try {
			this.serverSocket.close();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public void disconnectAll()
	{
		for (ClientConnection clientConnection : this.clientConnections) {
			clientConnection.disconnect();
		}
		this.clientConnections.clear();
	}

	public void broadcastChatMessage(String text, FontType fontType)
	{
	}

	public void displayToLog(String text, FontType fontType)
	{
	}

	public static Logger getLogger()
	{
		return Server.LOGGER;
	}

	public static Server getInstance()
	{
		return Server.instance;
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

	public List<ClientConnection> getClientConnections()
	{
		return this.clientConnections;
	}
}
