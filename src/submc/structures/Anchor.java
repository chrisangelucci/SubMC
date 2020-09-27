package submc.structures;

import java.util.List;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class Anchor {

	public Vector vector;
	public BlockFace bf;
	public List<String> connections;
	
	public Anchor() {
		
	}
	
	public Anchor(Vector vector, BlockFace bf, List<String> connections) {
		this.vector = vector;
		this.bf = bf;
		this.connections = connections;
	}
	
}
