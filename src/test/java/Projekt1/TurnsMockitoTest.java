package Projekt1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class TurnsMockitoTest {
	
	@Mock
	TurnsMongo turnMock;
		
	@InjectMocks
	TurnsCollection turnsMongo = new TurnsCollection();
	
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
		islandsPositions = mock(List.class);
		enemies = mock(List.class);
		coinsPositions = mock(List.class);
		player = mock(Ship.class);
		Turn turn = new Turn(turnNumber, islandsPositions, enemies, coinsPositions, mapSize, player);
		doReturn(turn).when(turnMock).findByTurnNumber(turnNumber);
		assertThat(turnMock.findByTurnNumber(turnNumber)).isEqualTo(turn);
	}
	
	@Test
	public void noTurnsInDb() {
		assertThat(turnsMongo.getAllSavedTurns().isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void twoCoinsOnTurnShouldBeEqualToDbTurnCoins() {
		turnNumber = 1;
		mapSize = 6;
		player = mock(Ship.class);
		islandsPositions = mock(List.class);
		enemies = mock(List.class);
		coinsPositions = spy(new ArrayList<int[]>());
		doReturn(2).when(coinsPositions).size();
		Turn turn = new Turn(turnNumber, islandsPositions, enemies, coinsPositions, mapSize, player);
		doReturn(turn).when(turnMock).findByTurnNumber(turnNumber);
		assertEquals(turn.getCoinsPositions().size(), turnsMongo.getCoinsPositions(turnNumber).size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void afterTwoTurnsInDbShouldBeTwoTurns() {
		final List<Turn> turnsExp = new ArrayList<Turn>();
		mapSize = 6;
		islandsPositions = mock(List.class);
		enemies = mock(List.class);
		coinsPositions = mock(List.class);
		player = mock(Ship.class);
		doAnswer(new Answer<Void>() {
			  public Void answer(final InvocationOnMock invocation) throws Throwable {
				  turnsExp.add((Turn) invocation.getArguments()[0]);
				return null;
			  }
			}).when(turnMock).insert(isA(Turn.class));
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		assertTrue(turnsExp.size() == 2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void afterFirstTurnInDbShouldBeTurnNumberOne() {
		final List<Turn> turnsExp = new ArrayList<Turn>();
		mapSize = 6;
		islandsPositions = mock(List.class);
		enemies = mock(List.class);
		coinsPositions = mock(List.class);
		player = mock(Ship.class);
		doAnswer(new Answer<Void>() {
			  public Void answer(final InvocationOnMock invocation) throws Throwable {
				  turnsExp.add((Turn) invocation.getArguments()[0]);
				return null;
			  }
			}).when(turnMock).insert(isA(Turn.class));
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		assertEquals(1, turnsExp.get(0).getTurnNumber());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void afterThreeTurnsInDbSecondTurnHasNumberTwo() {
		final List<Turn> turnsExp = new ArrayList<Turn>();
		mapSize = 6;
		islandsPositions = mock(List.class);
		enemies = mock(List.class);
		coinsPositions = mock(List.class);
		player = mock(Ship.class);
		doAnswer(new Answer<Void>() {
			  public Void answer(final InvocationOnMock invocation) throws Throwable {
				  turnsExp.add((Turn) invocation.getArguments()[0]);
				return null;
			  }
			}).when(turnMock).insert(isA(Turn.class));
		turnNumber = 0;
		doReturn(turnNumber++).when(turnMock).getLastTurnNumber();
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		doReturn(turnNumber++).when(turnMock).getLastTurnNumber();
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		doReturn(turnNumber++).when(turnMock).getLastTurnNumber();
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		assertEquals(2, turnsExp.get(1).getTurnNumber());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void afterAddingSomeTurnsListIsNotNull() {
			final List<Turn> turnsExp = new ArrayList<Turn>();
			mapSize = 6;
			islandsPositions = mock(List.class);
			enemies = mock(List.class);
			coinsPositions = mock(List.class);
			player = mock(Ship.class);
			doAnswer(new Answer<Void>() {
				  public Void answer(final InvocationOnMock invocation) throws Throwable {
					  turnsExp.add((Turn) invocation.getArguments()[0]);
					return null;
				  }
				}).when(turnMock).insert(isA(Turn.class));
			turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
			assertNotNull(turnsExp);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void whenSearchedTurnNotInDb() {
		final List<Turn> turnsExp = new ArrayList<Turn>();
		mapSize = 6;
		islandsPositions = mock(List.class);
		enemies = mock(List.class);
		coinsPositions = mock(List.class);
		player = mock(Ship.class);
		doAnswer(new Answer<Void>() {
			  public Void answer(final InvocationOnMock invocation) throws Throwable {
				  turnsExp.add((Turn) invocation.getArguments()[0]);
				return null;
			  }
			}).when(turnMock).insert(isA(Turn.class));
		turnNumber = 0;
		doReturn(turnNumber++).when(turnMock).getLastTurnNumber();
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		doReturn(turnNumber++).when(turnMock).getLastTurnNumber();
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);
		doReturn(turnNumber++).when(turnMock).getLastTurnNumber();
		turnsMongo.insertNewTurn(islandsPositions, enemies, coinsPositions, mapSize, player);     
        assertThat(turnsMongo.getEnemies(turnNumber+4).isEmpty());
	}
}
