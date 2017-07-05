package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class CLIHandlerShowBoardDevelopmentCards implements ICLIHandler
{
	private static final Map<Integer, CardType> CARD_TYPE_CHOICE = new HashMap<>();

	static {
		CLIHandlerShowBoardDevelopmentCards.CARD_TYPE_CHOICE.put(1, CardType.BUILDING);
		CLIHandlerShowBoardDevelopmentCards.CARD_TYPE_CHOICE.put(2, CardType.CHARACTER);
		CLIHandlerShowBoardDevelopmentCards.CARD_TYPE_CHOICE.put(3, CardType.TERRITORY);
		CLIHandlerShowBoardDevelopmentCards.CARD_TYPE_CHOICE.put(4, CardType.VENTURE);
	}

	private static void showDevelopmentCards(CardType cardType)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (Entry<Row, Integer> developmentCardInteger : GameStatus.getInstance().getCurrentDevelopmentCards().get(cardType).entrySet()) {
			stringBuilder.append("\n\n\nROW: ");
			stringBuilder.append(developmentCardInteger.getKey().name());
			stringBuilder.append('\n');
			stringBuilder.append(GameStatus.getInstance().getDevelopmentCards().get(cardType).get(developmentCardInteger.getValue()).getInformation());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	@Override
	public void execute()
	{
		this.askDevelopmentCardsType();
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public CLIHandlerShowBoardDevelopmentCards newInstance()
	{
		return new CLIHandlerShowBoardDevelopmentCards();
	}

	private void askDevelopmentCardsType()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Card Type...");
		for (Entry<Integer, CardType> cardType : CLIHandlerShowBoardDevelopmentCards.CARD_TYPE_CHOICE.entrySet()) {
			stringBuilder.append(cardType.getKey());
			stringBuilder.append(" - ");
			stringBuilder.append(CommonUtils.getCardTypesNames().get(cardType.getValue()));
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowBoardDevelopmentCards.CARD_TYPE_CHOICE.containsKey(Integer.parseInt(input)));
		CLIHandlerShowBoardDevelopmentCards.showDevelopmentCards(CLIHandlerShowBoardDevelopmentCards.CARD_TYPE_CHOICE.get(Integer.parseInt(input)));
	}
}
