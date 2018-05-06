package Projekt1;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MapTest {

	private static Map map;
	private static List<Ship> enemies;
	
	@Before()
	public void setUp() throws Exception
	{
		map = new Map(MapManager.loadMapFromFile("testMapPlayerWithCollision.txt"));
		enemies = map.getEnemies();
	}
	
	@Test
	public void updateEnemiesTest() 
	{	
		map.printMap();
    	map.printPlayerCords();
    	map.printEnemiesCords();
		Ship enemy = null;
		int x;
		int y;
		char direction;
		boolean moved = false;
			try {
				enemy = map.getEnemies().get(0); 
				x = enemy.getX();
				y = enemy.getY();
				direction = enemy.getDirection();
				map.updateEnemies();
				if(x != enemy.getX() || y != enemy.getY() || direction != enemy.getDirection())
					moved = true;
					
			} catch (Exception e) {
				e.printStackTrace();
				moved = true;
			}			
			assertTrue(moved);
	}	
	
	@Test
	public void getEnemiesAmountTest()
	{
		assertThat(enemies, hasSize(2));
	}
	
	@Test
	public void getEnemiesNamesTest()
	{
		assertThat(enemies.get(0).getName()).startsWith("Przeciwnik").isEqualToIgnoringCase("Przeciwnik1");
	}
	
	@Test
	public void getEnemiesTuplesTest()
	{
		assertThat(enemies).extracting("name", "x", "y", "direction")
        .contains(tuple("Przeciwnik1", 3, 3, 'N'),
                  tuple("Przeciwnik2", 8, 8, 'N'));
	}
	
	@Test
	public void getPlayertTest()
	{
		Ship player = new Ship(0, 0, 'N', "Gracz");
		assertTrue(map.getPlayer().getY() == player.getY() || map.getPlayer().getX() == player.getX() || map.getPlayer().getDirection() == player.getDirection());
	}
	
	@Test
	public void getIslandsTest()
	{
		int[] cords = new int[]{0,1};
		Assert.assertArrayEquals(cords, map.getIslands().get(0));
	}
	
	@Test
	public void getCoinsTest()
	{
		int[] cords = new int[]{4,4};
		Assert.assertArrayEquals(cords, map.getCoins().get(0));
	}
	
	@Test
	public void getMapSizeTest()
	{
		assertEquals(16, map.getMapSize());
	}
}
