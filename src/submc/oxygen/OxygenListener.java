package submc.oxygen;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class OxygenListener implements Listener{

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntityType() == EntityType.PLAYER)
			if(e.getCause() == DamageCause.DROWNING)
				e.setCancelled(true);
	}
	
}
