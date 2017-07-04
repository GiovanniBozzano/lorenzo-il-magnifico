package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

/**
 * The player has to choose which action he wants to perform.
 */
public class CLIHandlerAvailableActions implements ICLIHandler
{
	private static final Map<CLIStatus, String> ACTION_NAMES = new EnumMap<>(CLIStatus.class);

	static {
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.SHOW_BOARD_DEVELOPMENT_CARDS, "Show Board Development Cards");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.SHOW_OWN_BOARD, "Show Own Board");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.SHOW_OWN_LEADER_CARDS, "Show Own Leader Cards");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.SHOW_OTHER_BOARD, "Show Other Board");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.SHOW_OTHER_LEADER_CARDS, "Show Other Leader Cards");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.COUNCIL_PALACE, "Council Palace");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.HARVEST, "Harvest");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.MARKET, "Market");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.PICK_DEVELOPMENT_CARD, "Pick Development Card");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.PRODUCTION_START, "Start Production");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.LEADER_ACTIVATE, "Activate Leader Card");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.LEADER_DISCARD, "Discard Leader Card");
		CLIHandlerAvailableActions.ACTION_NAMES.put(CLIStatus.LEADER_PLAY, "Play Leader Card");
	}

	private final Map<Integer, CLIStatus> availableActions = new HashMap<>();

	@Override
	public void execute()
	{
		this.availableActions.put(1, CLIStatus.SHOW_BOARD_DEVELOPMENT_CARDS);
		this.availableActions.put(2, CLIStatus.SHOW_OWN_BOARD);
		this.availableActions.put(3, CLIStatus.SHOW_OWN_LEADER_CARDS);
		this.availableActions.put(4, CLIStatus.SHOW_OTHER_BOARD);
		this.availableActions.put(5, CLIStatus.SHOW_OTHER_LEADER_CARDS);
		this.showAvailableActions();
		this.askAction();
	}

	@Override
	public CLIHandlerAvailableActions newInstance()
	{
		return new CLIHandlerAvailableActions();
	}

	/**
	 * <p>Uses current available actions of the player to insert in a {@link
	 * Integer} {@link CLIStatus} {@link Map} the CLIStatus to perform the
	 * action and prints the corresponding {@link String} and their choosing
	 * indexes on screen.
	 */
	private void showAvailableActions()
	{
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.COUNCIL_PALACE);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.HARVEST);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.MARKET);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.PICK_DEVELOPMENT_CARD);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.PRODUCTION_START);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_ACTIVATE).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.LEADER_ACTIVATE);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_DISCARD).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.LEADER_DISCARD);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_PLAY).isEmpty()) {
			this.availableActions.put(this.availableActions.size() + 1, CLIStatus.LEADER_PLAY);
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Action...");
		for (Entry<Integer, CLIStatus> availableAction : this.availableActions.entrySet()) {
			stringBuilder.append(Utils.createListElement(availableAction.getKey(), CLIHandlerAvailableActions.ACTION_NAMES.get(availableAction.getValue())));
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Asks which CLIStatus index the player wants to choose and
	 * execute the corresponding CLIStatus.
	 */
	private void askAction()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableActions.containsKey(Integer.parseInt(input)));
		Client.getInstance().setCliStatus(this.availableActions.get(Integer.parseInt(input)));
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).execute());
	}
}
