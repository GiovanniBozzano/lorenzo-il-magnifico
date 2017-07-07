package it.polimi.ingsw.lim.server.utils;

import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class UtilsTest
{
	@Test
	public void testSha512Encrypt() throws NoSuchAlgorithmException
	{
		byte[] salt = Utils.getSalt();
		Assert.assertEquals(Utils.sha512Encrypt("test", salt), Utils.sha512Encrypt("test", salt));
		Assert.assertNotEquals(Utils.sha512Encrypt("test", salt), Utils.sha512Encrypt("test", Utils.getSalt()));
	}
}
