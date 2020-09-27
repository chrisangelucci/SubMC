package submc.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import submc.SubMC;
import submc.base.pieces.BasePiece;
import submc.base.pieces.BasePiece.Type;
import submc.structures.Anchor;
import submc.structures.Structure;
import submc.utils.ItemUtils;
import submc.utils.rays.RayTrace;

public class HabitatBuilderManager implements Listener{

	//Shows a ghost only when placing first base piece.
	private static HashMap<String, GhostPiece> placingFirstBasePiece = new HashMap<String, GhostPiece>();

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
		if(e.getItem() != null && ItemUtils.isSimilar(e.getItem(), ItemUtils.getHabitatBuilder(1))) {
			if(!Base.hasBase(p)) {
				if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if(placingFirstBasePiece.containsKey(p.getName())) {
						Location l = getPlaceLocation(p).getBlock().getLocation();
						if(!obstructed(p,l)) {
							GhostPiece gp = placingFirstBasePiece.get(p.getName());
							gp.structureType.getStructure().place(l, gp.rotated);
							placingFirstBasePiece.remove(p.getName());
							Base base = new Base(p);
							base.addBasePiece(gp.structureType, l, gp.rotated);
							p.sendMessage("You have made a new base!");
						}
					}else {
						openHabitatBuilder(p);
					}
				}
			}else {
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
					Base b = Base.getBase(p);
					if(b.isAnchor(e.getClickedBlock())) {
						Anchor a = b.getAnchor(e.getClickedBlock());
						if(a.bf == e.getBlockFace()) {
							//if has something to build, that will work at this point
							
							return;
						}
					}
				}
			}
		}
	}
	
	private static final String TITLE = ChatColor.BLUE + "Habitat Builder";
	
	private void openHabitatBuilder(Player p) {
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
					HabitatBuilder.setSelected(p, BasePiece.Type.BASIC);
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

	@EventHandler
	public void PlayerItemHeldEvent(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		if(placingFirstBasePiece.containsKey(p.getName())) {
			if(p.getInventory().getItemInMainHand().getType() == Material.STICK) {
				if(e.getNewSlot() == e.getPreviousSlot())return;
				e.setCancelled(true);
				GhostPiece gp = placingFirstBasePiece.get(p.getName());
				gp.rotated = !gp.rotated;
			}
		}
	}

	public static void showOutlines(SubMC plugin) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(String s : placingFirstBasePiece.keySet()) {
					Player p = Bukkit.getPlayer(s);
					if(p!=null)
						CAlgorithm.drawOutline(p, placingFirstBasePiece.get(p.getName()), getPlaceLocation(p));
				}
			}
		}.runTaskTimer(plugin, 1, 5);
	}

	private static boolean obstructed(Player p, Location center) {
		GhostPiece gp = placingFirstBasePiece.get(p.getName());
		Structure s = gp.structureType.getStructure();
		for(Location l : s.getLocations(center, gp.rotated)) {
			if(!isTransparent(l.getBlock().getType())) {
				return true;
			}
		}
		return false;
	}

	private static boolean isTransparent(Material m) {
		Material[] mats = {Material.AIR, Material.WATER};
		return Arrays.asList(mats).contains(m);
	}

	public static Location getPlaceLocation(Player p) {
		RayTrace rayTrace = new RayTrace(p.getEyeLocation().toVector(), p.getEyeLocation().getDirection());
		List<Vector> positions = rayTrace.traverse(7, 0.01);
		for(int i = 0; i < positions.size();i++) {
			Block block = positions.get(i).toLocation(p.getWorld()).getBlock();
			if(block != null) {
				if(!isTransparent(block.getType())) {
					return positions.get(i-1).toLocation(p.getWorld()).getBlock().getLocation();
				}
			}
		}
		return positions.get(positions.size()-1).toLocation(p.getWorld()).getBlock().getLocation();
	}

	class GhostPiece {
		public Type structureType;
		public boolean rotated;

		public GhostPiece(Type structureType, boolean rotated) {
			this.structureType = structureType;
			this.rotated = rotated;
		}
	}
}
