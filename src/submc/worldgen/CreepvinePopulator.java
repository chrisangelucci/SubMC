package submc.worldgen;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class CreepvinePopulator extends BlockPopulator{

	@Override
	public void populate(World world, Random rnd, Chunk chunk) {
		if(rnd.nextInt(100)<33){
			for(int i = 0; i <rnd.nextInt(5)+1;i++){
				int x = rnd.nextInt(15);
				int z = rnd.nextInt(15);
				for(int y = 0; y<255;y++){
					if(chunk.getBlock(x, y, z).getType() == Material.WATER){
						boolean last = false;
						for(int plantY = y; plantY<70; plantY++){
							if(chunk.getBlock(x, plantY, z).getType() != Material.WATER)
								break;
							
							if(rnd.nextInt(100)<10 && !last){
								chunk.getBlock(x, plantY, z).setType(Material.HORN_CORAL_BLOCK);
								last = true;
							}else{
								chunk.getBlock(x,plantY,z).setType(Material.KELP_PLANT);
								last = false;
							}
						}
						break;
					}
				}
			}
		}
	}

}
