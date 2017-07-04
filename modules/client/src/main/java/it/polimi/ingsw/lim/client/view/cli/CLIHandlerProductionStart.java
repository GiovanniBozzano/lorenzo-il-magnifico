package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsProductionStart;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionFamilyMember;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerProductionStart implements ICLIHandler
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
	public CLIHandlerProductionStart newInstance()
	{
		return new CLIHandlerProductionStart();
	}

	private void showFamilyMembers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Family Member...");
		int index = 0;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PRODUCTION_START)) {
			if (!this.familyMemberTypes.containsValue(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
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
		Client.getLogger().log(Level.INFO, "Enter Servants amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsProductionStart(this.familyMemberTypes.get(this.familyMemberValue), Integer.parseInt(input)));
	}
}
