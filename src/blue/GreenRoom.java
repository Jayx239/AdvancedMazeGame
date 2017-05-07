package blue;

import java.awt.Color;

import maze.Room;

public class GreenRoom extends Room {

	public GreenRoom(int num) {
		super(num);
	}
	
	@Override
	public Color getColor() {
		return Color.GREEN;
	}

}
