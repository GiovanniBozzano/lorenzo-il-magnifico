package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerConnection implements ICLIHandler
{
	private static Map<Integer, IInputHandler> INPUT_HANDLERS_CONNECTION_TYPE = new HashMap<>();

	static {
		CLIHandlerConnection.INPUT_HANDLERS_CONNECTION_TYPE.put(1, (cliHandler) -> {
			((CLIHandlerConnection) cliHandler).connectionType = ConnectionType.RMI;
			return true;
		});
		CLIHandlerConnection.INPUT_HANDLERS_CONNECTION_TYPE.put(2, (cliHandler) -> {
			((CLIHandlerConnection) cliHandler).connectionType = ConnectionType.SOCKET;
			return true;
		});
	}

	private ConnectionType connectionType;
	private String ip;
	private int port;

	@Override
	public void execute(String input)
	{
		if (!CommonUtils.isInteger(input)) {
			return;
		}
		if (!CLIHandlerConnection.INPUT_HANDLERS_CONNECTION_TYPE.containsKey(Integer.parseInt(input))) {
			return;
		}
		if (!CLIHandlerConnection.INPUT_HANDLERS_CONNECTION_TYPE.get(Integer.parseInt(input)).execute(this)) {
			return;
		}
		this.askIPAddress();
		this.askPort();
		Client.getInstance().setup(this.connectionType, this.ip, this.port);
	}

	private void askIPAddress()
	{
		Client.getLogger().log(Level.INFO, "Enter IP Address...");
		this.ip = Client.getCliListener().getScanner().next();
	}

	private void askPort()
	{
		Client.getLogger().log(Level.INFO, "Enter Port [default " + (this.connectionType == ConnectionType.RMI ? "8080" : "8081") + "]...");
		String input;
		do {
			input = Client.getCliListener().getScanner().next();
		} while (!CommonUtils.isInteger(input));
		this.port = Integer.parseInt(input);
	}
}
