package submc.base.pieces;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import submc.base.Base;
import submc.structures.Structure;
import submc.structures.StructureLoader;
import submc.utils.Loc;
import submc.utils.ItemUtils;

public class BasePiece implements Serializable{
	private static final long serialVersionUID = 6136860922071698575L;
	
	private String id;
	private Loc center;
	private Type type;
	private boolean rot;
	
	public BasePiece(String id, Loc center, Type type, boolean rot) {
		this.id = id;
		this.center = center;
		this.type = type;
		this.rot = rot;
	}
	
	public String getID() {
		return this.id;
	}
	
	public Base getParent() {
		return Base.getBase(this.id.split("_")[0]);
	}
	
	public Location getCenter() {
		return this.center.getLocation();
	}
	
	public Type getType() {
		return this.type;
	}
	
	public boolean getRotated() {
		return this.rot;
	}
	
	//update is called once per second. Should be used to do power calculations on machines and likely more.
	public void update() {
		
	}
	
	public enum Type {
		BASIC("Basic Compartment", "BASIC", ItemUtils.getTitanium(2)), X("X Compartment", "X"), HATCH("Hatch", "HATCH");
		
		private String displayName;
		private String structureName;
		private ItemStack[] materials;
		Type(String displayName, String structureName, ItemStack... materials) {
			this.displayName = displayName;
			this.structureName = structureName;
			this.materials = materials;
		}
		
		public String getDisplayName() {
			return this.displayName;
		}
		
		public Structure getStructure() {
			return StructureLoader.getStructure(this.structureName);
		}
		
		public ItemStack[] getMaterials() {
			return this.materials;
		}
		
		public static boolean isType(String type) {
			for(Type t : Type.values()) {
				if(t.toString().equals(type.toUpperCase())) {
					return true;
				}
			}
			return false;
		}
		
		public static Type getType(String type) {
			return Type.valueOf(type.toUpperCase());
		}
	}
	
}