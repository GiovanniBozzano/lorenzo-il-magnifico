package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionGetDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionMarket;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
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
import it.polimi.ingsw.lim.server.game.player.Player;
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
	private final Map<Period, List<DevelopmentCardBuilding>> developmentCardsBuilding = Utils.deepCopyDevelopmentCards(CardsHandler.DEVELOPMENT_CARDS_BUILDING);
	private final Map<Period, List<DevelopmentCardCharacter>> developmentCardsCharacters = Utils.deepCopyDevelopmentCards(CardsHandler.DEVELOPMENT_CARDS_CHARACTER);
	private final Map<Period, List<DevelopmentCardTerritory>> developmentCardsTerritory = Utils.deepCopyDevelopmentCards(CardsHandler.DEVELOPMENT_CARDS_TERRITORY);
	private final Map<Period, List<DevelopmentCardVenture>> developmentCardsVenture = Utils.deepCopyDevelopmentCards(CardsHandler.DEVELOPMENT_CARDS_VENTURE);
	private final Map<Period, Integer> excommunicationTilesIndexes = new EnumMap<>(Period.class);
	private final Map<Integer, PlayerIdentification> playersIdentifications = new HashMap<>();
	private final Map<FamilyMemberType, Integer> familyMemberTypeValues = new EnumMap<>(FamilyMemberType.class);
	private final List<Player> turnOrder = new LinkedList<>();
	private Player turnPlayer;
	private Period currentPeriod;
	private Round currentRound;
	private Phase currentPhase;
	private ActionType expectedAction;
	private Map<Player, Boolean> firstTurn = new HashMap<>();
	private final List<Integer> availablePersonalBonusTiles = new ArrayList<>();
	private int personalBonusTileChoicePlayerIndex = 0;
	private Map<Player, List<Integer>> availableLeaderCards = new HashMap<>();
	private Map<Player, Boolean> leaderCardsChoosingPlayers = new HashMap<>();

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
		excommunicationTiles.put(Period.SECOND, secondPeriodExcommunicationTiles.get(this.randomGenerator.nextInt(secondPeriodExcommunicationTiles.size())));
		excommunicationTiles.put(Period.THIRD, thirdPeriodExcommunicationTiles.get(this.randomGenerator.nextInt(thirdPeriodExcommunicationTiles.size())));
		this.boardHandler = new BoardHandler(excommunicationTiles);
		this.excommunicationTilesIndexes.put(Period.FIRST, excommunicationTiles.get(Period.FIRST).getIndex());
		this.excommunicationTilesIndexes.put(Period.SECOND, excommunicationTiles.get(Period.SECOND).getIndex());
		this.excommunicationTilesIndexes.put(Period.THIRD, excommunicationTiles.get(Period.THIRD).getIndex());
		for (Period period : Period.values()) {
			Collections.shuffle(this.developmentCardsBuilding.get(period), this.randomGenerator);
			Collections.shuffle(this.developmentCardsCharacters.get(period), this.randomGenerator);
			Collections.shuffle(this.developmentCardsTerritory.get(period), this.randomGenerator);
			Collections.shuffle(this.developmentCardsVenture.get(period), this.randomGenerator);
		}
		int currentIndex = 0;
		for (Connection connection : this.room.getPlayers()) {
			connection.setPlayer(new Player(connection, currentIndex));
			this.playersIdentifications.put(currentIndex, new PlayerIdentification(connection.getUsername(), Color.values()[currentIndex]));
			this.turnOrder.add(connection.getPlayer());
			this.firstTurn.put(connection.getPlayer(), true);
			currentIndex++;
		}
		Collections.shuffle(this.turnOrder, this.randomGenerator);
		this.personalBonusTileChoicePlayerIndex = this.turnOrder.get(0).getIndex();
		int startingCoins = 5;
		for (Player player : this.turnOrder) {
			player.getConnection().sendGameStarted(this.excommunicationTilesIndexes, this.playersIdentifications, player.getIndex());
			player.getPlayerResourceHandler().addResource(ResourceType.COIN, startingCoins);
			startingCoins++;
		}
		for (PersonalBonusTile personalBonusTile : Arrays.asList(PersonalBonusTile.values())) {
			this.availablePersonalBonusTiles.add(personalBonusTile.getIndex());
		}
		List<LeaderCard> leaderCards = Utils.deepCopyLeaderCards(CardsHandler.LEADER_CARDS);
		for (Player player : this.turnOrder) {
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

	public void receivePersonalBonusTileChoice(Player player, int personalBonusTileIndex)
	{
		if (this.personalBonusTileChoicePlayerIndex != player.getIndex()) {
			return;
		}
		if (!this.availablePersonalBonusTiles.contains(personalBonusTileIndex)) {
			return;
		}
		this.applyPersonalBonusTileChoice(player, personalBonusTileIndex);
	}

	void applyPersonalBonusTileChoice(Player player, int personalBonusTileIndex)
	{
		player.setPersonalBonusTile(PersonalBonusTile.fromIndex(personalBonusTileIndex));
		this.availablePersonalBonusTiles.remove((Integer) personalBonusTileIndex);
		for (Connection currentPlayer : this.room.getPlayers()) {
			if (currentPlayer.getPlayer().isOnline()) {
				currentPlayer.sendGamePersonalBonusTileChosen(player.getIndex());
			}
		}
		do {
			this.personalBonusTileChoicePlayerIndex++;
			if (this.personalBonusTileChoicePlayerIndex >= this.turnOrder.size()) {
				this.personalBonusTileChoicePlayerIndex = -1;
				for (Player currentPlayer : this.availableLeaderCards.keySet()) {
					this.leaderCardsChoosingPlayers.put(currentPlayer, true);
				}
				this.sendLeaderCardsChoiceRequest();
				return;
			}
		}
		while (!this.turnOrder.get(this.personalBonusTileChoicePlayerIndex).isOnline());
		this.sendGamePersonalBonusTileChoiceRequest(this.turnOrder.get(this.personalBonusTileChoicePlayerIndex));
	}

	public void receiveLeaderCardChoice(Player player, int leaderCardIndex)
	{
		if (!this.leaderCardsChoosingPlayers.get(player)) {
			return;
		}
		if (!this.availableLeaderCards.get(player).contains(leaderCardIndex)) {
			return;
		}
		this.applyLeaderCardChoice(player, leaderCardIndex);
	}

	void applyLeaderCardChoice(Player player, int leaderCardIndex)
	{
		this.leaderCardsChoosingPlayers.put(player, false);
		player.getPlayerCardHandler().addLeaderCard(Utils.getLeaderCardFromIndex(leaderCardIndex));
		this.availableLeaderCards.get(player).remove((Integer) leaderCardIndex);
		boolean finished = true;
		for (List<Integer> playerAvailableLeaderCards : this.availableLeaderCards.values()) {
			if (!playerAvailableLeaderCards.isEmpty()) {
				finished = false;
			}
		}
		if (finished) {
			for (Connection currentPlayer : this.room.getPlayers()) {
				if (currentPlayer.getPlayer().isOnline()) {
					currentPlayer.sendGameLeaderCardChosen(player.getIndex(), true);
				}
			}
			this.leaderCardsChoosingPlayers.clear();
			this.setupRound();
		} else {
			if (!this.leaderCardsChoosingPlayers.containsValue(true)) {
				for (Connection currentPlayer : this.room.getPlayers()) {
					if (currentPlayer.getPlayer().isOnline()) {
						currentPlayer.sendGameLeaderCardChosen(player.getIndex(), false);
					}
				}
				Map<Player, List<Integer>> newlyAvailableLeaderCards = new HashMap<>();
				List<List<Integer>> oldAvailableLeaderCards = new ArrayList<>(this.availableLeaderCards.values());
				int currentListIndex = 0;
				for (Player currentPlayer : this.availableLeaderCards.keySet()) {
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
				for (Player currentPlayer : this.turnOrder) {
					if (currentPlayer.isOnline()) {
						currentPlayer.getConnection().sendGameLeaderCardChosen(player.getIndex(), true);
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
		} while (!this.turnPlayer.isOnline());
		this.rollDices();
		this.drawCards();
		for (Player player : this.turnOrder) {
			this.firstTurn.put(player, true);
		}
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
		List<Player> newTurnOrder = new LinkedList<>();
		for (Player player : this.boardHandler.getCouncilPalaceOrder()) {
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
		for (Modifier temporaryModifier : this.turnPlayer.getTemporaryModifiers()) {
			this.turnPlayer.getActiveModifiers().remove(temporaryModifier);
		}
		this.turnPlayer.getTemporaryModifiers().clear();
		for (ResourceType resourceType : this.turnPlayer.getPlayerResourceHandler().getTemporaryResources().keySet()) {
			this.turnPlayer.getPlayerResourceHandler().addResource(resourceType, this.turnPlayer.getPlayerResourceHandler().getTemporaryResources().get(resourceType));
		}
		this.turnPlayer.getPlayerResourceHandler().getTemporaryResources().clear();
		for (LeaderCard leaderCard : this.turnPlayer.getPlayerCardHandler().getLeaderCards()) {
			if (leaderCard instanceof LeaderCardReward) {
				((LeaderCardReward) leaderCard).setActivated(false);
			}
		}
		boolean endRound = true;
		for (Connection player : this.room.getPlayers()) {
			if (player.getPlayer().getAvailableTurns() > 0) {
				endRound = false;
				break;
			}
		}
		if (endRound) {
			this.setupRound();
			return;
		}
		if (this.switchPlayer()) {
			this.sendGameUpdate(this.turnPlayer);
		}
	}

	private boolean switchPlayer()
	{
		int playerCounter = 0;
		do {
			if (playerCounter >= this.turnOrder.size()) {
				this.endGame();
				return false;
			}
			this.turnPlayer = this.getNextTurnPlayer();
			playerCounter++;
		} while (this.turnPlayer.getAvailableTurns() <= 0);
		if (this.firstTurn.get(this.turnPlayer)) {
			this.firstTurn.put(this.turnPlayer, false);
			EventFirstTurn eventFirstTurn = new EventFirstTurn(this.turnPlayer);
			eventFirstTurn.applyModifiers(this.turnPlayer.getActiveModifiers());
			if (eventFirstTurn.isCancelled()) {
				return this.switchPlayer();
			}
		}
		this.firstTurn.put(this.turnPlayer, false);
		this.turnPlayer.decreaseAvailableTurns();
		if (!this.turnPlayer.isOnline()) {
			this.turnPlayer.decreaseAvailableTurns();
			return this.switchPlayer();
		}
		return true;
	}

	private Player getNextTurnPlayer()
	{
		int index = this.turnOrder.indexOf(this.turnPlayer);
		return index + 1 >= this.turnOrder.size() ? this.turnOrder.get(0) : this.turnOrder.get(index + 1);
	}

	private void sendGamePersonalBonusTileChoiceRequest(Player player)
	{
		player.getConnection().sendGamePersonalBonusTileChoiceRequest(this.availablePersonalBonusTiles);
		this.sendGamePersonalBonusTileChoiceOther(player);
	}

	private void sendGamePersonalBonusTileChoiceOther(Player player)
	{
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer != player && otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGamePersonalBonusTileChoiceOther(player.getIndex());
			}
		}
	}

	private void sendLeaderCardsChoiceRequest()
	{
		for (Entry<Player, List<Integer>> playerAvailableLeaderCards : this.availableLeaderCards.entrySet()) {
			if (!playerAvailableLeaderCards.getKey().isOnline()) {
				int currentLeaderCardIndex = playerAvailableLeaderCards.getValue().get(this.randomGenerator.nextInt(playerAvailableLeaderCards.getValue().size()));
				this.applyLeaderCardChoice(playerAvailableLeaderCards.getKey(), currentLeaderCardIndex);
			} else {
				playerAvailableLeaderCards.getKey().getConnection().sendGameLeaderCardChoiceRequest(playerAvailableLeaderCards.getValue());
			}
		}
	}

	public void sendGameUpdate(Player player)
	{
		player.getConnection().sendGameUpdate(this.generateGameInformations(), this.generatePlayersInformations(), this.generateLeaderCardsHand(player), this.generateAvailableActions(player));
		this.sendGameUpdateOtherTurn(player);
	}

	public void sendGameUpdateExpectedAction(Player player, ExpectedAction expectedAction)
	{
		player.getConnection().sendGameUpdateExpectedAction(this.generateGameInformations(), this.generatePlayersInformations(), this.generateLeaderCardsHand(player), expectedAction);
		this.sendGameUpdateOtherTurn(player);
	}

	private void sendGameUpdateOtherTurn(Player player)
	{
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer != player && otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGameUpdateOtherTurn(this.generateGameInformations(), this.generatePlayersInformations(), this.generateLeaderCardsHand(otherPlayer), player.getIndex());
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
		for (Player player : this.turnOrder) {
			turnOrderInformations.put(currentPlace, player.getIndex());
			currentPlace++;
		}
		Map<Integer, Integer> councilPalaceOrderInformations = new HashMap<>();
		currentPlace = 0;
		for (Player player : this.boardHandler.getCouncilPalaceOrder()) {
			councilPalaceOrderInformations.put(currentPlace, player.getIndex());
			currentPlace++;
		}
		return new GameInformations(developmentCardsBuildingInformations, developmentCardsCharacterInformations, developmentCardsTerritoryInformations, developmentCardsVentureInformations, turnOrderInformations, councilPalaceOrderInformations);
	}

	public List<PlayerInformations> generatePlayersInformations()
	{
		List<PlayerInformations> playersInformations = new ArrayList<>();
		for (Connection player : this.room.getPlayers()) {
			List<Integer> developmentCardsBuildingInformations = new ArrayList<>();
			for (DevelopmentCardBuilding developmentCardBuilding : player.getPlayer().getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
				developmentCardsBuildingInformations.add(developmentCardBuilding.getIndex());
			}
			List<Integer> developmentCardsCharacterInformations = new ArrayList<>();
			for (DevelopmentCardCharacter developmentCardCharacter : player.getPlayer().getPlayerCardHandler().getDevelopmentCards(CardType.CHARACTER, DevelopmentCardCharacter.class)) {
				developmentCardsCharacterInformations.add(developmentCardCharacter.getIndex());
			}
			List<Integer> developmentCardsTerritoryInformations = new ArrayList<>();
			for (DevelopmentCardTerritory developmentCardTerritory : player.getPlayer().getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class)) {
				developmentCardsTerritoryInformations.add(developmentCardTerritory.getIndex());
			}
			List<Integer> developmentCardsVentureInformations = new ArrayList<>();
			for (DevelopmentCardVenture developmentCardVenture : player.getPlayer().getPlayerCardHandler().getDevelopmentCards(CardType.VENTURE, DevelopmentCardVenture.class)) {
				developmentCardsVentureInformations.add(developmentCardVenture.getIndex());
			}
			Map<Integer, Boolean> leaderCardsPlayed = new HashMap<>();
			int leaderCardsInHandNumber = 0;
			for (LeaderCard leaderCard : player.getPlayer().getPlayerCardHandler().getLeaderCards()) {
				if (leaderCard.isPlayed()) {
					leaderCardsPlayed.put(leaderCard.getIndex(), leaderCard.getLeaderCardType() != LeaderCardType.MODIFIER && ((LeaderCardReward) leaderCard).isActivated());
				} else {
					leaderCardsInHandNumber++;
				}
			}
			playersInformations.add(new PlayerInformations(player.getPlayer().getIndex(), developmentCardsBuildingInformations, developmentCardsCharacterInformations, developmentCardsTerritoryInformations, developmentCardsVentureInformations, leaderCardsPlayed, leaderCardsInHandNumber, player.getPlayer().getPlayerResourceHandler().getResources(), player.getPlayer().getFamilyMembersPositions()));
		}
		return playersInformations;
	}

	public List<AvailableAction> generateAvailableActions(Player player)
	{
		List<AvailableAction> availableActions = new ArrayList<>();
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			if (player.getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE && new ActionCouncilPalace(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), player).isLegal()) {
				availableActions.add(new AvailableAction(ActionType.COUNCIL_PALACE));
				break;
			}
		}
		List<List<ResourceAmount>> discountChoices = new ArrayList<>();
		for (Modifier modifier : player.getActiveModifiers()) {
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
					if (player.getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE) {
						continue;
					}
					if (this.cardsHandler.getCurrentDevelopmentCards().get(cardType).get(row).getResourceCostOptions().isEmpty()) {
						if (new ActionGetDevelopmentCard(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, null, null, player).isLegal()) {
							validFamilyMember = true;
						}
					} else {
						for (ResourceCostOption resourceCostOption : this.cardsHandler.getCurrentDevelopmentCards().get(cardType).get(row).getResourceCostOptions()) {
							if (discountChoices.isEmpty()) {
								if (new ActionGetDevelopmentCard(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, null, resourceCostOption, player).isLegal()) {
									validFamilyMember = true;
								}
							} else {
								for (List<ResourceAmount> discountChoice : discountChoices) {
									if (new ActionGetDevelopmentCard(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, discountChoice, resourceCostOption, player).isLegal()) {
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
			if (player.getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE && new ActionHarvestStart(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), player).isLegal()) {
				availableActions.add(new AvailableAction(ActionType.HARVEST_START));
				break;
			}
		}
		for (MarketSlot marketSlot : MarketSlot.values()) {
			for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
				if (player.getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE && new ActionMarket(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), marketSlot, player).isLegal()) {
					availableActions.add(new AvailableActionMarket(marketSlot));
					break;
				}
			}
		}
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			if (player.getFamilyMembersPositions().get(familyMemberType) == BoardPosition.NONE && new ActionProductionStart(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), player).isLegal()) {
				availableActions.add(new AvailableAction(ActionType.PRODUCTION_START));
				break;
			}
		}
		return availableActions;
	}

	public List<Integer> generateLeaderCardsHand(Player player)
	{
		List<Integer> leaderCardsHand = new ArrayList<>();
		for (LeaderCard leaderCard : player.getPlayerCardHandler().getLeaderCards()) {
			if (!leaderCard.isPlayed()) {
				leaderCardsHand.add(leaderCard.getIndex());
			}
		}
		return leaderCardsHand;
	}

	public CardsHandler getCardsHandler()
	{
		return this.cardsHandler;
	}

	public BoardHandler getBoardHandler()
	{
		return this.boardHandler;
	}

	Random getRandomGenerator()
	{
		return this.randomGenerator;
	}

	public Map<Period, Integer> getExcommunicationTilesIndexes()
	{
		return this.excommunicationTilesIndexes;
	}

	public Map<Integer, PlayerIdentification> getPlayersIdentifications()
	{
		return this.playersIdentifications;
	}

	public Map<FamilyMemberType, Integer> getFamilyMemberTypeValues()
	{
		return this.familyMemberTypeValues;
	}

	public List<Player> getTurnOrder()
	{
		return this.turnOrder;
	}

	public Player getTurnPlayer()
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

	List<Integer> getAvailablePersonalBonusTiles()
	{
		return this.availablePersonalBonusTiles;
	}

	int getPersonalBonusTileChoicePlayerIndex()
	{
		return this.personalBonusTileChoicePlayerIndex;
	}

	Map<Player, List<Integer>> getAvailableLeaderCards()
	{
		return this.availableLeaderCards;
	}

	public Map<Player, Boolean> getLeaderCardsChoosingPlayers()
	{
		return this.leaderCardsChoosingPlayers;
	}
}
