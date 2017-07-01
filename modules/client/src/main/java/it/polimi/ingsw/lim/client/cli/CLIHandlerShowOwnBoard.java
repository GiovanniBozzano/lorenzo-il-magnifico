package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerShowOwnBoard implements ICLIHandler
{
	private static final Map<Integer, Runnable> PLAYER_INFORMATION = new HashMap<>();
	private static final Map<Integer, CardType> CARD_TYPE_CHOICE = new HashMap<>();

	static {
		CARD_TYPE_CHOICE.put(1, CardType.BUILDING);
		CARD_TYPE_CHOICE.put(2, CardType.CHARACTER);
		CARD_TYPE_CHOICE.put(3, CardType.TERRITORY);
		CARD_TYPE_CHOICE.put(4, CardType.VENTURE);
	}

	static {
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.put(1, CLIHandlerShowOwnBoard::showPlayerFamilyMembers);
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.put(2, CLIHandlerShowOwnBoard::showPlayerResources);
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.put(3, CLIHandlerShowOwnBoard::showPlayerDevelopmentCards);
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

	private static void showPlayerResources()
	{
		for (Entry<ResourceType, Integer> resourceTypeAmount : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().entrySet()) {
			Client.getLogger().log(Level.INFO, "{0}=", new Object[] { CommonUtils.getResourcesTypesNames().get(resourceTypeAmount.getKey()) });
			Client.getLogger().log(Level.INFO, "{0}\n", new Object[] { resourceTypeAmount.getValue() });
		}
	}

	private static void showPlayerDevelopmentCards()
	{
		Client.getLogger().log(Level.INFO, "Enter Card Type...");
		Client.getLogger().log(Level.INFO, "1 - Own Building Cards");
		Client.getLogger().log(Level.INFO, "2 - Own Character Cards");
		Client.getLogger().log(Level.INFO, "3 - Own Territory Cards");
		Client.getLogger().log(Level.INFO, "4 - Own Venture Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.containsKey(Integer.parseInt(input)));
		StringBuilder stringBuilder = new StringBuilder();
		int index = 0;
		for (int value : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCards().get(CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input)))) {
			index++;
			stringBuilder.append(index);
			stringBuilder.append("===========\n");
			stringBuilder.append(GameStatus.getInstance().getDevelopmentCards().get(CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input))).get(value).getInformations());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askOwnInformation()
	{
		Client.getLogger().log(Level.INFO, "Enter what you want to see...");
		Client.getLogger().log(Level.INFO, "1 - Own Family Members' Positions");
		Client.getLogger().log(Level.INFO, "2 - Own Resources");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnBoard.PLAYER_INFORMATION.containsKey(Integer.parseInt(input)));
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.get(Integer.parseInt(input)).run();
	}
}