package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardLorenzoDeMediciLeader;
import it.polimi.ingsw.lim.server.game.cards.leaders.LeaderCardReward;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.game.utils.Reward;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class ActionChooseLorenzoDeMediciLeaderTest
{
	private ActionChooseLorenzoDeMediciLeader actionChooseLorenzoDeMediciLeader;

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
		gameHandler.setExpectedAction(ActionType.CHOOSE_LORENZO_DE_MEDICI_LEADER);
		gameHandler.setTimerExecutor(Executors.newSingleThreadScheduledExecutor());
		Player player = new Player(new ConnectionRMI(null, null), room, 0);
		player.setCurrentActionReward(new ActionRewardLorenzoDeMediciLeader(null));
		Player otherPlayer = new Player(new ConnectionRMI(null, null), room, 1);
		LeaderCardReward leaderCard = new LeaderCardReward(0, null, null, null, new ArrayList<>(), new Reward(null, new ArrayList<>()));
		leaderCard.setPlayed(true);
		otherPlayer.getPlayerCardHandler().getLeaderCards().add(leaderCard);
		gameHandler.getTurnOrder().add(player);
		gameHandler.getTurnOrder().add(otherPlayer);
		gameHandler.setupRound();
		this.actionChooseLorenzoDeMediciLeader = new ActionChooseLorenzoDeMediciLeader(0, player);
	}

	@Test
	public void testIsLegal() throws GameActionFailedException
	{
		this.actionChooseLorenzoDeMediciLeader.isLegal();
	}

	@Test
	public void testApply() throws GameActionFailedException
	{
		this.actionChooseLorenzoDeMediciLeader.apply();
	}

	@After
	public void tearDown()
	{
		Server.getInstance().stop();
	}
}
