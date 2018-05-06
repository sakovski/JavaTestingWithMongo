package Projekt1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import org.easymock.EasyMockRunner;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(EasyMockRunner.class)
public class TurnsEasyMockTest {
	
	@TestSubject
	TurnsCollection turnsMongo = new TurnsCollection();
	
	//A nice mock expects recorded calls in any order and returning null for other calls
	@Mock(type = MockType.NICE)
	TurnsMongo turnsCollection;
	
	int turnNumber;
	private List<int[]> islandsPositions;
	private List<Ship> enemies;
	private List<int[]> coinsPositions;
	int mapSize;
	Ship player;
	
	@SuppressWarnings("unchecked")
	@Test
	public void mockingWorksAsExpected(){
		turnNumber = 1;
		mapSize = 6;
		islandsPositions = createMock(List.class);
		enemies = createMock(List.class);
		coinsPositions = createMock(List.class);
		player = createMock(Ship.class);
		Turn turn = new Turn(turnNumber, islandsPositions, enemies, coinsPositions, mapSize, player);
		expect(turnsCollection.findByTurnNumber(turnNumber)).andReturn(turn);
		replay(turnsCollection);
		assertThat(turnsCollection.findByTurnNumber(turnNumber)).isEqualTo(turn);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void eraseDataFromDbListShouldBeEmpty() {
		final List<Turn> turnsExp = new ArrayList<Turn>();
		turnsExp.add(createMock(Turn.class));
		turnsExp.add(createMock(Turn.class));
		turnsCollection.eraseAll();
		expectLastCall().andAnswer(new IAnswer() {
		    public Object answer() {
		    	turnsExp.clear();
		        return null;
		    }
		});
		replay(turnsCollection);
		turnsMongo.eraseData();		
		assertFalse(turnsExp.size() > 0);
	}
	
	@Test
	public void AddDataToDbListShouldNotBeEmpty() {
		List<Turn> turnsExp = new ArrayList<Turn>();
		turnsExp.add(createMock(Turn.class));
		replay();
		assertTrue(turnsExp.size() > 0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void turnHasIslands() {
		turnNumber = 1;
		mapSize = 6;
		int[] cord1 = new int[] {2,4};
		int[] cord2 = new int[] {4,4};
		islandsPositions = new ArrayList<int[]>();
		islandsPositions.add(cord1);
		islandsPositions.add(cord2);
		enemies = createMock(List.class);
		coinsPositions = createMock(List.class);
		player = createMock(Ship.class);
		Turn turn = createMock(Turn.class);
		expect(turnsCollection.findByTurnNumber(turnNumber)).andReturn(turn);
		expect(turn.getIslandsPositions()).andReturn(islandsPositions);
		replay(enemies);
		replay(coinsPositions);
		replay(player);
		replay(turn);
		replay(turnsCollection);
		assertThat(turnsMongo.getIslandsPositions(turnNumber)).hasSize(2).containsOnly(cord1,cord2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void turnPlayerHasProperDirection() {
		turnNumber = 1;
		mapSize = 6;
		coinsPositions = createMock(List.class);
		enemies = createMock(List.class);
		islandsPositions = createMock(List.class);
		player = new Ship(4,4,Directions.EAST);
		Turn turn = createMock(Turn.class);
		expect(turnsCollection.findByTurnNumber(turnNumber)).andReturn(turn);
		expect(turn.getPlayer()).andReturn(player);
		replay(enemies);
		replay(islandsPositions);
		replay(coinsPositions);
		replay(turn);
		replay(turnsCollection);
		assertEquals(Directions.EAST, turnsMongo.getPlayer(turnNumber).getDirection());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void turnHasEnemies() {
		turnNumber = 1;
		mapSize = 6;
		islandsPositions = createMock(List.class);
		enemies = new ArrayList<Ship>();
		Ship enemy1 = new Ship(4,4,Directions.NORTH);
		Ship enemy2 = new Ship(5,5,Directions.EAST);
		enemies.add(enemy1);
		enemies.add(enemy2);
		coinsPositions = createMock(List.class);
		player = createMock(Ship.class);
		Turn turn = createMock(Turn.class);
		expect(turnsCollection.findByTurnNumber(turnNumber)).andReturn(turn);
		expect(turn.getEnemies()).andReturn(enemies);
		replay(islandsPositions);
		replay(coinsPositions);
		replay(player);
		replay(turn);
		replay(turnsCollection);
		assertFalse(turnsMongo.getEnemies(turnNumber).get(0).getDirection() != Directions.NORTH);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void turnHasCoins() {
		turnNumber = 1;
		mapSize = 6;
		int[] coin1 = new int[] {2,4};
		int[] coin2 = new int[] {4,4};
		coinsPositions = new ArrayList<int[]>();
		coinsPositions.add(coin1);
		coinsPositions.add(coin2);
		enemies = createMock(List.class);
		islandsPositions = createMock(List.class);
		player = createMock(Ship.class);
		Turn turn = createMock(Turn.class);
		expect(turnsCollection.findByTurnNumber(turnNumber)).andReturn(turn);
		expect(turn.getCoinsPositions()).andReturn(coinsPositions);
		replay(enemies);
		replay(islandsPositions);
		replay(player);
		replay(turn);
		replay(turnsCollection);
		assertThat(turnsMongo.getCoinsPositions(turnNumber)).hasSize(2).containsOnly(coin1, coin2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void turnPlayerisNotNull() {
		turnNumber = 1;
		mapSize = 6;
		coinsPositions = createMock(List.class);
		enemies = createMock(List.class);
		islandsPositions = createMock(List.class);
		player = new Ship(4,4,Directions.EAST);
		Turn turn = createMock(Turn.class);
		expect(turnsCollection.findByTurnNumber(turnNumber)).andReturn(turn);
		expect(turn.getPlayer()).andReturn(player);
		replay(enemies);
		replay(islandsPositions);
		replay(coinsPositions);
		replay(turn);
		replay(turnsCollection);
		assertNotNull(turnsMongo.getPlayer(turnNumber));
	}
}
