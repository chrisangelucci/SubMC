package submc.habitats.pieces;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import submc.habitats.Direction;
import submc.utils.ItemUtils;

public enum BasePieceType {
	BASIC_COMPARTMENT(
		new int[] {9,5,5}, 
		ItemUtils.getTitanium(2)
	),
	MULTIPURPOSE(
		new int[] {10,5,10},
		ItemUtils.getTitanium(6)
	);
	//,GLASS_COMPARTMENT(new int[] {5,3,3}, ItemUtils.get);

	private int[] bounds;
	
	private ItemStack[] materials;
	
	BasePieceType(int[] bounds, ItemStack... materials) {
		this.bounds = bounds;
		this.materials = materials;
	}
	
	public int[] getBounds() {
		return this.bounds;
	}

	public ItemStack[] getMaterials() {
		return this.materials;
	}

	public boolean canPlace(Location location, Direction d) {
		if(d == null)return false;
		int xMin = (int) Math.min(location.getBlockX(), location.getBlockX() + (d.getDirection().getX() * getBounds()[0]));
		int xMax = (int) Math.max(location.getBlockX(), location.getBlockX() + (d.getDirection().getX() * getBounds()[0]));
		int yMin = (int) Math.min(location.getBlockY(), location.getBlockY() + (d.getDirection().getY() * getBounds()[1]));
		int yMax = (int) Math.max(location.getBlockY(), location.getBlockY() + (d.getDirection().getY() * getBounds()[1]));
		int zMin = (int) Math.min(location.getBlockZ(), location.getBlockZ() + (d.getDirection().getZ() * getBounds()[2]));
		int zMax = (int) Math.max(location.getBlockZ(), location.getBlockZ() + (d.getDirection().getZ() * getBounds()[2]));
		
		boolean good = true;
		
		for(int x = xMin; x < xMax; x++) {
			for(int y = yMin; y < yMax; y++) {
				for(int z = zMin; z < zMax; z++) {
					Location dis = new Location(location.getWorld(), x,y,z);
					if(dis.getBlock().getType() != Material.WATER) {
						good = false;
					}
				}
			}
		}
		return good;
	}
}
