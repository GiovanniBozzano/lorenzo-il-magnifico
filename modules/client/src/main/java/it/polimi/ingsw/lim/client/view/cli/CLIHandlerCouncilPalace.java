package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationCouncilPalace;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionFamilyMember;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerCouncilPalace implements ICLIHandler
{
	private final Map<Integer, FamilyMemberType> familyMemberTypes = new HashMap<>();
	private int familyMemberValue;

	@Override
	public void execute()
	{
		this.showFamilyMembers();
		this.askFamilyMember();
		this.askServants();
	}

	@Override
	public CLIHandlerCouncilPalace newInstance()
	{
		return new CLIHandlerCouncilPalace();
	}

	/**
	 * <p>Uses current available actions of the player to insert in a {@link
	 * Integer} {@link FamilyMemberType} {@link Map} the available family
	 * members to perform an {@link ActionInformationCouncilPalace} and prints
	 * them and the corresponding choosing indexes on screen.
	 */
	private void showFamilyMembers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Family Member Choice...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.COUNCIL_PALACE)) {
			if (!this.familyMemberTypes.containsValue(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
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
		this.familyMemberValue = Integer.parseInt(input);
	}

	/**
	 * <p>Asks how many servants the player wants to use to increase the action
	 * value and sends the new {@link ActionInformationCouncilPalace} with the
	 * chosen values.
	 */
	private void askServants()
	{
		Client.getLogger().log(Level.INFO, "\n\nEnter Servant Amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationCouncilPalace(this.familyMemberTypes.get(this.familyMemberValue), Integer.parseInt(input)));
	}
}
