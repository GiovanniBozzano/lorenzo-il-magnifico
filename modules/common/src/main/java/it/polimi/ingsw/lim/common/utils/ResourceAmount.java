package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.Resource;

public class ResourceAmount
{
	private Resource resource;
	private int amount;

	public ResourceAmount(Resource resource, int amount)
	{
		this.resource = resource;
		this.amount = amount;
	}

	public Resource getResource()
	{
		return resource;
	}

	public int getAmount()
	{
		return amount;
	}
}
