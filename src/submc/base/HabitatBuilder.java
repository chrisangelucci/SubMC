package submc.base;

import java.util.HashMap;

import org.bukkit.entity.Player;

import submc.base.pieces.BasePiece.Type;

public class HabitatBuilder {

	private static HashMap<String, Type> selectedType = new HashMap<String, Type>();
	
	public static boolean hasSelected(Player p) {
		return selectedType.containsKey(p.getName());
	}
	
	public static Type getSelected(Player p) {
		return selectedType.get(p.getName());
	}
	
	public static void setSelected(Player p, Type type) {
		selectedType.put(p.getName(), type);
	}
	
	public static void removeSelected(Player p) {
		selectedType.remove(p.getName());
	}
	
	
	
}
