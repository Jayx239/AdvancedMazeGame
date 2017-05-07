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

import red.RedMazeFactory;
import blue.BlueMazeFactory;
import blue.BlueWall;
import maze.ui.MazeViewer;

/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class MazeFactory extends AbstractMazeFactory
{
	public MazeFactory() {
		super(null);
	}
	
	public MazeFactory(String filePath) {
		super(filePath);
	}

	/**
	 * Creates a small maze.
	 */
	
	/* Adapter methods for creating new maze items */
	@Override
	public Maze makeMaze() {
		return new Maze();
	}
	
	@Override
	public Wall makeWall() {
		Wall newWall = new BlueWall();
		return newWall;
	}
	
	@Override
	public Room makeRoom(int roomNumber) {
		return new Room(roomNumber);
	}
	
	@Override
	public Door makeDoor(Room room1, Room room2) {
		return new Door(room1,room2);
	}
	
	public static void main(String[] args)
	{
		/* parameter one indicates the color of the maze
		 * parameter two is optional and is the filepath to a maze
		 * configuration file
		 */
		MazeFactory mazeFactory = new MazeFactory();
		
		if(args.length >= 1)
			switch(args[0]) {
			case "red":
				mazeFactory = new RedMazeFactory();
				break;
			case "blue":
				mazeFactory = new BlueMazeFactory();
			default:
				break;
			}
		
		Maze maze = null;
		if(args.length <= 1)
			maze = mazeFactory.createMaze();
		else
			try {
				maze = mazeFactory.loadMaze(args[1]);
			} catch (IOException e) {
				System.err.println("Error generating maze from file!");
				e.printStackTrace();
			}
	    
		MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
	}
}
