package submc.base;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import submc.base.HabitatBuilderManager.GhostPiece;
import submc.structures.MatVec;
import submc.structures.Structure;

public class CAlgorithm {

	public static void drawOutline(Player p, GhostPiece gp, Location center) {
		List<Location> locations = gp.structureType.getStructure().getLocations(center, gp.rotated);
		List<MatVec> matvecs = gp.structureType.getStructure().getMatVecs(gp.rotated);
		DustOptions line = !obstructed(p, gp, center) ? new DustOptions(Color.LIME, 1) : new DustOptions(Color.RED, 1);
		for(int i = 0; i < locations.size(); i++) {
			MatVec mv = matvecs.get(i);
			if(!isTransparent(mv.getMaterial())) {
				//markBlock(locations.get(i));
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.NORTH, BlockFace.WEST, gp.rotated)) {
					markEdge(locations.get(i), "NW", line);
				}
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.NORTH, BlockFace.EAST, gp.rotated)) {
					markEdge(locations.get(i), "NE", line);
				}
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.SOUTH, BlockFace.WEST, gp.rotated)) {
					markEdge(locations.get(i), "SW", line);
				}
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.SOUTH, BlockFace.EAST, gp.rotated)) {
					markEdge(locations.get(i), "SE", line);
				}
				
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.NORTH, BlockFace.UP, gp.rotated)) {
					markEdge(locations.get(i), "NTOP", line);
				}
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.SOUTH, BlockFace.UP, gp.rotated)) {
					markEdge(locations.get(i), "STOP", line);
				}
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.EAST, BlockFace.UP, gp.rotated)) {
					markEdge(locations.get(i), "ETOP", line);
				}
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.WEST, BlockFace.UP, gp.rotated)) {
					markEdge(locations.get(i), "WTOP", line);
				}
				
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.NORTH, BlockFace.DOWN, gp.rotated)) {
					markEdge(locations.get(i), "NBOT", line);
				}
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.SOUTH, BlockFace.DOWN, gp.rotated)) {
					markEdge(locations.get(i), "SBOT", line);
				}
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.EAST, BlockFace.DOWN, gp.rotated)) {
					markEdge(locations.get(i), "EBOT", line);
				}
				if(bothOpen(gp.structureType.getStructure(), mv, BlockFace.WEST, BlockFace.DOWN, gp.rotated)) {
					markEdge(locations.get(i), "WBOT", line);
				}
			}
		}
	}
	
	private static boolean obstructed(Player p, GhostPiece gp, Location center) {
		Structure s = gp.structureType.getStructure();
		for(Location l : s.getLocations(center, gp.rotated)) {
			if(!isTransparent(l.getBlock().getType())) {
				return true;
			}
		}
		return false;
	}
	
	public static void markBlock(Location block) {
		markEdge(block, "NW", new DustOptions(Color.LIME, 1));
		markEdge(block, "NTOP", new DustOptions(Color.LIME, 1));
		markEdge(block, "ETOP", new DustOptions(Color.LIME, 1));
		markEdge(block, "SE", new DustOptions(Color.LIME, 1));
		markEdge(block, "EBOT", new DustOptions(Color.LIME, 1));
		markEdge(block, "NE", new DustOptions(Color.LIME, 1));
		markEdge(block, "WTOP", new DustOptions(Color.LIME, 1));
		markEdge(block, "WBOT", new DustOptions(Color.LIME, 1));
		markEdge(block, "NBOT", new DustOptions(Color.LIME, 1));
		markEdge(block, "STOP", new DustOptions(Color.LIME, 1));
		markEdge(block, "SBOT", new DustOptions(Color.LIME, 1));
		markEdge(block, "SW", new DustOptions(Color.LIME, 1));
	}
	
	private static void markEdge(Location block, String edge, DustOptions line) {
		Location NWTOP = new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ());
		Location NETOP = new Location(block.getWorld(), block.getX()+1, block.getY()+1, block.getZ());
		Location SETOP = new Location(block.getWorld(), block.getX()+1, block.getY()+1, block.getZ()+1);
		Location SWTOP = new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ()+1);
		Location NWBOT = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ());
		Location NEBOT = new Location(block.getWorld(), block.getX()+1, block.getY(), block.getZ());
		Location SEBOT = new Location(block.getWorld(), block.getX()+1, block.getY(), block.getZ()+1);
		Location SWBOT = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()+1);
		switch(edge) {
			case "NW": drawLine(NWTOP,NWBOT,line);break;
			case "NTOP": drawLine(NWTOP,NETOP,line);break;
			case "ETOP": drawLine(NETOP, SETOP,line);break;
			case "SE": drawLine(SETOP, SEBOT, line);break;
			case "EBOT": drawLine(NEBOT, SEBOT, line);break;
			case "NE": drawLine(NETOP,NEBOT,line);break;
			case "WTOP": drawLine(NWTOP, SWTOP,line);break;
			case "WBOT": drawLine(NWBOT, SWBOT, line);break;
			case "NBOT": drawLine(NWBOT, NEBOT, line);break;
			case "STOP": drawLine(SWTOP,SETOP,line);break;
			case "SBOT": drawLine(SWBOT, SEBOT, line);break;
			case "SW": drawLine(SWTOP, SWBOT, line);break;
		}
	}
	
	private static void drawLine(Location start, Location end, DustOptions ops) {
		Vector vector = getDirectionBetweenLocations(start, end);
		double dist = start.distance(end);
		for(double i = .001; i<=dist;i+=0.25) {
			vector.multiply(i);
			start.add(vector);
			particle(start, ops);
			start.subtract(vector);
			vector.normalize();
		}
	}
	
	private static void particle(Location l, DustOptions ops) {
		l.getWorld().spawnParticle(Particle.REDSTONE, l, 1, 0, 0, 0, 0, ops);
	}
	
	private static Vector getDirectionBetweenLocations(Location start, Location end) {
        Vector from = start.toVector();
        Vector to = end.toVector();
        return to.subtract(from);
    }
	
	private static boolean bothOpen(Structure s, MatVec mv, BlockFace bf1, BlockFace bf2, boolean rotated) {
		return open(s, mv, bf1, rotated) == open(s, mv, bf2, rotated) && middleOpen(s, mv, bf1,bf2, rotated);
	}
	
	private static boolean open(Structure s, MatVec mv, BlockFace bf, boolean rotated) {
		MatVec relative = getRelative(s, mv, bf, rotated);
		return relative == null || isTransparent(relative.getMaterial());
	}
	
	private static boolean middleOpen(Structure s, MatVec mv, BlockFace bf1, BlockFace bf2, boolean rotated) {
		int modX = bf1.getModX() + bf2.getModX();
		int modY = bf1.getModY() + bf2.getModY();
		int modZ = bf1.getModZ() + bf2.getModZ();
		MatVec relative = getRelative(s, mv, new Vector(modX,modY,modZ), rotated);
		return relative == null || isTransparent(relative.getMaterial());
	}
	
	private static MatVec getRelative(Structure s, MatVec mv, BlockFace bf, boolean rotated) {
		return getRelative(s, mv, new Vector(bf.getModX(), bf.getModY(), bf.getModZ()), rotated);
	}
	
	private static MatVec getRelative(Structure s, MatVec mv, Vector v, boolean rotated) {
		Vector targetVector = mv.getRelativeVector().clone().add(v);
		for(MatVec vec : s.getMatVecs(rotated)) {
			if(targetVector.equals(vec.getRelativeVector())) {
				return vec;
			}
		}
		return null;
	}
	
	private static boolean isTransparent(Material m) {
		Material[] mats = {Material.AIR, Material.BARRIER, Material.WATER};
		return Arrays.asList(mats).contains(m);
	}
	
}
