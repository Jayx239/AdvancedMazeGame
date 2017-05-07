package blue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import maze.Direction;
import maze.Door;
import maze.Maze;
import maze.MazeGameCreator;
import maze.Room;
import maze.Wall;
import maze.ui.MazeViewer;

public class BlueMazeGameCreator extends MazeGameCreator {
	
	@Override
	public BlueWall makeWall() {
		BlueWall newWall = new BlueWall();
		return newWall;
	}
	
	@Override
	public GreenRoom makeRoom(int roomNumber) {
		return new GreenRoom(roomNumber);
	}
	
	@Override
	public BrownDoor makeDoor(Room room1, Room room2) {
		return new BrownDoor(room1,room2);
	}
}
