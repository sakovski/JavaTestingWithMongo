package Projekt1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map 
{
	//TODO: zbieranie mone i testy 
	private char[][] graphicMap;
	private List<int[]> islandsPositions; 
	private List<Ship> enemies; 
	private List<int[]> coinsPositions; 
	private int mapSize;
	private Ship player;
	
	public Map()
	{
		islandsPositions = new ArrayList<int[]>();
		enemies = new ArrayList<Ship>();
		coinsPositions = new ArrayList<int[]>();
	}
	
	public Map(int mapSize, int amountOfEnemies)
	{
		this();
		this.mapSize = mapSize;
		graphicMap = new char[mapSize][mapSize];
		putWaterToMap();
		generateNewMap(amountOfEnemies);
	}
	
	public Map(String mapFromFile) throws IOException
	{
		this();
		generateMapFromFile(mapFromFile);
	}
	
	public void generateMapFromFile(String mapFromFile) throws IOException
	{		
		putWaterToMap();
		BufferedReader bufReader = new BufferedReader(new StringReader(mapFromFile));
		String line=null;
		int section = 0;
		String[] parts;
		while( (line=bufReader.readLine()) != null )
		{
			if(line.charAt(0) != '-')
			{			
				parts = line.split(";");
				switch(section)
				{
				case 0:
					mapSize = Integer.parseInt(line);
					graphicMap = new char[mapSize][mapSize];
					break;
				case 1:
					player = new Ship(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2].charAt(0), "Gracz");
					break;
				case 2:
					enemies.add(new Ship(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2].charAt(0), parts[3]));
					break;
				case 3:
					islandsPositions.add(new int[] {Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
					break;
				case 4:
					coinsPositions.add(new int[] {Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
					break;
				default:
					break;	
				}
			}
			else
				section++;
		}	
	}
	
	private void generateNewMap(int amountOfEnemies)
	{
		generateIslandsPositions();
		generateCoinsPositions();
		generateEnemies(amountOfEnemies);			
		setPlayerStartingPosition();
	}
	
	private void fillGraphicMap()
	{
		putWaterToMap();
		putIslandsToMap();
		putCoinsToMap();
		putEnemiesToMap();
		putPlayerToMap();
	}
	
	private void putWaterToMap()
	{
		for(int i = 0; i < mapSize; i++)
		{
			for(int j = 0; j < mapSize; j++)
			{
				graphicMap[i][j] = MapSignatures.WATER;
			}
		}
	}
	
	private void putIslandsToMap()
	{
		for(int[] island : islandsPositions)
		{
			graphicMap[island[0]][island[1]] = MapSignatures.ISLAND;
		}
	}
	
	private void putCoinsToMap()
	{
		for(int[] coin : coinsPositions)
		{
			graphicMap[coin[0]][coin[1]] = MapSignatures.COIN;
		}
	}
	
	private void putPlayerToMap()
	{
		graphicMap[player.getX()][player.getY()] = MapSignatures.PLAYER;
	}
	
	private void putEnemiesToMap()
	{
		for(Ship ship : enemies)
		{
			graphicMap[ship.getX()][ship.getY()] = MapSignatures.ENEMY;
		}
	}
	
	private void setPlayerStartingPosition()
	{
		int playerX;
		int playerY;
		Random generator = new Random();
		do
		{
			playerX = generator.nextInt(mapSize-1);
			playerY = generator.nextInt(mapSize-1);
		}while(graphicMap[playerX][playerY] != MapSignatures.WATER);	
		player = new Ship(playerX, playerY, Directions.NORTH, "Gracz");
		putPlayerToMap();
	}
	
	private void generateEnemies(int amountOfEnemies)
	{
		Random generator = new Random();
		for(int i = 0; i < amountOfEnemies; i++)
		{
			int x;
			int y;
			do
			{
				x = generator.nextInt(mapSize-1);
				y = generator.nextInt(mapSize-1);
			}while(graphicMap[x][y] != MapSignatures.WATER);
			graphicMap[x][y] = MapSignatures.ENEMY;
			Ship ship = new Ship(x, y, Directions.NORTH, "Przeciwnik"+(i+1));
			enemies.add(ship);
		}
	}
	
	private void generateCoinsPositions()
	{
		Random generator = new Random();
		int amountOfCoins = mapSize*2;
		for(int i = 0; i < amountOfCoins; i++)
		{
			int x;
			int y;
			do
			{
				x = generator.nextInt(mapSize-1);
				y = generator.nextInt(mapSize-1);
			}while(graphicMap[x][y] != MapSignatures.WATER);
			graphicMap[x][y] = MapSignatures.COIN;
			coinsPositions.add(new int[]{x,y});
		}
	}
	
	private void generateIslandsPositions()
	{
		Random generator = new Random();
		int amountOfIslands = mapSize/2;
		for(int i = 0; i < amountOfIslands; i++)
		{
			int x;
			int y;
			do
			{
				x = generator.nextInt(mapSize-3);
				y = generator.nextInt(mapSize-3);
			}while(graphicMap[x][y] != MapSignatures.WATER);
			islandsPositions.add(new int[]{x, y});
			islandsPositions.add(new int[]{x+1, y});
			islandsPositions.add(new int[]{x, y+1});
		}
		putIslandsToMap();
	}
	
	private void turnShip(Ship ship, char direction)
	{
		char currentDirection = ship.getDirection();
		if(currentDirection == Directions.EAST)
		{
			if(direction == UserInputs.LEFT)
				ship.setDirection(Directions.NORTH);
			else
				ship.setDirection(Directions.SOUTH);
		}
		if(currentDirection == Directions.WEST)
		{
			if(direction == UserInputs.LEFT)
				ship.setDirection(Directions.SOUTH);
			else
				ship.setDirection(Directions.NORTH);
		}
		if(currentDirection == Directions.NORTH)
		{
			if(direction == UserInputs.LEFT)
				ship.setDirection(Directions.WEST);
			else
				ship.setDirection(Directions.EAST);
		}
		if(currentDirection == Directions.SOUTH)
		{
			if(direction == UserInputs.LEFT)
				ship.setDirection(Directions.EAST);
			else
				ship.setDirection(Directions.WEST);
		}
	}
	
	private void moveShipUp(Ship ship) throws Exception
	{	
		if(!isCollision(ship.getX()-1, ship.getY()))
		{
			ship.setX(ship.getX() - 1);
		}
			
	}
	
	private void moveShipDown(Ship ship) throws Exception
	{
		if(!isCollision(ship.getX()+1, ship.getY()))
		{
			ship.setX(ship.getX() + 1);
		}
			
	}
	
	private void moveShipLeft(Ship ship) throws Exception
	{
		if(!isCollision(ship.getX(), ship.getY()+1))
		{
			ship.setY(ship.getY() + 1);
		}
			
	}
	
	private void moveShipRight(Ship ship) throws Exception
	{
		if(!isCollision(ship.getX(), ship.getY()-1))
		{
			ship.setY(ship.getY() - 1);
		}
			
	}
	
	private void moveShip(Ship ship, char direction) throws Exception
	{
		char currentDirection = ship.getDirection();
		if(currentDirection == Directions.EAST)
		{
			if(direction == UserInputs.FORWARD)
				moveShipLeft(ship);
			else
				moveShipRight(ship);
		}
		if(currentDirection == Directions.WEST)
		{
			if(direction == UserInputs.FORWARD)
				moveShipRight(ship);
			else
				moveShipLeft(ship);
		}
		if(currentDirection == Directions.NORTH)
		{
			if(direction == UserInputs.FORWARD)
				moveShipUp(ship);
			else
				moveShipDown(ship);
		}
		if(currentDirection == Directions.SOUTH)
		{
			if(direction == UserInputs.FORWARD)
				moveShipDown(ship);
			else
				moveShipUp(ship);
		}
	}
	
	public void updatePlayer(char move) throws Exception
	{
		if(move == UserInputs.FORWARD || move == UserInputs.BACK)
			moveShip(player, move);
		else
			turnShip(player, move);
			
		correctShipPosition(player);
	}
	
	public void updateEnemies() throws Exception
	{
		for(Ship enemy : enemies)
		{
			Random rand = new Random();
			int move = rand.nextInt(3);
			if(move == 0)
				turnShip(enemy, UserInputs.LEFT);
			if(move == 1)
				turnShip(enemy, UserInputs.RIGHT);
			if(move == 2)
				moveShip(enemy, UserInputs.FORWARD);
			if(move == 3)
				moveShip(enemy, UserInputs.BACK);
			correctShipPosition(enemy);
		}
	}
	
	private void correctShipPosition(Ship ship)
	{
		if(ship.getX() >= mapSize)
			ship.setX(0);
		else if(ship.getX() < 0)
			ship.setX(mapSize - 1);
		if(ship.getY() >= mapSize)
			ship.setY(0);
		else if(ship.getY() < 0)
			ship.setY(mapSize - 1);
	}
	
	private boolean isCollision(int x, int y) throws Exception
	{
		if(x >= mapSize)
			x = 0;
		else if (x < 0)
			x = mapSize - 1;
		if(y >= mapSize)
			y = 0;
		else if(y < 0)
			y = mapSize - 1;
		if(graphicMap[x][y] == MapSignatures.WATER)
			return false;
		if(graphicMap[x][y] == MapSignatures.COIN)
			//TODO: Add coin collecting
			return false;
		if(graphicMap[x][y] == MapSignatures.ISLAND || graphicMap[x][y] == MapSignatures.ENEMY || graphicMap[x][y] == MapSignatures.PLAYER)
			throw new Exception("Collision detected!");
		return true;
	}
	
	private String printShipCords(Ship ship)
	{
		return ship.getName() + ": x - " + ship.getY() + "; y - " + ship.getX() + "; Kierunek - " + ship.getDirection();
	}
	
	public String printPlayerCords()
	{
		return printShipCords(player);
	}
	
	public String printEnemiesCords()
	{
		StringBuilder builder = new StringBuilder();
		for(Ship enemy : enemies)
		{
			builder.append(printShipCords(enemy));
			builder.append(System.lineSeparator());
		}
		return builder.toString();
	}
	
	public String printMap()
	{
		fillGraphicMap();
		StringBuilder mapBuilder = new StringBuilder();
		for(int i = 0; i < mapSize; i++)
		{
			for(int j = 0; j < mapSize; j++)
			{
				mapBuilder.append(graphicMap[i][j]);
			}
			mapBuilder.append(System.lineSeparator());
		}
		return mapBuilder.toString();	
	}
	
	public void updateDb(TurnsCollection db) {
		db.insertNewTurn(getIslands(), getEnemies(), getCoins(), getMapSize(), getPlayer());
	}
	
	public Ship getPlayer() {return player;}
	public List<Ship> getEnemies() {return enemies;}
	public List<int[]> getCoins() {return coinsPositions;}
	public List<int[]> getIslands() {return islandsPositions;}
	public int getMapSize() {return mapSize;}
	
}
