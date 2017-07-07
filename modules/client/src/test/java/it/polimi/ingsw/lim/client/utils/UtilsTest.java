package it.polimi.ingsw.lim.client.utils;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest
{
	@Test
	public void testGetBoardImages()
	{
		Assert.assertFalse(Utils.getBoardImages().isEmpty());
	}

	@Test
	public void testGetDicesFamilyMemberTypesTextures()
	{
		Assert.assertFalse(Utils.getDicesFamilyMemberTypesTextures().isEmpty());
	}

	@Test
	public void testGetFamilyMemberTypesTextures()
	{
		Assert.assertFalse(Utils.getFamilyMemberTypesTextures().isEmpty());
	}

	@Test
	public void testGetPlayersFamilyMemberTypesTextures()
	{
		Assert.assertFalse(Utils.getPlayersFamilyMemberTypesTextures().isEmpty());
	}

	@Test
	public void testGetPlayersPlaceholdersTextures()
	{
		Assert.assertFalse(Utils.getPlayersPlaceholdersTextures().isEmpty());
	}

	@Test
	public void testGetExcommunicationPlayersPlaceholdersTextures()
	{
		Assert.assertFalse(Utils.getExcommunicationPlayersPlaceholdersTextures().isEmpty());
	}
}
