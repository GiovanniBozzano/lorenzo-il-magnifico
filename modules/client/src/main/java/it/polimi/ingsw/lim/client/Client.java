package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.packets.PacketHandshake;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
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
import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Instance
{
	private static final Logger LOGGER = Logger.getLogger(Client.class.getSimpleName().toUpperCase());
	private static Client instance;
	private final Stage stage;
	private final String ip;
	private final int port;
	private final String name;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PacketListener packetListener;

	Client(Stage stage, String ip, int port, String name)
	{
		super(Side.CLIENT);
		this.stage = stage;
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
		try {
			this.socket = new Socket(ip, port);
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
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

	public void disconnect()
	{
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
		ObservableList<Node> children = this.stage.getScene().getRoot().getChildrenUnmodifiable();
		children.get(0).setDisable(false);
		((Button) ((HBox) children.get(1)).getChildren().get(0)).setText("CONNECT");
		((Button) ((HBox) children.get(1)).getChildren().get(0)).setOnAction((ActionEvent event) -> new Client(this.stage, ((TextField) ((GridPane) children.get(0)).getChildren().get(1)).getText(), Integer.parseInt(((TextField) ((GridPane) children.get(0)).getChildren().get(3)).getText()), ((TextField) ((GridPane) children.get(0)).getChildren().get(5)).getText()));
	}

	public void sendHandshake()
	{
		try {
			this.out.writeObject(new PacketHandshake(name));
		} catch (IOException exception) {
			Client.LOGGER.log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public static Logger getLogger()
	{
		return Client.LOGGER;
	}

	public static Client getInstance()
	{
		return Client.instance;
	}

	private static void setInstance(Client instance)
	{
		Client.instance = instance;
	}

	public String getIp()
	{
		return this.ip;
	}

	public int getPort()
	{
		return this.port;
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
