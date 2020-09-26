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

public class FabricatorResources implements Listener{

	private static final String TITLE = ChatColor.BLUE + "Resources";
	
	public static void openCategory(Player p){
		Inventory resources = Bukkit.createInventory(null, 27, TITLE);
		resources.setItem(0, ItemUtils.addLore(ItemUtils.getTitaniumIngot(1), 
			ChatColor.AQUA + "------------", 
			ChatColor.GREEN + "10 x " + ChatColor.WHITE + "Titanium"
		));
		resources.setItem(1, ItemUtils.addLore(ItemUtils.getSiliconeRubber(1), 
			ChatColor.AQUA + "------------", 
			ChatColor.GREEN + "1 x " + ChatColor.WHITE + "Creepvine Seed Cluster"
		));
		resources.setItem(2, ItemUtils.addLore(ItemUtils.getWiringKit(1), 
			ChatColor.AQUA + "------------", 
			ChatColor.GREEN + "2 x " + ChatColor.WHITE + "Silver Ore"
		));
		resources.setItem(3, ItemUtils.addLore(ItemUtils.getCopperWire(1), 
			ChatColor.AQUA + "------------", 
			ChatColor.GREEN + "2 x " + ChatColor.WHITE + "Copper Ore"
		));
		resources.setItem(4, ItemUtils.addLore(ItemUtils.getComputerChip(1), 
			ChatColor.AQUA + "------------", 
			ChatColor.GREEN + "2 x " + ChatColor.WHITE + "Table Coral Sample",
			ChatColor.GREEN + "1 x " + ChatColor.WHITE + "Gold",
			ChatColor.GREEN + "1 x " + ChatColor.WHITE + "Copper Wire"
		));
		resources.setItem(5, ItemUtils.addLore(ItemUtils.getBattery(1), 
			ChatColor.AQUA + "------------", 
			ChatColor.GREEN + "2 x " + ChatColor.WHITE + "Acid Mushroom",
			ChatColor.GREEN + "1 x " + ChatColor.WHITE + "Copper Ore"
		));
		p.openInventory(resources);
	}
	
	@EventHandler
	public void resourcesClick(InventoryClickEvent e){
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
