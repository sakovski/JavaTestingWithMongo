package Projekt1;

import java.util.List;

public interface ITurnsService {
	
	Turn findByTurnNumber(int number);
	void insert(Turn turn);
	List<Turn> getAllTurns();
	void eraseAll();
	int getLastTurnNumber();
	boolean isDbErased();
	void save(Turn turn);
}
