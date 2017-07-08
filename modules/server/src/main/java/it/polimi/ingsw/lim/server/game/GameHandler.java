package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.actions.*;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.database.Database;
import it.polimi.ingsw.lim.server.enums.LeaderCardType;
import it.polimi.ingsw.lim.server.enums.QueryRead;
import it.polimi.ingsw.lim.server.enums.QueryValueType;
import it.polimi.ingsw.lim.server.enums.QueryWrite;
import it.polimi.ingsw.lim.server.game.actions.*;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.board.ExcommunicationTile;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.*;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.events.*;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.player.PlayerResourceHandler;
import it.polimi.ingsw.lim.server.game.utils.Phase;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.QueryArgument;
import it.polimi.ingsw.lim.server.utils.ServerSettings;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * <p>This class handles all the game logic of a single match.
 */
public class GameHandler
{
	private final Room room;
	private final CardsHandler cardsHandler = new CardsHandler();
	private final BoardHandler boardHandler;
	private final Random randomGenerator = new Random(System.nanoTime());
	private final Map<Period, List<DevelopmentCardBuilding>> developmentCardsBuilding = Utils.deepCopyDevelopmentCards(CardsHandler.getDevelopmentCardsBuilding());
	private final Map<Period, List<DevelopmentCardCharacter>> developmentCardsCharacters = Utils.deepCopyDevelopmentCards(CardsHandler.getDevelopmentCardsCharacter());
	private final Map<Period, List<DevelopmentCardTerritory>> developmentCardsTerritory = Utils.deepCopyDevelopmentCards(CardsHandler.getDevelopmentCardsTerritory());
	private final Map<Period, List<DevelopmentCardVenture>> developmentCardsVenture = Utils.deepCopyDevelopmentCards(CardsHandler.getDevelopmentCardsVenture());
	private final Map<Integer, PlayerIdentification> playersIdentifications = new HashMap<>();
	private final Map<FamilyMemberType, Integer> familyMemberTypeValues = new EnumMap<>(FamilyMemberType.class);
	private final List<Player> turnOrder = new LinkedList<>();
	private final Map<Period, List<Player>> excommunicatedPlayers = new EnumMap<>(Period.class);
	private final Map<Player, Boolean> firstTurn = new HashMap<>();
	private final List<Integer> availablePersonalBonusTiles = new ArrayList<>();
	private final Map<Player, List<Integer>> availableLeaderCards = new HashMap<>();
	private final List<Player> leaderCardsChoosingPlayers = new ArrayList<>();
	private final List<Player> excommunicationChoosingPlayers = new ArrayList<>();
	private final Map<Player, List<Player>> sentGoodGames = new HashMap<>();
	private Player turnPlayer;
	private Period currentPeriod;
	private Round currentRound;
	private Phase currentPhase;
	private boolean checkedExcommunications = false;
	private ActionType expectedAction;
	private int personalBonusTileChoicePlayerTurnIndex;
	private ScheduledExecutorService timerExecutor;
	private int timer;

	/**
	 * <p>Creates the match and sets up all initial attributes:
	 *
	 * <p>The match {@link ExcommunicationTile}s are randomly chosen.
	 *
	 * <p>The Council Privilege rewards are chosen based on the number of {@link
	 * Player}s.
	 *
	 * <p>The turn order is randomly initialized and resources are given
	 * accordingly.
	 *
	 * <p>The available {@link PersonalBonusTile}s are randomly chosen based on
	 * the number of {@link Player}s.
	 *
	 * @param room the {@link Room} on which hosts the match.
	 */
	public GameHandler(Room room)
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
		Map<Period, ExcommunicationTile> matchExcommunicationTiles = new EnumMap<>(Period.class);
		matchExcommunicationTiles.put(Period.FIRST, firstPeriodExcommunicationTiles.get(this.randomGenerator.nextInt(firstPeriodExcommunicationTiles.size())));
		matchExcommunicationTiles.put(Period.SECOND, secondPeriodExcommunicationTiles.get(this.randomGenerator.nextInt(secondPeriodExcommunicationTiles.size())));
		matchExcommunicationTiles.put(Period.THIRD, thirdPeriodExcommunicationTiles.get(this.randomGenerator.nextInt(thirdPeriodExcommunicationTiles.size())));
		Map<Integer, List<ResourceAmount>> matchCouncilPrivilegeRewards = new HashMap<>();
		if (this.room.getRoomType() == RoomType.EXTENDED) {
			matchCouncilPrivilegeRewards.putAll(BoardHandler.getCouncilPrivilegeRewards());
		} else {
			for (Entry<Integer, List<ResourceAmount>> councilPrivilegeReward : BoardHandler.getCouncilPrivilegeRewards().entrySet()) {
				boolean valid = true;
				for (ResourceAmount resourceAmount : councilPrivilegeReward.getValue()) {
					if (resourceAmount.getResourceType() == ResourceType.PRESTIGE_POINT) {
						valid = false;
						break;
					}
				}
				if (valid) {
					matchCouncilPrivilegeRewards.put(councilPrivilegeReward.getKey(), councilPrivilegeReward.getValue());
				}
			}
		}
		this.boardHandler = new BoardHandler(matchExcommunicationTiles, matchCouncilPrivilegeRewards);
		Map<Period, Integer> matchExcommunicationTilesIndexes = new EnumMap<>(Period.class);
		matchExcommunicationTilesIndexes.put(Period.FIRST, matchExcommunicationTiles.get(Period.FIRST).getIndex());
		matchExcommunicationTilesIndexes.put(Period.SECOND, matchExcommunicationTiles.get(Period.SECOND).getIndex());
		matchExcommunicationTilesIndexes.put(Period.THIRD, matchExcommunicationTiles.get(Period.THIRD).getIndex());
		for (Period period : Period.values()) {
			Collections.shuffle(this.developmentCardsBuilding.get(period), this.randomGenerator);
			Collections.shuffle(this.developmentCardsCharacters.get(period), this.randomGenerator);
			Collections.shuffle(this.developmentCardsTerritory.get(period), this.randomGenerator);
			Collections.shuffle(this.developmentCardsVenture.get(period), this.randomGenerator);
		}
		this.familyMemberTypeValues.put(FamilyMemberType.NEUTRAL, 0);
		int currentIndex = 0;
		for (Connection connection : this.room.getPlayers()) {
			connection.setPlayer(new Player(connection, this.room, currentIndex));
			this.playersIdentifications.put(currentIndex, new PlayerIdentification(connection.getUsername(), Color.values()[currentIndex]));
			this.turnOrder.add(connection.getPlayer());
			this.firstTurn.put(connection.getPlayer(), true);
			currentIndex++;
		}
		Collections.shuffle(this.turnOrder, this.randomGenerator);
		this.excommunicatedPlayers.put(Period.FIRST, new ArrayList<>());
		this.excommunicatedPlayers.put(Period.SECOND, new ArrayList<>());
		this.excommunicatedPlayers.put(Period.THIRD, new ArrayList<>());
		this.personalBonusTileChoicePlayerTurnIndex = this.turnOrder.size() - 1;
		for (int index = 0; index < PersonalBonusTile.values().length; index++) {
			if (index == 4 && this.room.getRoomType() == RoomType.NORMAL) {
				break;
			}
			this.availablePersonalBonusTiles.add(PersonalBonusTile.values()[index].getIndex());
		}
		List<LeaderCard> leaderCards = new ArrayList<>(CardsHandler.getLeaderCards());
		int startingCoins = 5;
		for (Player player : this.turnOrder) {
			player.getConnection().sendGameStarted(matchExcommunicationTilesIndexes, matchCouncilPrivilegeRewards, this.playersIdentifications, player.getIndex());
			player.getPlayerResourceHandler().addResource(ResourceType.COIN, startingCoins);
			List<Integer> playerAvailableLeaderCards = new ArrayList<>();
			for (int index = 0; index < 4; index++) {
				LeaderCard leaderCard = leaderCards.get(this.randomGenerator.nextInt(leaderCards.size()));
				leaderCards.remove(leaderCard);
				playerAvailableLeaderCards.add(leaderCard.getIndex());
			}
			this.availableLeaderCards.put(player, playerAvailableLeaderCards);
			this.sentGoodGames.put(player, new ArrayList<>());
			startingCoins++;
		}
	}

	/**
	 * <p>Starts the match by sending the first request.
	 */
	void start()
	{
		this.sendGamePersonalBonusTileChoiceRequest(this.turnOrder.get(this.personalBonusTileChoicePlayerTurnIndex));
	}

	/**
	 * <p>Receives a {@link PersonalBonusTile} choice and checks its regularity.
	 *
	 * @param player the {@link Player} who sent the choice.
	 * @param personalBonusTileIndex the chosen {@link PersonalBonusTile}
	 * index.
	 *
	 * @throws GameActionFailedException if the action is irregular.
	 */
	public void receivePersonalBonusTileChoice(Player player, int personalBonusTileIndex) throws GameActionFailedException
	{
		if (this.personalBonusTileChoicePlayerTurnIndex < 0 || this.turnOrder.get(this.personalBonusTileChoicePlayerTurnIndex) != player || !this.availablePersonalBonusTiles.contains(personalBonusTileIndex)) {
			throw new GameActionFailedException("You cannot do this!");
		}
		try {
			this.applyPersonalBonusTileChoice(player, personalBonusTileIndex);
		} catch (NoSuchElementException exception) {
			Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			throw new GameActionFailedException("This Personal Bonus Tile does not exist");
		}
	}

	/**
	 * <p>Applies a {@link PersonalBonusTile} choice.
	 *
	 * <p>If everyone chose his {@link PersonalBonusTile}, sets up the game for
	 * the {@link LeaderCard} choice.
	 *
	 * @param player the {@link Player} who sent the choice.
	 * @param personalBonusTileIndex the chosen {@link PersonalBonusTile}
	 * index.
	 */
	void applyPersonalBonusTileChoice(Player player, int personalBonusTileIndex)
	{
		this.timerExecutor.shutdownNow();
		player.setPersonalBonusTile(PersonalBonusTile.fromIndex(personalBonusTileIndex));
		this.availablePersonalBonusTiles.remove((Integer) personalBonusTileIndex);
		for (Player currentPlayer : this.turnOrder) {
			if (currentPlayer.isOnline()) {
				currentPlayer.getConnection().sendGamePersonalBonusTileChosen(player.getIndex());
			}
		}
		this.personalBonusTileChoicePlayerTurnIndex--;
		if (this.personalBonusTileChoicePlayerTurnIndex < 0) {
			this.leaderCardsChoosingPlayers.addAll(this.turnOrder);
			this.sendLeaderCardsChoiceRequest();
			return;
		}
		if (!this.turnOrder.get(this.personalBonusTileChoicePlayerTurnIndex).isOnline()) {
			int nextPlayerPersonalBonusTileIndex = this.availablePersonalBonusTiles.get(this.randomGenerator.nextInt(this.availablePersonalBonusTiles.size()));
			this.applyPersonalBonusTileChoice(this.turnOrder.get(this.personalBonusTileChoicePlayerTurnIndex), nextPlayerPersonalBonusTileIndex);
			return;
		}
		this.sendGamePersonalBonusTileChoiceRequest(this.turnOrder.get(this.personalBonusTileChoicePlayerTurnIndex));
	}

	/**
	 * <p>Receives a {@link LeaderCard} choice and checks its regularity.
	 *
	 * @param player the {@link Player} who sent the choice.
	 * @param leaderCardIndex the chosen {@link LeaderCard} index.
	 *
	 * @throws GameActionFailedException if the action is irregular.
	 */
	public void receiveLeaderCardChoice(Player player, int leaderCardIndex) throws GameActionFailedException
	{
		if (!this.leaderCardsChoosingPlayers.contains(player) || !this.availableLeaderCards.get(player).contains(leaderCardIndex)) {
			throw new GameActionFailedException("You cannot do this!");
		}
		try {
			this.applyLeaderCardChoice(player, leaderCardIndex);
		} catch (NoSuchElementException exception) {
			Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			throw new GameActionFailedException("This Leader Card does not exist");
		}
	}

	/**
	 * <p>Applies a {@link LeaderCard} choice.
	 *
	 * <p>If everyone chose all the {@link LeaderCard}s, sets up the game for
	 * the first {@link Period} and {@link Round}.
	 *
	 * @param player the {@link Player} who sent the choice.
	 * @param leaderCardIndex the chosen {@link LeaderCard} index.
	 */
	void applyLeaderCardChoice(Player player, int leaderCardIndex)
	{
		this.leaderCardsChoosingPlayers.remove(player);
		player.getPlayerCardHandler().addLeaderCard(CardsHandler.getleaderCardFromIndex(leaderCardIndex));
		this.availableLeaderCards.get(player).remove((Integer) leaderCardIndex);
		boolean finished = true;
		for (List<Integer> playerAvailableLeaderCards : this.availableLeaderCards.values()) {
			if (!playerAvailableLeaderCards.isEmpty()) {
				finished = false;
			}
		}
		if (finished) {
			this.timerExecutor.shutdownNow();
			this.setupRound();
			return;
		}
		if (this.leaderCardsChoosingPlayers.isEmpty()) {
			this.timerExecutor.shutdownNow();
			Map<Player, List<Integer>> newlyAvailableLeaderCards = new HashMap<>();
			List<List<Integer>> oldAvailableLeaderCards = new ArrayList<>(this.availableLeaderCards.values());
			int currentListIndex = 0;
			for (Player currentPlayer : this.availableLeaderCards.keySet()) {
				this.leaderCardsChoosingPlayers.add(currentPlayer);
				if (currentListIndex + 1 >= this.availableLeaderCards.size()) {
					newlyAvailableLeaderCards.put(currentPlayer, oldAvailableLeaderCards.get(0));
				} else {
					newlyAvailableLeaderCards.put(currentPlayer, oldAvailableLeaderCards.get(++currentListIndex));
				}
			}
			this.availableLeaderCards.clear();
			this.availableLeaderCards.putAll(newlyAvailableLeaderCards);
			this.sendLeaderCardsChoiceRequest();
		}
	}

	/**
	 * <p>Receives an excommunication choice and checks its regularity.
	 *
	 * @param player the {@link Player} who sent the choice.
	 * @param excommunicated whether the {@link Player} chose to be
	 * excommunicated or not.
	 *
	 * @throws GameActionFailedException if the action is irregular.
	 */
	public void receiveExcommunicationChoice(Player player, boolean excommunicated) throws GameActionFailedException
	{
		if (!this.excommunicationChoosingPlayers.contains(player)) {
			throw new GameActionFailedException("You cannot do this!");
		}
		this.applyExcommunicationChoice(player, excommunicated);
	}

	/**
	 * <p>Applies an excommunication choice.
	 *
	 * <p>If everyone chose an outcome for the excommunication phase, sets up
	 * the game for the next {@link Period}.
	 *
	 * @param player the {@link Player} who sent the choice.
	 * @param excommunicated whether the {@link Player} chose to be
	 * excommunicated or not.
	 */
	void applyExcommunicationChoice(Player player, boolean excommunicated)
	{
		this.excommunicationChoosingPlayers.remove(player);
		if (excommunicated) {
			this.excommunicatedPlayers.get(this.currentPeriod).add(player);
			player.getActiveModifiers().add(this.boardHandler.getMatchExcommunicationTiles().get(this.currentPeriod).getModifier());
		} else {
			EventChurchSupport eventChurchSupport = new EventChurchSupport(player);
			eventChurchSupport.fire();
			player.getPlayerResourceHandler().addResource(ResourceType.VICTORY_POINT, eventChurchSupport.getVictoryPoints() + PlayerResourceHandler.getFaithPointsPrices().get(player.getPlayerResourceHandler().getResources().get(ResourceType.FAITH_POINT)));
			player.getPlayerResourceHandler().resetFaithPoints();
		}
		if (this.excommunicationChoosingPlayers.isEmpty()) {
			this.timerExecutor.shutdownNow();
			this.checkedExcommunications = true;
			this.setupRound();
		}
	}

	/**
	 * <p>Receives an {@link IAction}, checks its regularity and applies it.
	 *
	 * @param player the {@link Player} who sent the {@link IAction}.
	 * @param actionInformation the {@link IAction} represented as a network
	 * object.
	 *
	 * @throws GameActionFailedException if the action is irregular.
	 */
	public void receiveAction(Player player, ActionInformation actionInformation) throws GameActionFailedException
	{
		IAction action = Utils.getActionsTransformers().get(actionInformation.getActionType()).transform(actionInformation, player);
		action.isLegal();
		this.timerExecutor.shutdownNow();
		action.apply();
	}

	/**
	 * <p>Applies a "Good Game" vote to the target {@link Player}.
	 *
	 * @param sender the {@link Player} who sent the vote.
	 * @param receiverIndex the index of the {@link Player} targeted by the
	 * vote.
	 *
	 * @throws GameActionFailedException if the receiving {@link Player} is the
	 * sending one or he has already received a vote by the same sender.
	 */
	public void applyGoodGame(Player sender, int receiverIndex) throws GameActionFailedException
	{
		Player receiver = this.getPlayerFromIndex(receiverIndex);
		if (this.sentGoodGames.get(sender).contains(receiver) || sender == receiver) {
			throw new GameActionFailedException("You cannot do this!");
		}
		this.sentGoodGames.get(sender).add(receiver);
		List<QueryArgument> queryArguments = new ArrayList<>();
		queryArguments.add(new QueryArgument(QueryValueType.STRING, receiver.getConnection().getUsername()));
		Server.getInstance().getDatabaseSaver().execute(() -> {
			try {
				Utils.sqlWrite(QueryWrite.UPDATE_GOOD_GAMES, queryArguments);
			} catch (SQLException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
		});
	}

	/**
	 * <p>Retrieves a {@link Player} in this match with his index.
	 *
	 * @param playerIndex the index of the requested {@link Player}.
	 *
	 * @return the requested {@link Player}.
	 */
	private Player getPlayerFromIndex(int playerIndex)
	{
		for (Player player : this.turnOrder) {
			if (player.getIndex() == playerIndex) {
				return player;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * <p>Sets up a new game {@link Round}.
	 *
	 * <p>This method is also used to track the general timing of the game
	 * status. It sets up a new {@link Period}, if needed, and checks if the
	 * game has ended.
	 */
	public void setupRound()
	{
		if (this.currentRound == null) {
			// the game is being started
			this.setupPeriod();
		} else if (this.currentRound == Round.SECOND) {
			if (!this.checkedExcommunications) {
				this.calculateExcommunications();
				return;
			}
			this.setupPeriod();
		} else {
			this.currentRound = Round.SECOND;
		}
		if (this.currentPeriod == null) {
			// the game has ended
			return;
		}
		for (Player player : this.turnOrder) {
			player.resetAvailableTurns();
			this.firstTurn.put(player, true);
			for (FamilyMemberType familyMemberType : player.getFamilyMembersPositions().keySet()) {
				player.getFamilyMembersPositions().put(familyMemberType, BoardPosition.NONE);
			}
		}
		Connection.broadcastLogMessage(this.room, "*** " + this.currentRound.name() + " ROUND ***");
		this.setupTurnOrder();
		this.boardHandler.getCouncilPalaceOrder().clear();
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
		for (LeaderCard leaderCard : this.turnPlayer.getPlayerCardHandler().getLeaderCards()) {
			if (leaderCard instanceof LeaderCardReward) {
				((LeaderCardReward) leaderCard).setActivated(false);
			}
		}
		this.turnPlayer.decreaseAvailableTurns();
		this.sendGameUpdate(this.turnPlayer);
	}

	/**
	 * <p>Sets up a new game {@link Period}.
	 */
	private void setupPeriod()
	{
		if (this.currentPeriod == null) {
			// the game is being started
			this.currentPeriod = Period.FIRST;
			this.currentRound = Round.FIRST;
			Connection.broadcastLogMessage(this.room, "=== " + this.currentPeriod.name() + " PERIOD ===");
			return;
		}
		this.currentPeriod = Period.next(this.currentPeriod);
		if (this.currentPeriod == null) {
			// the are no more periods to play
			this.endGame();
			return;
		}
		for (Player player : this.turnOrder) {
			if (player.isOnline()) {
				player.getConnection().sendGameLogMessage("=== " + this.currentPeriod.name() + " PERIOD ===");
			}
		}
		this.currentRound = Round.FIRST;
		this.checkedExcommunications = false;
	}

	/**
	 * <p>Ends the game, calculating and saving the scores.
	 */
	private void endGame()
	{
		this.room.setEndGame(true);
		for (Player player : this.turnOrder) {
			if (player.isOnline()) {
				player.getConnection().sendGameLogMessage("==================\n=== GAME ENDED ===\n==================");
			}
		}
		boolean someoneOnline = false;
		for (Player player : this.turnOrder) {
			if (player.isOnline()) {
				someoneOnline = true;
			}
		}
		if (!someoneOnline) {
			Server.getInstance().getRooms().remove(this.room);
			return;
		}
		Map<Player, Integer> playersScores = new LinkedHashMap<>();
		List<Player> militaryPointsFirstPlayers = new ArrayList<>();
		List<Player> militaryPointsSecondPlayers = new ArrayList<>();
		for (Player player : this.turnOrder) {
			if (militaryPointsFirstPlayers.isEmpty()) {
				militaryPointsFirstPlayers.add(player);
			} else {
				if (player.getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT).equals(militaryPointsFirstPlayers.get(0).getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT))) {
					militaryPointsFirstPlayers.add(player);
				} else if (player.getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT) > militaryPointsFirstPlayers.get(0).getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT)) {
					militaryPointsFirstPlayers.clear();
					militaryPointsFirstPlayers.add(player);
				} else if (militaryPointsSecondPlayers.isEmpty() || player.getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT).equals(militaryPointsSecondPlayers.get(0).getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT))) {
					militaryPointsSecondPlayers.add(player);
				} else if (player.getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT) > militaryPointsSecondPlayers.get(0).getPlayerResourceHandler().getResources().get(ResourceType.MILITARY_POINT)) {
					militaryPointsSecondPlayers.clear();
					militaryPointsSecondPlayers.add(player);
				}
			}
		}
		List<Player> prestigePointsFirstPlayers = new ArrayList<>();
		List<Player> prestigePointsSecondPlayers = new ArrayList<>();
		for (Player player : this.turnOrder) {
			if (prestigePointsFirstPlayers.isEmpty()) {
				prestigePointsFirstPlayers.add(player);
			} else {
				if (player.getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT).equals(prestigePointsFirstPlayers.get(0).getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT))) {
					prestigePointsFirstPlayers.add(player);
				} else if (player.getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT) > prestigePointsFirstPlayers.get(0).getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT)) {
					prestigePointsFirstPlayers.clear();
					prestigePointsFirstPlayers.add(player);
				} else if (prestigePointsSecondPlayers.isEmpty() || player.getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT).equals(prestigePointsSecondPlayers.get(0).getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT))) {
					prestigePointsSecondPlayers.add(player);
				} else if (player.getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT) > prestigePointsSecondPlayers.get(0).getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT)) {
					prestigePointsSecondPlayers.clear();
					prestigePointsSecondPlayers.add(player);
				}
			}
		}
		for (Player player : this.turnOrder) {
			EventPreVictoryPointsCalculation eventPreVictoryPointsCalculation = new EventPreVictoryPointsCalculation(player);
			eventPreVictoryPointsCalculation.fire();
			player.getPlayerResourceHandler().resetVictoryPoints();
			player.getPlayerResourceHandler().addResource(ResourceType.VICTORY_POINT, eventPreVictoryPointsCalculation.getVictoryPoints());
			EventVictoryPointsCalculation eventVictoryPointsCalculation = new EventVictoryPointsCalculation(player);
			eventPreVictoryPointsCalculation.fire();
			player.convertToVictoryPoints(eventVictoryPointsCalculation.isCountingCharacters(), eventVictoryPointsCalculation.isCountingTerritories(), eventVictoryPointsCalculation.isCountingVentures());
			if (militaryPointsFirstPlayers.contains(player)) {
				player.getPlayerResourceHandler().addResource(ResourceType.VICTORY_POINT, 5);
			} else if (militaryPointsSecondPlayers.contains(player)) {
				player.getPlayerResourceHandler().addResource(ResourceType.VICTORY_POINT, 2);
			}
			if (prestigePointsFirstPlayers.contains(player)) {
				player.getPlayerResourceHandler().addResource(ResourceType.VICTORY_POINT, player.getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT) * 2);
			} else if (prestigePointsSecondPlayers.contains(player)) {
				player.getPlayerResourceHandler().addResource(ResourceType.VICTORY_POINT, player.getPlayerResourceHandler().getResources().get(ResourceType.PRESTIGE_POINT));
			}
			EventPostVictoryPointsCalculation eventPostVictoryPointsCalculation = new EventPostVictoryPointsCalculation(player);
			player.getPlayerResourceHandler().resetVictoryPoints();
			player.getPlayerResourceHandler().addResource(ResourceType.VICTORY_POINT, eventPostVictoryPointsCalculation.getVictoryPoints());
			playersScores.put(player, player.getPlayerResourceHandler().getResources().get(ResourceType.VICTORY_POINT));
		}
		Map<Integer, Integer> playerIndexesVictoryPointsRecord = new LinkedHashMap<>();
		int index = 0;
		for (Entry<Player, Integer> playerScore : playersScores.entrySet()) {
			List<QueryArgument> queryArguments = new ArrayList<>();
			queryArguments.add(new QueryArgument(QueryValueType.STRING, playerScore.getKey().getConnection().getUsername()));
			try (ResultSet resultSet = Utils.sqlRead(QueryRead.GET_PLAYER_VICTORY_POINTS_RECORD, queryArguments)) {
				resultSet.next();
				if (resultSet.getInt(Database.TABLE_PLAYERS_COLUMN_VICTORY_POINTS_RECORD) < playerScore.getValue()) {
					playerIndexesVictoryPointsRecord.put(playerScore.getKey().getIndex(), playerScore.getValue());
					queryArguments.clear();
					queryArguments.add(new QueryArgument(QueryValueType.INTEGER, playerScore.getValue()));
					queryArguments.add(new QueryArgument(QueryValueType.STRING, playerScore.getKey().getConnection().getUsername()));
					Server.getInstance().getDatabaseSaver().execute(() -> {
						try {
							Utils.sqlWrite(QueryWrite.UPDATE_PLAYER_VICTORY_POINTS_RECORD, queryArguments);
						} catch (SQLException exception) {
							Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
						}
					});
				} else {
					playerIndexesVictoryPointsRecord.put(playerScore.getKey().getIndex(), resultSet.getInt(Database.TABLE_PLAYERS_COLUMN_VICTORY_POINTS_RECORD));
				}
				resultSet.getStatement().close();
			} catch (SQLException exception) {
				Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
			queryArguments.clear();
			queryArguments.add(new QueryArgument(QueryValueType.STRING, playerScore.getKey().getConnection().getUsername()));
			if (index == 0) {
				Server.getInstance().getDatabaseSaver().execute(() -> {
					try {
						Utils.sqlWrite(QueryWrite.UPDATE_PLAYER_VICTORY, queryArguments);
					} catch (SQLException exception) {
						Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
					}
				});
			} else {
				Server.getInstance().getDatabaseSaver().execute(() -> {
					try {
						Utils.sqlWrite(QueryWrite.UPDATE_PLAYER_DEFEAT, queryArguments);
					} catch (SQLException exception) {
						Server.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
					}
				});
			}
			index++;
		}
		Map<Integer, Integer> playersIndexesScores = new LinkedHashMap<>();
		for (Entry<Player, Integer> playerScore : playersScores.entrySet()) {
			playersIndexesScores.put(playerScore.getKey().getIndex(), playerScore.getValue());
		}
		for (Player player : this.turnOrder) {
			if (player.isOnline()) {
				player.getConnection().sendGameEnded(playersIndexesScores, playerIndexesVictoryPointsRecord);
			}
		}
		this.timer = ServerSettings.getInstance().getEndGameTimer();
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGameTimer(this.timer);
			}
		}
		this.timerExecutor.shutdownNow();
		this.timerExecutor = Executors.newSingleThreadScheduledExecutor();
		this.timerExecutor.scheduleWithFixedDelay(() -> {
			this.timer--;
			if (this.timer == 0) {
				for (Player player : this.turnOrder) {
					if (player.isOnline()) {
						player.getConnection().disconnect(false, "Thank you for playing!");
					}
				}
				Server.getInstance().getRooms().remove(this.room);
			} else {
				for (Player otherPlayer : this.turnOrder) {
					if (otherPlayer.isOnline()) {
						otherPlayer.getConnection().sendGameTimer(this.timer);
					}
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
	}

	/**
	 * <p>Randomly sets new dices values.
	 */
	private void rollDices()
	{
		this.familyMemberTypeValues.put(FamilyMemberType.BLACK, this.randomGenerator.nextInt(6) + 1);
		this.familyMemberTypeValues.put(FamilyMemberType.ORANGE, this.randomGenerator.nextInt(6) + 1);
		this.familyMemberTypeValues.put(FamilyMemberType.WHITE, this.randomGenerator.nextInt(6) + 1);
	}

	/**
	 * <p>Randomly draws new {@link DevelopmentCard}s and puts the on the right
	 * towers.
	 */
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

	/**
	 * <p>Checks the excommunications and sets up the game for the
	 * excommunication choices.
	 *
	 * <p>If a {@link Player} does not meet the faith points requirement, he is
	 * automatically excommunicated.
	 */
	void calculateExcommunications()
	{
		for (Player player : this.turnOrder) {
			if (player.isExcommunicated(this.currentPeriod)) {
				this.excommunicatedPlayers.get(this.currentPeriod).add(player);
				player.getActiveModifiers().add(this.boardHandler.getMatchExcommunicationTiles().get(this.currentPeriod).getModifier());
			} else {
				if (this.currentPeriod != Period.THIRD) {
					this.excommunicationChoosingPlayers.add(player);
				} else {
					player.getPlayerResourceHandler().addResource(ResourceType.VICTORY_POINT, PlayerResourceHandler.getFaithPointsPrices().get(player.getPlayerResourceHandler().getResources().get(ResourceType.FAITH_POINT)));
				}
			}
		}
		if (this.excommunicationChoosingPlayers.isEmpty()) {
			this.checkedExcommunications = true;
			this.setupRound();
			return;
		}
		this.timer = ServerSettings.getInstance().getExcommunicationChoiceTimer();
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGameTimer(this.timer);
			}
		}
		this.timerExecutor = Executors.newSingleThreadScheduledExecutor();
		this.timerExecutor.scheduleWithFixedDelay(() -> {
			this.timer--;
			if (this.timer == 0) {
				for (Player player : this.excommunicationChoosingPlayers) {
					this.applyExcommunicationChoice(player, false);
				}
			} else {
				for (Player otherPlayer : this.turnOrder) {
					if (otherPlayer.isOnline()) {
						otherPlayer.getConnection().sendGameTimer(this.timer);
					}
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
		for (Player player : this.turnOrder) {
			if (this.excommunicationChoosingPlayers.contains(player)) {
				player.getConnection().sendGameExcommunicationChoiceRequest(this.currentPeriod);
			} else {
				player.getConnection().sendGameExoommunicationChoiceOther();
			}
		}
	}

	/**
	 * <p>Applies the right turn order by checking the current Council Palace
	 * order.
	 */
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

	/**
	 * <p>Stops the current {@link Player} turn and sets up the game for the
	 * next one.
	 */
	public void nextTurn()
	{
		this.timerExecutor.shutdownNow();
		this.currentPhase = Phase.LEADER;
		this.expectedAction = null;
		for (Modifier temporaryModifier : this.turnPlayer.getTemporaryModifiers()) {
			this.turnPlayer.getActiveModifiers().remove(temporaryModifier);
		}
		this.turnPlayer.getTemporaryModifiers().clear();
		this.turnPlayer.getPlayerResourceHandler().convertTemporaryResources();
		boolean endRound = true;
		for (Player player : this.turnOrder) {
			if (player.getAvailableTurns() > 0) {
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

	/**
	 * <p>Switches to the next {@link Player} in the turn order.
	 *
	 * <p>If the new {@link Player} is offline or finished his available turns,
	 * skips to the next one until no {@link Player} is left.
	 *
	 * @return true if the game goes on, otherwise false.
	 */
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
			eventFirstTurn.fire();
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

	/**
	 * <p>Sends a {@link PersonalBonusTile} choice request to the target {@link
	 * Player}.
	 *
	 * @param player the target {@link Player}.
	 */
	private void sendGamePersonalBonusTileChoiceRequest(Player player)
	{
		player.getConnection().sendGamePersonalBonusTileChoiceRequest(this.availablePersonalBonusTiles);
		this.timer = ServerSettings.getInstance().getPersonalBonusTileChoiceTimer();
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGameTimer(this.timer);
			}
		}
		this.timerExecutor = Executors.newSingleThreadScheduledExecutor();
		this.timerExecutor.scheduleWithFixedDelay(() -> {
			this.timer--;
			if (this.timer == 0) {
				int personalBonusTileIndex = this.availablePersonalBonusTiles.get(this.randomGenerator.nextInt(this.availablePersonalBonusTiles.size()));
				this.applyPersonalBonusTileChoice(player, personalBonusTileIndex);
			} else {
				for (Player otherPlayer : this.turnOrder) {
					if (otherPlayer.isOnline()) {
						otherPlayer.getConnection().sendGameTimer(this.timer);
					}
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
		this.sendGamePersonalBonusTileChoiceOther(player);
	}

	/**
	 * <p>Informs {@link Player}s about the target {@link Player} {@link
	 * PersonalBonusTile} choice request.
	 *
	 * @param player the request target {@link Player}.
	 */
	private void sendGamePersonalBonusTileChoiceOther(Player player)
	{
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer != player && otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGamePersonalBonusTileChoiceOther(player.getIndex());
			}
		}
	}

	/**
	 * <p>Sends everyone a {@link LeaderCard} choice request.
	 */
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
		this.timer = ServerSettings.getInstance().getLeaderCardsChoiceTimer();
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGameTimer(this.timer);
			}
		}
		this.timerExecutor = Executors.newSingleThreadScheduledExecutor();
		this.timerExecutor.scheduleWithFixedDelay(() -> {
			this.timer--;
			if (this.timer == 0) {
				for (Player player : this.leaderCardsChoosingPlayers) {
					int leaderCardIndex = this.availableLeaderCards.get(player).get(this.randomGenerator.nextInt(this.availableLeaderCards.get(player).size()));
					this.applyLeaderCardChoice(player, leaderCardIndex);
				}
			} else {
				for (Player otherPlayer : this.turnOrder) {
					if (otherPlayer.isOnline()) {
						otherPlayer.getConnection().sendGameTimer(this.timer);
					}
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
	}

	/**
	 * <p>Sends a game update to the target {@link Player}, meaning it is his
	 * turn, and informs the others.
	 *
	 * @param player the target {@link Player}.
	 */
	public void sendGameUpdate(Player player)
	{
		this.timerExecutor.shutdownNow();
		this.timer = ServerSettings.getInstance().getGameActionTimer();
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGameTimer(this.timer);
			}
		}
		this.timerExecutor = Executors.newSingleThreadScheduledExecutor();
		this.timerExecutor.scheduleWithFixedDelay(() -> {
			this.timer--;
			if (this.timer == 0) {
				this.nextTurn();
			} else {
				for (Player otherPlayer : this.turnOrder) {
					if (otherPlayer.isOnline()) {
						otherPlayer.getConnection().sendGameTimer(this.timer);
					}
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
		player.getConnection().sendGameUpdate(this.generateGameInformation(), this.generatePlayersInformation(), this.generateLeaderCardsHand(player), this.generateAvailableActions(player));
		this.sendGameUpdateOtherTurn(player);
	}

	/**
	 * <p>Sends a game update to the target {@link Player}, meaning it is his
	 * turn and he has to perform an {@link ExpectedAction}, and informs the
	 * others.
	 *
	 * @param player the target {@link Player}.
	 */
	public void sendGameUpdateExpectedAction(Player player, ExpectedAction expectedAction)
	{
		this.timer = ServerSettings.getInstance().getGameActionTimer();
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGameTimer(this.timer);
			}
		}
		this.timerExecutor = Executors.newSingleThreadScheduledExecutor();
		this.timerExecutor.scheduleWithFixedDelay(() -> {
			this.timer--;
			if (this.timer == 0) {
				this.expectedAction = null;
				player.getPlayerResourceHandler().getTemporaryResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
				player.getPlayerResourceHandler().getResources().put(ResourceType.COUNCIL_PRIVILEGE, 0);
				if (this.currentPhase == Phase.LEADER) {
					this.timer = ServerSettings.getInstance().getGameActionTimer();
					this.sendGameUpdate(player);
				} else {
					this.nextTurn();
				}
			} else {
				for (Player otherPlayer : this.turnOrder) {
					if (otherPlayer.isOnline()) {
						otherPlayer.getConnection().sendGameTimer(this.timer);
					}
				}
			}
		}, 1L, 1L, TimeUnit.SECONDS);
		player.getConnection().sendGameUpdateExpectedAction(this.generateGameInformation(), this.generatePlayersInformation(), this.generateLeaderCardsHand(player), expectedAction);
		this.sendGameUpdateOtherTurn(player);
	}

	/**
	 * <p>Informs {@link Player}s about the target {@link Player} game action.
	 *
	 * @param player the game action target {@link Player}.
	 */
	private void sendGameUpdateOtherTurn(Player player)
	{
		for (Player otherPlayer : this.turnOrder) {
			if (otherPlayer != player && otherPlayer.isOnline()) {
				otherPlayer.getConnection().sendGameUpdateOtherTurn(this.generateGameInformation(), this.generatePlayersInformation(), this.generateLeaderCardsHand(otherPlayer), player.getIndex());
			}
		}
	}

	/**
	 * <p>Generates the current game status as a client shareable object. The
	 * generated object includes:
	 *
	 * <p>The current {@link DevelopmentCard}s available in the game towers.
	 *
	 * <p>The current dices values.
	 *
	 * <p>The current turn order.
	 *
	 * <p>The current Council Palace order.
	 *
	 * <p>The current excommunicated {@link Player}s.
	 *
	 * @return an object representing the current game status.
	 */
	public GameInformation generateGameInformation()
	{
		Map<CardType, Map<Row, Integer>> developmentCardsInformation = new EnumMap<>(CardType.class);
		developmentCardsInformation.put(CardType.BUILDING, new EnumMap<>(Row.class));
		developmentCardsInformation.put(CardType.CHARACTER, new EnumMap<>(Row.class));
		developmentCardsInformation.put(CardType.TERRITORY, new EnumMap<>(Row.class));
		developmentCardsInformation.put(CardType.VENTURE, new EnumMap<>(Row.class));
		for (CardType cardType : CardType.values()) {
			for (Row row : Row.values()) {
				DevelopmentCard developmentCard = this.cardsHandler.getCurrentDevelopmentCards().get(cardType).get(row);
				if (developmentCard != null) {
					developmentCardsInformation.get(cardType).put(row, developmentCard.getIndex());
				}
			}
		}
		Map<FamilyMemberType, Integer> dices = new EnumMap<>(FamilyMemberType.class);
		for (Entry<FamilyMemberType, Integer> dice : this.familyMemberTypeValues.entrySet()) {
			if (dice.getKey() != FamilyMemberType.NEUTRAL) {
				dices.put(dice.getKey(), dice.getValue());
			}
		}
		Map<Integer, Integer> turnOrderInformation = new HashMap<>();
		int currentPlace = 0;
		for (Player player : this.turnOrder) {
			turnOrderInformation.put(currentPlace, player.getIndex());
			currentPlace++;
		}
		Map<Integer, Integer> councilPalaceOrderInformation = new HashMap<>();
		currentPlace = 0;
		for (Player player : this.boardHandler.getCouncilPalaceOrder()) {
			councilPalaceOrderInformation.put(currentPlace, player.getIndex());
			currentPlace++;
		}
		Map<Period, List<Integer>> excommunicatedPlayersIndexes = new EnumMap<>(Period.class);
		excommunicatedPlayersIndexes.put(Period.FIRST, new ArrayList<>());
		excommunicatedPlayersIndexes.put(Period.SECOND, new ArrayList<>());
		excommunicatedPlayersIndexes.put(Period.THIRD, new ArrayList<>());
		for (Entry<Period, List<Player>> period : this.excommunicatedPlayers.entrySet()) {
			for (Player player : period.getValue()) {
				excommunicatedPlayersIndexes.get(period.getKey()).add(player.getIndex());
			}
		}
		return new GameInformation(developmentCardsInformation, dices, turnOrderInformation, councilPalaceOrderInformation, excommunicatedPlayersIndexes);
	}

	/**
	 * <p>Generates the current {@link Player}s statuses as client shareable
	 * objects. The generated objects includes:
	 *
	 * <p>The {@link Player} {@link DevelopmentCard}s.
	 *
	 * <p>The {@link Player} played {@link LeaderCard}s.
	 *
	 * <p>The {@link Player} {@link LeaderCard}s in hand number.
	 *
	 * <p>The {@link Player} resources.
	 *
	 * <p>The {@link Player} {@link FamilyMemberType}s positions.
	 *
	 * @return a {@link List} of objects representing the {@link Player}s
	 * statuses.
	 */
	public List<PlayerInformation> generatePlayersInformation()
	{
		List<PlayerInformation> playersInformation = new ArrayList<>();
		for (Player player : this.turnOrder) {
			Map<CardType, List<Integer>> developmentCardsInformation = new EnumMap<>(CardType.class);
			List<Integer> developmentCardsBuildingInformation = new ArrayList<>();
			for (DevelopmentCardBuilding developmentCardBuilding : player.getPlayerCardHandler().getDevelopmentCards(CardType.BUILDING, DevelopmentCardBuilding.class)) {
				developmentCardsBuildingInformation.add(developmentCardBuilding.getIndex());
			}
			developmentCardsInformation.put(CardType.BUILDING, developmentCardsBuildingInformation);
			List<Integer> developmentCardsCharacterInformation = new ArrayList<>();
			for (DevelopmentCardCharacter developmentCardCharacter : player.getPlayerCardHandler().getDevelopmentCards(CardType.CHARACTER, DevelopmentCardCharacter.class)) {
				developmentCardsCharacterInformation.add(developmentCardCharacter.getIndex());
			}
			developmentCardsInformation.put(CardType.CHARACTER, developmentCardsCharacterInformation);
			List<Integer> developmentCardsTerritoryInformation = new ArrayList<>();
			for (DevelopmentCardTerritory developmentCardTerritory : player.getPlayerCardHandler().getDevelopmentCards(CardType.TERRITORY, DevelopmentCardTerritory.class)) {
				developmentCardsTerritoryInformation.add(developmentCardTerritory.getIndex());
			}
			developmentCardsInformation.put(CardType.TERRITORY, developmentCardsTerritoryInformation);
			List<Integer> developmentCardsVentureInformation = new ArrayList<>();
			for (DevelopmentCardVenture developmentCardVenture : player.getPlayerCardHandler().getDevelopmentCards(CardType.VENTURE, DevelopmentCardVenture.class)) {
				developmentCardsVentureInformation.add(developmentCardVenture.getIndex());
			}
			developmentCardsInformation.put(CardType.VENTURE, developmentCardsVentureInformation);
			Map<Integer, Boolean> leaderCardsPlayed = new HashMap<>();
			int leaderCardsInHandNumber = 0;
			for (LeaderCard leaderCard : player.getPlayerCardHandler().getLeaderCards()) {
				if (leaderCard.isPlayed()) {
					leaderCardsPlayed.put(leaderCard.getIndex(), leaderCard.getLeaderCardType() != LeaderCardType.MODIFIER && !((LeaderCardReward) leaderCard).isActivated());
				} else {
					leaderCardsInHandNumber++;
				}
			}
			playersInformation.add(new PlayerInformation(player.getIndex(), player.getPersonalBonusTile().getIndex(), developmentCardsInformation, leaderCardsPlayed, leaderCardsInHandNumber, player.getPlayerResourceHandler().getResources(), player.getFamilyMembersPositions()));
		}
		return playersInformation;
	}

	/**
	 * <p>Generates the target {@link Player}s {@link LeaderCard}s hand.
	 *
	 * @param player the target {@link Player}.
	 *
	 * @return an {@link Integer} {@link Boolean} {@link Map} representing the
	 * tuples [{@link LeaderCard} index - playable].
	 */
	public Map<Integer, Boolean> generateLeaderCardsHand(Player player)
	{
		Map<Integer, Boolean> leaderCardsHand = new HashMap<>();
		for (LeaderCard leaderCard : player.getPlayerCardHandler().getLeaderCards()) {
			if (leaderCard.isPlayed()) {
				continue;
			}
			try {
				new ActionLeaderPlay(leaderCard.getIndex(), player).isLegal();
				leaderCardsHand.put(leaderCard.getIndex(), true);
			} catch (GameActionFailedException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				leaderCardsHand.put(leaderCard.getIndex(), false);
			}
		}
		return leaderCardsHand;
	}

	/**
	 * <p>Generates the target {@link Player}s available actions.
	 *
	 * @param player the target {@link Player}.
	 *
	 * @return an [{@link ActionType} - available action {@link List}] {@link
	 * Map}.
	 */
	public Map<ActionType, List<Serializable>> generateAvailableActions(Player player)
	{
		Map<ActionType, List<Serializable>> availableActions = new EnumMap<>(ActionType.class);
		availableActions.put(ActionType.COUNCIL_PALACE, new ArrayList<>());
		availableActions.put(ActionType.HARVEST, new ArrayList<>());
		availableActions.put(ActionType.MARKET, new ArrayList<>());
		availableActions.put(ActionType.PICK_DEVELOPMENT_CARD, new ArrayList<>());
		availableActions.put(ActionType.PRODUCTION_START, new ArrayList<>());
		availableActions.put(ActionType.LEADER_ACTIVATE, new ArrayList<>());
		availableActions.put(ActionType.LEADER_DISCARD, new ArrayList<>());
		availableActions.put(ActionType.LEADER_PLAY, new ArrayList<>());
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			try {
				new ActionCouncilPalace(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), player).isLegal();
				availableActions.get(ActionType.COUNCIL_PALACE).add(new AvailableActionFamilyMember(familyMemberType));
			} catch (GameActionFailedException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			try {
				new ActionHarvest(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), player).isLegal();
				availableActions.get(ActionType.HARVEST).add(new AvailableActionFamilyMember(familyMemberType));
			} catch (GameActionFailedException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		for (MarketSlot marketSlot : MarketSlot.values()) {
			for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
				try {
					new ActionMarket(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), marketSlot, player).isLegal();
					availableActions.get(ActionType.MARKET).add(new AvailableActionMarket(familyMemberType, marketSlot));
				} catch (GameActionFailedException exception) {
					Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				}
			}
		}
		for (CardType cardType : this.cardsHandler.getCurrentDevelopmentCards().keySet()) {
			List<List<ResourceAmount>> discountChoices = new ArrayList<>();
			for (Modifier modifier : player.getActiveModifiers()) {
				if (modifier instanceof ModifierPickDevelopmentCard && ((ModifierPickDevelopmentCard) modifier).getCardType() == cardType && !((ModifierPickDevelopmentCard) modifier).getDiscountChoices().isEmpty()) {
					discountChoices.addAll(((ModifierPickDevelopmentCard) modifier).getDiscountChoices());
				}
			}
			for (Row row : this.cardsHandler.getCurrentDevelopmentCards().get(cardType).keySet()) {
				if (this.cardsHandler.getCurrentDevelopmentCards().get(cardType).get(row) == null) {
					continue;
				}
				List<ResourceCostOption> availableResourceCostOptions = new ArrayList<>();
				List<List<ResourceAmount>> availableDiscountChoises = new ArrayList<>();
				for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
					if (player.getFamilyMembersPositions().get(familyMemberType) != BoardPosition.NONE) {
						continue;
					}
					boolean validFamilyMember = false;
					if (this.cardsHandler.getCurrentDevelopmentCards().get(cardType).get(row).getResourceCostOptions().isEmpty()) {
						try {
							new ActionPickDevelopmentCard(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, new ArrayList<>(), null, player).isLegal();
							validFamilyMember = true;
						} catch (GameActionFailedException exception) {
							Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
						}
					} else {
						for (ResourceCostOption resourceCostOption : this.cardsHandler.getCurrentDevelopmentCards().get(cardType).get(row).getResourceCostOptions()) {
							if (discountChoices.isEmpty()) {
								try {
									new ActionPickDevelopmentCard(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, new ArrayList<>(), new ResourceCostOption(resourceCostOption), player).isLegal();
									validFamilyMember = true;
									if (!availableResourceCostOptions.contains(resourceCostOption)) {
										availableResourceCostOptions.add(resourceCostOption);
									}
								} catch (GameActionFailedException exception) {
									Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
								}
							} else {
								for (List<ResourceAmount> discountChoice : discountChoices) {
									try {
										new ActionPickDevelopmentCard(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, discountChoice, new ResourceCostOption(resourceCostOption), player).isLegal();
										validFamilyMember = true;
										if (!availableResourceCostOptions.contains(resourceCostOption)) {
											availableResourceCostOptions.add(resourceCostOption);
										}
										if (!availableDiscountChoises.contains(discountChoice)) {
											availableDiscountChoises.add(discountChoice);
										}
									} catch (GameActionFailedException exception) {
										Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
									}
								}
							}
						}
					}
					if (validFamilyMember) {
						availableActions.get(ActionType.PICK_DEVELOPMENT_CARD).add(new AvailableActionPickDevelopmentCard(familyMemberType, cardType, row, availableResourceCostOptions, availableDiscountChoises));
					}
				}
			}
		}
		for (FamilyMemberType familyMemberType : FamilyMemberType.values()) {
			try {
				new ActionProductionStart(familyMemberType, player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), player).isLegal();
				availableActions.get(ActionType.PRODUCTION_START).add(new AvailableActionFamilyMember(familyMemberType));
			} catch (GameActionFailedException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			}
		}
		return availableActions;
	}

	/**
	 * <p>Gets the next {@link Player} in the turn order, cycling over it if it
	 * is ended.
	 *
	 * @return the next {@link Player}.
	 */
	private Player getNextTurnPlayer()
	{
		int index = this.turnOrder.indexOf(this.turnPlayer);
		return index + 1 >= this.turnOrder.size() ? this.turnOrder.get(0) : this.turnOrder.get(index + 1);
	}

	public Room getRoom()
	{
		return this.room;
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

	boolean isCheckedExcommunications()
	{
		return this.checkedExcommunications;
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

	int getPersonalBonusTileChoicePlayerTurnIndex()
	{
		return this.personalBonusTileChoicePlayerTurnIndex;
	}

	void setPersonalBonusTileChoicePlayerTurnIndex(int personalBonusTileChoicePlayerTurnIndex)
	{
		this.personalBonusTileChoicePlayerTurnIndex = personalBonusTileChoicePlayerTurnIndex;
	}

	Map<Player, List<Integer>> getAvailableLeaderCards()
	{
		return this.availableLeaderCards;
	}

	List<Player> getLeaderCardsChoosingPlayers()
	{
		return this.leaderCardsChoosingPlayers;
	}

	List<Player> getExcommunicationChoosingPlayers()
	{
		return this.excommunicationChoosingPlayers;
	}

	ScheduledExecutorService getTimerExecutor()
	{
		return this.timerExecutor;
	}

	public void setTimerExecutor(ScheduledExecutorService timerExecutor)
	{
		this.timerExecutor = timerExecutor;
	}
}
