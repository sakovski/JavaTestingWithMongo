package Projekt1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShipTest 
{
	private String testShipName = "TestShip";
	private int testShipX = 10;
	private int testShipY = 10;
	private char testShipDirection = 'N'; 
	private Ship testShip;
	
	@Before
	public void setUp()
	{
		testShip = new Ship(testShipX, testShipY, testShipDirection, testShipName);
	}
	
	@Test
	public void getNameTest() 
	{
		assertEquals(testShipName, testShip.getName());
	}
	
	@Test
	public void getXTest() 
	{
		assertEquals(testShipX, testShip.getX());
	}
	
	@Test
	public void getYTest() 
	{
		assertEquals(testShipY, testShip.getY());
	}
	
	@Test
	public void getDirectionTest() 
	{
		assertEquals(testShipDirection, testShip.getDirection());
	}
	
	@Test
	public void setDirectionTest() 
	{
		char newDirection = 'S';
		testShip.setDirection(newDirection);
		assertEquals(newDirection, testShip.getDirection());
	}
	
	@Test
	public void setXOneFiledTest() 
	{
		int newX = 11;
		testShip.setX(newX);
		assertEquals(newX, testShip.getX());
	}
	
	@Test
	public void setYOneFieldTest() 
	{
		int newY = 11;
		testShip.setY(newY);
		assertEquals(newY, testShip.getY());
	}
	
	@Test
	public void constructorWithoutNameTest()
	{
		testShip = new Ship(testShipX, testShipY, testShipDirection);
		assertEquals("Nieznany", testShip.getName());
	}
}
