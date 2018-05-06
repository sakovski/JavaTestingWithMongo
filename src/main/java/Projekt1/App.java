package Projekt1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 @author Seweryn Rutkowski
 */
public class App 
{
	public static TurnsCollection db = new TurnsCollection();
	
    public static void main( String[] args )
    {
    	@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
    	Map map;
    	String choice = null;
    	db.connectDb();
		db.eraseData();
    	do
    	{
    		displayMenu();
    		choice = scan.next();   		
    	}while(!(choice.equals("1") || choice.equals("2") || choice.equals("3")));
    	switch(Integer.parseInt(choice))
    	{
    		case 1:
    			int size = 0;
    			int amountOfEnemies = 0;
    			do
    			{   				
    				System.out.println("Podaj rozmiar planszy (od 10 do 30):");
    				choice = scan.next();
    				size = Integer.parseInt(choice);
    			}while(!(size >= 10 && size <= 30));
    			do
    			{   				
    				System.out.println("Podaj liczbe przeciwnikow (od 1 do 6):");
    				choice = scan.next();
    				amountOfEnemies = Integer.parseInt(choice);
    			}while(!(amountOfEnemies >= 1 && amountOfEnemies <= 6));
    			map = new Map(size, amountOfEnemies);
    			do
    			{
    				update(map, scan);
    			}while(true);
    		case 2:
    			String mapString = null;
    			map = null;
    			try {
    				mapString = MapManager.loadMapFromFile("map.txt");
    				if(mapString != null)
    				{
    					map = new Map(mapString);
    					System.out.println("Mapa wczytana!");
    				}        				
        			else
        			{
        				map = new Map(10, 2);
    					System.out.println("Blad odczytu mapy, tworze mape 10-2!");
        			}        				
    			} catch (Exception e) {
    				e.printStackTrace();
    			}   			
    			do
    			{
    				update(map, scan);
    			}while(true);
    		case 3:
    			System.exit(0);
    			break;
    	}
    }
    
    private static void displayMenu()
    {
    	System.out.println("Menu Glowne:");
        System.out.println("1.Nowa Mapa");
        System.out.println("2.Wczytaj Mape");
        System.out.println("3.Wyjscie");
    }
        
    @SuppressWarnings("finally")
	private static void update(Map map, Scanner scanner)
    {
    	System.out.println(map.printMap());
    	System.out.println(map.printPlayerCords());
    	System.out.println(map.printEnemiesCords());
    	String input = scanner.next();
    	ArrayList<Character> moves = handleUserInput(input);
    	if(moves.size() == 0)
    		return;
    	for(char move : moves)
    	{
    		map.updateDb(db);
    		if (move == UserInputs.SAVE_MAP)
				try {
					MapManager.saveMapToFile(map, "map.txt");
				} catch (IOException e) {
					e.printStackTrace();
				} finally {return;}
    		if (move == UserInputs.EXIT)
    			System.exit(0);
    		try {
				map.updatePlayer(move);
				map.updateEnemies();
			} catch (Exception e) {
				e.printStackTrace();
			}    		
    	}
    }
    
    private static ArrayList<Character> handleUserInput(String input)
    {
    	ArrayList<Character> moves = new ArrayList<Character>();
    	for(int i = 0; i < input.length(); i++)
    	{
    		char c = input.charAt(i);
    		if(c == UserInputs.BACK || c == UserInputs.FORWARD || c == UserInputs.LEFT || c == UserInputs.RIGHT || c == UserInputs.EXIT || c == UserInputs.SAVE_MAP)
    			moves.add(c);
    	}
    	return moves;
    }
}
