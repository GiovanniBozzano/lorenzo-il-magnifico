package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
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
	public CLIHandlerInterfaceChoice newInstance()
	{
		return new CLIHandlerInterfaceChoice();
	}

	private void showMarketSlots()
	{
		StringBuilder stringBuilder = new StringBuilder();
		int index = 1;
		boolean firstLine = true;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET)) {
			if (!this.marketSlots.containsValue(((AvailableActionMarket) availableAction).getMarketSlot())) {
				if (!firstLine) {
					stringBuilder.append('\n');
				} else {
					firstLine = false;
				}
				this.marketSlots.put(index, ((AvailableActionMarket) availableAction).getMarketSlot());
				stringBuilder.append(index);
				stringBuilder.append(" ========");
				stringBuilder.append(((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askMarketSlot()
	{
		Client.getLogger().log(Level.INFO, "Enter Market Slot Choice...");
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
		int index = 1;
		boolean firstLine = true;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET)) {
			if (this.marketSlots.get(this.marketSlot) == ((AvailableActionMarket) availableAction).getMarketSlot() && !this.familyMemberTypes.containsValue(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
				if (!firstLine) {
					stringBuilder.append('\n');
				} else {
					firstLine = false;
				}
				this.familyMemberTypes.put(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				stringBuilder.append(index);
				stringBuilder.append(" ========\n");
				stringBuilder.append(((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askFamilyMember()
	{
		Client.getLogger().log(Level.INFO, "Enter Family Member Choice...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.familyMemberTypes.containsKey(Integer.parseInt(input)));
		this.familyMemberValue = Integer.parseInt(input);
	}

	private void askServants()
	{
		Client.getLogger().log(Level.INFO, "Enter Servant Amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsMarket(this.familyMemberTypes.get(this.familyMemberValue), Integer.parseInt(input), this.marketSlots.get(this.marketSlot)));
	}
}
