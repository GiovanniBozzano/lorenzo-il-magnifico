package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsHarvest;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionFamilyMember;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerHarvest implements ICLIHandler
{
	Map<Integer, FamilyMemberType> familyMemberTypes = new HashMap<>();
	int familyMemberValue;
	int servantAmount;

	@Override
	public void execute()
	{
		this.showFamilyMembers();
		this.askFamilyMember();
		this.askServants();
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsHarvest(this.familyMemberTypes.get(familyMemberValue), servantAmount));
	}

	private void showFamilyMembers()
	{
		int index = 0;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.HARVEST)) {
			if (!this.familyMemberTypes.containsValue(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
				index++;
				this.familyMemberTypes.put(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				Client.getLogger().log(Level.INFO, "{0}========", new Object[] { index });
				Client.getLogger().log(Level.INFO, "{0}\n", new Object[] { ((AvailableActionFamilyMember) availableAction).getFamilyMemberType() });
			}
		}
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
		this.servantAmount = Integer.parseInt(input);
	}

	@Override
	public CLIHandlerInterfaceChoice newInstance()
	{
		return new CLIHandlerInterfaceChoice();
	}
}

