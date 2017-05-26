package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.utils.Calculation;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

public class MathTest extends TestCase
{
	private int value1;
	private int value2;

	public MathTest(String testName)
	{
		super(testName);
	}

	@Before
	protected void setUp() throws Exception
	{
		super.setUp();
		this.value1 = 3;
		this.value2 = 5;
	}

	@After
	protected void tearDown() throws Exception
	{
		super.tearDown();
		this.value1 = 0;
		this.value2 = 0;
	}

	public void testAdd()
	{
		int total = 8;
		int sum = Calculation.add(this.value1, this.value2);
		assertEquals(sum, total);
	}

	public void testFailedAdd()
	{
		int total = 9;
		int sum = Calculation.add(this.value1, this.value2);
		assertNotSame(sum, total);
	}

	public void testSub()
	{
		int total = 0;
		int sub = Calculation.sub(4, 4);
		assertEquals(sub, total);
	}
}
