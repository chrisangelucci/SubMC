package submc.beacons;

import java.io.Serializable;

import org.bukkit.Location;

import submc.utils.Loc;

public class BeaconPoint implements Serializable{
	private static final long serialVersionUID = 8422157709964596695L;
	
	private String name;
	private Loc location;
	
	public BeaconPoint(String name, Location location) {
		this.name = name;
		this.location = new Loc(location);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Location getLocation() {
		return this.location.getLocation();
	}
	
	public boolean isLocated(Location l) {
		return location.equals(new Loc(l));
	}
	
}
