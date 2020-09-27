package submc.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import submc.utils.ItemUtils;

public class LifepodCreator implements Listener{

	private static Location lifepodLocation;

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
//		if(!SubMC.lifepodCreated) {
//			lifepodLocation = Bukkit.getWorld("world").getSpawnLocation().add(6, 5, 6);
//			StructureLoader.createStructure("lifepod", lifepodLocation.clone().add(-6,-1,-6), EnumBlockRotation.NONE);
//			lifepodLocation.add(-0.5,3,-0.5);
//			SubMC.lifepodCreated = true;
//			e.getPlayer().teleport(lifepodLocation);
//			SubMC.getBeaconManager().addBeacon("Lifepod 5", lifepodLocation);
//		}
		e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false));
		e.getPlayer().getInventory().addItem(ItemUtils.giveUniqueUUID(ItemUtils.getHabitatBuilder(1)));
	}

}