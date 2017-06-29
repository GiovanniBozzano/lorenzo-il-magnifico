package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActionChooseRewardProductionStartTest
{
	private ActionChooseRewardProductionStart actionChooseRewardProductionStart;

	@Before
	public void setUp()
	{
		Server.setInstance(new Server());
		this.actionChooseRewardProductionStart = new ActionChooseRewardProductionStart(0, new Player(new ConnectionRMI(null, null), 0));
	}

	@Test(expected = GameActionFailedException.class)
	public void testIsLegal() throws GameActionFailedException
	{
		this.actionChooseRewardProductionStart.isLegal();
	}

	@Test(expected = GameActionFailedException.class)
	public void testApply() throws GameActionFailedException
	{
		this.actionChooseRewardProductionStart.apply();
	}

	@After
	public void tearDown()
	{
	}
}
