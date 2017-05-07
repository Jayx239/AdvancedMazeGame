/*
 * SimpleMazeGame.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import red.RedMazeGameCreator;
import blue.BlueMazeGameCreator;
import maze.ui.MazeViewer;

/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class MazeGameCreator
{
	/**
	 * Creates a small maze.
	 */
	

	/* Key words */
	protected static String wallWord = "wall";
	protected static char doorChar = 'd';
	protected static String openWord = "open";
	
	/* Parameter indexes*/
	protected static int paramItemType = 0;
	protected static int paramIdNumber = 1;
	protected static int paramNorthIndex = 2;
	protected static int paramSouthIndex = 3;
	protected static int paramEastIndex = 4;
	protected static int paramWestIndex = 5;
	protected static int doorRoom1Index = 2;
	protected static int doorRoom2Index = 3;
	protected static int doorOpenIndex = 4;
	protected static int defaultStartRoom = 0;
	
	/* Default create maze method */
	public Maze createMaze()
	{
		
		Maze maze = makeMaze();
		Room room1 = getOrCreateRoom(maze,"0");
		Room room2 = getOrCreateRoom(maze, "1");
		insertDoor(room1,room2,makeDoor(room1,room2),Direction.West);
		
		maze.setCurrentRoom(1);
		return maze;
		
	}

	/* Method to load maze from file if cmd line parameter of the file path is entered */
	public Maze loadMaze(final String path) throws IOException
	{
		Maze maze = makeMaze();
		
		BufferedReader fileReader = new BufferedReader(new FileReader(path));
		String line;
		while((line = fileReader.readLine()) != null) {
			if(line.equals("\n"))
				continue;
			
			String[] params = line.split(" ");
			
			switch(params[paramItemType]) {
			case "room":
				addRoom(maze,params);
				break;
			case "door":
				makeDoor(maze,params);
				break;
			}
		}
		
		maze.setCurrentRoom(defaultStartRoom);
		System.out.println("Maze loaded from file!");
		return maze;
	}
	
	/* Adapter methods for creating new maze items */
	public Maze makeMaze() {
		return new Maze();
	}
	
	public Wall makeWall() {
		Wall newWall = new Wall();
		return newWall;
	}
	
	public Room makeRoom(int roomNumber) {
		return new Room(roomNumber);
	}
	
	public Door makeDoor(Room room1, Room room2) {
		return new Door(room1,room2);
	}
	
	/* Method for inserting door between two rooms */
	protected void insertDoor(Room room1, Room room2, Door door, Direction room1To2) {
		room1.setSide(room1To2, door);
		
		if(room1To2.equals(Direction.North))
			room2.setSide(Direction.South, door);
		else if(room1To2.equals(Direction.South))
			room2.setSide(Direction.North, door);
		else if(room1To2.equals(Direction.East))
			room2.setSide(Direction.West, door);
		else
			room2.setSide(Direction.East, door);

	}
	
	/* Method for making new door from input file parameters */
	protected void makeDoor(Maze maze, String[] params) {
		int room1Number = Integer.parseInt(params[doorRoom1Index]);
		int room2Number = Integer.parseInt(params[doorRoom2Index]);
		boolean isOpen = params[doorOpenIndex].toLowerCase().equals(openWord);
		
		Room room1 = maze.getRoom(room1Number);
		Room room2 = maze.getRoom(room2Number);
		
		Door newDoor = makeDoor(room1,room2);
		
		for(Direction direction : Direction.values()) {
			if(room1.getSide(direction) == null) {
				insertDoor(room1,room2,newDoor,direction);
				break;
			}

		}
		
		newDoor.setOpen(isOpen);
	}
	
	/* Method for adding new room from cmd line parameters */
	protected void addRoom(Maze maze, String[] params) {
		Room newRoom = getOrCreateRoom(maze,params[paramIdNumber]);
		
		if(sideIsWall(params[paramNorthIndex]))
			newRoom.setSide(Direction.North, makeWall());
		else if(sideIsRoom(params[paramNorthIndex]))
			setSideRoom(maze,newRoom,params[paramNorthIndex],Direction.North);
			
		if(sideIsWall(params[paramSouthIndex]))
			newRoom.setSide(Direction.South, makeWall());
		else if(sideIsRoom(params[paramSouthIndex]))
			setSideRoom(maze,newRoom,params[paramSouthIndex],Direction.South);
		
		if(sideIsWall(params[paramEastIndex]))
			newRoom.setSide(Direction.East, makeWall());
		else if(sideIsRoom(params[paramEastIndex]))
			setSideRoom(maze,newRoom,params[paramEastIndex],Direction.East);
		
		if(sideIsWall(params[paramWestIndex]))
			newRoom.setSide(Direction.West, makeWall());
		else if(sideIsRoom(params[paramWestIndex]))
			setSideRoom(maze,newRoom,params[paramWestIndex],Direction.West);
		
		
	}
	
	/* Method for determining whether side is a wall */
	protected boolean sideIsWall(String param) {
		return param.equals(wallWord);
	}
	
	/* Method for determining whether side is attached to another room */
	protected boolean sideIsRoom(String param) {
		return !sideIsWall(param) && param.toLowerCase().charAt(0) != doorChar;
	}
	
	/* Method to set a rooms side to be a room */
	protected void setSideRoom(Maze maze, Room newRoom, String sideRoomNumberString, Direction direction) {
		Room sideRoom = getOrCreateRoom(maze, sideRoomNumberString);
		newRoom.setSide(direction, sideRoom);
	}
	
	/* Get a room if it exists or create it */
	protected Room getOrCreateRoom(Maze maze, String roomNumberString) {
		int roomNumber = Integer.parseInt(roomNumberString);
		Room room = maze.getRoom(roomNumber);
		room = room != null ? room : makeRoom(roomNumber);
		maze.addRoom(room);
		return room;
	}

	public static void main(String[] args)
	{
		/* parameter one indicates the color of the maze
		 * parameter two is optional and is the filepath to a maze
		 * configuration file
		 */
		MazeGameCreator gameCreator = new MazeGameCreator();
		
		if(args.length >= 1)
			switch(args[0]) {
			case "red":
				gameCreator = new RedMazeGameCreator();
				break;
			case "blue":
				gameCreator = new BlueMazeGameCreator();
			default:
				break;
			}
		
		Maze maze = null;
		if(args.length <= 1)
			maze = gameCreator.createMaze();
		else
			try {
				maze = gameCreator.loadMaze(args[1]);
			} catch (IOException e) {
				System.err.println("Error generating maze from file!");
				e.printStackTrace();
			}
	    
		MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
	}
}
