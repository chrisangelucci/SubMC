package submc.listeners;

import java.util.Arrays;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class SpawnBlocker implements Listener{

	@EventHandler
	public void onSpawn(EntitySpawnEvent e){
		EntityType[] canSpawn = {EntityType.DROPPED_ITEM, EntityType.COD,EntityType.SALMON,EntityType.PUFFERFISH,EntityType.TROPICAL_FISH};
		if(e.getEntityType() == EntityType.DROWNED){
			e.setCancelled(true);
			e.getEntity().getWorld().spawnEntity(e.getLocation(), EntityType.PUFFERFISH);
			return;
		}
		if(!Arrays.asList(canSpawn).contains(e.getEntityType())){
			e.setCancelled(true);
		}
	}
	
}
