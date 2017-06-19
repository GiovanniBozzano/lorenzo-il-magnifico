package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.Main;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerInterfaceChoice implements ICLIHandler
{
	private static final Map<Integer, IInputHandler> INPUT_HANDLERS = new HashMap<>();

	static {
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.put(1, cliHandler -> {
			try {
				Main.launch(Main.class, Main.getArgs());
			} catch (Exception exception) {
				Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			return true;
		});
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.put(2, cliHandler -> {
			Client.getLogger().log(Level.INFO, "Enter Connection Type...");
			Client.getLogger().log(Level.INFO, "1 - RMI");
			Client.getLogger().log(Level.INFO, "2 - Socket");
			return true;
		});
	}

	@Override
	public void execute(String input)
	{
		if (!CommonUtils.isInteger(input)) {
			return;
		}
		if (!CLIHandlerInterfaceChoice.INPUT_HANDLERS.containsKey(Integer.parseInt(input))) {
			return;
		}
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.get(Integer.parseInt(input)).execute(this);
	}
}
