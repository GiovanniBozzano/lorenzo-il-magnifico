package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.Main;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerAvailableActions implements ICLIHandler
{
	private final Map<Integer, ActionType> availableActions = new HashMap<>();
	private static final Map<ActionType, IInputHandler> INPUT_HANDLER_ACTION_TYPE = new HashMap<>();

	static {
		CLIHandlerAvailableActions.INPUT_HANDLER_ACTION_TYPE.put(ActionType.SHOW_CARDS, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.SHOW_CARDS);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
		CLIHandlerAvailableActions.INPUT_HANDLER_ACTION_TYPE.put(ActionType.SHOW_LEADERS, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.SHOW_LEADERS);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
		CLIHandlerAvailableActions.INPUT_HANDLER_ACTION_TYPE.put(ActionType.COUNCIL_PALACE, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.COUNCIL_PALACE);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
		CLIHandlerAvailableActions.INPUT_HANDLER_ACTION_TYPE.put(ActionType.HARVEST, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.HARVEST);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
		CLIHandlerAvailableActions.INPUT_HANDLER_ACTION_TYPE.put(ActionType.MARKET, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.MARKET);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
		CLIHandlerAvailableActions.INPUT_HANDLER_ACTION_TYPE.put(ActionType.PICK_DEVELOPMENT_CARD, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.PICK_DEVELOPMENT_CARD);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
		CLIHandlerAvailableActions.INPUT_HANDLER_ACTION_TYPE.put(ActionType.PRODUCTION_START, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.PRODUCTION_START);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
		CLIHandlerAvailableActions.INPUT_HANDLER_ACTION_TYPE.put(ActionType.LEADER_ACTION, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.LEADER_ACTION);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
	}

	@Override
	public void execute()
	{
		this.availableActions.put(1, ActionType.SHOW_CARDS);
		this.availableActions.put(2, ActionType.SHOW_LEADERS);
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE).isEmpty()) {
			this.availableActions.put(availableActions.size() + 1, ActionType.COUNCIL_PALACE);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST).isEmpty()) {
			this.availableActions.put(availableActions.size() + 1, ActionType.HARVEST);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET).isEmpty()) {
			this.availableActions.put(availableActions.size() + 1, ActionType.MARKET);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD).isEmpty()) {
			this.availableActions.put(availableActions.size() + 1, ActionType.PICK_DEVELOPMENT_CARD);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START).isEmpty()) {
			this.availableActions.put(availableActions.size() + 1, ActionType.PRODUCTION_START);
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_ACTIVATE).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_DISCARD).isEmpty() || !GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_PLAY).isEmpty()) {
			this.availableActions.put(availableActions.size() + 1, ActionType.LEADER_ACTION);
		}
		this.showAvailableActions();
		this.askAction();
	}

	public void showAvailableActions()
	{
		for (Entry<Integer, ActionType> currentavailableAction : this.availableActions.entrySet()) {
			StringBuilder stringBuilder = new StringBuilder();
			Client.getLogger().log(Level.INFO, "============ {0} ============", new Object[] { currentavailableAction.getKey() });
			Client.getLogger().log(Level.INFO, currentavailableAction.getValue().toString());
			Client.getLogger().log(Level.INFO, stringBuilder.toString());
			Client.getLogger().log(Level.INFO, "=============================");
			stringBuilder.append("\n\n");
		}
	}

	private void askAction()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableActions.containsKey(Integer.parseInt(input)));
		CLIHandlerAvailableActions.INPUT_HANDLER_ACTION_TYPE.get(Integer.parseInt(input)).execute(this);	}

	@Override
	public CLIHandlerAvailableActions newInstance()
	{
		return new CLIHandlerAvailableActions();
	}
}
