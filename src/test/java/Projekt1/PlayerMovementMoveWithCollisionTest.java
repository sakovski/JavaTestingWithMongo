package Projekt1;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.*;

@RunWith(JUnitParamsRunner.class)
public class PlayerMovementMoveWithCollisionTest {
	
	private static Map map;
	
	@BeforeClass()
	public static void setUp() throws Exception
	{
		map = new Map(MapManager.loadMapFromFile("testMapPlayerWithCollision.txt"));
	}
	
	@Test
	@FileParameters("src/test/resources/playerMovementWithCollisionsBorderMapTest.csv")
	public void playerMovementWithCollisionsBorderMapTest(char move, int x, int y) 
	{
		map.printMap();
    	map.printPlayerCords();
    	map.printEnemiesCords();
		move = Character.toLowerCase(move);
		try {
			map.updatePlayer(move);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		List<Integer> list = Arrays.asList(map.getPlayer().getX(), map.getPlayer().getY());
		assertThat(list, contains(x, y));
	}	
	
	@Test(expected=Exception.class)
	public void playerMovementWithCollisionsIslandsTest() throws Exception 
	{
		map.printMap();
    	map.printPlayerCords();
    	map.printEnemiesCords();
		map.updatePlayer('w');

	}
}