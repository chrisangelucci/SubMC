package submc.structures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import com.google.gson.GsonBuilder;

import submc.base.pieces.BasePiece.Type;
import submc.utils.ItemUtils;

public class StructureMaker implements Listener{
	
	private static HashMap<Player, PartialStructure> making = new HashMap<Player, PartialStructure>();
	
	public void makeStructure(Player p) {
		p.sendMessage("Starting structure creation...");
		p.getInventory().addItem(ItemUtils.getStructureMaker(1));
		PartialStructure ps = new PartialStructure();
		ps.creating = new Structure();
		ps.anchors = new ArrayList<Anchor>();
		ps.center = p.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
		addMatVecs(ps);
		ps.state = 1;
		making.put(p, ps);
		p.sendMessage("Right click the first anchor with wooden axe.");
	}
	
	private static Vector getRelativeLocation(Location position, Location center) {
		int relX = position.getBlockX() - center.getBlockX();
		int relY = position.getBlockY() - center.getBlockY();
		int relZ = position.getBlockZ() - center.getBlockZ();
		return new Vector(relX, relY, relZ);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getHand() == EquipmentSlot.OFF_HAND)return;
		if(e.getItem()==null)return;
		if(!ItemUtils.isSimilar(e.getItem(), ItemUtils.getStructureMaker(1)))return;
		e.setCancelled(true);
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(making.containsKey(p)) {
				PartialStructure ps = making.get(p);
				if(ps.state == 0) {
					p.sendMessage("Position 2 set.");
					ps.pos2 = e.getClickedBlock().getLocation();
					if(ps.pos1 != null) {
						ps.state = 1;
					}
				} else if(ps.state == 1) {
					Block b = e.getClickedBlock();
					BlockFace bf = e.getBlockFace();
					Vector loc = getRelativeLocation(b.getLocation(), ps.center);
					ps.constructing = new Anchor();
					ps.constructing.bf = bf;
					ps.constructing.vector = loc;
					p.sendMessage("Anchor location set. Type connections in chat, separated by commas.");
					ps.state = 2;
					return;
				}
			}
		}else if(e.getAction() == Action.LEFT_CLICK_BLOCK){
			if(making.containsKey(p)) {
				PartialStructure ps = making.get(p);
				if(ps.state == 0) {
					p.sendMessage("Position 1 set.");
					ps.pos1 = e.getClickedBlock().getLocation();
					if(ps.pos2 != null) {
						ps.state = 1;
					}
				}
			}
		}
	}

	@EventHandler
	public void onCHat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String message = e.getMessage();
		if(making.containsKey(p)) {
			PartialStructure ps = making.get(p);
			if(ps.state == 2) {
				List<String> connections = new ArrayList<String>();
				if(message.contains(",")) {
					String[] types = message.split(",");
					for(String type : types) {
						connections.add(type.toUpperCase());
						if(!Type.isType(type)) {
							p.sendMessage("Added unknown connection: " + type.toUpperCase());
						}
					}
				}else {
					connections.add(e.getMessage().toUpperCase());
					if(!Type.isType(e.getMessage())) {
						e.getPlayer().sendMessage("Added unknown connection: " + e.getMessage().toUpperCase());
					}
				}
				ps.constructing.connections = connections;
				ps.anchors.add(ps.constructing);
				e.setCancelled(true);
				p.sendMessage("Anchor connections set. Right click to add another anchor or type 'done' in chat to finish.");
				ps.state = 1;
			}else if(ps.state == 1) {
				if(message.equalsIgnoreCase("done")) {
					ps.creating.anchors = ps.anchors;
					p.sendMessage("Name the structure:");
					ps.state = 3;
					e.setCancelled(true);
				}
			}else if(ps.state == 3) {
				String name = message;
				createFile(name, ps.creating);
				e.getPlayer().sendMessage("New structure '" + name + ".json' created.");
				e.setCancelled(true);
				ps.state = 0;
				making.remove(p);
			}
		}
	}

	private Location[] minMax(Location l1, Location l2) {
		int xMin = Math.min(l1.getBlockX(), l2.getBlockX());
		int yMin = Math.min(l1.getBlockY(), l2.getBlockY());
		int zMin = Math.min(l1.getBlockZ(), l2.getBlockZ());
		int xMax = Math.max(l1.getBlockX(), l2.getBlockX());
		int yMax = Math.max(l1.getBlockY(), l2.getBlockY());
		int zMax = Math.max(l1.getBlockZ(), l2.getBlockZ());
		return new Location[]{new Location(l1.getWorld(), xMin, yMin, zMin), new Location(l1.getWorld(), xMax, yMax, zMax)};
	}
	
	private void addMatVecs(PartialStructure ps){
		Location[] minMax = minMax(ps.pos1, ps.pos2);
		ps.pos1 = minMax[0];
		ps.pos2 = minMax[1];
		List<MatVec> matvecs = new ArrayList<MatVec>();
		int sizeX = ps.pos2.getBlockX()-ps.pos1.getBlockX()+1;
		int sizeY = ps.pos2.getBlockY()-ps.pos1.getBlockY()+1;
		int sizeZ = ps.pos2.getBlockZ()-ps.pos1.getBlockZ()+1;
		for(int x = ps.pos1.getBlockX(); x <= ps.pos2.getBlockX(); x++) {
			for(int y = ps.pos1.getBlockY(); y <= ps.pos2.getBlockY(); y++) {
				for(int z = ps.pos1.getBlockZ(); z <= ps.pos2.getBlockZ(); z++) {
					int relX = x-ps.center.getBlockX();
					int relY = y-ps.center.getBlockY();
					int relZ = z-ps.center.getBlockZ();
					Material type = ps.center.getWorld().getBlockAt(x, y, z).getType();
					matvecs.add(new MatVec(type, relX,relY,relZ));
				}
			}
		}
		ps.creating.matvecs = matvecs;
		ps.creating.sizeX = sizeX;
		ps.creating.sizeY = sizeY;
		ps.creating.sizeZ = sizeZ;
	}

	private void createFile(String fileName, Structure s) {
		String json = new GsonBuilder().create().toJson(s);
		try {
			FileWriter writer = new FileWriter(new File("structures\\" + fileName + ".json"));
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			//TODO something
		}
	}
	
	public class PartialStructure {
		public Structure creating;
		
		public int state = 0; //0-4

		public List<Anchor> anchors;

		public Anchor constructing;

		public Location center;
		
		public Location pos1;
		public Location pos2;
	}
	
}
