package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.rmi.ServerSession;
import it.polimi.ingsw.lim.client.socket.PacketListener;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.common.rmi.IHandshake;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.socket.packets.client.PacketHandshake;
import it.polimi.ingsw.lim.common.utils.Constants;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.WindowInformations;
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
	private WindowInformations windowInformations;
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

	public void connect(ConnectionType connectionType, String ip, int port, String name)
	{
		this.connectionType = connectionType;
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.windowInformations.getStage().getScene().getRoot().setDisable(true);
		if (connectionType == ConnectionType.RMI) {
			try {
				IHandshake handshake = (IHandshake) Naming.lookup("rmi://" + ip + ":" + port + "/lorenzo-il-magnifico");
				this.clientSession = handshake.send(name, Constants.VERSION, new ServerSession());
				if (this.clientSession != null) {
					this.setNewWindow("/fxml/SceneLobby.fxml", new Thread(() -> Client.getInstance().sendRequestRoomList()));
				} else {
					this.windowInformations.getStage().getScene().getRoot().setDisable(false);
				}
			} catch (NotBoundException | MalformedURLException | RemoteException exception) {
				this.windowInformations.getStage().getScene().getRoot().setDisable(false);
				Client.LOGGER.log(Level.INFO, "Could not connect to host", exception);
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
				this.windowInformations.getStage().getScene().getRoot().setDisable(false);
				Client.LOGGER.log(Level.INFO, "Could not connect to host.", exception);
			}
		}
	}

	public void stop()
	{
		this.isStopping = true;
		this.disconnect();
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
				Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		});
	}

	public void disconnect()
	{
		if (connectionType == ConnectionType.RMI) {
			Client.getLogger().log(Level.INFO, "The connection has been closed.");
			this.clientSession = null;
		} else {
			if (this.packetListener != null) {
				this.packetListener.close();
			}
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
			this.setNewWindow("/fxml/SceneConnection.fxml");
		} else {
			Platform.runLater(() -> this.windowInformations.getStage().close());
		}
	}

	private void sendHandshake()
	{
		if (this.connectionType == ConnectionType.SOCKET) {
			try {
				this.out.writeObject(new PacketHandshake(this.name));
			} catch (IOException exception) {
				Client.LOGGER.log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
	}

	public void sendRequestRoomList()
	{
		if (this.connectionType == ConnectionType.RMI) {
			try {
				this.clientSession.requestRoomList();
			} catch (RemoteException exception) {
				Client.LOGGER.log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		} else {
			try {
				this.out.writeObject(new Packet(PacketType.REQUEST_ROOM_LIST));
			} catch (IOException exception) {
				Client.LOGGER.log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
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

	public WindowInformations getWindowInformations()
	{
		return this.windowInformations;
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

	public String getName()
	{
		return this.name;
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
