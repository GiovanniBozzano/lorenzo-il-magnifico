package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.gui.ControllerConnection;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.network.rmi.ConnectionHandlerRMI;
import it.polimi.ingsw.lim.client.network.socket.ConnectionHandlerSocket;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.WindowInformations;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class Client extends Instance
{
	private ConnectionType connectionType;
	private String ip;
	private int port;
	private String username;
	private ConnectionHandler connectionHandler;
	private boolean isConnected;

	/**
	 * Tries to connect to an RMI or Socket Server and, if successful, opens the
	 * lobby screen.
	 *
	 * @param connectionType the type of connection used.
	 * @param ip the IP address of the Server.
	 * @param port the port of the Server.
	 */
	public void setup(ConnectionType connectionType, String ip, int port)
	{
		this.connectionType = connectionType;
		this.ip = ip;
		this.port = port;
		this.username = null;
		this.isConnected = false;
		this.getWindowInformations().getStage().getScene().getRoot().setDisable(true);
		if (connectionType == ConnectionType.RMI) {
			this.connectionHandler = new ConnectionHandlerRMI();
		} else {
			this.connectionHandler = new ConnectionHandlerSocket();
		}
		this.connectionHandler.start();
	}

	/**
	 * Disconnects from the Server and closes all windows.
	 */
	@Override
	public void stop()
	{
		this.disconnect(true, false);
	}

	/**
	 * Disconnects from the Server. If the Client is stopping, it closes all the
	 * windows, otherwise it closes all the current windows and opens the
	 * connection window.
	 *
	 * @param isStopping the flag to check whether the Client has to be closed
	 * or not.
	 * @param notifyServer the flag to check wether the Client has to notify the
	 * Server or not.
	 */
	public void disconnect(boolean isStopping, boolean notifyServer)
	{
		if (this.connectionHandler != null) {
			this.connectionHandler.disconnect(notifyServer);
			Client.getLogger().log(Level.INFO, "Connection closed.");
		}
		if (isStopping) {
			CommonUtils.closeAllWindows(this.getWindowInformations().getStage());
		} else if (this.getWindowInformations().getController() instanceof ControllerConnection) {
			this.getWindowInformations().getStage().getScene().getRoot().setDisable(false);
		} else {
			Platform.runLater(() -> {
				FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(Utils.SCENE_CONNECTION));
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

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public ConnectionHandler getConnectionHandler()
	{
		return this.connectionHandler;
	}
}
