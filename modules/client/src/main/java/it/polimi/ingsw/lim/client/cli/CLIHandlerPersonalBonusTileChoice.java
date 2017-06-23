package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerPersonalBonusTileChoice implements ICLIHandler
{
	private boolean ownTurn = false;
	private final Map<Integer, Integer> personalBonusTiles = new HashMap<>();

	@Override
	public void execute()
	{
		for (int index = 0; index < GameStatus.getInstance().getAvailablePersonalBonusTiles().size(); index++) {
			this.personalBonusTiles.put(index + 1, GameStatus.getInstance().getAvailablePersonalBonusTiles().get(index));
		}
		this.showPersonalBonusTiles(ownTurn);
		this.askPersonalBonusTileIndex();
	}

	private void askPersonalBonusTileIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.personalBonusTiles.containsKey(Integer.parseInt(input)) || !this.ownTurn);
		Client.getInstance().getConnectionHandler().sendGamePersonalBonusTilePlayerChoice(this.personalBonusTiles.get(Integer.parseInt(input)));
	}

	public void showPersonalBonusTiles(boolean ownTurn)
	{
		if (ownTurn) {
			Client.getLogger().log(Level.INFO, "Enter PersonalBonusTile choice...");
			for (Entry<Integer, Integer> personalBonusTile : this.personalBonusTiles.entrySet()) {
				Client.getLogger().log(Level.INFO, "== {0} ============", new Object[] { personalBonusTile.getKey() });
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Production activation cost: ");
				stringBuilder.append(GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTile).getProductionActivationCost());
				if (!GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTile).getProductionInstantResources().isEmpty()) {
					stringBuilder.append("\n\nProduction bonus resources:");
				}
				for (ResourceAmount resourceAmount : GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTile).getProductionInstantResources()) {
					stringBuilder.append('\n');
					stringBuilder.append(Utils.RESOURCES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
				stringBuilder.append("\n\nHarvest activation cost: ");
				stringBuilder.append(GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTile).getProductionActivationCost());
				if (!GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTile).getHarvestInstantResources().isEmpty()) {
					stringBuilder.append("\n\nHarvest bonus resources:");
				}
				for (ResourceAmount resourceAmount : GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTile).getHarvestInstantResources()) {
					stringBuilder.append('\n');
					stringBuilder.append(Utils.RESOURCES_NAMES.get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
				Client.getLogger().log(Level.INFO, "===================");
				GameStatus.getInstance().getPersonalBonusTiles().get(personalBonusTile.getValue());
			}
		}
	}

	public boolean isOwnTurn()
	{
		return this.ownTurn;
	}

	public void setOwnTurn(boolean ownTurn)
	{
		this.ownTurn = ownTurn;
	}
}
