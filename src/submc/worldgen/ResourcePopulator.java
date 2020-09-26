package submc.worldgen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class ResourcePopulator extends BlockPopulator{

	@Override
	public void populate(World world, Random rnd, Chunk chunk) {
		if(rnd.nextBoolean()){
			//50% chance to have any resource outcrop in chunk.
			for(int i = 0; i <rnd.nextInt(9)+1;i++){
				int x = rnd.nextInt(15);
				int z = rnd.nextInt(15);
				for(int y = 0; y<255;y++){
					if(chunk.getBlock(x, y, z).getType() == Material.WATER){
						if(populatable(chunk.getBlock(x, y-1, z).getType())) {
							double chance = rnd.nextDouble();
							if(chance < 0.25) {
								chunk.getBlock(x,y,z).setType(Material.DEAD_BRAIN_CORAL);
							}else if(chance < 0.50) {
								chunk.getBlock(x,y,z).setType(Material.TUBE_CORAL);
							}else if(chance < 0.75){
								chunk.getBlock(x,y,z).setType(Material.FIRE_CORAL_FAN);
							}else {
								chunk.getBlock(x, y, z).setType(Material.DIORITE_SLAB);
							}
						}
						break;
					}
				}
			}
		}
	}
	
	private boolean populatable(Material m) {
		List<Material> populatable = Arrays.asList(new Material[] {Material.SAND, Material.DIRT, Material.STONE});
		return populatable.contains(m);
	}

}
