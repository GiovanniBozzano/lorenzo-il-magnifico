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
import it.polimi.ingsw.lim.common.game.actions.ActionInformationMarket;
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
	private MarketSlot marketSlot;
	private FamilyMemberType familyMemberType;

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

	/**
	 * <p>Uses current available actions of the player to insert in a {@link
	 * Integer} {@link MarketSlot} {@link Map} the available market
	 * slots to perform an {@link ActionInformationMarket} and prints them
	 * and the corresponding choosing indexes on screen.
	 */
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

	/**
	 * <p>Asks which {@link MarketSlot} the player wants to use to perform
	 * the action and saves it.
	 */
	private void askMarketSlot()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.marketSlots.containsKey(Integer.parseInt(input)));
		this.marketSlot = this.marketSlots.get(Integer.parseInt(input));
	}

	/**
	 * <p>Uses current available actions of the player to insert in a {@link
	 * Integer} {@link FamilyMemberType} {@link Map} the available family
	 * members to perform an {@link ActionInformationMarket} and prints them
	 * and the corresponding choosing indexes on screen.
	 */
	private void showFamilyMembers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Family Member...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.MARKET)) {
			if (((AvailableActionMarket) availableAction).getMarketSlot() == this.marketSlot  && !this.familyMemberTypes.containsValue(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
				stringBuilder.append(Utils.createListElement(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType().name()));
				this.familyMemberTypes.put(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Asks which {@link FamilyMemberType} the player wants to use to perform
	 * the action and saves it.
	 */
	private void askFamilyMember()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.familyMemberTypes.containsKey(Integer.parseInt(input)));
		this.familyMemberType = this.familyMemberTypes.get(Integer.parseInt(input));
	}

	/**
	 * <p>Asks how many servants the player wants to use to increase the action
	 * value and sends the new {@link ActionInformationMarket} with the chosen
	 * values.
	 */
	private void askServants()
	{
		Client.getLogger().log(Level.INFO, "\n\nEnter Servants amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationMarket(this.familyMemberType, Integer.parseInt(input), this.marketSlot));
	}
}
