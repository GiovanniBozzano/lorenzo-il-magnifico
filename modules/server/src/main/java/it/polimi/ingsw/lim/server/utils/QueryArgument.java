package it.polimi.ingsw.lim.server.utils;

import it.polimi.ingsw.lim.server.enums.QueryValueType;

class QueryArgument
{
	private final QueryValueType valueType;
	private final String value;

	public QueryArgument(QueryValueType valueType, String value)
	{
		this.valueType = valueType;
		this.value = value;
	}

	QueryValueType getValueType()
	{
		return this.valueType;
	}

	String getValue()
	{
		return this.value;
	}
}

