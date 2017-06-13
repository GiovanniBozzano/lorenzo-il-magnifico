package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.common.cli.CLIListener;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;

import java.util.EnumMap;
import java.util.Map;

public class CLIListenerClient extends CLIListener
{
	private static Map<CLIStatus, ICLIHandler> CLI_HANDLERS = new EnumMap<>(CLIStatus.class);

	static {
		CLIListenerClient.CLI_HANDLERS.put(CLIStatus.INTERFACE_CHOICE, new CLIHandlerInterfaceChoice());
		CLIListenerClient.CLI_HANDLERS.put(CLIStatus.CONNECTION, new CLIHandlerConnection());
	}

	private CLIStatus status = CLIStatus.INTERFACE_CHOICE;

	@Override
	public void run()
	{
		while (this.isKeepGoing()) {
			CLIListenerClient.CLI_HANDLERS.get(this.status).execute(this.getScanner().next());
		}
	}

	public CLIStatus getStatus()
	{
		return this.status;
	}

	public void setStatus(CLIStatus status)
	{
		this.status = status;
	}
}
