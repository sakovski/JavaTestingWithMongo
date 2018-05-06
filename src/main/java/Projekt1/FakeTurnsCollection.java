package Projekt1;

import java.util.ArrayList;
import java.util.List;

public class FakeTurnsCollection implements ITurnsService{
	
	private List<Turn> turns;
	
	public FakeTurnsCollection() {
		turns = new ArrayList<Turn>();
	}
	
	@Override
	public Turn findByTurnNumber(int number) {
		for(Turn turn : turns) {
			if(turn.getTurnNumber() == number)
				return turn;
		}
		return null;
	}

	@Override
	public void insert(Turn turn) {
		turns.add(turn);	
	}

	@Override
	public List<Turn> getAllTurns() {
		if(turns.size() != 0)
			return turns;
		return null;
	}

	@Override
	public void eraseAll() {
		turns.clear();	
	}

	@Override
	public int getLastTurnNumber() {
		if(turns.size() != 0)
			return turns.get(turns.size()-1).getTurnNumber();
		return 0;
	}

	@Override
	public boolean isDbErased() {
		if(turns.size() != 0)
			return false;
		return true;
	}

	@Override
	public void save(Turn t) {
		boolean saved = false;
		for(Turn turn : turns) {
			if(turn.getTurnNumber() == t.getTurnNumber()) {
				turn = t;
				saved = true;
			}								
		}
		if(!saved)
			throw new IllegalArgumentException();
	}

}
