package submc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.ChunkCoordIntPair;
import net.minecraft.server.v1_16_R2.DefinedStructure;
import net.minecraft.server.v1_16_R2.DefinedStructureInfo;
import net.minecraft.server.v1_16_R2.EnumBlockMirror;
import net.minecraft.server.v1_16_R2.EnumBlockRotation;
import net.minecraft.server.v1_16_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R2.WorldServer;

public class StructureLoader {

//	public static void createStructure(String name, Location l, EnumBlockRotation rotation) {
//		try {
//			insertSingleStructure(loadSingleStructure(new File("structures//" + name + ".nbt")), l, rotation);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static net.minecraft.server.v1_16_R2.DefinedStructure loadSingleStructure(File source) throws FileNotFoundException, IOException {
//	    DefinedStructure structure = new DefinedStructure();
//	    structure.b(NBTCompressedStreamTools.a(new FileInputStream(source)));
//	    return structure;
//	}
//	
//	public static void insertSingleStructure(DefinedStructure structure, Location startEdge, EnumBlockRotation rotation) {
//	    WorldServer world = ((CraftWorld) startEdge.getWorld()).getHandle();
//	    DefinedStructureInfo structInfo = new DefinedStructureInfo()
//	    		.a(EnumBlockMirror.NONE)
//	    		.a(rotation)
//	    		.a(false)
//	    		.a((ChunkCoordIntPair) null)
//	    		.c(true)
//	    		.a(new Random());
//	    structure.a(world, new BlockPosition(startEdge.getBlockX(), startEdge.getBlockY(), startEdge.getBlockZ()), structInfo);
//	}
	
}
