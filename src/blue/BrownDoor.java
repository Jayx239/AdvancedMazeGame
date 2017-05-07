package blue;

import java.awt.Color;

import maze.Door;
import maze.Room;

public class BrownDoor extends Door {

	public BrownDoor(Room r1, Room r2) {
		super(r1, r2);
	}
	
	@Override
	public Color getColor() {
		return new Color(139,69,19);	// no brown color
	}

}
