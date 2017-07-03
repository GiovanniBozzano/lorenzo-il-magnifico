package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
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
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class CLIHandlerShowOwnBoard implements ICLIHandler
{
	private static final Map<Integer, Runnable> PLAYER_INFORMATION = new HashMap<>();

	static {
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.put(1, CLIHandlerShowOwnBoard::showPlayerFamilyMembers);
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.put(2, CLIHandlerShowOwnBoard::showPlayerResources);
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.put(3, CLIHandlerShowOwnBoard::showPlayerDevelopmentCards);
	}

	private static final Map<Integer, CardType> CARD_TYPE_CHOICE = new HashMap<>();

	static {
		CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.put(1, CardType.BUILDING);
		CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.put(2, CardType.CHARACTER);
		CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.put(3, CardType.TERRITORY);
		CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.put(4, CardType.VENTURE);
	}

	@Override
	public void execute()
	{
		this.askOwnInformation();
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public CLIHandlerShowOwnBoard newInstance()
	{
		return new CLIHandlerShowOwnBoard();
	}

	private void askOwnInformation()
	{
		Client.getLogger().log(Level.INFO, "Enter what you want to see...");
		Client.getLogger().log(Level.INFO, "1 - Own Family Members' Positions");
		Client.getLogger().log(Level.INFO, "2 - Own Resources");
		Client.getLogger().log(Level.INFO, "2 - Own Development Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnBoard.PLAYER_INFORMATION.containsKey(Integer.parseInt(input)));
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.get(Integer.parseInt(input)).run();
	}

	private static void showPlayerFamilyMembers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Player Family Memebers:");
		for (Entry<FamilyMemberType, BoardPosition> familyMemberTypeBoardPosition : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getFamilyMembersPositions().entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append(familyMemberTypeBoardPosition.getKey().name());
			stringBuilder.append(" ===");
			stringBuilder.append(familyMemberTypeBoardPosition.getValue().name());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private static void showPlayerResources()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Player Resources:");
		for (Entry<ResourceType, Integer> resourceTypeAmount : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceTypeAmount.getKey()));
			stringBuilder.append(" ===");
			stringBuilder.append(resourceTypeAmount.getValue());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
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
		stringBuilder.append(CommonUtils.getCardTypesNames().get(CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input))));
		stringBuilder.append(" cards:");
		for (int value : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCards().get(CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input)))) {
			stringBuilder.append("\n========\n");
			stringBuilder.append(GameStatus.getInstance().getDevelopmentCards().get(CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input))).get(value).getInformations());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}
}