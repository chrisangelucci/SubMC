package submc.structures;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class Structure {

	public int sizeX;
	public int sizeY;
	public int sizeZ;
	public List<MatVec> matvecs;
	public List<Anchor> anchors;
	
	public void place(Location center, boolean rotate) {
		Vector centerVector = center.toVector();
		this.matvecs.forEach(mv ->{
			Vector placeLocation = mv.getRelativeVector().clone();
			if(rotate) {
				int tempX = placeLocation.getBlockX();
				placeLocation.setX(placeLocation.getBlockZ() * -1);
				placeLocation.setZ(tempX);
			}
			placeLocation.add(centerVector);
			Block placeBlock = placeLocation.toLocation(center.getWorld()).getBlock();
			if(mv.getMaterial() != Material.BARRIER) {
				placeBlock.setType(mv.getMaterial());
			}
		});
	}
	
	public List<Location> getAnchorLocations(Location center, boolean rotate){
		List<Anchor> anchors = getAnchors(rotate);
		List<Location> locs = new ArrayList<Location>();
		anchors.forEach(anchor ->{
			Vector vector = anchor.vector.clone();
			vector.add(center.toVector());
			locs.add(vector.toLocation(center.getWorld()));
		});
		return locs;
	}
	
	private List<Anchor> getAnchors(boolean rotate){
		List<Anchor> rotated = new ArrayList<Anchor>();
		this.anchors.forEach(anchor->{
			Vector rotatedV = anchor.vector.clone();
			BlockFace rotatedBF = anchor.bf;
			if(rotate) {
				rotatedV.setX(-1*rotatedV.getBlockZ());
				rotatedV.setZ(anchor.vector.getBlockX());
				rotatedBF = next(rotatedBF);
			}
			rotated.add(new Anchor(rotatedV, rotatedBF, anchor.connections));
		});
		return rotated;
	}
	
	private BlockFace next(BlockFace bf) {
		switch(bf) {
			case NORTH: return BlockFace.EAST;
			case EAST: return BlockFace.SOUTH;
			case SOUTH: return BlockFace.WEST;
			case WEST: return BlockFace.NORTH;
			default: return BlockFace.NORTH;
		}
	}
	
	public List<MatVec> getMatVecs(boolean rotate){
		List<MatVec> rotated = new ArrayList<MatVec>();
		this.matvecs.forEach(mv->{
			Vector rotatedMV = mv.getRelativeVector().clone();
			if(rotate) {
				rotatedMV.setX(-1*rotatedMV.getBlockZ());
				rotatedMV.setZ(mv.getRelativeVector().getBlockX());
			}
			rotated.add(new MatVec(mv.getMaterial(), rotatedMV));
		});
		return rotated;
	}
	
	public List<Location> getLocations(Location center, boolean rotate){
		Vector centerVector = center.toVector();
		List<Location> locs = new ArrayList<Location>();
		this.matvecs.forEach(mv->{
			Vector placeLocation = mv.getRelativeVector().clone();
			if(rotate) {
				int tempX = placeLocation.getBlockX();
				placeLocation.setX(placeLocation.getBlockZ() * -1);
				placeLocation.setZ(tempX);
			}
			placeLocation.add(centerVector);
			locs.add(placeLocation.toLocation(center.getWorld()));
		});
		return locs;
	}
	
}
