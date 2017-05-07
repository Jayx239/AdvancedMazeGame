package red;

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

public class RedMazeGameCreator extends MazeGameCreator {
	
	@Override
	public RedWall makeWall() {
		RedWall newWall = new RedWall();
		return newWall;
	}
	
	@Override
	public RedRoom makeRoom(int roomNumber) {
		return new RedRoom(roomNumber);
	}
	
	@Override
	public Door makeDoor(Room room1, Room room2) {
		return new Door(room1,room2);
	}
}
