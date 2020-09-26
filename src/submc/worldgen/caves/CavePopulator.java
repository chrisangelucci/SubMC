package submc.worldgen.caves;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class CavePopulator extends BlockPopulator{

	@Override
	public void populate(final World world, final Random random, final Chunk source) {
		CaveRandom gcRandom = new CaveRandom(source);

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = CaveRandom.caveBandMax; y >= CaveRandom.caveBandMin; y--) {
					if (gcRandom.isInGiantCave(x, y, z)) {
						Block block = source.getBlock(x, y, z);
						block.setType(Material.WATER);
						if(!gcRandom.isInGiantCave(x, y-1, z)) {
							if(random.nextDouble() < .001) {
								block.setType(Material.DEAD_TUBE_CORAL);
							}
						}
					}
				}
			}
		}
	}
}