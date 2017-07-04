package it.polimi.ingsw.lim.server.game.utils;

import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.ArrayList;
import java.util.List;

public class BoardPositionInformation
{
	private final int value;
	private final List<ResourceAmount> resourceAmounts;

	public BoardPositionInformation(int value, List<ResourceAmount> resourceAmounts)
	{
		this.value = value;
		this.resourceAmounts = new ArrayList<>(resourceAmounts);
	}

	public int getValue()
	{
		return this.value;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
