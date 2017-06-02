package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.*;
import it.polimi.ingsw.lim.server.game.events.Event;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.player.PlayerInformations;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.*;

public class GameHandler
{
	private final Room room;
	private final CardsHandler cardsHandler = new CardsHandler();
	private final BoardHandler boardHandler = new BoardHandler();
	private final Random randomGenerator = new Random(System.nanoTime());
	private DevelopmentCardsDeck<DevelopmentCardBuilding> developmentCardsBuilding = CardsHandler.DEVELOPMENT_CARDS_BUILDING.clone();
	private DevelopmentCardsDeck<DevelopmentCardCharacter> developmentCardsCharacters = CardsHandler.DEVELOPMENT_CARDS_CHARACTER.clone();
	private DevelopmentCardsDeck<DevelopmentCardTerritory> developmentCardsTerritory = CardsHandler.DEVELOPMENT_CARDS_TERRITORY.clone();
	private DevelopmentCardsDeck<DevelopmentCardVenture> developmentCardsVenture = CardsHandler.DEVELOPMENT_CARDS_VENTURE.clone();
	private final Map<FamilyMemberType, Integer> familyMemberTypeValues = new EnumMap<>(FamilyMemberType.class);
	private List<Connection> turnOrder = new LinkedList<>();
	private Connection turnPlayer;
	private Period period;
	private Round round;
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
			player.setPlayerInformations(new PlayerInformations(personalBonusTile));
		}
		this.turnOrder.addAll(this.room.getPlayers());
		Collections.shuffle(this.turnOrder, this.randomGenerator);
		int startingCoins = 5;
		for (Connection player : this.turnOrder) {
			player.getPlayerInformations().getPlayerResourceHandler().addResource(new ResourceAmount(ResourceType.COIN, startingCoins));
			startingCoins++;
		}
		this.setupRound();
	}

	private void setupPeriod()
	{
		if (this.period == null) {
			this.period = Period.FIRST;
		} else {
			this.period = Period.next(this.period);
		}
		if (this.period == null) {
			this.endGame();
		} else {
			this.round = Round.FIRST;
		}
	}

	private void setupRound()
	{
		if (this.round == null || this.round == Round.SECOND) {
			this.setupPeriod();
		}
		if (this.period == null) {
			return;
		}
		this.setupTurnOrder();
		this.turnPlayer = this.turnOrder.get(0);
		this.rollDices();
		this.drawCards();
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
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCardsBuilding().length; index++) {
			this.cardsHandler.getCurrentDevelopmentCardsBuilding()[index] = this.developmentCardsBuilding.getPeriods().get(this.period).get(index);
			this.developmentCardsBuilding.getPeriods().get(this.period).remove(index);
		}
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCardsCharacters().length; index++) {
			this.cardsHandler.getCurrentDevelopmentCardsCharacters()[index] = this.developmentCardsCharacters.getPeriods().get(this.period).get(index);
			this.developmentCardsCharacters.getPeriods().get(this.period).remove(index);
		}
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCardsTerritory().length; index++) {
			this.cardsHandler.getCurrentDevelopmentCardsTerritory()[index] = this.developmentCardsTerritory.getPeriods().get(this.period).get(index);
			this.developmentCardsTerritory.getPeriods().get(this.period).remove(index);
		}
		for (int index = 0; index < this.cardsHandler.getCurrentDevelopmentCardsVenture().length; index++) {
			this.cardsHandler.getCurrentDevelopmentCardsVenture()[index] = this.developmentCardsVenture.getPeriods().get(this.period).get(index);
			this.developmentCardsVenture.getPeriods().get(this.period).remove(index);
		}
	}

	private void setupTurnOrder()
	{
		if (this.period == Period.FIRST && this.round == Round.FIRST) {
			return;
		}
	}

	public void nextTurn()
	{
		for (Modifier<? extends Event> temporaryModifier : this.turnPlayer.getPlayerInformations().getTemporaryModifiers()) {
			this.turnPlayer.getPlayerInformations().getActiveModifiers().remove(temporaryModifier);
		}
		this.turnPlayer.getPlayerInformations().getTemporaryModifiers().clear();
		this.turnPlayer = this.getNextTurnPlayer();
		if (this.turnPlayer == null) {
			this.setupRound();
		}
	}

	private Connection getNextTurnPlayer()
	{
		int index = this.turnOrder.indexOf(this.turnPlayer);
		return index + 1 >= this.turnOrder.size() ? null : this.turnOrder.get(index + 1);
	}

	public Map<FamilyMemberType, Integer> getFamilyMemberTypeValues()
	{
		return this.familyMemberTypeValues;
	}

	public Connection getTurnPlayer()
	{
		return this.turnPlayer;
	}

	public ActionType getExpectedAction()
	{
		return this.expectedAction;
	}
}
