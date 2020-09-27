package submc.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import submc.SubMC;
import submc.base.pieces.BasePiece;
import submc.structures.Anchor;
import submc.utils.Loc;

public class Base implements Serializable{
	private static final long serialVersionUID = -5304307504238102738L;

	private static HashMap<String, Base> bases = new HashMap<String, Base>();
	
	private String owner;
	private int power;
	
	private List<BasePiece> basePieces = new ArrayList<BasePiece>();
	
	private List<Loc> usedAnchors = new ArrayList<Loc>();
	
	public Base(Player owner) {
		this.owner = owner.getUniqueId().toString();
		this.power = 0;
		bases.put(this.owner, this);
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public void addBasePiece(BasePiece.Type type, Location center, boolean rot) {
		String pieceId = owner + "_" + basePieces.size();
		this.basePieces.add(new BasePiece(pieceId, new Loc(center), type, rot));
	}
	
	public boolean isAnchor(Block b) {
		return getUnusedAnchors().containsKey(b.getLocation());
	}
	
	public Anchor getAnchor(Block b) {
		return getUnusedAnchors().get(b.getLocation());
	}
	
	private HashMap<Location, Anchor> getUnusedAnchors(){
		HashMap<Location, Anchor> unused = new HashMap<Location, Anchor>();
		for(BasePiece bp : basePieces) {
			List<Location> anchorLocs = bp.getType().getStructure().getAnchorLocations(bp.getCenter(), bp.getRotated());
			List<Anchor> anchors = bp.getType().getStructure().anchors;
			for(int i = 0; i < anchorLocs.size();i++) {
				if(!usedAnchors.contains(new Loc(anchorLocs.get(i)))) {
					unused.put(anchorLocs.get(i), anchors.get(i));
				}
			}
		}
		return unused;
	}
	
	private void update() {
		basePieces.forEach((bp)->bp.update());
	}
	
	public static Base getBase(Player p) {
		return getBase(p.getUniqueId().toString());
	}
	
	public static Base getBase(String owner) {
		return bases.get(owner);
	}
	
	public static boolean hasBase(Player p) {
		return bases.containsKey(p.getUniqueId().toString());
	}
	
	public static void update(SubMC plugin) {
		new BukkitRunnable() {
			@Override
			public void run() {
				bases.forEach((owner, base)->base.update());
			}
		}.runTaskTimer(plugin, 1, 20);
	}

	public static HashMap<String, Base> getBases() {
		return bases;
	}
	
	public static void setBases(HashMap<String, Base> newBases) {
		bases = newBases;
	}
	
}