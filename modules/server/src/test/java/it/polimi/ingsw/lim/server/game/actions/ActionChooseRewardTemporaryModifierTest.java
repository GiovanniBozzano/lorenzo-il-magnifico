package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActionChooseRewardTemporaryModifierTest
{
	private ActionChooseRewardTemporaryModifier actionChooseRewardTemporaryModifier;

	@Before
	public void setUp()
	{
		Server.setInstance(new Server());
		this.actionChooseRewardTemporaryModifier = new ActionChooseRewardTemporaryModifier(FamilyMemberType.NEUTRAL, new Player(new ConnectionRMI(null, null), 0));
	}

	@Test(expected = GameActionFailedException.class)
	public void testIsLegal() throws GameActionFailedException
	{
		this.actionChooseRewardTemporaryModifier.isLegal();
	}

	@Test(expected = GameActionFailedException.class)
	public void testApply() throws GameActionFailedException
	{
		this.actionChooseRewardTemporaryModifier.apply();
	}

	@After
	public void tearDown()
	{
	}
}
