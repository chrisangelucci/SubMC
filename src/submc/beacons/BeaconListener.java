package submc.beacons;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import submc.SubMC;
import submc.utils.ItemUtils;

public class BeaconListener implements Listener{

	@EventHandler
	public void onBeaconPlace(PlayerInteractEvent e) {
		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
		if(e.getAction() == Action.RIGHT_CLICK_AIR) {
			if(e.getItem() != null && ItemUtils.isSimilar(e.getItem(), ItemUtils.getBeacon(1))) {
				Block below = e.getPlayer().getLocation().add(0,-1,0).getBlock();
				if(below.getType() == Material.WATER) {
					SubMC.getBeaconManager().addBeacon("beacon", below.getLocation());
					below.setType(Material.BEACON);
					for(int i = 0; i<e.getPlayer().getInventory().getSize(); i++) {
						if(e.getPlayer().getInventory().getItem(i) == null)continue;
						if(ItemUtils.isSimilar(e.getPlayer().getInventory().getItem(i), ItemUtils.getBeacon(1))) {
							e.getPlayer().getInventory().setItem(i, new ItemStack(Material.AIR));
							e.getPlayer().updateInventory();
							return;
						}
					}
				}
			}
		}else if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.BEACON) {
				e.getClickedBlock().setType(Material.WATER);
				SubMC.getBeaconManager().removeBeacon(e.getClickedBlock().getLocation());
				e.getPlayer().getInventory().addItem(ItemUtils.giveUniqueUUID(ItemUtils.getBeacon(1)));
				e.getPlayer().updateInventory();
				e.setCancelled(true);
			}
		}
	}
	
}
