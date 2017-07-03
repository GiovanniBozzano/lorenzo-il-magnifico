package it.polimi.ingsw.lim.server.view.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;

public class CLIHandlerStart implements ICLIHandler
{
	private int rmiPort;
	private int socketPort;

	@Override
	public void execute()
	{
		this.askRmiPort();
		this.askSocketPort();
		Server.getInstance().setup(this.rmiPort, this.socketPort);
	}

	private void askRmiPort()
	{
		Server.getInstance().getInterfaceHandler().displayToLog("Enter RMI Port [default 8080]...");
		String input;
		do {
			input = Server.getInstance().getCliScanner().nextLine();
		} while (!CommonUtils.isInteger(input));
		this.rmiPort = Integer.parseInt(input);
	}

	private void askSocketPort()
	{
		Server.getInstance().getInterfaceHandler().displayToLog("Enter Socket Port [default 8081]...");
		String input;
		do {
			input = Server.getInstance().getCliScanner().nextLine();
		} while (!CommonUtils.isInteger(input));
		this.socketPort = Integer.parseInt(input);
	}

	@Override
	public CLIHandlerStart newInstance()
	{
		return new CLIHandlerStart();
	}
}
