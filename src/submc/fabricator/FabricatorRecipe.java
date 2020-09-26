package submc.fabricator;

import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import submc.utils.ItemUtils;

public class FabricatorRecipe {

	private static HashMap<ItemStack, ItemStack[]> recipes = new HashMap<ItemStack, ItemStack[]>();
	
	public FabricatorRecipe(ItemStack result, ItemStack... resources) {
		recipes.put(result, resources);
	}
	
	private static ItemStack[] customGet(ItemStack getKey){
		for(ItemStack key : recipes.keySet()){
			if(ItemUtils.isSimilar(key, getKey)){
				return recipes.get(key);
			}
		}
		return null;
	}
	
	public static void fabricate(Player p, ItemStack currentItem) {
		if(tryFabricate(p, currentItem)){
			p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
		}else{
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 100, 5);
		}
	}
	
	private static boolean tryFabricate(Player p, ItemStack result){
		ItemStack[] resources = customGet(result);
		if(ItemUtils.tryRemove(p, resources)) {
			p.getInventory().addItem(ItemUtils.giveUniqueUUID(result));
			p.updateInventory();
			return true;
		}
		return false;
	}
	
}
