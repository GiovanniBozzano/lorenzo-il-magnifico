package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ActionPickDevelopmentCardTest
{
	private ActionPickDevelopmentCard actionPickDevelopmentCard;

	@Before
	public void setUp()
	{
		Server.setInstance(new Server());
		Room room = new Room(RoomType.NORMAL);
		GameHandler gameHandler = new GameHandler(room);
		Player player = new Player(new ConnectionRMI(null, null), room, 0);
		gameHandler.setTurnPlayer(player);
		gameHandler.setTimerExecutor(Executors.newSingleThreadScheduledExecutor());
		room.setGameHandler(gameHandler);
		this.actionPickDevelopmentCard = new ActionPickDevelopmentCard(FamilyMemberType.NEUTRAL, 0, CardType.BUILDING, Row.FIRST, new ArrayList<>(), null, player);
	}

	@Test(expected = GameActionFailedException.class)
	public void testIsLegal() throws GameActionFailedException
	{
		this.actionPickDevelopmentCard.isLegal();
	}

	@Test(expected = GameActionFailedException.class)
	public void testApply() throws GameActionFailedException
	{
		this.actionPickDevelopmentCard.apply();
	}

	@After
	public void tearDown()
	{
	}
}
