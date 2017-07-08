package it.polimi.ingsw.lim.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class CommonUtilsTest
{
	@Test
	public void testIsInteger()
	{
		Assert.assertTrue(CommonUtils.isInteger("10"));
		Assert.assertTrue(CommonUtils.isInteger("-10"));
		Assert.assertTrue(CommonUtils.isInteger("0"));
		Assert.assertFalse(CommonUtils.isInteger("test"));
		Assert.assertFalse(CommonUtils.isInteger("1."));
		Assert.assertFalse(CommonUtils.isInteger(".1"));
		Assert.assertFalse(CommonUtils.isInteger("1.0"));
		Assert.assertFalse(CommonUtils.isInteger(""));
	}

	@Test
	public void testEncrypt()
	{
		Assert.assertEquals(CommonUtils.encrypt("test"), CommonUtils.encrypt("test"));
		Assert.assertEquals("test", CommonUtils.decrypt(CommonUtils.encrypt("test")));
	}
}
