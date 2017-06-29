package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ActionChooseRewardPickDevelopmentCardTest
{
	private ActionChooseRewardPickDevelopmentCard actionChooseRewardPickDevelopmentCard;

	@Before
	public void setUp()
	{
		Server.setInstance(new Server());
		this.actionChooseRewardPickDevelopmentCard = new ActionChooseRewardPickDevelopmentCard(0, CardType.BUILDING, Row.FIRST, Row.FOURTH, new ArrayList<>(), new ArrayList<>(), null, new Player(new ConnectionRMI(null, null), 0));
	}

	@Test(expected = GameActionFailedException.class)
	public void testIsLegal() throws GameActionFailedException
	{
		this.actionChooseRewardPickDevelopmentCard.isLegal();
	}

	@Test(expected = GameActionFailedException.class)
	public void testApply() throws GameActionFailedException
	{
		this.actionChooseRewardPickDevelopmentCard.apply();
	}

	@After
	public void tearDown()
	{
	}
}

