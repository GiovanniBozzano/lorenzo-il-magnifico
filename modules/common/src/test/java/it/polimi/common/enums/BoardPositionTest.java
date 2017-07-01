package it.polimi.common.enums;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import org.junit.Assert;
import org.junit.Test;

public class BoardPositionTest
{
	@Test
	public void testGetDevelopmentCardPosition()
	{
		Assert.assertEquals(BoardPosition.BUILDING_1, BoardPosition.getDevelopmentCardPosition(CardType.BUILDING, Row.FIRST));
	}

	@Test
	public void testGetDevelopmentCardsColumnPositions()
	{
		Assert.assertFalse(BoardPosition.getDevelopmentCardsColumnPositions(CardType.BUILDING).isEmpty());
	}

	@Test
	public void testGetMarketPositions()
	{
		Assert.assertFalse(BoardPosition.getMarketPositions().isEmpty());
	}
}
