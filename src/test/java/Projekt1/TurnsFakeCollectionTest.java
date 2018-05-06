package Projekt1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TurnsFakeCollectionTest {
	
	FakeTurnsCollection turnsCollection;	
	TurnsCollection turnsMongo;
	
	int turnNumber;
	private List<int[]> islandsPositions;
	private List<Ship> enemies;
	private List<int[]> coinsPositions;
	int mapSize;
	Ship player;
	
	@Before
	public void setUp() {
		turnsCollection = new FakeTurnsCollection();	
		turnsMongo = new TurnsCollection(turnsCollection);
		islandsPositions = new ArrayList<int[]>();
		enemies = new ArrayList<Ship>();
		coinsPositions = new ArrayList<int[]>();
		coinsPositions.add(new int[] {4,4});
		coinsPositions.add(new int[] {2,2});
		enemies.add(new Ship(1,2,Directions.EAST));
		enemies.add(new Ship(1,3,Directions.NORTH));
		islandsPositions.add(new int[] {5,5});
		islandsPositions.add(new int[] {3,3});
		mapSize = 6;
		player = new Ship(0,0,Directions.WEST);
	}
	
	@Test
	public void addedTwoTurnsShouldHaveSameIslandsPositions() {
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		assertEquals(islandsPositions, turnsMongo.getIslandsPositions(1));
		assertEquals(islandsPositions, turnsMongo.getIslandsPositions(2));
	}
	
	@Test
	public void addedTwoTurnsShouldHaveSameCoinsPositions() {
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		assertEquals(coinsPositions, turnsMongo.getCoinsPositions(1));
		assertEquals(coinsPositions, turnsMongo.getCoinsPositions(2));
	}
	
	@Test
	public void addedTwoTurnsSHouldHaveSamePlayer() {
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		assertEquals(player, turnsMongo.getPlayer(1));
		assertEquals(player, turnsMongo.getPlayer(2));
	}
	
	@Test
	public void forManyTurnsIfTurnFromTheMiddleHasProperNumber() {
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		assertTrue(turnsMongo.getAllSavedTurns().get(4).getTurnNumber() == 5);
	}
	
	@Test
	public void tryToGetTurnNotInDbGetPlayerShouldReturNull() {
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		assertNull(turnsMongo.getPlayer(10));
	}
	
	@Test
	public void tryToGetAllTurnsWhenEmptyDbShouldReturnNull() {
		assertNull(turnsMongo.getAllSavedTurns());
	}
	
	@Test
	public void checkIfDbIsErasedAfterClearMethod() {
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.eraseData();
		assertTrue(turnsMongo.isDbEmpty());
	}
	
	@Test
	public void checkIfDbIsErasedIfNoClearMethod() {
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		assertFalse(turnsMongo.isDbEmpty());
	}
	
	@Test
	public void turnWithAddedEnemySavedCorrectly(){
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		enemies.add(new Ship(1,5,Directions.WEST));
		Turn turn = new Turn(2,islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.saveTurn(turn);
		assertEquals(enemies, turnsMongo.getEnemies(2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void turnWithAddedEnemyButNotInDbSaveShouldRaiseException(){
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		enemies.add(new Ship(1,5,Directions.WEST));
		Turn turn = new Turn(5,islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.saveTurn(turn);
	}
	
	@After
	public void tearDown()
	{
		turnsCollection.eraseAll();
	}
}
