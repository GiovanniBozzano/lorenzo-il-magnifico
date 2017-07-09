package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationProductionTrade;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionProductionTrade;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerProductionTrade implements ICLIHandler
{
	private final Map<Integer, Integer> availableTradeCards = new HashMap<>();
	private final List<Integer> chosenTradeCards = new ArrayList<>();
	private final Map<Integer, ResourceTradeOption> chosenResourceTradeOptions = new HashMap<>();

	/**
	 * <p>Uses current expected action of the player and the {@code
	 * chosenTradeCards} {@link Integer} {@link List} to insert in a {@link
	 * Integer} {@link ResourceTradeOption} {@link Map} the available resource
	 * trade options to perform an {@link ActionInformationProductionTrade} and
	 * prints them and the corresponding choosing indexes on screen.
	 *
	 * @param chosenTradeCard the {@link Integer} of the index of the card.
	 */
	private static Map<Integer, ResourceTradeOption> showAvailableResourceTradeOptions(int chosenTradeCard)
	{
		Map<Integer, ResourceTradeOption> availableResourceTradeOptions = new HashMap<>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Resource Trade Option...");
		int index = 1;
		for (ResourceTradeOption resourceTradeOption : ((ExpectedActionProductionTrade) GameStatus.getInstance().getCurrentExpectedAction()).getAvailableCards().get(chosenTradeCard)) {
			stringBuilder.append(Utils.createListElement(index, resourceTradeOption.getInformation(true)));
			availableResourceTradeOptions.put(index, resourceTradeOption);
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
		return availableResourceTradeOptions;
	}

	@Override
	public void execute()
	{
		this.showAvailableTradeCards();
		this.askTradeCards();
		if (!this.chosenTradeCards.isEmpty()) {
			for (int chosenTradeCard : this.chosenTradeCards) {
				this.askResourceTradeOptions(chosenTradeCard, CLIHandlerProductionTrade.showAvailableResourceTradeOptions(chosenTradeCard));
			}
		}
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationProductionTrade(this.chosenResourceTradeOptions));
	}

	@Override
	public CLIHandlerProductionTrade newInstance()
	{
		return new CLIHandlerProductionTrade();
	}

	/**
	 * <p>Uses current expected action of the player to insert in a {@link
	 * Integer} {@link Integer}{@link Map} the available trade cards to perform
	 * an {@link ActionInformationProductionTrade} and prints them and the
	 * corresponding choosing indexes on screen.
	 */
	private void showAvailableTradeCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nSelect Trade Cards...");
		int index = 1;
		for (int availableTradeCard : ((ExpectedActionProductionTrade) GameStatus.getInstance().getCurrentExpectedAction()).getAvailableCards().keySet()) {
			stringBuilder.append(Utils.createListElement(index, GameStatus.getInstance().getDevelopmentCards().get(CardType.BUILDING).get(availableTradeCard).getInformation()));
			this.availableTradeCards.put(index, availableTradeCard);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Asks which {@code availableTradeCards} the player wants to use to
	 * perform the action and saves it in a {@link Integer} {@link List}.
	 */
	private void askTradeCards()
	{
		Client.getLogger().log(Level.INFO, "\n\nTYPE Q TO END THE SELECTION");
		String input;
		do {
			do {
				input = Client.getInstance().getCliScanner().nextLine();
			}
			while (!("q").equals(input) && !("Q").equals(input) && (!CommonUtils.isInteger(input) || !this.availableTradeCards.containsKey(Integer.parseInt(input))));
			if (CommonUtils.isInteger(input)) {
				this.chosenTradeCards.add(this.availableTradeCards.get(Integer.parseInt(input)));
				this.availableTradeCards.remove(Integer.parseInt(input));
				Client.getLogger().log(Level.INFO, "{0} registered", new Object[] { CommonUtils.isInteger(input) });
			}
		} while (!("q").equals(input) && !("Q").equals(input));
	}

	/**
	 * <p>Uses the {@code chosenTradeCard} {@link Integer} and the
	 * {@code availableResourceTradeOptions} {@link Integer} {@link
	 * ResourceTradeOption} {@link Map} to ask which {@link ResourceTradeOption}
	 * the player wants to choose.
	 *
	 * @param chosenTradeCard the {@link Integer} of the index of the card.
	 * @param availableResourceTradeOptions the {@link Integer} {@link
	 * ResourceTradeOption} {@link Map} of the card.
	 */
	private void askResourceTradeOptions(int chosenTradeCard, Map<Integer, ResourceTradeOption> availableResourceTradeOptions)
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !availableResourceTradeOptions.containsKey(Integer.parseInt(input)));
		this.chosenResourceTradeOptions.put(chosenTradeCard, availableResourceTradeOptions.get(Integer.parseInt(input)));
	}
}
