package Projekt1;

import java.rmi.UnknownHostException;
import java.util.Collections;
import java.util.List;

public class TurnsCollection {
	
	private ITurnsService service;
	  
	public TurnsCollection(){}
	
	public TurnsCollection(FakeTurnsCollection fake) {
		service = fake;
	}
	
	public void connectDb() {
		try {
			service = new TurnsMongo();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public List<Turn> getAllSavedTurns() {
		return service.getAllTurns();
	}
	
	public List<int[]> getIslandsPositions(int turn) {
		Turn t = service.findByTurnNumber(turn);
		if (t == null)
			return Collections.emptyList();
		return t.getIslandsPositions();
	}
	
	public List<Ship> getEnemies(int turn) {
		Turn t = service.findByTurnNumber(turn);
		if (t == null)
			return Collections.emptyList();
		return t.getEnemies();
	}
	
	public Ship getPlayer(int turn) {
		Turn t = service.findByTurnNumber(turn);
		if(t == null)
			return null;
		return t.getPlayer();
	}
	
	public List<int[]> getCoinsPositions(int turn) {
		Turn t = service.findByTurnNumber(turn);
		if (t == null)
			return Collections.emptyList();
		return t.getCoinsPositions();
	}
	
	public void insertNewTurn(List<int[]> islandsPositions, List<Ship> enemies, List<int[]> coinsPositions, int mapSize, Ship player) {
		int turnNumber = service.getLastTurnNumber()+1;
		service.insert(new Turn(turnNumber, islandsPositions, enemies, coinsPositions, mapSize, player));
	}
	
	public boolean isDbEmpty() {
		return service.isDbErased();
	}
	
	public void eraseData() {
		service.eraseAll();
	}
	
	public void saveTurn(Turn turn) {
		service.save(turn);
	}
}
