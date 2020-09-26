package submc.listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import submc.utils.ItemUtils;

public class HarvestableListener implements Listener{

	@EventHandler
	public void onHarvest(PlayerInteractEvent e) {
		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Material type = e.getClickedBlock().getType();
			if(type == Material.DEAD_TUBE_CORAL) { //SANDSTONE
				e.getClickedBlock().setType(Material.AIR);
				e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation(), ItemUtils.giveUniqueUUID(ItemUtils.getRandomSandstoneDrop())).setVelocity(new Vector());
			}else if(type == Material.DEAD_BRAIN_CORAL) { //LIMESTONE
				e.getClickedBlock().setType(Material.AIR);
				e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation(), ItemUtils.giveUniqueUUID(ItemUtils.getRandomLimestoneDrop())).setVelocity(new Vector());
			}else if(type == Material.DEAD_HORN_CORAL) { //SHALE
				e.getClickedBlock().setType(Material.AIR);
				e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation(), ItemUtils.giveUniqueUUID(ItemUtils.getRandomShaleDrop())).setVelocity(new Vector());
			}
		}else if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.HORN_CORAL_BLOCK){
				e.getClickedBlock().setType(Material.KELP_PLANT);
				if(e.getClickedBlock().getRelative(BlockFace.DOWN).getType()==Material.KELP){
					e.getClickedBlock().getRelative(BlockFace.DOWN).setType(Material.KELP_PLANT);
				}
				e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation(), ItemUtils.giveUniqueUUID(ItemUtils.getSeedCluster(1)));
			}else if(e.getClickedBlock().getType() == Material.DIORITE_SLAB) {
				e.getClickedBlock().setType(Material.AIR);
				e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation(), ItemUtils.giveUniqueUUID(ItemUtils.getSaltDeposit(1)));
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e){
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onKnifeClickBlock(PlayerInteractEvent e) {
		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getItem() != null && ItemUtils.isSimilar(e.getItem(), ItemUtils.getKnife(1))) {
				if(e.getClickedBlock().getType() == Material.KELP_PLANT) { 
					e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation(), ItemUtils.giveUniqueUUID(ItemUtils.getCreepvineSample(1)));
				}else if(e.getClickedBlock().getType() == Material.TUBE_CORAL) {
					e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation(), ItemUtils.giveUniqueUUID(ItemUtils.getCoralTubeSample(1)));
				}else if(e.getClickedBlock().getType() == Material.FIRE_CORAL_FAN) {
					int amount = new Random().nextInt(1)+1;
					e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation(), ItemUtils.giveUniqueUUID(ItemUtils.getTableCoralSample(amount)));
					e.getClickedBlock().setType(Material.AIR);
				}
			}
		}
	}

	@EventHandler
	public void onFishRightClick(PlayerInteractEntityEvent e){
		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
		if(e.getRightClicked().getType() == EntityType.PUFFERFISH){
			e.getRightClicked().getWorld().dropItem(e.getRightClicked().getLocation(), ItemUtils.giveUniqueUUID(ItemUtils.getBladderfish(1)));
			e.getRightClicked().remove();
		}
	}

}
