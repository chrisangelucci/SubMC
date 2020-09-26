package submc;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Stats {

	private static HashMap<String, Integer> playerWater = new HashMap<String, Integer>();
	
	public static int getWater(Player p){
		return playerWater.get(p.getName());
	}
	
	public static void setWater(Player p, int water){
		playerWater.put(p.getName(), water);
	}
	
	public static void addWater(Player p, int water){
		setWater(p, Math.min(getWater(p)+water, 20));
	}
	
	public static void removeWater(Player p, int water){
		setWater(p, Math.max(getWater(p)-water, 0));
	}
}
