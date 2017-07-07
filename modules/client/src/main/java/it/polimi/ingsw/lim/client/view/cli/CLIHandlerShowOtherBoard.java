package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
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

public class CLIHandlerShowOtherBoard implements ICLIHandler
{
	private static final Map<Integer, IInputHandler> PLAYER_INFORMATION = new HashMap<>();
	private static final Map<Integer, CardType> CARD_TYPE_CHOICE = new HashMap<>();

	static {
		CLIHandlerShowOtherBoard.PLAYER_INFORMATION.put(1, cliHandler -> ((CLIHandlerShowOtherBoard) cliHandler).showFamilyMemberTypes());
		CLIHandlerShowOtherBoard.PLAYER_INFORMATION.put(2, cliHandler -> ((CLIHandlerShowOtherBoard) cliHandler).showResources());
		CLIHandlerShowOtherBoard.PLAYER_INFORMATION.put(3, cliHandler -> ((CLIHandlerShowOtherBoard) cliHandler).showDevelopmentCards());
	}

	static {
		CLIHandlerShowOtherBoard.CARD_TYPE_CHOICE.put(1, CardType.BUILDING);
		CLIHandlerShowOtherBoard.CARD_TYPE_CHOICE.put(2, CardType.CHARACTER);
		CLIHandlerShowOtherBoard.CARD_TYPE_CHOICE.put(3, CardType.TERRITORY);
		CLIHandlerShowOtherBoard.CARD_TYPE_CHOICE.put(4, CardType.VENTURE);
	}

	private final Map<Integer, Integer> availableOtherPlayers = new HashMap<>();
	private int chosenOtherPlayer;

	@Override
	public void execute()
	{
		this.showOtherPlayers();
		this.askOtherPlayer();
		this.askInformation();
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public CLIHandlerShowOtherBoard newInstance()
	{
		return new CLIHandlerShowOtherBoard();
	}

	private void showOtherPlayers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Other Player...");
		int index = 1;
		for (Entry<Integer, PlayerData> playerData : GameStatus.getInstance().getCurrentPlayersData().entrySet()) {
			if (playerData.getKey() != GameStatus.getInstance().getOwnPlayerIndex()) {
				stringBuilder.append(Utils.createListElement(index, playerData.getValue().getUsername()));
				this.availableOtherPlayers.put(index, playerData.getKey());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askOtherPlayer()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableOtherPlayers.containsKey(Integer.parseInt(input)));
		this.chosenOtherPlayer = this.availableOtherPlayers.get(Integer.parseInt(input));
	}

	private void askInformation()
	{
		Client.getLogger().log(Level.INFO, "\n\n\nEnter what you want to see...");
		Client.getLogger().log(Level.INFO, "1 - His Family Members' Positions");
		Client.getLogger().log(Level.INFO, "2 - His Resources");
		Client.getLogger().log(Level.INFO, "3 - His Development Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOtherBoard.PLAYER_INFORMATION.containsKey(Integer.parseInt(input)));
		CLIHandlerShowOtherBoard.PLAYER_INFORMATION.get(Integer.parseInt(input)).execute(this);
	}

	private void showFamilyMemberTypes()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nFamily Members:");
		for (Entry<FamilyMemberType, BoardPosition> familyMemberTypeBoardPosition : GameStatus.getInstance().getCurrentPlayersData().get(this.chosenOtherPlayer).getFamilyMemberTypesPositions().entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append(familyMemberTypeBoardPosition.getKey().name());
			stringBuilder.append(" === ");
			stringBuilder.append(familyMemberTypeBoardPosition.getValue().name());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void showResources()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nResources:");
		for (Entry<ResourceType, Integer> resourceTypeAmount : GameStatus.getInstance().getCurrentPlayersData().get(this.chosenOtherPlayer).getResourceAmounts().entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append(CommonUtils.getResourcesTypesNames().get(resourceTypeAmount.getKey()));
			stringBuilder.append(" === ");
			stringBuilder.append(resourceTypeAmount.getValue());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void showDevelopmentCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Card Type...");
		for (Entry<Integer, CardType> cardType : CLIHandlerShowOtherBoard.CARD_TYPE_CHOICE.entrySet()) {
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
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOtherBoard.CARD_TYPE_CHOICE.containsKey(Integer.parseInt(input)));
		stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n");
		stringBuilder.append(CommonUtils.getCardTypesNames().get(CLIHandlerShowOtherBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input))));
		stringBuilder.append(" cards:");
		for (int value : GameStatus.getInstance().getCurrentPlayersData().get(this.chosenOtherPlayer).getDevelopmentCards().get(CLIHandlerShowOtherBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input)))) {
			stringBuilder.append("\n\n========\n");
			stringBuilder.append(GameStatus.getInstance().getDevelopmentCards().get(CLIHandlerShowOtherBoard.CARD_TYPE_CHOICE.get(Integer.parseInt(input))).get(value).getInformation());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}
}
