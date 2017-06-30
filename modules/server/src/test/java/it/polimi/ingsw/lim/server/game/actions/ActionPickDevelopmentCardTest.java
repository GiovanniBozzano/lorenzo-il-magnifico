package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCard;
import it.polimi.ingsw.lim.server.game.cards.DevelopmentCardBuilding;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Reward;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class ActionPickDevelopmentCardTest
{
	private ActionPickDevelopmentCard actionPickDevelopmentCard;

	@Before
	public void setUp()
	{
		Server.setDebugger(Logger.getLogger(Server.class.getSimpleName().toUpperCase()));
		Server.getDebugger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new DebuggerFormatter());
		Server.getDebugger().addHandler(consoleHandler);
		Server.setInstance(new Server());
		Room room = new Room(RoomType.NORMAL);
		GameHandler gameHandler = new GameHandler(room);
		room.setGameHandler(gameHandler);
		gameHandler.setTimerExecutor(Executors.newSingleThreadScheduledExecutor());
		Player player = new Player(new ConnectionRMI(null, null), room, 0);
		player.getPlayerResourceHandler().addResource(ResourceType.SERVANT, 10);
		gameHandler.getTurnOrder().add(player);
		gameHandler.setupRound();
		Map<Row, DevelopmentCard> developmentCardsBuilding = new EnumMap<>(Row.class);
		developmentCardsBuilding.put(Row.FIRST, new DevelopmentCardBuilding(0, null, null, new ArrayList<>(), new Reward(null, new ArrayList<>()), 0, new ArrayList<>()));
		gameHandler.getCardsHandler().getCurrentDevelopmentCards().put(CardType.BUILDING, developmentCardsBuilding);
		this.actionPickDevelopmentCard = new ActionPickDevelopmentCard(FamilyMemberType.NEUTRAL, 10, CardType.BUILDING, Row.FIRST, new ArrayList<>(), null, player);
	}

	@Test
	public void testIsLegal() throws GameActionFailedException
	{
		this.actionPickDevelopmentCard.isLegal();
	}

	@After
	public void tearDown()
	{
		Server.getInstance().stop();
	}
}
