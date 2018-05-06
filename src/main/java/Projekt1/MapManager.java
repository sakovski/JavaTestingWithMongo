package Projekt1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MapManager 
{
		static void saveMapToFile(Map map, String fileName) throws IOException
		{
			File file = new File("src" + File.separator + "maps" + File.separator + fileName);
			BufferedWriter writer = null;
			StringBuilder sb = new StringBuilder(); 
			try 
			{
			    writer = new BufferedWriter(new FileWriter(file));
			    sb.append(map.getMapSize()).append(System.lineSeparator());
			    sb.append('-').append(System.lineSeparator());
			    sb.append(map.getPlayer().getX()).append(';').append(map.getPlayer().getY()).append(';').append(map.getPlayer().getDirection()).append(System.lineSeparator());
			    sb.append('-').append(System.lineSeparator());
			    for(Ship enemy : map.getEnemies())
			    {
			    	sb.append(enemy.getX()).append(';').append(enemy.getY()).append(';').append(enemy.getDirection()).append(';').append(enemy.getName()).append(System.lineSeparator());
			    }
			    sb.append('-').append(System.lineSeparator());
			    for(int[] island : map.getIslands())
			    {
			    	sb.append(island[0]).append(';').append(island[1]).append(System.lineSeparator());
			    }
			    sb.append('-').append(System.lineSeparator());
			    for(int[] coin : map.getCoins())
			    {
			    	sb.append(coin[0]).append(';').append(coin[1]).append(System.lineSeparator());
			    }
			    writer.write(sb.toString());
			    System.out.println("Mapa zapisana!");
			}
			finally {
			    if (writer != null) writer.close();
			}
		}
	    
	    static String loadMapFromFile(String mapName) throws Exception
	    {
	    	BufferedReader br = null;
	    	StringBuilder sb;
	    	String map;
	    	try {
	    		br = new BufferedReader(new FileReader("src" + File.separator + "maps" + File.separator + mapName));
	    	    sb = new StringBuilder();
	    	    String line = br.readLine();

	    	    while (line != null) {
	    	        sb.append(line);
	    	        sb.append(System.lineSeparator());
	    	        line = br.readLine();
	    	    }
	    	    map = sb.toString();
	    	} finally {
	    	    br.close();
	    	}
	    	return map;
	    }
}
