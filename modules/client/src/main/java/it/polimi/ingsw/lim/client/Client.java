package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.client.network.rmi.ServerSession;
import it.polimi.ingsw.lim.client.network.socket.PacketListener;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.rmi.IClientSession;
import it.polimi.ingsw.lim.common.rmi.IHandshake;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.WindowInformations;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

public class Client extends Instance
{
	private ConnectionType connectionType;
	private String ip;
	private int port;
	private String name;
	private IClientSession clientSession;
	private ServerSession serverSession;
	private PacketListener packetListener;
	private boolean isConnected;

	/**
	 * Tries to connect to an RMI or Socket Server and, if successful, opens the lobby screen.
	 * @param connectionType the type of connection used.
	 * @param ip the IP address of the Server.
	 * @param port the port of the Server.
	 * @param name the name of the Player.
	 */
	public void setup(ConnectionType connectionType, String ip, int port, String name)
	{
		this.connectionType = connectionType;
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.isConnected = false;
		this.getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		if (connectionType == ConnectionType.RMI) {
			new Thread(() -> {
				try {
					IHandshake handshake = (IHandshake) Naming.lookup("rmi://" + ip + ":" + port + "/lorenzo-il-magnifico");
					this.serverSession = new ServerSession();
					this.clientSession = handshake.sendLogin(name, CommonUtils.VERSION, this.serverSession);
				} catch (NotBoundException | MalformedURLException | RemoteException exception) {
					this.getWindowInformations().getStage().getScene().getRoot().setDisable(false);
					Client.getLogger().log(Level.INFO, "Could not connect to host", exception);
					return;
				}
				if (this.clientSession == null) {
					this.getWindowInformations().getStage().getScene().getRoot().setDisable(false);
					return;
				}
				this.isConnected = true;
				CommonUtils.setNewWindow("/fxml/SceneLobby.fxml", null, null, new Thread(Connection::sendRequestRoomList));
			}).start();
		} else {
			this.packetListener = new PacketListener(ip, port);
			this.packetListener.start();
		}
	}

	/**
	 * Disconnects from the Server and closes all windows.
	 */
	@Override
	public void stop()
	{
		this.disconnect(true);
	}

	/**
	 * Disconnects from the Server.
	 * If the Client is stopping, it closes all the windows, otherwise it closes all the current windows and opens the connection window.
	 * @param isStopping the flag to check whether the Client has to be closed or not.
	 */
	public void disconnect(boolean isStopping)
	{
		if (this.isConnected) {
			this.isConnected = false;
			if (this.connectionType == ConnectionType.RMI) {
				if (this.serverSession != null) {
					try {
						UnicastRemoteObject.unexportObject(this.serverSession, true);
					} catch (NoSuchObjectException exception) {
						Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
					}
				}
				this.clientSession = null;
				System.gc();
				System.runFinalization();
			} else {
				if (this.packetListener != null) {
					this.packetListener.close();
					try {
						this.packetListener.join();
					} catch (InterruptedException exception) {
						Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
						Thread.currentThread().interrupt();
					}
				}
			}
			Client.getLogger().log(Level.INFO, "Connection closed.");
		}
		if (isStopping) {
			Platform.runLater(() -> CommonUtils.closeAllWindows(this.getWindowInformations().getStage()));
		} else {
			Platform.runLater(() -> {
				FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/SceneConnection.fxml"));
				try {
					Parent parent = fxmlLoader.load();
					Stage stage = new Stage();
					stage.setScene(new Scene(parent));
					stage.sizeToScene();
					stage.setResizable(false);
					CommonUtils.closeAllWindows(this.getWindowInformations().getStage());
					this.setWindowInformations(new WindowInformations(fxmlLoader.getController(), stage));
					stage.show();
				} catch (IOException exception) {
					Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				}
			});
		}
	}

	public static Client getInstance()
	{
		return (Client) Instance.getInstance();
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
		return this.clientSession;
	}

	public PacketListener getPacketListener()
	{
		return this.packetListener;
	}

	public boolean isConnected()
	{
		return this.isConnected;
	}

	public void setConnected(boolean isConnected)
	{
		this.isConnected = isConnected;
	}
}
