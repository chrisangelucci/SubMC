package submc.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Loc implements Serializable{
	private static final long serialVersionUID = -1739875531166036001L;
	
	private String world;
	private double x;
	private double y;
	private double z;
	
	public Loc(Location l) {
		this.world = l.getWorld().getName();
		this.x = l.getX();
		this.y = l.getY();
		this.z = l.getZ();
	}
	
	public Location getLocation() {
		return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
	}
	
	public int hashCode() {
		return Objects.hash(world, x, y, z);
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Loc))return false;
		Loc l = (Loc)o;
		return this.world.equals(l.world) && this.x == l.x && this.y == l.y && this.z == l.z;
	}
	
	public static List<Loc> getLocs(List<Location> locations) {
		List<Loc> locs = new ArrayList<Loc>();
		for(Location l : locations)
			locs.add(new Loc(l));
		return locs;
	}
	
}
