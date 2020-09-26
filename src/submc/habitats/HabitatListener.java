package submc.habitats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import submc.SubMC;
import submc.habitats.pieces.BasePieceType;
import submc.utils.ItemUtils;

public class HabitatListener implements Listener{

	@EventHandler
	public void onHabitatBuilder(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
		if(e.getItem() != null && ItemUtils.isSimilar(e.getItem(), ItemUtils.getHabitatBuilder(1))) {
			if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
				 Block b = e.getClickedBlock();
				 //TODO IF CLICKED BLOCK IS ATTACH POINT
				 if(HabitatBuilder.hasSelected(p)) {
					 if(SubMC.getHabitatManager().hasHabitat(p)) {
						 Habitat h = SubMC.getHabitatManager().getHabitat(p);
						 if(h.isAttachPoint(b)) {
							 
						 }else {
							 p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder().color(net.md_5.bungee.api.ChatColor.RED).append("##########").create());
							 //play sound?
						 }
					 }else {
						 
					 }
					 
					 if(HabitatBuilder.getSelected(p).canPlace(b.getLocation(), Direction.valueOf(e.getBlockFace()))) {
						 //build habitat
						 p.sendMessage("can place");
					 }
				 }
			}else if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
				openHabitatBuilder(p);
			}
		}
	}

	private static final String TITLE = ChatColor.BLUE + "Habitat Builder";
	
	public void openHabitatBuilder(Player p) {
		Inventory habitatBuilder = Bukkit.createInventory(null, 27, TITLE);
		habitatBuilder.setItem(0, ItemUtils.addLore(ItemUtils.easyItemStack("Basic Compartment", Material.IRON_INGOT, "", 1), 
				ChatColor.AQUA + "------------",
				ChatColor.GREEN + "2 x " + ChatColor.WHITE + "Titanium"
		));
		p.openInventory(habitatBuilder);
	}

	@EventHandler
	public void onPlayerInteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equals(TITLE)){
			if(e.getClickedInventory() == null)return;
			if(e.getClickedInventory().getType() == InventoryType.PLAYER)return;
			e.setCancelled(true);
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR){
				int slot = e.getSlot();
				p.closeInventory();
				if(slot == 0) {
					HabitatBuilder.setSelected(p, BasePieceType.BASIC_COMPARTMENT);
				}
				
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if(e.getView().getTitle().equals(TITLE)){
			if(HabitatBuilder.hasSelected(p)) {
				HabitatBuilder.removeSelected(p);
			}
		}
	}
	
}