package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerPersonalBonusTileChoice implements ICLIHandler
{
	private final Map<Integer, Integer> personalBonusTiles = new HashMap<>();

	@Override
	public void execute()
	{
		this.showPersonalBonusTiles();
		this.askPersonalBonusTileIndex();
	}

	@Override
	public CLIHandlerPersonalBonusTileChoice newInstance()
	{
		return new CLIHandlerPersonalBonusTileChoice();
	}

	private void showPersonalBonusTiles()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Personal Bonus Tile...");
		int index = 1;
		for (int personalBonusTile : GameStatus.getInstance().getAvailablePersonalBonusTiles()) {
			stringBuilder.append(Utils.createListElement(index, GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTile).getInformation()));
			this.personalBonusTiles.put(index, personalBonusTile);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askPersonalBonusTileIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.personalBonusTiles.containsKey(Integer.parseInt(input)));
		Client.getInstance().getConnectionHandler().sendGamePersonalBonusTilePlayerChoice(this.personalBonusTiles.get(Integer.parseInt(input)));
	}
}
