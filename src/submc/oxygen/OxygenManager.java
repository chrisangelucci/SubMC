package submc.oxygen;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import submc.SubMC;

public class OxygenManager {

	private SubMC plugin;

	private HashMap<String, Integer> oxygen;

	public OxygenManager(SubMC plugin) {
		this.plugin = plugin;
		this.oxygen = new HashMap<String, Integer>();
		plugin.getServer().getPluginManager().registerEvents(new OxygenListener(), plugin);
		startOxygen();
	}

	public HashMap<String, Integer> getOxygen(){
		return this.oxygen;
	}
	
	public void setOxygen(HashMap<String, Integer> oxygen) {
		this.oxygen = oxygen;
	}
	
	private int getOxygen(Player p) {
		if(!oxygen.containsKey(p.getName()))
			oxygen.put(p.getName(), getMaxOxygen(p));
		return oxygen.get(p.getName());
	}
	
	private boolean removeOxygen(Player p, int amount) {
		setOxygen(p, Math.max(0, getOxygen(p) - amount));
		return getOxygen(p) == 0;
	}
	
	private void addOxygen(Player p, int amount) {
		setOxygen(p, Math.min(getMaxOxygen(p), getOxygen(p) + amount));
	}
	
	private void setOxygen(Player p, int amount) {
		oxygen.put(p.getName(), amount);
	}
	
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