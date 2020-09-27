package submc.utils.rays;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Ray {

	private Vector origin;
	private Vector direction;
	
	public Ray(Vector origin, Vector direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	public Ray(Location start, Location end) {
		this(start.toVector(), getDirectionBetweenLocations(start, end));
	}
	
	private static Vector getDirectionBetweenLocations(Location start, Location end) {
        Vector from = start.toVector();
        Vector to = end.toVector();
        return to.subtract(from);
    }
	
	public Vector getPosition(double distance) {
		return origin.clone().add(direction.clone().multiply(distance));
	}
	
}
