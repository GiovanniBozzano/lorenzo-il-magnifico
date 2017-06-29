package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerAvailableActions implements ICLIHandler
{
	private final Map<Integer, IInputHandler> inputHandlerActionType = new HashMap<>();

	@Override
	public void execute()
	{
		this.inputHandlerActionType.put(1, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.SHOW_CARDS);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
		this.inputHandlerActionType.put(2, cliHandler -> {
			Client.getInstance().setCliStatus(CLIStatus.SHOW_LEADERS);
			Client.getInstance().getCliListener().execute(() -> {
				ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
				Client.getInstance().setCurrentCliHandler(newCliHandler);
				newCliHandler.execute();
			});
		});
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE).isEmpty()) {
			this.inputHandlerActionType.put(this.inputHandlerActionType.size() + 1, cliHandler -> {
				Client.getInstance().setCliStatus(CLIStatus.COUNCIL_PALACE);
				Client.getInstance().getCliListener().execute(() -> {
					ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
					Client.getInstance().setCurrentCliHandler(newCliHandler);
					newCliHandler.execute();
				});
			});
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST).isEmpty()) {
			this.inputHandlerActionType.put(this.inputHandlerActionType.size() + 1, cliHandler -> {
				Client.getInstance().setCliStatus(CLIStatus.HARVEST);
				Client.getInstance().getCliListener().execute(() -> {
					ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
					Client.getInstance().setCurrentCliHandler(newCliHandler);
					newCliHandler.execute();
				});
			});
		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET).isEmpty()) {
			this.inputHandlerActionType.put(this.inputHandlerActionType.size() + 1, cliHandler -> {
				Client.getInstance().setCliStatus(CLIStatus.MARKET);
				Client.getInstance().getCliListener().execute(() -> {
					ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
					Client.getInstance().setCurrentCliHandler(newCliHandler);
					newCliHandler.execute();
				});
			});		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD).isEmpty()) {
			this.inputHandlerActionType.put(this.inputHandlerActionType.size() + 1, cliHandler -> {
				Client.getInstance().setCliStatus(CLIStatus.PICK_DEVELOPMENT_CARD);
				Client.getInstance().getCliListener().execute(() -> {
					ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
					Client.getInstance().setCurrentCliHandler(newCliHandler);
					newCliHandler.execute();
				});
			});		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START).isEmpty()) {
			this.inputHandlerActionType.put(this.inputHandlerActionType.size() + 1, cliHandler -> {
				Client.getInstance().setCliStatus(CLIStatus.PRODUCTION_START);
				Client.getInstance().getCliListener().execute(() -> {
					ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
					Client.getInstance().setCurrentCliHandler(newCliHandler);
					newCliHandler.execute();
				});
			});		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_ACTIVATE).isEmpty()) {
			this.inputHandlerActionType.put(this.inputHandlerActionType.size() + 1, cliHandler -> {
				Client.getInstance().setCliStatus(CLIStatus.LEADER_ACTIVATE);
				Client.getInstance().getCliListener().execute(() -> {
					ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
					Client.getInstance().setCurrentCliHandler(newCliHandler);
					newCliHandler.execute();
				});
			});		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_DISCARD).isEmpty()) {
			this.inputHandlerActionType.put(this.inputHandlerActionType.size() + 1, cliHandler -> {
				Client.getInstance().setCliStatus(CLIStatus.LEADER_DISCARD);
				Client.getInstance().getCliListener().execute(() -> {
					ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
					Client.getInstance().setCurrentCliHandler(newCliHandler);
					newCliHandler.execute();
				});
			});		}
		if (!GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.LEADER_PLAY).isEmpty()) {
			this.inputHandlerActionType.put(this.inputHandlerActionType.size() + 1, cliHandler -> {
				Client.getInstance().setCliStatus(CLIStatus.LEADER_PLAY);
				Client.getInstance().getCliListener().execute(() -> {
					ICLIHandler newCliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
					Client.getInstance().setCurrentCliHandler(newCliHandler);
					newCliHandler.execute();
				});
			});		}
		this.showAvailableActions();
		this.askAction();
	}

	public void showAvailableActions()
	{
		for (Entry<Integer, IInputHandler > currentavailableAction : this.inputHandlerActionType.entrySet()) {
			StringBuilder stringBuilder = new StringBuilder();
			Client.getLogger().log(Level.INFO, "============= {0} =============", new Object[] { currentavailableAction.getKey() });
			Client.getLogger().log(Level.INFO, currentavailableAction.getValue().toString());
			Client.getLogger().log(Level.INFO, stringBuilder.toString());
			Client.getLogger().log(Level.INFO, "=============================");
			stringBuilder.append("\n\n");
		}
	}

	private void askAction()
	{
		Client.getLogger().log(Level.INFO, "Enter Action choice...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.inputHandlerActionType.containsKey(Integer.parseInt(input)));
		this.inputHandlerActionType.get(Integer.parseInt(input)).execute(this);
	}

	@Override
	public CLIHandlerAvailableActions newInstance()
	{
		return new CLIHandlerAvailableActions();
	}
}
