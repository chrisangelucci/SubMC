package submc.structures;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class MatVec {

	private Material m;
	private Vector v;
	
	public MatVec(Material m, int x, int y, int z) {
		this.m = m;
		this.v = new Vector(x, y, z);
	}
	
	public MatVec(Material m, Vector v) {
		this.m = m;
		this.v = v;
	}
	
	public Material getMaterial() {
		return this.m;
	}
	
	public Vector getRelativeVector() {
		return this.v;
	}
	
	public Location getAbsoluteLocation(Location center) {
		return this.v.add(center.toVector()).toLocation(center.getWorld());
	}
	
	public MatVec clone() {
		return new MatVec(this.m, this.v);
	}
	
}
