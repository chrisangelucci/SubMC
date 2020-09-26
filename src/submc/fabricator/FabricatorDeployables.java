package submc.fabricator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import submc.utils.ItemUtils;

public class FabricatorDeployables implements Listener {

	private static final String TITLE = ChatColor.BLUE + "Deployables";

	public static void openCategory(Player p){
		Inventory resources = Bukkit.createInventory(null, 27, TITLE);
		resources.setItem(0, ItemUtils.addLore(ItemUtils.getBeacon(1), ChatColor.AQUA + "------------", ChatColor.GREEN + "1 x " + ChatColor.WHITE + "Copper Ore", ChatColor.GREEN + "1 x " + ChatColor.WHITE + "Titanium"));
		p.openInventory(resources);
	}

	@EventHandler
	public void personalClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equals(TITLE)){
			if(e.getClickedInventory() == null)return;
			if(e.getClickedInventory().getType() == InventoryType.PLAYER)return;
			e.setCancelled(true);
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR){
				FabricatorRecipe.fabricate(p, e.getCurrentItem());
			}
		}
	}
	
}
