package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerAvailableActions implements ICLIHandler
{
	private final static Map<CLIStatus, String> ACTION_NAMES = new EnumMap<>(CLIStatus.class);

	static {
		ACTION_NAMES.put(CLIStatus.SHOW_DEVELOPMENT_CARDS, "Show Development Cards");
		ACTION_NAMES.put(CLIStatus.SHOW_LEADERS, "Show Leaders");
		ACTION_NAMES.put(CLIStatus.COUNCIL_PALACE, "Council Palace");
		ACTION_NAMES.put(CLIStatus.HARVEST, "Harvest");
		ACTION_NAMES.put(CLIStatus.MARKET, "Market");
		ACTION_NAMES.put(CLIStatus.PICK_DEVELOPMENT_CARD, "Pick Development Card");
		ACTION_NAMES.put(CLIStatus.PRODUCTION_START, "Start Production");
		ACTION_NAMES.put(CLIStatus.LEADER_ACTIVATE, "Activate Leader");
		ACTION_NAMES.put(CLIStatus.LEADER_DISCARD, "Discard Leader");
		ACTION_NAMES.put(CLIStatus.LEADER_PLAY, "Play Leader");
	}

	private final Map<Integer, CLIStatus> availableActions = new HashMap<>();

	@Override
	public void execute()
	{
		this.availableActions.put(1, CLIStatus.SHOW_DEVELOPMENT_CARDS);
		this.availableActions.put(2, CLIStatus.SHOW_LEADERS);
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE).isEmpty()) {
			this.availableActions.put(3, CLIStatus.COUNCIL_PALACE);
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
		this.showAvailableActions();
		this.askAction();
	}

	@Override
	public CLIHandlerAvailableActions newInstance()
	{
		return new CLIHandlerAvailableActions();
	}

	private void showAvailableActions()
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean firstLine = true;
		for (Entry<Integer, CLIStatus> availableAction : this.availableActions.entrySet()) {
			if (!firstLine) {
				stringBuilder.append('\n');
			} else {
				firstLine = false;
			}
			stringBuilder.append("============= ");
			stringBuilder.append(availableAction.getKey());
			stringBuilder.append(" =============\n");
			stringBuilder.append(CLIHandlerAvailableActions.ACTION_NAMES.get(availableAction.getValue()));
			stringBuilder.append("=============================");
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askAction()
	{
		Client.getLogger().log(Level.INFO, "Enter Action choice...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableActions.containsKey(Integer.parseInt(input)));
		Client.getInstance().setCliStatus(this.availableActions.get(Integer.parseInt(input)));
		Client.getInstance().getCliListener().execute(() -> {
			ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
			Client.getInstance().setCurrentCliHandler(newCliHandler);
			newCliHandler.execute();
		});
	}
}
