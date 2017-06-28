package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerConnection implements ICLIHandler
{
	private static final Map<Integer, ConnectionType> CONNECTION_TYPES = new HashMap<>();

	static {
		CLIHandlerConnection.CONNECTION_TYPES.put(1, ConnectionType.RMI);
		CLIHandlerConnection.CONNECTION_TYPES.put(2, ConnectionType.SOCKET);
	}

	private ConnectionType connectionType;
	private String ip;
	private int port;

	@Override
	public void execute()
	{
		this.askConnectionType();
		this.askIPAddress();
		this.askPort();
		Client.getInstance().setup(this.connectionType, this.ip, this.port);
		Client.getInstance().setCliStatus(CLIStatus.AUTHENTICATION);
		Client.getInstance().getCliListener().execute(() -> {
			ICLIHandler cliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
			Client.getInstance().setCurrentCliHandler(cliHandler);
			cliHandler.execute();
		});
	}

	private void askConnectionType()
	{
		Client.getLogger().log(Level.INFO, "Enter Connection Type...");
		Client.getLogger().log(Level.INFO, "1 - RMI");
		Client.getLogger().log(Level.INFO, "2 - Socket");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerConnection.CONNECTION_TYPES.containsKey(Integer.parseInt(input)));
		this.connectionType = CLIHandlerConnection.CONNECTION_TYPES.get(Integer.parseInt(input));
	}

	private void askIPAddress()
	{
		Client.getLogger().log(Level.INFO, "Enter IP Address...");
		this.ip = Client.getInstance().getCliScanner().nextLine();
	}

	private void askPort()
	{
		Client.getLogger().log(Level.INFO, "Enter Port [default {0}]...", new Object[] { this.connectionType == ConnectionType.RMI ? "8080" : "8081" });
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		} while (!CommonUtils.isInteger(input));
		this.port = Integer.parseInt(input);
	}
}
