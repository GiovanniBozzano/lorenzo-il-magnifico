package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.*;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
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
		List<PersonalBonusTile> personalBonusTiles = Arrays.asList(PersonalBonusTile.values());
		for (Connection player : this.room.getPlayers()) {
			PersonalBonusTile personalBonusTile = personalBonusTiles.get(this.randomGenerator.nextInt(personalBonusTiles.size()));
			personalBonusTiles.remove(personalBonusTile);
			player.setPlayerHandler(new PlayerHandler(personalBonusTile));
		}
		this.turnOrder.addAll(this.room.getPlayers());
		Collections.shuffle(this.turnOrder, this.randomGenerator);
		int startingCoins = 5;
		for (Connection player : this.turnOrder) {
			player.getPlayerHandler().getPlayerResourceHandler().addResource(ResourceType.COIN, startingCoins);
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
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCardsBuilding().size(); index++) {
			this.cardsHandler.addDevelopmentCard(this.developmentCardsBuilding.getPeriods().get(this.period).get(index), Row.values()[index]);
			this.developmentCardsBuilding.getPeriods().get(this.period).remove(index);
		}
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCardsCharacter().size(); index++) {
			this.cardsHandler.addDevelopmentCard(this.developmentCardsCharacters.getPeriods().get(this.period).get(index), Row.values()[index]);
			this.developmentCardsCharacters.getPeriods().get(this.period).remove(index);
		}
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCardsTerritory().size(); index++) {
			this.cardsHandler.addDevelopmentCard(this.developmentCardsTerritory.getPeriods().get(this.period).get(index), Row.values()[index]);
			this.developmentCardsTerritory.getPeriods().get(this.period).remove(index);
		}
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCardsVenture().size(); index++) {
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
		for (Modifier<? extends Event> temporaryModifier : this.turnPlayer.getPlayerHandler().getTemporaryModifiers()) {
			this.turnPlayer.getPlayerHandler().getActiveModifiers().remove(temporaryModifier);
		}
		this.turnPlayer.getPlayerHandler().getTemporaryModifiers().clear();
		for (ResourceType resourceType : this.turnPlayer.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().keySet()) {
			this.turnPlayer.getPlayerHandler().getPlayerResourceHandler().addResource(resourceType, this.turnPlayer.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().get(resourceType));
		}
		this.turnPlayer.getPlayerHandler().getPlayerResourceHandler().getTemporaryResources().clear();
		this.turnPlayer = this.getNextTurnPlayer();
		if (this.turnPlayer == null) {
			this.setupRound();
		} else if (!this.turnPlayer.getPlayerHandler().isOnline()) {
			this.nextTurn();
			return;
		}
		// TODO aggiorno tutti
		// TODO turno prossimo giocatore
	}

	private Connection getNextTurnPlayer()
	{
		int index = this.turnOrder.indexOf(this.turnPlayer);
		return index + 1 >= this.turnOrder.size() ? null : this.turnOrder.get(index + 1);
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
