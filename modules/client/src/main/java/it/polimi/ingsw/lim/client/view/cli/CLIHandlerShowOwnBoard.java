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
	private static final Map<Integer, CardType> CARD_TYPE_CHOICE = new HashMap<>();

	static {
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.put(1, CLIHandlerShowOwnBoard::showFamilyMemberTypes);
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.put(2, CLIHandlerShowOwnBoard::showResources);
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.put(3, CLIHandlerShowOwnBoard::showDevelopmentCards);
		CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.put(1, CardType.BUILDING);
		CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.put(2, CardType.CHARACTER);
		CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.put(3, CardType.TERRITORY);
		CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.put(4, CardType.VENTURE);
	}

	/**
	 * <p>Uses current player's {@code familyMemberTypePositions} to insert in a
	 * {@link FamilyMemberType} {@link BoardPosition} {@link Map} the family
	 * members and their positions on the board and prints them and the
	 * corresponding information on screen.
	 */
	private static void showFamilyMemberTypes()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nFamily Members:");
		for (Entry<FamilyMemberType, BoardPosition> familyMemberTypeBoardPosition : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getFamilyMemberTypesPositions().entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append(familyMemberTypeBoardPosition.getKey().name());
			stringBuilder.append(" === ");
			stringBuilder.append(familyMemberTypeBoardPosition.getValue().name());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Uses current player's {@code resourceAmounts} to insert in a {@link
	 * ResourceType} {@link Integer} {@link Map} the resources and their amounts
	 * and prints them and the corresponding information on screen.
	 */
	private static void showResources()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nResources:");
		for (Entry<ResourceType, Integer> resourceTypeAmount : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceTypeAmount.getKey()));
			stringBuilder.append(" === ");
			stringBuilder.append(resourceTypeAmount.getValue());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Uses a {@link Integer} {@link CardType} {@link Map} to ask which
	 * {@link CardType} the player wants to choose to perform a show development
	 * cards action. Then uses the player data to get the {@link Map} of the
	 * current development cards of the player and the chosen {@link CardType}
	 * to print the corresponding development cards and their {@link Integer} on
	 * screen.
	 */
	private static void showDevelopmentCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Card Type...");
		for (Entry<Integer, CardType> cardType : CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append(cardType.getKey());
			stringBuilder.append(" - ");
			stringBuilder.append(CommonUtils.getCardTypesNames().get(cardType.getValue()));
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.containsKey(Integer.parseInt(input)));
		stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n");
		stringBuilder.append(CommonUtils.getCardTypesNames().get(CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input))));
		stringBuilder.append(" cards:");
		for (int value : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCards().get(CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input)))) {
			stringBuilder.append("\n\n========\n");
			stringBuilder.append(GameStatus.getInstance().getDevelopmentCards().get(CLIHandlerShowOwnBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input))).get(value).getInformation());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Uses the {@link Integer} {@link Runnable} {@link Map} of player
	 * information to ask which one the player wants to see and sends the
	 * corresponding action with the chosen information.
	 */
	private static void askInformation()
	{
		Client.getLogger().log(Level.INFO, "\n\n\nEnter what you want to see...");
		Client.getLogger().log(Level.INFO, "1 - Own Family Members' Positions");
		Client.getLogger().log(Level.INFO, "2 - Own Resources");
		Client.getLogger().log(Level.INFO, "3 - Own Development Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnBoard.PLAYER_INFORMATION.containsKey(Integer.parseInt(input)));
		CLIHandlerShowOwnBoard.PLAYER_INFORMATION.get(Integer.parseInt(input)).run();
	}

	@Override
	public void execute()
	{
		CLIHandlerShowOwnBoard.askInformation();
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
}