package submc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class GameruleDefiner implements Listener{

	@EventHandler
	public void onWorldInit(WorldInitEvent e) {
		Bukkit.broadcastMessage("Wrodl init");
		e.getWorld().setGameRule(GameRule.NATURAL_REGENERATION, false);
	}
	
}
