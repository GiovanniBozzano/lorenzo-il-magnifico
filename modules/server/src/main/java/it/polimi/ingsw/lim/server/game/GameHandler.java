package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionGetDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionMarket;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardStatus;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.enums.LeaderCardType;
import it.polimi.ingsw.lim.server.game.actions.*;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.board.ExcommunicationTile;
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
import it.polimi.ingsw.lim.server.utils.Utils;

import java.util.*;
import java.util.Map.Entry;

public class GameHandler
{
	private final Room room;
	private final CardsHandler cardsHandler = new CardsHandler();
	private final BoardHandler boardHandler;
	private final Random randomGenerator = new Random(System.nanoTime());
	private final Map<Period, List<DevelopmentCardBuilding>> developmentCardsBuilding = new EnumMap<>(CardsHandler.DEVELOPMENT_CARDS_BUILDING);
	private final Map<Period, List<DevelopmentCardCharacter>> developmentCardsCharacters = new EnumMap<>(CardsHandler.DEVELOPMENT_CARDS_CHARACTER);
	private final Map<Period, List<DevelopmentCardTerritory>> developmentCardsTerritory = new EnumMap<>(CardsHandler.DEVELOPMENT_CARDS_TERRITORY);
	private final Map<Period, List<DevelopmentCardVenture>> developmentCardsVenture = new EnumMap<>(CardsHandler.DEVELOPMENT_CARDS_VENTURE);
	private final Map<FamilyMemberType, Integer> familyMemberTypeValues = new EnumMap<>(FamilyMemberType.class);
	private final List<Connection> turnOrder = new LinkedList<>();
	private Connection turnPlayer;
	private Period currentPeriod;
	private Round currentRound;
	private Phase currentPhase;
	private ActionType expectedAction;
	private final List<Integer> availablePersonalBonusTiles = new ArrayList<>();
	private int personalBonusTileChoicePlayerIndex = 0;
	private Map<Connection, List<Integer>> availableLeaderCards = new HashMap<>();
	private Map<Connection, Boolean> leaderCardsChoosingPlayers = new HashMap<>();

	GameHandler(Room room)
	{
		this.room = room;
		List<ExcommunicationTile> firstPeriodExcommunicationTiles = new ArrayList<>();
		for (ExcommunicationTile excommunicationTile : ExcommunicationTile.values()) {
			if (excommunicationTile.getPeriod() == Period.FIRST) {
				firstPeriodExcommunicationTiles.add(excommunicationTile);
			}
		}
		List<ExcommunicationTile> secondPeriodExcommunicationTiles = new ArrayList<>();
		for (ExcommunicationTile excommunicationTile : ExcommunicationTile.values()) {
			if (excommunicationTile.getPeriod() == Period.SECOND) {
				secondPeriodExcommunicationTiles.add(excommunicationTile);
			}
		}
		List<ExcommunicationTile> thirdPeriodExcommunicationTiles = new ArrayList<>();
		for (ExcommunicationTile excommunicationTile : ExcommunicationTile.values()) {
			if (excommunicationTile.getPeriod() == Period.THIRD) {
				thirdPeriodExcommunicationTiles.add(excommunicationTile);
			}
		}
		Map<Period, ExcommunicationTile> excommunicationTiles = new EnumMap<>(Period.class);
		excommunicationTiles.put(Period.FIRST, firstPeriodExcommunicationTiles.get(this.randomGenerator.nextInt(firstPeriodExcommunicationTiles.size())));
		excommunicationTiles.put(Period.SECOND, firstPeriodExcommunicationTiles.get(this.randomGenerator.nextInt(secondPeriodExcommunicationTiles.size())));
		excommunicationTiles.put(Period.THIRD, firstPeriodExcommunicationTiles.get(this.randomGenerator.nextInt(thirdPeriodExcommunicationTiles.size())));
		this.boardHandler = new BoardHandler(excommunicationTiles);
		Map<Period, Integer> excommunicationTilesIndexes = new EnumMap<>(Period.class);
		excommunicationTilesIndexes.put(Period.FIRST, excommunicationTiles.get(Period.FIRST).getIndex());
		excommunicationTilesIndexes.put(Period.SECOND, excommunicationTiles.get(Period.SECOND).getIndex());
		excommunicationTilesIndexes.put(Period.THIRD, excommunicationTiles.get(Period.THIRD).getIndex());
		for (Period period : Period.values()) {
			Collections.shuffle(this.developmentCardsBuilding.get(period), this.randomGenerator);
			Collections.shuffle(this.developmentCardsCharacters.get(period), this.randomGenerator);
			Collections.shuffle(this.developmentCardsTerritory.get(period), this.randomGenerator);
			Collections.shuffle(this.developmentCardsVenture.get(period), this.randomGenerator);
		}
		this.turnOrder.addAll(this.room.getPlayers());
		Collections.shuffle(this.turnOrder, this.randomGenerator);
		Map<Integer, PlayerIdentification> playersIdentifications = new HashMap<>();
		int currentIndex = 0;
		int startingCoins = 5;
		for (Connection player : this.turnOrder) {
			player.setPlayerHandler(new PlayerHandler(currentIndex));
			player.getPlayerHandler().getPlayerResourceHandler().addResource(ResourceType.COIN, startingCoins);
			startingCoins++;
			playersIdentifications.put(currentIndex, new PlayerIdentification(player.getUsername(), Color.values()[currentIndex]));
			currentIndex++;
		}
		for (Connection player : this.turnOrder) {
			player.sendGameStarted(excommunicationTilesIndexes, playersIdentifications, player.getPlayerHandler().getIndex());
		}
		for (PersonalBonusTile personalBonusTile : Arrays.asList(PersonalBonusTile.values())) {
			this.availablePersonalBonusTiles.add(personalBonusTile.getIndex());
		}
		List<LeaderCard> leaderCards = new ArrayList<>(CardsHandler.LEADER_CARDS);
		for (Connection player : room.getPlayers()) {
			List<Integer> playerAvailableLeaderCards = new ArrayList<>();
			for (int index = 0; index < 4; index++) {
				LeaderCard leaderCard = leaderCards.get(this.randomGenerator.nextInt(leaderCards.size()));
				leaderCards.remove(leaderCard);
				playerAvailableLeaderCards.add(leaderCard.getIndex());
			}
			this.availableLeaderCards.put(player, playerAvailableLeaderCards);
		}
		this.sendGamePersonalBonusTileChoiceRequest(this.turnOrder.get(0));
	}

	public void receivePersonalBonusTileChoice(Connection player, int personalBonusTileIndex)
	{
		if (this.personalBonusTileChoicePlayerIndex != player.getPlayerHandler().getIndex()) {
			return;
		}
		if (!this.availablePersonalBonusTiles.contains(personalBonusTileIndex)) {
			return;
		}
		this.applyPersonalBonusTileChoice(player, personalBonusTileIndex);
	}

	public void applyPersonalBonusTileChoice(Connection player, int personalBonusTileIndex)
	{
		player.getPlayerHandler().setPersonalBonusTile(PersonalBonusTile.fromIndex(personalBonusTileIndex));
		this.availablePersonalBonusTiles.remove((Integer) personalBonusTileIndex);
		for (Connection currentPlayer : this.room.getPlayers()) {
			if (currentPlayer.getPlayerHandler().isOnline()) {
				currentPlayer.sendGamePersonalBonusTileChosen(player.getPlayerHandler().getIndex());
			}
		}
		do {
			this.personalBonusTileChoicePlayerIndex++;
			if (this.personalBonusTileChoicePlayerIndex >= this.turnOrder.size()) {
				this.personalBonusTileChoicePlayerIndex = -1;
				for (Connection currentPlayer : this.availableLeaderCards.keySet()) {
					this.leaderCardsChoosingPlayers.put(currentPlayer, true);
				}
				this.sendLeaderCardsChoiceRequest();
				return;
			}
		}
		while (!this.turnOrder.get(this.personalBonusTileChoicePlayerIndex).getPlayerHandler().isOnline());
		this.sendGamePersonalBonusTileChoiceRequest(this.turnOrder.get(this.personalBonusTileChoicePlayerIndex));
	}

	public void receiveLeaderCardChoice(Connection player, int leaderCardIndex)
	{
		if (!this.leaderCardsChoosingPlayers.get(player)) {
			return;
		}
		if (!this.availableLeaderCards.get(player).contains(leaderCardIndex)) {
			return;
		}
		this.applyLeaderCardChoice(player, leaderCardIndex);
	}

	public void applyLeaderCardChoice(Connection player, int leaderCardIndex)
	{
		this.leaderCardsChoosingPlayers.put(player, false);
		player.getPlayerHandler().getPlayerCardHandler().addLeaderCard(Utils.getLeaderCardFromIndex(leaderCardIndex));
		this.availableLeaderCards.get(player).remove((Integer) leaderCardIndex);
		boolean finished = true;
		for (List<Integer> playerAvailableLeaderCards : this.availableLeaderCards.values()) {
			if (!playerAvailableLeaderCards.isEmpty()) {
				finished = false;
			}
		}
		if (finished) {
			for (Connection currentPlayer : this.room.getPlayers()) {
				if (currentPlayer.getPlayerHandler().isOnline()) {
					currentPlayer.sendGameLeaderCardChosen(player.getPlayerHandler().getIndex(), true);
				}
			}
			this.setupRound();
		} else {
			if (!this.leaderCardsChoosingPlayers.containsValue(true)) {
				for (Connection currentPlayer : this.room.getPlayers()) {
					if (currentPlayer.getPlayerHandler().isOnline()) {
						currentPlayer.sendGameLeaderCardChosen(player.getPlayerHandler().getIndex(), false);
					}
				}
				Map<Connection, List<Integer>> newlyAvailableLeaderCards = new HashMap<>();
				List<List<Integer>> oldAvailableLeaderCards = new ArrayList<>(this.availableLeaderCards.values());
				int currentListIndex = 0;
				for (Connection currentPlayer : this.availableLeaderCards.keySet()) {
					this.leaderCardsChoosingPlayers.put(currentPlayer, true);
					if (currentListIndex + 1 >= this.availableLeaderCards.size()) {
						newlyAvailableLeaderCards.put(currentPlayer, oldAvailableLeaderCards.get(0));
					} else {
						newlyAvailableLeaderCards.put(currentPlayer, oldAvailableLeaderCards.get(++currentListIndex));
					}
				}
				this.availableLeaderCards.clear();
				this.availableLeaderCards = newlyAvailableLeaderCards;
				this.sendLeaderCardsChoiceRequest();
			} else {
				for (Connection currentPlayer : this.room.getPlayers()) {
					if (currentPlayer.getPlayerHandler().isOnline()) {
						currentPlayer.sendGameLeaderCardChosen(player.getPlayerHandler().getIndex(), true);
					}
				}
			}
		}
	}

	private void setupRound()
	{
		if (this.currentRound == null || this.currentRound == Round.SECOND) {
			// the game is being started
			this.setupPeriod();
		} else {
			this.currentRound = Round.SECOND;
		}
		if (this.currentPeriod == null) {
			// the game has ended
			return;
		}
		this.setupTurnOrder();
		int playerCounter = 0;
		do {
			if (playerCounter >= this.turnOrder.size()) {
				this.endGame();
				return;
			}
			this.turnPlayer = this.turnOrder.get(playerCounter);
			playerCounter++;
		} while (!this.turnPlayer.getPlayerHandler().isOnline());
		this.rollDices();
		this.drawCards();
		this.sendGameUpdate(this.turnPlayer);
	}

	private void setupPeriod()
	{
		if (this.currentPeriod == null) {
			// the game is being started
			this.currentPeriod = Period.FIRST;
		} else {
			this.currentPeriod = Period.next(this.currentPeriod);
		}
		if (this.currentPeriod == null) {
			// the are no more periods to play
			this.endGame();
		} else {
			this.currentRound = Round.FIRST;
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
		for (Row row : Row.values()) {
			this.cardsHandler.addDevelopmentCard(row, this.developmentCardsBuilding.get(this.currentPeriod).get(0));
			this.cardsHandler.addDevelopmentCard(row, this.developmentCardsCharacters.get(this.currentPeriod).get(0));
			this.cardsHandler.addDevelopmentCard(row, this.developmentCardsTerritory.get(this.currentPeriod).get(0));
			this.cardsHandler.addDevelopmentCard(row, this.developmentCardsVenture.get(this.currentPeriod).get(0));
			this.developmentCardsBuilding.get(this.currentPeriod).remove(0);
			this.developmentCardsCharacters.get(this.currentPeriod).remove(0);
			this.developmentCardsTerritory.get(this.currentPeriod).remove(0);
			this.developmentCardsVenture.get(this.currentPeriod).remove(0);
		}
	}

	private void setupTurnOrder()
	{
		if (this.currentPeriod == Period.FIRST && this.currentRound == Round.FIRST) {
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
		this.currentPhase = Phase.LEADER;
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
		this.sendGameUpdate(this.turnPlayer);
	}

	private void switchPlayer()
	{
		int playerCounter = 0;
		do {
			if (playerCounter >= this.turnOrder.size()) {
				this.endGame();
				return;
			}
			this.turnPlayer = this.getNextTurnPlayer();
			playerCounter++;
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

	private void sendGamePersonalBonusTileChoiceRequest(Connection player)
	{
		player.sendGamePersonalBonusTileChoiceRequest(this.availablePersonalBonusTiles);
		this.sendGamePersonalBonusTileChoiceOther(player);
	}

	private void sendGamePersonalBonusTileChoiceOther(Connection player)
	{
		for (Connection otherPlayer : this.room.getPlayers()) {
			if (otherPlayer != player && otherPlayer.getPlayerHandler().isOnline()) {
				otherPlayer.sendGamePersonalBonusTileChoiceOther(player.getPlayerHandler().getIndex());
			}
		}
	}

	private void sendLeaderCardsChoiceRequest()
	{
		for (Entry<Connection, List<Integer>> playerAvailableLeaderCards : this.availableLeaderCards.entrySet()) {
			if (!playerAvailableLeaderCards.getKey().getPlayerHandler().isOnline()) {
				int currentLeaderCardIndex = playerAvailableLeaderCards.getValue().get(this.randomGenerator.nextInt(playerAvailableLeaderCards.getValue().size()));
				playerAvailableLeaderCards.getKey().getPlayerHandler().getPlayerCardHandler().addLeaderCard(Utils.getLeaderCardFromIndex(currentLeaderCardIndex));
				this.availableLeaderCards.get(playerAvailableLeaderCards.getKey()).remove((Integer) currentLeaderCardIndex);
				this.applyLeaderCardChoice(playerAvailableLeaderCards.getKey(), currentLeaderCardIndex);
			} else {
				playerAvailableLeaderCards.getKey().sendGameLeaderCardChoiceRequest(playerAvailableLeaderCards.getValue());
			}
		}
	}

	public void sendGameUpdate(Connection player)
	{
		player.sendGameUpdate(this.generateGameInformations(), this.generatePlayersInformations(), this.generateAvailableActions(player));
		this.sendGameUpdateOtherTurn(player);
	}

	public void sendGameUpdateExpectedAction(Connection player, ExpectedAction expectedAction)
	{
		player.sendGameUpdateExpectedAction(this.generateGameInformations(), this.generatePlayersInformations(), expectedAction);
		this.sendGameUpdateOtherTurn(player);
	}

	private void sendGameUpdateOtherTurn(Connection player)
	{
		for (Connection otherPlayer : this.room.getPlayers()) {
			if (otherPlayer != player && otherPlayer.getPlayerHandler().isOnline()) {
				otherPlayer.sendGameUpdateOtherTurn(this.generateGameInformations(), this.generatePlayersInformations(), player.getPlayerHandler().getIndex());
			}
		}
	}

	public GameInformations generateGameInformations()
	{
		Map<Row, Integer> developmentCardsBuildingInformations = new EnumMap<>(Row.class);
		Map<Row, Integer> developmentCardsCharacterInformations = new EnumMap<>(Row.class);
		Map<Row, Integer> developmentCardsTerritoryInformations = new EnumMap<>(Row.class);
		Map<Row, Integer> developmentCardsVentureInformations = new EnumMap<>(Row.class);
		for (Row row : Row.values()) {
			DevelopmentCard developmentCard = this.cardsHandler.getCurrentDevelopmentCards().get(CardType.BUILDING).get(row);
			developmentCardsBuildingInformations.put(row, developmentCard == null ? null : developmentCard.getIndex());
			developmentCard = this.cardsHandler.getCurrentDevelopmentCards().get(CardType.CHARACTER).get(row);
			developmentCardsCharacterInformations.put(row, developmentCard == null ? null : developmentCard.getIndex());
			developmentCard = this.cardsHandler.getCurrentDevelopmentCards().get(CardType.TERRITORY).get(row);
			developmentCardsTerritoryInformations.put(row, developmentCard == null ? null : developmentCard.getIndex());
			developmentCard = this.cardsHandler.getCurrentDevelopmentCards().get(CardType.VENTURE).get(row);
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

	public List<PlayerInformations> generatePlayersInformations()
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
				if (!availableFamilyMemberTypes.isEmpty()) {
					availableActions.add(new AvailableActionGetDevelopmentCard(cardType, row, availableFamilyMemberTypes, availableResourceCostOptions, availableDiscountChoises));
				}
			}
		}
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
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

	public Random getRandomGenerator()
	{
		return this.randomGenerator;
	}

	public Map<FamilyMemberType, Integer> getFamilyMemberTypeValues()
	{
		return this.familyMemberTypeValues;
	}

	public Connection getTurnPlayer()
	{
		return this.turnPlayer;
	}

	public Period getCurrentPeriod()
	{
		return this.currentPeriod;
	}

	public Round getCurrentRound()
	{
		return this.currentRound;
	}

	public Phase getCurrentPhase()
	{
		return this.currentPhase;
	}

	public void setCurrentPhase(Phase currentPhase)
	{
		this.currentPhase = currentPhase;
	}

	public ActionType getExpectedAction()
	{
		return this.expectedAction;
	}

	public void setExpectedAction(ActionType expectedAction)
	{
		this.expectedAction = expectedAction;
	}

	public List<Integer> getAvailablePersonalBonusTiles()
	{
		return this.availablePersonalBonusTiles;
	}

	public int getPersonalBonusTileChoicePlayerIndex()
	{
		return this.personalBonusTileChoicePlayerIndex;
	}

	public Map<Connection, List<Integer>> getAvailableLeaderCards()
	{
		return this.availableLeaderCards;
	}

	public Map<Connection, Boolean> getLeaderCardsChoosingPlayers()
	{
		return this.leaderCardsChoosingPlayers;
	}
}
