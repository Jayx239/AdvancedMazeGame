package maze;

import java.io.IOException;

import maze.ui.MazeViewer;
import red.RedMazeFactory;
import blue.BlueMazeFactory;

public class MazeGame {
	public static Maze createMaze(AbstractMazeFactory mazeFactory) throws IOException{
		Maze maze = null;
		
		if(mazeFactory.filePath != null)
			return mazeFactory.loadMaze(mazeFactory.getFilePath());
		else
			return mazeFactory.createMaze();
		
	}
	
	public static void main(String args[]) {
		
		AbstractMazeFactory mazeFactory = new MazeFactory();
		Maze maze = null;
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
		if(args.length >= 2) {
			mazeFactory.setFilePath(args[1]);
		}
		
		try{
			maze = MazeGame.createMaze(mazeFactory);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	    
		MazeViewer viewer = new MazeViewer(maze);
	    viewer.run();
		
	}
}
