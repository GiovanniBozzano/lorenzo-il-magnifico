package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerExcommunicationChoice implements ICLIHandler
{
	private static final Map<Integer, Boolean> EXCOMMUNICATION_CHOICES = new HashMap<>();

	static {
		CLIHandlerExcommunicationChoice.EXCOMMUNICATION_CHOICES.put(1, Boolean.TRUE);
		CLIHandlerExcommunicationChoice.EXCOMMUNICATION_CHOICES.put(2, Boolean.FALSE);
	}

	/**
	 * <p>Uses a {@link Integer} {@link Boolean} {@link Map} to ask if the
	 * player wants to praise the SUN and sends the corresponding action with
	 * the chosen information.
	 */
	private static void askExcommunicationChoice()
	{
		Client.getLogger().log(Level.INFO, "\n\n\nEnter Excommunication choice...");
		Client.getLogger().log(Level.INFO, "1 - Do not praise the SUN...");
		Client.getLogger().log(Level.INFO, "2 - Praise the SUN...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerExcommunicationChoice.EXCOMMUNICATION_CHOICES.containsKey(Integer.parseInt(input)));
		Client.getInstance().getConnectionHandler().sendGameExcommunicationPlayerChoice(CLIHandlerExcommunicationChoice.EXCOMMUNICATION_CHOICES.get(Integer.parseInt(input)));
	}

	@Override
	public void execute()
	{
		CLIHandlerExcommunicationChoice.askExcommunicationChoice();
	}

	@Override
	public CLIHandlerExcommunicationChoice newInstance()
	{
		return new CLIHandlerExcommunicationChoice();
	}
}
