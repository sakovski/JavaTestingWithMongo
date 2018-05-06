package Projekt1;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Turn {
	private int turnNumber;
	private List<int[]> islandsPositions; 
	private List<Ship> enemies; 
	private List<int[]> coinsPositions;
	private int mapSize;
	private Ship player;
	
	@JsonCreator
	public Turn(@JsonProperty("turnNumber") int turnNumber, @JsonProperty("islandsPositions") List<int[]> islandsPositions, @JsonProperty("enemies") List<Ship> enemies, @JsonProperty("coinsPositions") List<int[]> coinsPositions, @JsonProperty("mapSize") int mapSize, @JsonProperty("player") Ship player)
	{
		this.turnNumber = turnNumber;
		this.islandsPositions = islandsPositions;
		this.enemies = enemies;
		this.coinsPositions = coinsPositions;
		this.mapSize = mapSize;
		this.player = player;
	}
	
	public int getTurnNumber() {return this.turnNumber;}
	public List<int[]> getIslandsPositions() {return this.islandsPositions;}
	public List<Ship> getEnemies() {return this.enemies;}
	public List<int[]> getCoinsPositions() {return coinsPositions;}
	public int getMapSize() {return this.mapSize;}
	public Ship getPlayer() {return this.player;}
}
	