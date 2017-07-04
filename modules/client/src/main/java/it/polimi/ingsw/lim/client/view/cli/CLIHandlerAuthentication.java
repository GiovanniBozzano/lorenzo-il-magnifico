package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * The player has to choose the RoomType in which he wants to enter (NORMAL =
 * 2/4 players, EXTENDED = 5 players).
 */
public class CLIHandlerAuthentication implements ICLIHandler
{
	private static final Map<Integer, RoomType> ROOM_TYPES = new HashMap<>();

	static {
		CLIHandlerAuthentication.ROOM_TYPES.put(1, RoomType.NORMAL);
		CLIHandlerAuthentication.ROOM_TYPES.put(2, RoomType.EXTENDED);
	}

	private static final Map<Integer, IInputHandler> INPUT_HANDLERS_AUTHENTICATION_TYPE = new HashMap<>();

	static {
		CLIHandlerAuthentication.INPUT_HANDLERS_AUTHENTICATION_TYPE.put(1, cliHandler -> Client.getInstance().getConnectionHandler().sendLogin(((CLIHandlerAuthentication) cliHandler).username, ((CLIHandlerAuthentication) cliHandler).password, ((CLIHandlerAuthentication) cliHandler).roomType));
		CLIHandlerAuthentication.INPUT_HANDLERS_AUTHENTICATION_TYPE.put(2, cliHandler -> Client.getInstance().getConnectionHandler().sendRegistration(((CLIHandlerAuthentication) cliHandler).username, ((CLIHandlerAuthentication) cliHandler).password, ((CLIHandlerAuthentication) cliHandler).roomType));
	}

	private String username;
	private String password;
	private RoomType roomType;

	@Override
	public void execute()
	{
		this.askUsername();
		this.askPassword();
		this.askRoomType();
		this.askAuthentication();
	}

	@Override
	public CLIHandlerAuthentication newInstance()
	{
		return new CLIHandlerAuthentication();
	}

	private void askUsername()
	{
		Client.getLogger().log(Level.INFO, "\n\n\nEnter Username...");
		this.username = Client.getInstance().getCliScanner().nextLine();
	}

	private void askPassword()
	{
		Client.getLogger().log(Level.INFO, "\n\nEnter Password...");
		this.password = Client.getInstance().getCliScanner().nextLine();
	}

	private void askRoomType()
	{
		Client.getLogger().log(Level.INFO, "\n\nEnter Room Type...");
		Client.getLogger().log(Level.INFO, "1 - NORMAL");
		Client.getLogger().log(Level.INFO, "2 - EXTENDED");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerAuthentication.ROOM_TYPES.containsKey(Integer.parseInt(input)));
		this.roomType = CLIHandlerAuthentication.ROOM_TYPES.get(Integer.parseInt(input));
	}

	private void askAuthentication()
	{
		Client.getLogger().log(Level.INFO, "\n\nEnter Authentication Type...");
		Client.getLogger().log(Level.INFO, "1 - LOGIN");
		Client.getLogger().log(Level.INFO, "2 - REGISTRATION");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerAuthentication.INPUT_HANDLERS_AUTHENTICATION_TYPE.containsKey(Integer.parseInt(input)));
		CLIHandlerAuthentication.INPUT_HANDLERS_AUTHENTICATION_TYPE.get(Integer.parseInt(input)).execute(this);
	}
}
