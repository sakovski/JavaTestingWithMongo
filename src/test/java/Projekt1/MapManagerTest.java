package Projekt1;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MapManagerTest 
{
	
	private Map mapToSave;
	private Map loadedMap;
	private static final String fileName = "testSavedMap.txt";
	private int mapSize;
	private int amountOfEnemies;
	
	public MapManagerTest(int mapSize, int amountOfEnemies)
	{
		this.mapSize = mapSize;
		this.amountOfEnemies = amountOfEnemies;
	}
	
	@Before
	public void setUp() throws Exception
	{
		mapToSave = new Map(mapSize, amountOfEnemies);
		MapManager.saveMapToFile(mapToSave, fileName);
		loadedMap = new Map(MapManager.loadMapFromFile(fileName));
	}
	
	@Parameters
    public static Collection<Object[]> data() {
        Object[][] c = {
        		{11,3}, {22,5}, {30,5}, {30,6}
   		};
        return  Arrays.asList(c);
    }
	
	@Test
	public void printMapTest() 
	{
		assertEquals(mapToSave.printMap(), loadedMap.printMap());
	}
	
	@Test
	public void printPlayerCordsTest() 
	{
		assertEquals(mapToSave.printPlayerCords(), loadedMap.printPlayerCords());
	}
	
	@Test
	public void printEnemiesCordsTest() 
	{
		assertEquals(mapToSave.printEnemiesCords(), loadedMap.printEnemiesCords());
	}
}
