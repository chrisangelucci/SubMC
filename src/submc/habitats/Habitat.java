package submc.habitats;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import submc.SubMC;
import submc.habitats.machines.Machine;
import submc.habitats.pieces.BasePiece;
import submc.utils.Loc;


public class Habitat implements Serializable{
	private static final long serialVersionUID = 8147064523009390329L;

	private List<BasePiece> basePieces;
	
	private int energy;
	
	private UUID uuid;
	
	public Habitat(Player owner, BasePiece base) {
		this.basePieces = new ArrayList<BasePiece>();
		basePieces.add(base);
		energy = 1000;
		this.uuid = owner.getUniqueId();
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public void tick() {
		for(BasePiece bp : basePieces)
			for(Machine m : bp.getMachines())
				m.tick();
	}
	
	public void addEnergy(int energy) {
		this.energy += energy;
	}
	
	public void addBasePiece(BasePiece piece) {
		basePieces.add(piece);
	}
	
	public void removeBasePiece(BasePiece piece) {
		basePieces.remove(piece);
		piece.remove();
		if(basePieces.isEmpty()) {
			SubMC.getHabitatManager().removeHabitat(this);
		}
	}
	
	public int hashCode() {
		return Objects.hash(uuid, energy);
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Habitat))return false;
		Habitat h = (Habitat)o;
		return h.uuid == uuid;
	}

	public boolean isAttachPoint(Block b) {
		Loc bLoc = new Loc(b.getLocation());
		for(BasePiece bp : basePieces)
			for(AttachPoint ap : bp.getAttachPoints())
				if(ap.getLoc().equals(bLoc))
					return true;
		return false;
	}
	
	
}