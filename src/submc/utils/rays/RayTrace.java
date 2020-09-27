package submc.utils.rays;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.Vector;

public class RayTrace {

	private Ray ray;
	
	public RayTrace(Vector origin, Vector direction) {
        this(new Ray(origin, direction));
    }
	
	public RayTrace(Ray ray) {
        this.ray = ray;
    }
	
	public List<Vector> traverse(double maxBlocks, double accuracy) {
		List<Vector> positions = new ArrayList<Vector>();
		for(double d = 0; d <= maxBlocks; d+=accuracy) {
			positions.add(this.ray.getPosition(d));
		}
		return positions;
	}
	
}
