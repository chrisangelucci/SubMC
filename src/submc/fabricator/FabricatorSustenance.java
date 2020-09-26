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

public class FabricatorSustenance implements Listener{

	private static final String TITLE = ChatColor.BLUE + "Sustenance";
	
	public static void openCategory(Player p){
		Inventory sustenance = Bukkit.createInventory(null, 27, TITLE);
		sustenance.setItem(9, ItemUtils.addLore(ItemUtils.getCookedBladderfish(1), ChatColor.AQUA + "------------", ChatColor.GREEN + "1 x " + ChatColor.WHITE + "Bladderfish"));
		p.openInventory(sustenance);
	}
	
	@EventHandler
	public void sustenanceClick(InventoryClickEvent e){
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
