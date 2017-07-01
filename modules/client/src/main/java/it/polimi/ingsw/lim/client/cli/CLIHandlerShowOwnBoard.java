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
import java.util.logging.Level;

public class CLIHandlerShowOwnBoard implements ICLIHandler
{
	private static final Map<Integer, Map<FamilyMemberType, BoardPosition>> PLAYER_FAMILY_MEMBERS = new HashMap<>();
	private static final Map<Integer, Map<ResourceType, Integer>> PLAYER_RESOURCES = new HashMap<>();
	private static Map<Integer, List<Integer>> PLAYER_DEVELOPMENT_CARDS = new HashMap<>();

	static {
		PLAYER_FAMILY_MEMBERS.put(1, GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getFamilyMembersPositions());
	}

	static {
		PLAYER_RESOURCES.put(2, GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts());
	}

	static {
		PLAYER_DEVELOPMENT_CARDS.put(3, GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCardsBuilding());
		PLAYER_DEVELOPMENT_CARDS.put(4, GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCardsCharacter());
		PLAYER_DEVELOPMENT_CARDS.put(5, GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCardsTerritory());
		PLAYER_DEVELOPMENT_CARDS.put(6, GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCardsVenture());
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

	private void showPlayerFamilyMembers(Map<FamilyMemberType, BoardPosition> information)
	{
		for (FamilyMemberType familyMemberType : information.keySet()) {
			Client.getLogger().log(Level.INFO, "{0}===", new Object[] { familyMemberType });
			Client.getLogger().log(Level.INFO, "{0}\n", new Object[] { information.get(familyMemberType) });
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
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnBoard.PLAYER_FAMILY_MEMBERS.containsKey(Integer.parseInt(input)) || !CLIHandlerShowOwnBoard.PLAYER_RESOURCES.containsKey(Integer.parseInt(input)) || !CLIHandlerShowOwnBoard.PLAYER_DEVELOPMENT_CARDS.containsKey(Integer.parseInt(input)));
		if (CLIHandlerShowOwnBoard.PLAYER_FAMILY_MEMBERS.containsKey(Integer.parseInt(input))) {
			this.showPlayerFamilyMembers(CLIHandlerShowOwnBoard.PLAYER_FAMILY_MEMBERS.get(Integer.parseInt(input)));
		} else if (CLIHandlerShowOwnBoard.PLAYER_RESOURCES.containsKey(Integer.parseInt(input))) {
			this.showPlayerResources(CLIHandlerShowOwnBoard.PLAYER_RESOURCES.get(Integer.parseInt(input)));
		} else {
			this.showPlayerDevelopmentCards(CLIHandlerShowOwnBoard.PLAYER_DEVELOPMENT_CARDS.get(Integer.parseInt(input)));
		}
	}
}