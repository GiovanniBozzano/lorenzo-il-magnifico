package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionProductionTrade implements IAction
{
	private final Connection player;
	private final Map<Integer, ResourceTradeOption> chosenDevelopmentCardsBuilding;

	public ActionProductionTrade(Connection player, Map<Integer, ResourceTradeOption> chosenDevelopmentCardsBuilding)
	{
		this.player = player;
		this.chosenDevelopmentCardsBuilding = new HashMap<>(chosenDevelopmentCardsBuilding);
	}

	@Override
	public boolean isLegal()
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return false;
		}
		// check if the game has started
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return false;
		}
		// check if it is the player's turn
		if (this.player != gameHandler.getTurnPlayer()) {
			return false;
		}
		// check whether the server expects the player to make this action
		if (gameHandler.getExpectedAction() != ActionType.PRODUCTION_TRADE) {
			return false;
		}
		return true;
	}

	@Override
	public void apply()
	{
		Room room = Room.getPlayerRoom(this.player);
		if (room == null) {
			return;
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			return;
		}
		List<DevelopmentCardBuilding> developmentCardsBuilding = new ArrayList<>();
		for (int index : this.chosenDevelopmentCardsBuilding.keySet()) {
			developmentCardsBuilding.add(this.player.getPlayerInformations().getPlayerCardHandler().getDevelopmentCardFromIndex(CardType.BUILDING, index, DevelopmentCardBuilding.class));
		}
		List<ResourceAmount> employedResources = new ArrayList<>();
		List<ResourceAmount> producedResources = new ArrayList<>();
		for (DevelopmentCardBuilding developmentCardBuilding : developmentCardsBuilding) {
			employedResources.addAll(this.chosenDevelopmentCardsBuilding.get(developmentCardBuilding.getIndex()).getEmployedResources());
			producedResources.addAll(this.chosenDevelopmentCardsBuilding.get(developmentCardBuilding.getIndex()).getProducedResources());
		}
		producedResources.addAll(this.player.getPlayerInformations().getPersonalBonusTile().getProductionInstantResources());
		this.player.getPlayerInformations().getPlayerResourceHandler().subtractResources(employedResources);
		this.player.getPlayerInformations().getPlayerResourceHandler().addTemporaryResources(producedResources);
		if (gameHandler.getPhase() == Phase.LEADER) {
			gameHandler.setExpectedAction(null);
			gameHandler.setPhase(Phase.FAMILY_MEMBER);
			// TODO aggiorno tutti
			// TODO prosegui turno
		} else {
			int councilPrivilegesCount = this.player.getPlayerInformations().getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
			if (councilPrivilegesCount > 0) {
				gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
				// TODO aggiorno tutti
				// TODO manda scelta di privilegio
			} else {
				gameHandler.nextTurn();
			}
		}
	}
}