package Projekt1;

import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class TurnsMongo implements ITurnsService{
	
	private MongoCollection turns;
	
	public TurnsMongo() throws UnknownHostException{
		@SuppressWarnings({ "deprecation", "resource" })
		DB db = new MongoClient().getDB("turns");
		turns = new Jongo(db).getCollection("turns");
	}
	
	public Turn findByTurnNumber(int turn){
		  return turns.findOne("{_id: #", turn).as(Turn.class);
	}
	
	public void insert(Turn turn){
		turns.insert(turn);
	}
	
	public List<Turn> getAllTurns(){
		List<Turn> all = new ArrayList<Turn>();
		MongoCursor<Turn> cursor = turns.find().as(Turn.class);
		if(cursor == null)
			return Collections.emptyList();
		for(Turn t : cursor) {
			all.add(t);
		}
		//cursor.close();
		return all;
	}
	
	public void eraseAll() {
		turns.drop();
	}
	
	public int getLastTurnNumber() {
		Turn t = turns.findOne().orderBy("{turnNumber: -1}").as(Turn.class);
		if(t == null)
			return 0;
		return t.getTurnNumber();
	}
	
	public boolean isDbErased() {
		return turns.count() == 0;
	}
	
	public void save(Turn turn){
		  turns.save(turn);
	}
}
