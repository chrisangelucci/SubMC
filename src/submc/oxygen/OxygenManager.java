package submc.oxygen;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import submc.SubMC;

public class OxygenManager {

	private SubMC plugin;

	private HashMap<UUID, Integer> oxygen;

	public OxygenManager(SubMC plugin) {
		this.plugin = plugin;
		this.oxygen = new HashMap<UUID, Integer>();
		plugin.getServer().getPluginManager().registerEvents(new OxygenListener(), plugin);
		startOxygen();
	}

	public HashMap<UUID, Integer> getOxygen(){
		return this.oxygen;
	}
	
	public void setOxygen(HashMap<UUID, Integer> oxygen) {
		this.oxygen = oxygen;
	}
	
	private int getOxygen(Player p) {
		if(!oxygen.containsKey(p.getUniqueId()))
			oxygen.put(p.getUniqueId(), getMaxOxygen(p));
		return oxygen.get(p.getUniqueId());
	}
	
	private boolean removeOxygen(Player p, int amount) {
		setOxygen(p, Math.max(0, getOxygen(p) - amount));
		return getOxygen(p) == 0;
	}
	
	private void addOxygen(Player p, int amount) {
		setOxygen(p, Math.min(getMaxOxygen(p), getOxygen(p) + amount));
	}
	
	private void setOxygen(Player p, int amount) {
		oxygen.put(p.getUniqueId(), amount);
	}
	
	//TODO Allow this value to change using different oxygen tanks.
	private int getMaxOxygen(Player p) {
		return 45;
	}

	private void startOxygen() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getEyeLocation().getBlock().getType() == Material.WATER) {
						if(removeOxygen(p, 1)) {
							p.damage(2);
						}
					} else {
						addOxygen(p, 5);
					}
					p.setLevel(getOxygen(p));
					p.setExp(getOxygen(p)/Float.valueOf(getMaxOxygen(p)));
				}
			}
		}.runTaskTimer(plugin, 1, 20);
	}

}