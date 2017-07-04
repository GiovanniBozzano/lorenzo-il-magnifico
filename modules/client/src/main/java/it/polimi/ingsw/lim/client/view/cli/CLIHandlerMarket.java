package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.MarketSlot;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsMarket;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionFamilyMember;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionMarket;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerMarket implements ICLIHandler
{
	private final Map<Integer, FamilyMemberType> familyMemberTypes = new HashMap<>();
	private final Map<Integer, MarketSlot> marketSlots = new HashMap<>();
	private int marketSlot;
	private int familyMemberValue;

	@Override
	public void execute()
	{
		this.showMarketSlots();
		this.askMarketSlot();
		this.showFamilyMembers();
		this.askFamilyMember();
		this.askServants();
	}

	@Override
	public CLIHandlerMarket newInstance()
	{
		return new CLIHandlerMarket();
	}

	private void showMarketSlots()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Market Slot...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET)) {
			if (!this.marketSlots.containsValue(((AvailableActionMarket) availableAction).getMarketSlot())) {
				stringBuilder.append(Utils.createListElement(index, ((AvailableActionMarket) availableAction).getMarketSlot().name()));
				this.marketSlots.put(index, ((AvailableActionMarket) availableAction).getMarketSlot());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askMarketSlot()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.marketSlots.containsKey(Integer.parseInt(input)));
		this.marketSlot = Integer.parseInt(input);
	}

	private void showFamilyMembers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Family Member...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET)) {
			if (this.marketSlots.get(this.marketSlot) == ((AvailableActionMarket) availableAction).getMarketSlot() && !this.familyMemberTypes.containsValue(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
				stringBuilder.append(Utils.createListElement(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType().name()));
				this.familyMemberTypes.put(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askFamilyMember()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.familyMemberTypes.containsKey(Integer.parseInt(input)));
		this.familyMemberValue = Integer.parseInt(input);
	}

	private void askServants()
	{
		Client.getLogger().log(Level.INFO, "\n\nEnter Servants amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsMarket(this.familyMemberTypes.get(this.familyMemberValue), Integer.parseInt(input), this.marketSlots.get(this.marketSlot)));
	}
}
