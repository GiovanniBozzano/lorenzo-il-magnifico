package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerExcommunicationChoice implements ICLIHandler
{
	private static final Map<Integer, IInputHandler> EXCOMMUNICATION_CHOICES = new HashMap<>();

	static {
		CLIHandlerExcommunicationChoice.EXCOMMUNICATION_CHOICES.put(1, cliHandler -> {
		});
		CLIHandlerExcommunicationChoice.EXCOMMUNICATION_CHOICES.put(2, cliHandler -> {
		});
	}

	@Override
	public void execute()
	{
		askExcommunicationChoice();
	}

	@Override
	public CLIHandlerExcommunicationChoice newInstance()
	{
		return new CLIHandlerExcommunicationChoice();
	}

	private void askExcommunicationChoice()
	{
		Client.getLogger().log(Level.INFO, "Enter your choice...");
		Client.getLogger().log(Level.INFO, "1 - ...");
		Client.getLogger().log(Level.INFO, "2 - ...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerExcommunicationChoice.EXCOMMUNICATION_CHOICES.containsKey(Integer.parseInt(input)));
		CLIHandlerExcommunicationChoice.EXCOMMUNICATION_CHOICES.get(Integer.parseInt(input)).execute(this);
	}
}
