package submc.fabricator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import submc.SubMC;

public class FabricatorMain implements Listener {

	private static final String TITLE = ChatColor.BLUE + "Fabricator";

	public FabricatorMain(SubMC plugin) {
		plugin.getServer().getPluginManager().registerEvents(new FabricatorResources(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new FabricatorSustenance(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new FabricatorPersonal(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new FabricatorDeployables(), plugin);
	}

	public static void openFabricator(Player p) {
		Inventory fabricator = Bukkit.createInventory(null, 9, TITLE);
		fabricator.setItem(1, getResourcesItem());
		fabricator.setItem(3, getSustenanceItem());
		fabricator.setItem(5, getPersonalItem());
		fabricator.setItem(7, getDeployablesItem());
		p.openInventory(fabricator);
	}

	@EventHandler
	public void fabricatorClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().equals(TITLE)){
			e.setCancelled(true);
			if(e.getCurrentItem() != null){
				if(e.getCurrentItem().isSimilar(getResourcesItem())){
					FabricatorResources.openCategory(p);
				}
				if(e.getCurrentItem().isSimilar(getSustenanceItem())){
					FabricatorSustenance.openCategory(p);
				}
				if(e.getCurrentItem().isSimilar(getPersonalItem())){
					FabricatorPersonal.openCategory(p);
				}
				if(e.getCurrentItem().isSimilar(getDeployablesItem())){
					FabricatorDeployables.openCategory(p);
				}
			}
		}
	}

	private static ItemStack getResourcesItem() {
		ItemStack resources = new ItemStack(Material.IRON_NUGGET);
		ItemMeta im = resources.getItemMeta();
		im.setDisplayName(ChatColor.RESET + "Resources");
		resources.setItemMeta(im);
		return resources;
	}

	private static ItemStack getSustenanceItem() {
		ItemStack sustenance = new ItemStack(Material.COD);
		ItemMeta im = sustenance.getItemMeta();
		im.setDisplayName(ChatColor.RESET + "Sustenance");
		sustenance.setItemMeta(im);
		return sustenance;
	}

	private static ItemStack getPersonalItem() {
		ItemStack personal = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta im = personal.getItemMeta();
		im.setDisplayName(ChatColor.RESET + "Personal");
		personal.setItemMeta(im);
		return personal;
	}

	private static ItemStack getDeployablesItem() {
		ItemStack deployables = new ItemStack(Material.PISTON);
		ItemMeta im = deployables.getItemMeta();
		im.setDisplayName(ChatColor.RESET + "Deployables");
		deployables.setItemMeta(im);
		return deployables;
	}

}
