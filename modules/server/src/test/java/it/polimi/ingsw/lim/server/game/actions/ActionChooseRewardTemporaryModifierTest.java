package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.actionrewards.ActionRewardTemporaryModifier;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import it.polimi.ingsw.lim.server.view.cli.InterfaceHandlerCLI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class ActionChooseRewardTemporaryModifierTest
{
	private ActionChooseRewardTemporaryModifier actionChooseRewardTemporaryModifier;

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
		GameHandler gameHandler = new GameHandler(room);
		room.setGameHandler(gameHandler);
		gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_TEMPORARY_MODIFIER);
		gameHandler.setTimerExecutor(Executors.newSingleThreadScheduledExecutor());
		Player player = new Player(new ConnectionRMI(null, null), room, 0);
		player.setPersonalBonusTile(PersonalBonusTile.PERSONAL_BONUS_TILES_0);
		player.setCurrentActionReward(new ActionRewardTemporaryModifier(null));
		gameHandler.getTurnOrder().add(player);
		gameHandler.setupRound();
		this.actionChooseRewardTemporaryModifier = new ActionChooseRewardTemporaryModifier(FamilyMemberType.BLACK, player);
	}

	@Test
	public void testIsLegal() throws GameActionFailedException
	{
		this.actionChooseRewardTemporaryModifier.isLegal();
	}

	@Test
	public void testApply() throws GameActionFailedException
	{
		this.actionChooseRewardTemporaryModifier.apply();
	}

	@After
	public void tearDown()
	{
		Server.getInstance().stop();
	}
}
