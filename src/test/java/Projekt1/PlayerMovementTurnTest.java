package Projekt1;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.*;

@RunWith(JUnitParamsRunner.class)
public class PlayerMovementTurnTest {
	
	private static Map map;
	
	@BeforeClass()
	public static void setUp()
	{
		 map = new Map(15, 2);
	}
	@Test
	@FileParameters("src/test/resources/playerTurnTest.csv")
	public void playerTurnTest(char move, char expectedDirection) 
	{	
			try {
				map.updatePlayer(move);
			} catch (Exception e) {
				e.printStackTrace();
			}
			assertEquals(expectedDirection, map.getPlayer().getDirection());
	}	
}