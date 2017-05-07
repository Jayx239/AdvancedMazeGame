package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public interface IMazeFactory {
	
	/* Key words */
	static String wallWord = "wall";
	static char doorChar = 'd';
	static String openWord = "open";
	
	/* Parameter indexes*/
	static int paramItemType = 0;
	static int paramIdNumber = 1;
	static int paramNorthIndex = 2;
	static int paramSouthIndex = 3;
	static int paramEastIndex = 4;
	static int paramWestIndex = 5;
	static int doorRoom1Index = 2;
	static int doorRoom2Index = 3;
	static int doorOpenIndex = 4;
	static int defaultStartRoom = 0;
	
	/* Adapter methods for creating new maze items */
	public Maze makeMaze();
	
	public Wall makeWall();
	
	public Room makeRoom(int roomNumber);
	
	public Door makeDoor(Room room1, Room room2);
	
}
