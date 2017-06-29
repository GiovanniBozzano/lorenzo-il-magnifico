package it.polimi.common.utils;

import it.polimi.ingsw.lim.common.utils.CommonUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CommonUtilsTest
{
	@Test
	public void testSortMapByValue()
	{
		Random random = new Random(System.currentTimeMillis());
		Map<Integer, Integer> testMap = new HashMap<>(1000);
		for (int index = 0; index < 1000; index++) {
			testMap.put(random.nextInt(), random.nextInt());
		}
		testMap = CommonUtils.sortMapByValue(testMap);
		Assert.assertEquals(1000, testMap.size());
		Integer previous = null;
		for (Map.Entry<Integer, Integer> entry : testMap.entrySet()) {
			Assert.assertNotNull(entry.getValue());
			if (previous != null) {
				Assert.assertTrue(entry.getValue() >= previous);
			}
			previous = entry.getValue();
		}
	}
}
