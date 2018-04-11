package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.cards.CardsHandler;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import it.polimi.ingsw.lim.server.view.cli.InterfaceHandlerCLI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class GameHandlerTest
{
	private GameHandler gameHandler;
	private Player player;

	@Before
	public void setUp()
	{
		Server.setDebugger(Logger.getLogger(Server.class.getSimpleName().toUpperCase()));
		Server.getDebugger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new DebuggerFormatter());
		Server.getDebugger().addHandler(consoleHandler);
		Server.setInstance(new Server());
		Server.getInstance().setInterfaceHandler(new InterfaceHandlerCLI());
		Room room = new Room(RoomType.NORMAL);
		this.gameHandler = new GameHandler(room);
		room.setGameHandler(this.gameHandler);
		this.gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_COUNCIL_PRIVILEGE);
		this.gameHandler.setTimerExecutor(Executors.newSingleThreadScheduledExecutor());
		this.player = new Player(new ConnectionRMI(null, null), room, 0);
		this.player.setPersonalBonusTile(BoardHandler.getPersonalBonusTiles().get(0));
		this.gameHandler.getTurnOrder().add(this.player);
		this.gameHandler.setupRound();
	}

	@Test
	public void testCalculateExcommunications()
	{
		this.gameHandler.calculateExcommunications();
		//Assert.assertTrue(this.gameHandler.isCheckedExcommunications());
	}

	@Test
	public void testReceivePersonalBonusTileChoice() throws GameActionFailedException
	{
		this.gameHandler.setPersonalBonusTileChoicePlayerTurnIndex(0);
		this.gameHandler.receivePersonalBonusTileChoice(this.player, 0);
		Assert.assertEquals(-1, this.gameHandler.getPersonalBonusTileChoicePlayerTurnIndex());
	}

	@Test
	public void testReceiveLeaderCardChoice() throws GameActionFailedException
	{
		this.gameHandler.getLeaderCardsChoosingPlayers().add(this.player);
		List<Integer> playerAvailableLeaderCards = new ArrayList<>();
		playerAvailableLeaderCards.add(CardsHandler.getLeaderCards().get(0).getIndex());
		this.gameHandler.getAvailableLeaderCards().put(this.player, playerAvailableLeaderCards);
		this.gameHandler.receiveLeaderCardChoice(this.player, this.gameHandler.getAvailableLeaderCards().get(this.player).get(0));
		Assert.assertTrue(this.gameHandler.getLeaderCardsChoosingPlayers().isEmpty());
	}

	@Test
	public void testReceiveExcommunicationChoice() throws GameActionFailedException
	{
		this.gameHandler.getExcommunicationChoosingPlayers().add(this.player);
		this.gameHandler.receiveExcommunicationChoice(this.player, false);
	}

	@Test
	public void testGenerateAvailableActions()
	{
		this.player.getActiveModifiers().add(new ModifierPickDevelopmentCard(null, 10, CardType.BUILDING, Collections.singletonList(Collections.singletonList(new ResourceAmount(ResourceType.COIN, 10)))));
		this.gameHandler.generateAvailableActions(this.player);
	}

	@After
	public void tearDown()
	{
		Server.getInstance().stop();
	}
}
