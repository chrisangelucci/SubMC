package submc;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

public class ItemSinker {

	public static void sinkItems(SubMC plugin){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run(){
				List<Entity> entities = Bukkit.getWorlds().get(0).getEntities();
				for(Entity item : entities){
					if (item instanceof Item){
						item.setVelocity(new Vector(item.getVelocity().getX(), item.getVelocity().getY() - 0.01D, item.getVelocity().getZ()));
					}
				}
			}
		}, 1, 1);
	}
}
