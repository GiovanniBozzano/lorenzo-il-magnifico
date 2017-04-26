package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.rmi.ServerSession;
import it.polimi.ingsw.lim.client.socket.PacketListener;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.socket.packets.client.PacketHandshake;
import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.common.rmi.IHandshake;
import it.polimi.ingsw.lim.common.utils.Constants;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Instance
{
	private static final Logger LOGGER = Logger.getLogger(Client.class.getSimpleName().toUpperCase());
	private static final Client INSTANCE = new Client();
	private Stage stage;
	private ConnectionType connectionType;
	private String ip;
	private int port;
	private String name;
	private IClientSession clientSession;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PacketListener packetListener;
	private boolean isStopping;

	static {
		Client.LOGGER.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		Client.LOGGER.addHandler(consoleHandler);
	}

	private Client()
	{
		super(Side.CLIENT);
	}

	public void start(Stage stage, ConnectionType connectionType, String ip, int port, String name)
	{
		this.stage = stage;
		this.connectionType = connectionType;
		this.ip = ip;
		this.port = port;
		this.name = name;
		if (connectionType == ConnectionType.RMI) {
			try {
				IHandshake handshake = (IHandshake) Naming.lookup("rmi://" + ip + ":" + port + "/lorenzo-il-magnifico");
				this.clientSession = handshake.send(name, Constants.VERSION, new ServerSession());
				if (this.clientSession != null) {
					Client.LOGGER.log(Level.INFO, "Connected to Server.");
				} else {
					this.stage.getScene().getRoot().setDisable(false);
					Client.LOGGER.log(Level.INFO, "Client version not compatible with the Server.");
					return;
				}
			} catch (NotBoundException | MalformedURLException | RemoteException exception) {
				this.stage.getScene().getRoot().setDisable(false);
				Client.LOGGER.log(Level.INFO, "Could not connect to host", exception);
				return;
			}
		} else {
			try {
				this.socket = new Socket(ip, port);
				this.out = new ObjectOutputStream(this.socket.getOutputStream());
				this.in = new ObjectInputStream(this.socket.getInputStream());
				this.packetListener = new PacketListener();
				this.packetListener.start();
				this.sendHandshake();
			} catch (IOException exception) {
				this.stage.getScene().getRoot().setDisable(false);
				Client.LOGGER.log(Level.INFO, "Could not connect to host.", exception);
				return;
			}
		}
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneLobby.fxml"));
			this.stage.close();
			this.stage = new Stage();
			this.stage.setScene(new Scene(root));
			this.stage.sizeToScene();
			this.stage.setResizable(false);
			this.stage.show();
		} catch (IOException exception) {
			Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public void stop()
	{
		this.isStopping = true;
		this.disconnect();
		this.stage.close();
	}

	private void sendHandshake()
	{
		try {
			this.out.writeObject(new PacketHandshake(this.name));
		} catch (IOException exception) {
			Client.LOGGER.log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public void disconnect()
	{
		if (connectionType == ConnectionType.RMI) {
			Client.getLogger().log(Level.INFO, "The connection has been closed.");
			this.clientSession = null;
		} else {
			this.packetListener.close();
			try {
				if (this.out != null) {
					this.out.close();
				}
				if (this.in != null) {
					this.in.close();
				}
				this.socket.close();
			} catch (IOException exception) {
				Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		if (!isStopping) {
			Platform.runLater(() -> {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneConnection.fxml"));
					this.stage.close();
					Stage newStage = new Stage();
					newStage.setScene(new Scene(root));
					newStage.sizeToScene();
					newStage.setResizable(false);
					newStage.show();
				} catch (IOException exception) {
					Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
			});
		}
	}

	public static Logger getLogger()
	{
		return Client.LOGGER;
	}

	public static Client getInstance()
	{
		return Client.INSTANCE;
	}

	public Stage getStage()
	{
		return this.stage;
	}

	public ConnectionType getConnectionType()
	{
		return this.connectionType;
	}

	public String getIp()
	{
		return this.ip;
	}

	public int getPort()
	{
		return this.port;
	}

	public IClientSession getClientSession()
	{
		return clientSession;
	}

	public ObjectInputStream getIn()
	{
		return this.in;
	}

	public ObjectOutputStream getOut()
	{
		return this.out;
	}
}
