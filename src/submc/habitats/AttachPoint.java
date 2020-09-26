package submc.habitats;

import java.util.Objects;

import org.bukkit.Location;

import submc.utils.Loc;

public class AttachPoint {

	private Loc loc;
	private Direction direction;
	
	public AttachPoint(Loc l, Direction direction) {
		this.loc = l;
		this.direction = direction;
	}
	
	public Loc getLoc() {
		return this.loc;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public int hashCode() {
		return Objects.hash(loc, direction);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Object) {
			AttachPoint ap = (AttachPoint)o;
			return this.loc.equals(ap.loc);
		}
		return false;
	}
	
}