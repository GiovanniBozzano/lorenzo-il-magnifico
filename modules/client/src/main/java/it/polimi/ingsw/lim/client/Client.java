package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.cli.CLIHandlerAuthentication;
import it.polimi.ingsw.lim.client.cli.CLIHandlerConnection;
import it.polimi.ingsw.lim.client.cli.CLIHandlerInterfaceChoice;
import it.polimi.ingsw.lim.client.cli.CLIHandlerPersonalBonusTileChoice;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.gui.ControllerConnection;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.network.rmi.ConnectionHandlerRMI;
import it.polimi.ingsw.lim.client.network.socket.ConnectionHandlerSocket;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.application.Platform;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class Client extends Instance
{
	private static final Map<CLIStatus, ICLIHandler> CLI_HANDLERS = new EnumMap<>(CLIStatus.class);

	static {
		Client.CLI_HANDLERS.put(CLIStatus.INTERFACE_CHOICE, new CLIHandlerInterfaceChoice());
		Client.CLI_HANDLERS.put(CLIStatus.CONNECTION, new CLIHandlerConnection());
		Client.CLI_HANDLERS.put(CLIStatus.AUTHENTICATION, new CLIHandlerAuthentication());
		Client.CLI_HANDLERS.put(CLIStatus.PERSONAL_BONUS_TILE_CHOICE, new CLIHandlerPersonalBonusTileChoice());
	}

	private CLIStatus cliStatus = CLIStatus.INTERFACE_CHOICE;
	private String ip;
	private int port;
	private String username;
	private ConnectionHandler connectionHandler;

	/**
	 * <p>Tries to connect to an RMI or Socket Server and, if successful, opens
	 * the lobby screen.
	 *
	 * @param connectionType the type of connection used.
	 * @param ip the IP address of the Server.
	 * @param port the port of the Server.
	 */
	public synchronized void setup(ConnectionType connectionType, String ip, int port)
	{
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(() -> {
			this.ip = ip;
			this.port = port;
			this.username = null;
			if (connectionType == ConnectionType.RMI) {
				this.connectionHandler = new ConnectionHandlerRMI();
			} else {
				this.connectionHandler = new ConnectionHandlerSocket();
			}
			this.connectionHandler.start();
		});
		executorService.shutdown();
	}

	/**
	 * <p>Disconnects from the Server and closes all windows.
	 */
	@Override
	public void stop()
	{
		this.disconnect(true, true);
	}

	/**
	 * <p>Disconnects from the Server. If the Client is stopping, it closes all
	 * the windows, otherwise it closes all the current windows and opens the
	 * connection window.
	 *
	 * @param isStopping the flag to check whether the Client has to be closed
	 * or not.
	 * @param notifyServer the flag to check wether the Client has to notify the
	 * Server or not.
	 */
	public synchronized void disconnect(boolean isStopping, boolean notifyServer)
	{
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(() -> {
			if (this.connectionHandler != null) {
				this.connectionHandler.disconnect(notifyServer);
				this.connectionHandler = null;
				Client.getDebugger().log(Level.INFO, "Connection closed.");
			}
			if (isStopping) {
				this.getCliScanner().close();
				this.getCliListener().shutdownNow();
				if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
					Platform.runLater(() -> WindowFactory.getInstance().closeWindow());
				}
			} else if (Client.getInstance().getCliStatus() == CLIStatus.NONE && WindowFactory.getInstance().isWindowOpen(ControllerConnection.class)) {
				WindowFactory.getInstance().enableWindow();
			} else if (Client.getInstance().getCliStatus() == CLIStatus.NONE) {
				WindowFactory.getInstance().setNewWindow(Utils.SCENE_CONNECTION);
			}
		});
		executorService.shutdown();
	}

	public static Client getInstance()
	{
		return (Client) Instance.getInstance();
	}

	public static Map<CLIStatus, ICLIHandler> getCliHandlers()
	{
		return CLI_HANDLERS;
	}

	public CLIStatus getCliStatus()
	{
		return this.cliStatus;
	}

	public void setCliStatus(CLIStatus cliStatus)
	{
		this.cliStatus = cliStatus;
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
