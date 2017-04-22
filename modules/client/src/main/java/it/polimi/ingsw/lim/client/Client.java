package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.rmi.ServerSession;
import it.polimi.ingsw.lim.client.socket.PacketListener;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.packets.client.PacketHandshake;
import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.common.rmi.IHandshake;
import it.polimi.ingsw.lim.common.utils.Constants;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	private static Client instance;
	private final Stage stage;
	private final ConnectionType connectionType;
	private final String ip;
	private final int port;
	private final String name;
	private IClientSession clientSession;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PacketListener packetListener;

	public Client(Stage stage, ConnectionType connectionType, String ip, int port, String name)
	{
		super(Side.CLIENT);
		this.stage = stage;
		this.connectionType = connectionType;
		this.ip = ip;
		this.port = port;
		this.name = name;
		if (Client.instance != null) {
			return;
		}
		Client.setInstance(this);
		Client.LOGGER.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		Client.LOGGER.addHandler(consoleHandler);
		this.stage.getScene().getRoot().setDisable(true);
		if (connectionType == ConnectionType.RMI) {
			try {
				IHandshake handshake = (IHandshake) Naming.lookup("rmi://" + ip + ":" + (port + 1) + "/lorenzo-il-magnifico");
				this.clientSession = handshake.send(this.name, Constants.VERSION, new ServerSession());
				if (this.clientSession != null) {
					this.clientSession.getMyName();
				}
			} catch (NotBoundException | MalformedURLException | RemoteException exception) {
				Client.LOGGER.log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		try {
			this.socket = new Socket(ip, port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
			this.packetListener = new PacketListener();
			this.packetListener.start();
			this.stage.getScene().getRoot().setDisable(false);
			ObservableList<Node> children = this.stage.getScene().getRoot().getChildrenUnmodifiable();
			children.get(0).setDisable(true);
			((Button) ((HBox) children.get(1)).getChildren().get(0)).setText("DISCONNECT");
			((Button) ((HBox) children.get(1)).getChildren().get(0)).setOnAction((ActionEvent event) -> disconnect());
			this.sendHandshake();
		} catch (IOException exception) {
			Client.LOGGER.log(Level.INFO, "Could not connect to host", exception);
			this.stage.getScene().getRoot().setDisable(false);
			Client.setInstance(null);
		}
	}

	public void stop()
	{
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
			this.clientSession = null;
		}
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
		Client.setInstance(null);
		Platform.runLater(() -> {
			ObservableList<Node> children = stage.getScene().getRoot().getChildrenUnmodifiable();
			children.get(0).setDisable(false);
			((Button) ((HBox) children.get(1)).getChildren().get(0)).setText("CONNECT");
			((Button) ((HBox) children.get(1)).getChildren().get(0)).setOnAction((ActionEvent event) -> new Client(stage, ConnectionType.RMI, ((TextField) ((GridPane) children.get(0)).getChildren().get(1)).getText(), Integer.parseInt(((TextField) ((GridPane) children.get(0)).getChildren().get(3)).getText()), ((TextField) ((GridPane) children.get(0)).getChildren().get(5)).getText()));
		});
	}

	public static Logger getLogger()
	{
		return Client.LOGGER;
	}

	public static Client getInstance()
	{
		return Client.instance;
	}

	public static void setInstance(Client instance)
	{
		Client.instance = instance;
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
