package submc.habitats;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public enum Direction {
	NORTH(0,0,-1),SOUTH(0,0,1),EAST(1,0,0),WEST(-1,0,0);

	private Vector change;
	
	Direction(int xChange, int yChange, int zChange) {
		this.change = new Vector(xChange, yChange, zChange);
	}
	
	public Vector getDirection() {
		return this.change;
	}
	
	public static Direction valueOf(BlockFace bf) {
		switch(bf) {
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
				return Direction.valueOf(bf.toString());
			default:
				return null;
		}
		
	}
}
