package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.cli.*;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.network.ConnectionHandler;
import it.polimi.ingsw.lim.client.network.rmi.ConnectionHandlerRMI;
import it.polimi.ingsw.lim.client.network.socket.ConnectionHandlerSocket;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import javafx.scene.media.MediaPlayer;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends Instance
{
	private static final Map<CLIStatus, ICLIHandler> CLI_HANDLERS = new EnumMap<>(CLIStatus.class);

	static {
		Client.CLI_HANDLERS.put(CLIStatus.INTERFACE_CHOICE, new CLIHandlerInterfaceChoice());
		Client.CLI_HANDLERS.put(CLIStatus.CONNECTION, new CLIHandlerConnection());
		Client.CLI_HANDLERS.put(CLIStatus.AUTHENTICATION, new CLIHandlerAuthentication());
		Client.CLI_HANDLERS.put(CLIStatus.PERSONAL_BONUS_TILE_CHOICE, new CLIHandlerPersonalBonusTileChoice());
		Client.CLI_HANDLERS.put(CLIStatus.LEADER_CARDS_CHOICE, new CLIHandlerLeaderCardsChoice());
		Client.CLI_HANDLERS.put(CLIStatus.EXCOMMUNICATION_CHOICE, new CLIHandlerExcommunicationChoice());
		Client.CLI_HANDLERS.put(CLIStatus.AVAILABLE_ACTIONS, new CLIHandlerAvailableActions());
		Client.CLI_HANDLERS.put(CLIStatus.SHOW_DEVELOPMENT_CARDS, new CLIHandlerShowDevelopmentCards());
		Client.CLI_HANDLERS.put(CLIStatus.HARVEST, new CLIHandlerHarvest());
		Client.CLI_HANDLERS.put(CLIStatus.PRODUCTION_START, new CLIHandlerProductionStart());
		Client.CLI_HANDLERS.put(CLIStatus.MARKET, new CLIHandlerMarket());
		Client.CLI_HANDLERS.put(CLIStatus.COUNCIL_PALACE, new CLIHandlerCouncilPalace());
		Client.CLI_HANDLERS.put(CLIStatus.LEADER_DISCARD, new CLIHandlerLeaderDiscard());
		Client.CLI_HANDLERS.put(CLIStatus.LEADER_ACTIVATE, new CLIHandlerLeaderActivate());
		Client.CLI_HANDLERS.put(CLIStatus.LEADER_PLAY, new CLIHandlerLeaderPlay());
		Client.CLI_HANDLERS.put(CLIStatus.SHOW_OWN_LEADERS, new CLIHandlerShowOwnLeaders());
		Client.CLI_HANDLERS.put(CLIStatus.SHOW_OWN_BOARD, new CLIHandlerShowOwnBoard());
		Client.CLI_HANDLERS.put(CLIStatus.GAME_ENDED, new CLIHandlerGameEnded());
	}

	private CLIStatus cliStatus = CLIStatus.INTERFACE_CHOICE;
	private String ip;
	private int port;
	private String username;
	private ConnectionHandler connectionHandler;
	private IInterfaceHandler interfaceHandler;
	private MediaPlayer backgroundMediaPlayer;

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
			if (this.backgroundMediaPlayer != null) {
				this.backgroundMediaPlayer.dispose();
			}
			if (this.connectionHandler != null) {
				this.connectionHandler.disconnect(notifyServer);
				this.connectionHandler = null;
			}
			if (isStopping) {
				this.interfaceHandler.stop();
			} else {
				this.interfaceHandler.disconnect();
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

	public IInterfaceHandler getInterfaceHandler()
	{
		return this.interfaceHandler;
	}

	public void setInterfaceHandler(IInterfaceHandler interfaceHandler)
	{
		this.interfaceHandler = interfaceHandler;
	}

	public MediaPlayer getBackgroundMediaPlayer()
	{
		return this.backgroundMediaPlayer;
	}

	public void setBackgroundMediaPlayer(MediaPlayer backgroundMediaPlayer)
	{
		this.backgroundMediaPlayer = backgroundMediaPlayer;
	}
}
