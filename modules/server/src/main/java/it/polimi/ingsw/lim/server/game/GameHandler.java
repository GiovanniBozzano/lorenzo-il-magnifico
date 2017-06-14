package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionGetDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionMarket;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardStatus;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.enums.LeaderCardType;
import it.polimi.ingsw.lim.server.game.actions.*;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.*;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.EventFirstTurn;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.player.PlayerHandler;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.*;

public class GameHandler
{
	private final Room room;
	private final CardsHandler cardsHandler = new CardsHandler();
	private final BoardHandler boardHandler = new BoardHandler();
	private final Random randomGenerator = new Random(System.nanoTime());
	private final DevelopmentCardsDeck<DevelopmentCardBuilding> developmentCardsBuilding = new DevelopmentCardsDeck<>(CardsHandler.DEVELOPMENT_CARDS_BUILDING);
	private final DevelopmentCardsDeck<DevelopmentCardCharacter> developmentCardsCharacters = new DevelopmentCardsDeck<>(CardsHandler.DEVELOPMENT_CARDS_CHARACTER);
	private final DevelopmentCardsDeck<DevelopmentCardTerritory> developmentCardsTerritory = new DevelopmentCardsDeck<>(CardsHandler.DEVELOPMENT_CARDS_TERRITORY);
	private final DevelopmentCardsDeck<DevelopmentCardVenture> developmentCardsVenture = new DevelopmentCardsDeck<>(CardsHandler.DEVELOPMENT_CARDS_VENTURE);
	private final Map<FamilyMemberType, Integer> familyMemberTypeValues = new EnumMap<>(FamilyMemberType.class);
	private final List<Connection> turnOrder = new LinkedList<>();
	private Connection turnPlayer;
	private Period period;
	private Round round;
	private Phase phase;
	private ActionType expectedAction;

	GameHandler(Room room)
	{
		this.room = room;
		this.developmentCardsBuilding.shuffle();
		this.developmentCardsCharacters.shuffle();
		this.developmentCardsTerritory.shuffle();
		this.developmentCardsVenture.shuffle();
		this.turnOrder.addAll(this.room.getPlayers());
		Collections.shuffle(this.turnOrder, this.randomGenerator);
		List<PersonalBonusTile> personalBonusTiles = Arrays.asList(PersonalBonusTile.values());
		int currentIndex = 0;
		int startingCoins = 5;
		for (Connection player : this.turnOrder) {
			PersonalBonusTile personalBonusTile = personalBonusTiles.get(this.randomGenerator.nextInt(personalBonusTiles.size()));
			personalBonusTiles.remove(personalBonusTile);
			player.setPlayerHandler(new PlayerHandler(currentIndex, personalBonusTile));
			player.getPlayerHandler().getPlayerResourceHandler().addResource(ResourceType.COIN, startingCoins);
			currentIndex++;
			startingCoins++;
		}
		this.setupRound();
	}

	private void setupRound()
	{
		if (this.round == null || this.round == Round.SECOND) {
			// the game is being started
			this.setupPeriod();
		} else {
			this.round = Round.SECOND;
		}
		if (this.period == null) {
			// the game has ended
			return;
		}
		this.setupTurnOrder();
		this.turnPlayer = this.turnOrder.get(0);
		this.rollDices();
		this.drawCards();
		// TODO aggiorno tutti
		// TODO turno primo giocatore
	}

	private void setupPeriod()
	{
		if (this.period == null) {
			// the game is being started
			this.period = Period.FIRST;
		} else {
			this.period = Period.next(this.period);
		}
		if (this.period == null) {
			// the are no more periods to play
			this.endGame();
		} else {
			this.round = Round.FIRST;
		}
	}

	private void endGame()
	{
	}

	private void rollDices()
	{
		this.familyMemberTypeValues.put(FamilyMemberType.BLACK, this.randomGenerator.nextInt(6) + 1);
		this.familyMemberTypeValues.put(FamilyMemberType.ORANGE, this.randomGenerator.nextInt(6) + 1);
		this.familyMemberTypeValues.put(FamilyMemberType.WHITE, this.randomGenerator.nextInt(6) + 1);
	}

	private void drawCards()
	{
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCards().get(CardType.BUILDING).size(); index++) {
			this.cardsHandler.addDevelopmentCard(this.developmentCardsBuilding.getPeriods().get(this.period).get(index), Row.values()[index]);
			this.developmentCardsBuilding.getPeriods().get(this.period).remove(index);
		}
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCards().get(CardType.CHARACTER).size(); index++) {
			this.cardsHandler.addDevelopmentCard(this.developmentCardsCharacters.getPeriods().get(this.period).get(index), Row.values()[index]);
			this.developmentCardsCharacters.getPeriods().get(this.period).remove(index);
		}
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCards().get(CardType.TERRITORY).size(); index++) {
			this.cardsHandler.addDevelopmentCard(this.developmentCardsTerritory.getPeriods().get(this.period).get(index), Row.values()[index]);
			this.developmentCardsTerritory.getPeriods().get(this.period).remove(index);
		}
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCards().get(CardType.VENTURE).size(); index++) {
			this.cardsHandler.addDevelopmentCard(this.developmentCardsVenture.getPeriods().get(this.period).get(index), Row.values()[index]);
			this.developmentCardsVenture.getPeriods().get(this.period).remove(index);
		}
	}

	private void setupTurnOrder()
	{
		if (this.period == Period.FIRST && this.round == Round.FIRST) {
			return;
		}
		List<Connection> newTurnOrder = new LinkedList<>();
		for (Connection player : this.boardHandler.getCouncilPalaceOrder()) {
			newTurnOrder.add(player);
			this.turnOrder.remove(player);
		}
		newTurnOrder.addAll(this.turnOrder);
		this.turnOrder.clear();
		this.turnOrder.addAll(newTurnOrder);
	}

	public void nextTurn()
	{
		this.phase = Phase.LEADER;
		this.expectedAction = null;
		for (Modifier temporaryModifier : this.turnPlayer.getPlayerHandler().getTemporaryModifiers()) {
			this.turnPlayer.getPlayerHandler().getActiveModifiers().remove(temporaryModifier);
		}
		this.turnPlayer.getPlayerHandler().getTemporaryModifiers().clear();
		for (ResourceType resourceType : this.turnPlayer.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().keySet()) {
			this.turnPlayer.getPlayerHandler().getPlayerResourceHandler().addResource(resourceType, this.turnPlayer.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().get(resourceType));
		}
		this.turnPlayer.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().clear();
		for (LeaderCard leaderCard : this.turnPlayer.getPlayerHandler().getPlayerCardHandler().getLeaderCards()) {
			if (leaderCard instanceof LeaderCardReward) {
				((LeaderCardReward) leaderCard).setActivated(false);
			}
		}
		boolean endRound = true;
		for (Connection player : this.room.getPlayers()) {
			if (player.getPlayerHandler().getAvailableTurns() > 0) {
				endRound = false;
				break;
			}
		}
		if (endRound) {
			this.setupRound();
			return;
		}
		this.switchPlayer();
		// TODO aggiorno tutti
		// TODO turno prossimo giocatore
	}

	private void switchPlayer()
	{
		do {
			this.turnPlayer = this.getNextTurnPlayer();
		} while (this.turnPlayer.getPlayerHandler().getAvailableTurns() <= 0);
		if (this.turnPlayer.getPlayerHandler().getAvailableTurns() >= 4) {
			EventFirstTurn eventFirstTurn = new EventFirstTurn(this.turnPlayer);
			eventFirstTurn.applyModifiers(this.turnPlayer.getPlayerHandler().getActiveModifiers());
			if (eventFirstTurn.isCancelled()) {
				this.switchPlayer();
				return;
			}
		}
		this.turnPlayer.getPlayerHandler().decreaseAvailableTurns();
		if (!this.turnPlayer.getPlayerHandler().isOnline()) {
			this.turnPlayer.getPlayerHandler().decreaseAvailableTurns();
			this.switchPlayer();
		}
	}

	private Connection getNextTurnPlayer()
	{
		int index = this.turnOrder.indexOf(this.turnPlayer);
		return index + 1 >= this.turnOrder.size() ? this.turnOrder.get(0) : this.turnOrder.get(index + 1);
	}

	private GameInformations generateGameInformations()
	{
		Map<Row, Integer> developmentCardsBuildingInformations = new EnumMap<>(Row.class);
		for (Row row : this.cardsHandler.getCurrentDevelopmentCards().get(CardType.BUILDING).keySet()) {
			DevelopmentCard developmentCard = this.cardsHandler.getCurrentDevelopmentCards().get(CardType.BUILDING).get(row);
			developmentCardsBuildingInformations.put(row, developmentCard == null ? null : developmentCard.getIndex());
		}
		Map<Row, Integer> developmentCardsCharacterInformations = new EnumMap<>(Row.class);
		for (Row row : this.cardsHandler.getCurrentDevelopmentCards().get(CardType.CHARACTER).keySet()) {
			DevelopmentCard developmentCard = this.cardsHandler.getCurrentDevelopmentCards().get(CardType.CHARACTER).get(row);
			developmentCardsCharacterInformations.put(row, developmentCard == null ? null : developmentCard.getIndex());
		}
		Map<Row, Integer> developmentCardsTerritoryInformations = new EnumMap<>(Row.class);
		for (Row row : this.cardsHandler.getCurrentDevelopmentCards().get(CardType.TERRITORY).keySet()) {
			DevelopmentCard developmentCard = this.cardsHandler.getCurrentDevelopmentCards().get(CardType.TERRITORY).get(row);
			developmentCardsTerritoryInformations.put(row, developmentCard == null ? null : developmentCard.getIndex());
		}
		Map<Row, Integer> developmentCardsVentureInformations = new EnumMap<>(Row.class);
		for (Row row : this.cardsHandler.getCurrentDevelopmentCards().get(CardType.VENTURE).keySet()) {
			DevelopmentCard developmentCard = this.cardsHandler.getCurrentDevelopmentCards().get(CardType.VENTURE).get(row);
			developmentCardsVentureInformations.put(row, developmentCard == null ? null : developmentCard.getIndex());
		}
		Map<Integer, Integer> turnOrderInformations = new HashMap<>();
		int currentPlace = 0;
		for (Connection player : this.turnOrder) {
			turnOrderInformations.put(currentPlace, player.getPlayerHandler().getIndex());
			currentPlace++;
		}
		Map<Integer, Integer> councilPalaceOrderInformations = new HashMap<>();
		currentPlace = 0;
		for (Connection player : this.boardHandler.getCouncilPalaceOrder()) {
			councilPalaceOrderInformations.put(currentPlace, player.getPlayerHandler().getIndex());
			currentPlace++;
		}
		return new GameInformations(developmentCardsBuildingInformations, developmentCardsCharacterInformations, developmentCardsTerritoryInformations, developmentCardsVentureInformations, turnOrderInformations, councilPalaceOrderInformations);
	}

	private List<PlayerInformations> generatePlayersInformations()
	{
		List<PlayerInformations> playersInformations = new ArrayList<>();
		for (Connection player : this.room.getPlayers()) {
			List<Integer> developmentCardsBuildingInformations = new ArrayList<>();
			for (DevelopmentCardBuilding developmentCardBuilding : player.getPlayerHandler().getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
				developmentCardsBuildingInformations.add(developmentCardBuilding.getIndex());
			}
			List<Integer> developmentCardsCharacterInformations = new ArrayList<>();
			for (DevelopmentCardCharacter developmentCardCharacter : player.getPlayerHandler().getPlayerCardHandler().getDevelopmentCards(CardType.CHARACTER, DevelopmentCardCharacter.class)) {
				developmentCardsCharacterInformations.add(developmentCardCharacter.getIndex());
			}
			List<Integer> developmentCardsTerritoryInformations = new ArrayList<>();
			for (DevelopmentCardTerritory developmentCardTerritory : player.getPlayerHandler().getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class)) {
				developmentCardsTerritoryInformations.add(developmentCardTerritory.getIndex());
			}
			List<Integer> developmentCardsVentureInformations = new ArrayList<>();
			for (DevelopmentCardVenture developmentCardVenture : player.getPlayerHandler().getPlayerCardHandler().getDevelopmentCards(CardType.VENTURE, DevelopmentCardVenture.class)) {
				developmentCardsVentureInformations.add(developmentCardVenture.getIndex());
			}
			Map<Integer, LeaderCardStatus> leaderCardsStatuses = new HashMap<>();
			for (LeaderCard leaderCard : player.getPlayerHandler().getPlayerCardHandler().getLeaderCards()) {
				leaderCardsStatuses.put(leaderCard.getIndex(), new LeaderCardStatus(leaderCard.isPlayed(), leaderCard.getLeaderCardType() == LeaderCardType.MODIFIER ? leaderCard.isPlayed() : ((LeaderCardReward) leaderCard).isActivated()));
			}
			playersInformations.add(new PlayerInformations(player.getPlayerHandler().getIndex(), developmentCardsBuildingInformations, developmentCardsCharacterInformations, developmentCardsTerritoryInformations, developmentCardsVentureInformations, leaderCardsStatuses, player.getPlayerHandler().getPlayerResourceHandler().getResources(), player.getPlayerHandler().getFamilyMembersPositions()));
		}
		return playersInformations;
	}

	private List<AvailableAction> generateAvailableActions(Connection player)
	{
		List<AvailableAction> availableActions = new ArrayList<>();
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			if (player.getPlayerHandler().getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE && new ActionCouncilPalace(player, familyMemberType, player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT)).isLegal()) {
				availableActions.add(new AvailableAction(ActionType.COUNCIL_PALACE));
				break;
			}
		}
		List<List<ResourceAmount>> discountChoices = new ArrayList<>();
		for (Modifier modifier : player.getPlayerHandler().getActiveModifiers()) {
			if (modifier.getEventClass() == EventGetDevelopmentCard.class) {
				discountChoices.addAll(((ModifierGetDevelopmentCard) modifier).getDiscountChoices());
			}
		}
		for (CardType cardType : this.cardsHandler.getCurrentDevelopmentCards().keySet()) {
			for (Row row : this.cardsHandler.getCurrentDevelopmentCards().get(cardType).keySet()) {
				List<FamilyMemberType> availableFamilyMemberTypes = new ArrayList<>();
				List<ResourceCostOption> availableResourceCostOptions = new ArrayList<>();
				List<List<ResourceAmount>> availableDiscountChoises = new ArrayList<>();
				for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
					boolean validFamilyMember = false;
					if (player.getPlayerHandler().getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE) {
						continue;
					}
					if (this.cardsHandler.getCurrentDevelopmentCards().get(cardType).get(row).getResourceCostOptions().isEmpty()) {
						if (new ActionGetDevelopmentCard(player, familyMemberType, player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, null, null).isLegal()) {
							validFamilyMember = true;
						}
					} else {
						for (ResourceCostOption resourceCostOption : this.cardsHandler.getCurrentDevelopmentCards().get(cardType).get(row).getResourceCostOptions()) {
							if (discountChoices.isEmpty()) {
								if (new ActionGetDevelopmentCard(player, familyMemberType, player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, null, resourceCostOption).isLegal()) {
									validFamilyMember = true;
								}
							} else {
								for (List<ResourceAmount> discountChoice : discountChoices) {
									if (new ActionGetDevelopmentCard(player, familyMemberType, player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, discountChoice, resourceCostOption).isLegal()) {
										validFamilyMember = true;
										if (!availableDiscountChoises.contains(discountChoice)) {
											availableDiscountChoises.add(discountChoice);
										}
									}
								}
							}
							if (validFamilyMember) {
								if (!availableResourceCostOptions.contains(resourceCostOption)) {
									availableResourceCostOptions.add(resourceCostOption);
								}
							}
						}
					}
					if (validFamilyMember) {
						availableFamilyMemberTypes.add(familyMemberType);
					}
				}
				if (availableFamilyMemberTypes.size() > 0) {
					availableActions.add(new AvailableActionGetDevelopmentCard(cardType, row, availableFamilyMemberTypes, availableResourceCostOptions, availableDiscountChoises));
				}
			}
		} for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
		if (player.getPlayerHandler().getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE && new ActionHarvestStart(player, familyMemberType, player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT)).isLegal()) {
			availableActions.add(new AvailableAction(ActionType.HARVEST_START));
			break;
		}
	}
		for (MarketSlot marketSlot : MarketSlot.values()) {
			for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
				if (player.getPlayerHandler().getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE && new ActionMarket(player, familyMemberType, player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), marketSlot).isLegal()) {
					availableActions.add(new AvailableActionMarket(marketSlot));
					break;
				}
			}
		}
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			if (player.getPlayerHandler().getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE && new ActionProductionStart(player, familyMemberType, player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT)).isLegal()) {
				availableActions.add(new AvailableAction(ActionType.PRODUCTION_START));
				break;
			}
		}
		return availableActions;
	}

	public CardsHandler getCardsHandler()
	{
		return this.cardsHandler;
	}

	public BoardHandler getBoardHandler()
	{
		return this.boardHandler;
	}

	public Map<FamilyMemberType, Integer> getFamilyMemberTypeValues()
	{
		return this.familyMemberTypeValues;
	}

	public Connection getTurnPlayer()
	{
		return this.turnPlayer;
	}

	public Phase getPhase()
	{
		return this.phase;
	}

	public void setPhase(Phase phase)
	{
		this.phase = phase;
	}

	public ActionType getExpectedAction()
	{
		return this.expectedAction;
	}

	public void setExpectedAction(ActionType expectedAction)
	{
		this.expectedAction = expectedAction;
	}
}
