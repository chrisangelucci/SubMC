package submc.habitats.pieces;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;

import submc.habitats.AttachPoint;
import submc.habitats.machines.Machine;
import submc.utils.Loc;

public class BasePiece implements Serializable{
	private static final long serialVersionUID = -4559826319402219496L;

	private List<AttachPoint> attachPoints;
	
	private List<Machine> machines;
	
	private List<Loc> blocks;
	
	private UUID uuid;
	
	public BasePiece(List<Location> blocks) {
		this.blocks = Loc.getLocs(blocks);
		this.uuid = UUID.randomUUID();
	}
	
	public boolean isIn(Location l) {
		return blocks.contains(new Loc(l));
	}
	
	public void remove() {
		for(Loc l : blocks)
			l.getLocation().getBlock().setType(Material.WATER);
	}
	
	public List<Machine> getMachines() {
		return this.machines;
	}
	
	public void addMachine(Machine m) {
		machines.add(m);
	}
	
	public void removeMachine(Machine m) {
		machines.remove(m);
	}
	
	public List<AttachPoint> getAttachPoints() {
		return this.attachPoints;
	}
	
	public void addAttachPoint(AttachPoint ap) {
		attachPoints.add(ap);
	}
	
	public void removeAttachPoint(AttachPoint ap) {
		attachPoints.remove(ap);
	}
	
	public int hashCode() {
		return Objects.hash(attachPoints, machines, blocks);
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof BasePiece))return false;
		BasePiece bp = (BasePiece)o;
		return bp.uuid.equals(this.uuid);
	}
	
}