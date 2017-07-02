package it.polimi.ingsw.lim.server.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Main;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.enums.CLIStatus;
import it.polimi.ingsw.lim.server.gui.InterfaceHandlerGUI;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerInterfaceChoice implements ICLIHandler
{
	private static final Map<Integer, IInputHandler> INPUT_HANDLERS = new HashMap<>();

	static {
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.put(1, cliHandler -> {
			Server.getInstance().setCliStatus(CLIStatus.NONE);
			Server.getInstance().setInterfaceHandler(new InterfaceHandlerGUI());
			try {
				Main.launch(Main.class, Main.getArgs());
			} catch (Exception exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
		});
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.put(2, cliHandler -> {
			Server.getInstance().setCliStatus(CLIStatus.START);
			Server.getInstance().getCliListener().execute(() -> Server.getCliHandlers().get(Server.getInstance().getCliStatus()).newInstance().execute());
		});
	}

	@Override
	public void execute()
	{
		this.askInterfaceType();
	}

	private void askInterfaceType()
	{
		Server.getInstance().getInterfaceHandler().displayToLog("Enter Interface Type...");
		Server.getInstance().getInterfaceHandler().displayToLog("1 - GUI");
		Server.getInstance().getInterfaceHandler().displayToLog("2 - CLI");
		String input;
		do {
			input = Server.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerInterfaceChoice.INPUT_HANDLERS.containsKey(Integer.parseInt(input)));
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.get(Integer.parseInt(input)).execute(this);
	}

	@Override
	public CLIHandlerInterfaceChoice newInstance()
	{
		return new CLIHandlerInterfaceChoice();
	}
}
