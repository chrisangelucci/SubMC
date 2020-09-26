package submc.worldgen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import submc.worldgen.caves.CavePopulator;

public class OceanGenerator extends ChunkGenerator{

	int currentHeight = 50;
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		ChunkData chunk = createChunkData(world);
		SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
		generator.setScale(0.01D);
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				currentHeight = (int) (generator.noise(chunkX*16+x, chunkZ*16+z, 0.5D, 0.5D)*15D+50D);
				for(int i = 70; i > currentHeight; i--)
					chunk.setBlock(x, i, z, Material.WATER);
                for (int i = currentHeight; i > 0; i--) {
                	if(random.nextBoolean()){
                		chunk.setBlock(x, i, z, Material.STONE);
                	}else{
                		chunk.setBlock(x, i, z, Material.DIRT);
                	}
                }
                chunk.setBlock(x, 0, z, Material.BEDROCK);
			}
		}
		return chunk;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList(new CavePopulator(), new ResourcePopulator(), new CreepvinePopulator());
	}

}
