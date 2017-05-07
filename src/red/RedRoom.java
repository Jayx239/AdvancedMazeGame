package red;

import java.awt.Color;

import maze.Room;

public class RedRoom extends Room{

	public RedRoom(int num) {
		super(num);
		
	}
	
	private Color Red = new Color(255, 230, 230);
	
	@Override
	public Color getColor() {
		return Red;
	}

}
