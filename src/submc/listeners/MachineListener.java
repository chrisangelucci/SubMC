package submc.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import submc.fabricator.FabricatorMain;

public class MachineListener implements Listener{

	@EventHandler
	public void fabricatorClick(PlayerInteractEvent e) {
		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getType() == Material.COBBLESTONE_WALL){
				if(!e.getPlayer().isSneaking()){
					FabricatorMain.openFabricator(e.getPlayer());
				}else{
					//destory w constructor?
					
				}
			}
		}
	}
	
}
