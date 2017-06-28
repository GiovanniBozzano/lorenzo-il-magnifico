package it.polimi.ingsw.lim.server.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Main;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.enums.CLIStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerInterfaceChoice implements ICLIHandler
{
	private static final Map<Integer, IInputHandler> INPUT_HANDLERS = new HashMap<>();

	static {
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.put(1, cliHandler -> {
			Server.getInstance().setCliStatus(CLIStatus.NONE);
			try {
				Main.launch(Main.class, Main.getArgs());
			} catch (Exception exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
		});
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.put(2, cliHandler -> {
			Server.getInstance().setCliStatus(CLIStatus.START);
			Server.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Server.getCliHandlers().get(Server.getInstance().getCliStatus());
				Server.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
	}

	@Override
	public void execute()
	{
		this.askInterfaceType();
	}

	private void askInterfaceType()
	{
		Server.getLogger().log(Level.INFO, "Enter Interface Type...");
		Server.getLogger().log(Level.INFO, "1 - GUI");
		Server.getLogger().log(Level.INFO, "2 - CLI");
		String input;
		do {
			input = Server.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerInterfaceChoice.INPUT_HANDLERS.containsKey(Integer.parseInt(input)));
		CLIHandlerInterfaceChoice.INPUT_HANDLERS.get(Integer.parseInt(input)).execute(this);
	}
}
