package submc.habitats;

import java.util.HashMap;

import org.bukkit.entity.Player;

import submc.habitats.pieces.BasePieceType;

public class HabitatBuilder {

	private static HashMap<String, BasePieceType> selectedType = new HashMap<String, BasePieceType>();
	
	public static boolean hasSelected(Player p) {
		return selectedType.containsKey(p.getName());
	}
	
	public static BasePieceType getSelected(Player p) {
		return selectedType.get(p.getName());
	}
	
	public static void setSelected(Player p, BasePieceType type) {
		selectedType.put(p.getName(), type);
	}
	
	public static void removeSelected(Player p) {
		selectedType.remove(p.getName());
	}
	
}
