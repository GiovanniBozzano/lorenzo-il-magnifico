package it.polimi.ingsw.lim.common.utils;

import java.util.HashMap;
import java.util.Map;

public class BidirectionalMap<K1, K2>
{
	private final Map<K1, K2> directMap = new HashMap<>();
	private final Map<K2, K1> inverseMap = new HashMap<>();

	public void put(K1 key1, K2 key2)
	{
		this.directMap.put(key1, key2);
		this.inverseMap.put(key2, key1);
	}

	public K2 getDirect(K1 key)
	{
		return this.directMap.get(key);
	}

	public K1 getInverse(K2 key)
	{
		return this.inverseMap.get(key);
	}

	public void clear()
	{
		this.directMap.clear();
		this.inverseMap.clear();
	}
}
