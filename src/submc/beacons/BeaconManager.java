package submc.beacons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import submc.SubMC;
import submc.utils.rays.Ray;

public class BeaconManager {

	private List<BeaconPoint> beacons;
	
	private SubMC plugin;
	
	public BeaconManager(SubMC plugin) {
		this.plugin = plugin;
		this.beacons = new ArrayList<BeaconPoint>();
		plugin.getServer().getPluginManager().registerEvents(new BeaconListener(), plugin);
		displayBeacons();
	}
	
	public List<BeaconPoint> getBeacons(){
		return beacons;
	}
	
	public void setBeacons(List<BeaconPoint> newBeaconsList) {
		beacons = newBeaconsList;
	}
	
	public void addBeacon(String name, Location l) {
		beacons.add(new BeaconPoint(name, l));
	}
	
	public void removeBeacon(Location l) {
		for(int i = 0; i<beacons.size(); i++) {
			if(beacons.get(i).isLocated(l)){
				beacons.remove(i);
				return;
			}
		}
	}
	
	//TODO Change this to store holograms, and then teleport them on a short interval, for a seamless look.
	private void displayBeacons() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					for(BeaconPoint beacon : beacons) {
						if(p.getEyeLocation().distanceSquared(beacon.getLocation()) < 100)continue;
						Location bIcon = new Ray(p.getEyeLocation(), beacon.getLocation()).getPosition(5).toLocation(p.getWorld());
						Hologram h = HologramsAPI.createHologram(plugin, bIcon);
						h.getVisibilityManager().showTo(p);
						h.getVisibilityManager().setVisibleByDefault(false);
						h.appendTextLine(beacon.getName());
						new BukkitRunnable() {
							@Override
							public void run() {
								h.delete();
							}
						}.runTaskLater(plugin, 10);
					}
				}
			}
		}.runTaskTimer(plugin, 1, 10);
	}
	
}