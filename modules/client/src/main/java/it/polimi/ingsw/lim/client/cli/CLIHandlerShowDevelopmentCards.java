package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerShowDevelopmentCards implements ICLIHandler
{
	private final static Map<Integer, CardType> CARD_TYPE_CHOICE = new HashMap<>();

	static {
		CARD_TYPE_CHOICE.put(1, CardType.BUILDING);
		CARD_TYPE_CHOICE.put(2, CardType.CHARACTER);
		CARD_TYPE_CHOICE.put(3, CardType.TERRITORY);
		CARD_TYPE_CHOICE.put(4, CardType.VENTURE);
	}

	@Override
	public void execute()
	{
		this.askDevelopmentCardsType();
	}

	@Override
	public CLIHandlerShowDevelopmentCards newInstance()
	{
		return new CLIHandlerShowDevelopmentCards();
	}

	private void askDevelopmentCardsType()
	{
		Client.getLogger().log(Level.INFO, "Enter Card Type...");
		Client.getLogger().log(Level.INFO, "1 - BUILDING");
		Client.getLogger().log(Level.INFO, "2 - CHARACTER");
		Client.getLogger().log(Level.INFO, "3 - TERRITORY");
		Client.getLogger().log(Level.INFO, "4 - VENTURE");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowDevelopmentCards.CARD_TYPE_CHOICE.containsKey(Integer.parseInt(input)));
		this.showDevelopmentCards(CLIHandlerShowDevelopmentCards.CARD_TYPE_CHOICE.get(Integer.parseInt(input)));
	}

	private void showDevelopmentCards(CardType cardType)
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (Entry<Row, Integer> developmentCardInteger : GameStatus.getInstance().getCurrentDevelopmentCards().get(cardType).entrySet()) {
			stringBuilder.append("ROW: ");
			stringBuilder.append(developmentCardInteger.getKey().name());
			stringBuilder.append('\n');
			stringBuilder.append(GameStatus.getInstance().getDevelopmentCards().get(cardType).get(developmentCardInteger.getValue()).getInformations());
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}
}
