package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerShowOwnBoard implements ICLIHandler
{
	private static final Map<Integer, Runnable> PLAYER_FAMILY_MEMBERS = new HashMap<>();

	static {
		CLIHandlerShowOwnBoard.PLAYER_FAMILY_MEMBERS.put(1, CLIHandlerShowOwnBoard::showPlayerFamilyMembers);
	}

	@Override
	public void execute()
	{
		this.askOwnInformation();
	}

	@Override
	public CLIHandlerShowOwnLeaders newInstance()
	{
		return new CLIHandlerShowOwnLeaders();
	}

	private static void showPlayerFamilyMembers()
	{
		for (Entry<FamilyMemberType, BoardPosition> familyMemberTypeBoardPosition : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getFamilyMembersPositions().entrySet()) {
			Client.getLogger().log(Level.INFO, "{0}===", new Object[] { familyMemberTypeBoardPosition.getKey().name() });
			Client.getLogger().log(Level.INFO, "{0}\n", new Object[] { familyMemberTypeBoardPosition.getValue().name() });
		}
	}

	private void showPlayerResources(Map<ResourceType, Integer> information)
	{
		for (ResourceType resourceType : information.keySet()) {
			Client.getLogger().log(Level.INFO, "{0}=", new Object[] { resourceType });
			Client.getLogger().log(Level.INFO, "{0}\n", new Object[] { information.get(resourceType) });
		}
	}

	private void showPlayerDevelopmentCards(List<Integer> information)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (int index : information) {
			Client.getLogger().log(Level.INFO, "{0}===========", new Object[] { index });
			stringBuilder.append(GameStatus.getInstance().getDevelopmentCards().get(cardType).get(index).getInformations());
		}
	}

	private void askOwnInformation()
	{
		Client.getLogger().log(Level.INFO, "Enter what you want to see...");
		Client.getLogger().log(Level.INFO, "1 - Own Family Members' Positions");
		Client.getLogger().log(Level.INFO, "2 - Own Resources");
		Client.getLogger().log(Level.INFO, "3 - Own Building Cards");
		Client.getLogger().log(Level.INFO, "4 - Own Character Cards");
		Client.getLogger().log(Level.INFO, "5 - Own Territory Cards");
		Client.getLogger().log(Level.INFO, "6 - Own Venture Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnBoard.PLAYER_FAMILY_MEMBERS.containsKey(Integer.parseInt(input)));
		CLIHandlerShowOwnBoard.PLAYER_FAMILY_MEMBERS.get(Integer.parseInt(input)).run();
	}
}