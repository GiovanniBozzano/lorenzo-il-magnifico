package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsProductionTrade;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardCouncilPrivilege;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.server.enums.ResourcesSource;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.server.game.events.EventGainResources;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Phase;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ActionProductionTrade extends ActionInformationsProductionTrade implements IAction
{
	private transient final Player player;

	public ActionProductionTrade(Map<Integer, ResourceTradeOption> chosenDevelopmentCardsBuilding, Player player)
	{
		super(chosenDevelopmentCardsBuilding);
		this.player = player;
	}

	@Override
	public void isLegal() throws GameActionFailedException
	{
		// check if the player is inside a room
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			throw new GameActionFailedException("");
		}
		// check if the game has started
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			throw new GameActionFailedException("");
		}
		// check if it is the player's turn
		if (this.player != gameHandler.getTurnPlayer()) {
			throw new GameActionFailedException("");
		}
		// check whether the server expects the player to make this action
		if (gameHandler.getExpectedAction() != ActionType.PRODUCTION_TRADE) {
			throw new GameActionFailedException("");
		}
		//ckeck trades' correctness
		Map<ResourceType, Integer> employedResources = new EnumMap<>(ResourceType.class);
		for (Entry<Integer, ResourceTradeOption> chosenDevelopmentCardBuilding : this.getChosenDevelopmentCardsBuilding().entrySet()) {
			DevelopmentCardBuilding developmentCardBuilding = this.player.getPlayerCardHandler().getDevelopmentCardFromIndex(CardType.BUILDING, chosenDevelopmentCardBuilding.getKey(), DevelopmentCardBuilding.class);
			if (developmentCardBuilding == null) {
				throw new GameActionFailedException("");
			}
			if (!developmentCardBuilding.getResourceTradeOptions().contains(chosenDevelopmentCardBuilding.getValue())) {
				throw new GameActionFailedException("");
			}
			for (ResourceAmount resourceAmount : chosenDevelopmentCardBuilding.getValue().getEmployedResources()) {
				if (employedResources.containsKey(resourceAmount.getResourceType())) {
					employedResources.put(resourceAmount.getResourceType(), employedResources.get(resourceAmount.getResourceType()) + resourceAmount.getAmount());
				} else {
					employedResources.put(resourceAmount.getResourceType(), resourceAmount.getAmount());
				}
			}
		}
		for (Entry<ResourceType, Integer> employedResource : employedResources.entrySet()) {
			if (this.player.getPlayerResourceHandler().getResources().get(employedResource.getKey()) < employedResource.getValue()) {
				throw new GameActionFailedException("");
			}
		}
	}

	@Override
	public void apply() throws GameActionFailedException
	{
		Room room = Room.getPlayerRoom(this.player.getConnection());
		if (room == null) {
			throw new GameActionFailedException("");
		}
		GameHandler gameHandler = room.getGameHandler();
		if (gameHandler == null) {
			throw new GameActionFailedException("");
		}
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
		int councilPrivilegesCount = this.player.getPlayerResourceHandler().getTemporaryResources().get(ResourceType.COUNCIL_PRIVILEGE);
		if (councilPrivilegesCount > 0) {
			gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
			gameHandler.sendGameUpdateExpectedAction(this.player, new ExpectedActionChooseRewardCouncilPrivilege(councilPrivilegesCount));
			return;
		}
		if (gameHandler.getCurrentPhase() == Phase.LEADER) {
			gameHandler.setExpectedAction(null);
			gameHandler.sendGameUpdate(this.player);
			return;
		}
		gameHandler.nextTurn();
	}
}