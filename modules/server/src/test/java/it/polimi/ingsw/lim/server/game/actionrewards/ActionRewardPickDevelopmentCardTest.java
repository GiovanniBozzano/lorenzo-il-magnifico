package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import it.polimi.ingsw.lim.server.view.cli.InterfaceHandlerCLI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class ActionRewardPickDevelopmentCardTest
{
	private ActionRewardPickDevelopmentCard actionReward1;
	private ActionRewardPickDevelopmentCard actionReward2;
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
		GameHandler gameHandler = new GameHandler(room);
		room.setGameHandler(gameHandler);
		gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD);
		gameHandler.setTimerExecutor(Executors.newSingleThreadScheduledExecutor());
		this.player = new Player(new ConnectionRMI(null, null), room, 0);
		this.player.setPersonalBonusTile(BoardHandler.getPersonalBonusTiles().get(0));
		this.player.getPlayerResourceHandler().addResource(ResourceType.COIN, 10);
		this.player.getPlayerResourceHandler().addResource(ResourceType.STONE, 10);
		this.player.getPlayerResourceHandler().addResource(ResourceType.WOOD, 10);
		this.player.getPlayerResourceHandler().addResource(ResourceType.SERVANT, 10);
		gameHandler.getTurnOrder().add(this.player);
		gameHandler.setupRound();
		this.actionReward1 = new ActionRewardPickDevelopmentCard(null, 10, new ArrayList<>(Arrays.asList(CardType.values())), new ArrayList<>());
		this.actionReward2 = new ActionRewardPickDevelopmentCard(null, 10, new ArrayList<>(Arrays.asList(CardType.values())), Collections.singletonList(Collections.singletonList(new ResourceAmount(ResourceType.COIN, 10))));
	}

	@Test
	public void testCreateExpectedAction()
	{
		this.player.setCurrentActionReward(this.actionReward1);
		Assert.assertFalse(((ExpectedActionChooseRewardPickDevelopmentCard) this.actionReward1.createExpectedAction(this.player)).getAvailableActions().isEmpty());
		this.player.setCurrentActionReward(this.actionReward2);
		Assert.assertFalse(((ExpectedActionChooseRewardPickDevelopmentCard) this.actionReward2.createExpectedAction(this.player)).getAvailableActions().isEmpty());
		this.player.getActiveModifiers().add(new ModifierPickDevelopmentCard(null, 10, CardType.BUILDING, Collections.singletonList(Collections.singletonList(new ResourceAmount(ResourceType.COIN, 10)))));
		this.player.setCurrentActionReward(this.actionReward1);
		Assert.assertFalse(((ExpectedActionChooseRewardPickDevelopmentCard) this.actionReward1.createExpectedAction(this.player)).getAvailableActions().isEmpty());
		this.player.setCurrentActionReward(this.actionReward2);
		Assert.assertFalse(((ExpectedActionChooseRewardPickDevelopmentCard) this.actionReward2.createExpectedAction(this.player)).getAvailableActions().isEmpty());
	}

	@After
	public void tearDown()
	{
		Server.getInstance().stop();
	}
}
