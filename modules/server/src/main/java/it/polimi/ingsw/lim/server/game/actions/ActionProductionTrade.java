package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationProductionTrade;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ActionProductionTrade extends ActionInformationProductionTrade implements IAction
{
	private final transient Player player;

	public ActionProductionTrade(Map<Integer, ResourceTradeOption> chosenDevelopmentCardsBuilding, Player player)
	{
		super(chosenDevelopmentCardsBuilding);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if it is the player's turn
		if (this.player != this.player.getRoom().getGameHandler().getTurnPlayer()) {
			throw new GameActionFailedException("It's not your turn");
		}
		// check whether the server expects the player to make this action
		if (this.player.getRoom().getGameHandler().getExpectedAction() != ActionType.PRODUCTION_TRADE) {
			throw new GameActionFailedException("This action was not expected");
		}
		// check trades' correctness
		List<ResourceAmount> employedResources = new ArrayList<>();
		for (Entry<Integer, ResourceTradeOption> chosenDevelopmentCardBuilding : this.getChosenDevelopmentCardsBuilding().entrySet()) {
			DevelopmentCardBuilding developmentCardBuilding = this.player.getPlayerCardHandler().getDevelopmentCardFromIndex(CardType.BUILDING, chosenDevelopmentCardBuilding.getKey(), DevelopmentCardBuilding.class);
			if (developmentCardBuilding == null) {
				throw new GameActionFailedException("You don't have this card");
			}
			if (!developmentCardBuilding.getResourceTradeOptions().contains(chosenDevelopmentCardBuilding.getValue())) {
				throw new GameActionFailedException("This trade option is not present in the current card");
			}
			employedResources.addAll(chosenDevelopmentCardBuilding.getValue().getEmployedResources());
		}
		if (!this.player.getPlayerResourceHandler().canAffordResources(employedResources)) {
			throw new GameActionFailedException("You don't have enough resources to perform the trade");
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		List<DevelopmentCardBuilding> developmentCardsBuilding = new ArrayList<>();
		for (int index : this.getChosenDevelopmentCardsBuilding().keySet()) {
			developmentCardsBuilding.add(this.player.getPlayerCardHandler().getDevelopmentCardFromIndex(CardType.BUILDING, index, DevelopmentCardBuilding.class));
		}
		List<ResourceAmount> employedResources = new ArrayList<>();
		List<ResourceAmount> producedResources = new ArrayList<>();
		for (DevelopmentCardBuilding developmentCardBuilding : developmentCardsBuilding) {
			employedResources.addAll(this.getChosenDevelopmentCardsBuilding().get(developmentCardBuilding.getIndex()).getEmployedResources());
			producedResources.addAll(this.getChosenDevelopmentCardsBuilding().get(developmentCardBuilding.getIndex()).getProducedResources());
		}
		EventGainResources eventGainResources = new EventGainResources(this.player, producedResources, ResourcesSource.WORK);
		eventGainResources.applyModifiers(this.player.getActiveModifiers());
		this.player.getPlayerResourceHandler().subtractResources(employedResources);
		this.player.getPlayerResourceHandler().addTemporaryResources(eventGainResources.getResourceAmounts());
		Connection.broadcastLogMessageToOthers(this.player, this.player.getConnection().getUsername() + " finished trading for a production");
		if (Utils.sendCouncilPrivileges(this.player)) {
			return;
		}
		if (Utils.checkLeaderPhase(this.player)) {
			return;
		}
		this.player.getRoom().getGameHandler().nextTurn();
	}
}