package submc.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

	public static ItemStack getTitanium(int amount) {
		return easyItemStack("Titanium", Material.IRON_NUGGET, "Ti. Basic building material.", amount);
	}

	public static ItemStack getTitaniumIngot(int amount) {
		return easyItemStack("Titanium Ingot", Material.IRON_INGOT, "Ti. Condensed titanium bar.", amount);
	}
	
	public static ItemStack getBladderfish(int amount) {
		return easyItemStack("Bladderfish", Material.PUFFERFISH, "Unique outer membrane stores excess water.", amount);
	}
	
	public static ItemStack getCookedBladderfish(int amount) {
		return easyItemStack("Cooked Bladderfish", Material.COOKED_CHICKEN, "Spongy. Gristly. Low calorie count.", amount);
	}
	
	public static ItemStack getSeedCluster(int amount) {
		return easyItemStack("Creepvine Seed Cluster", Material.HORN_CORAL, "Indigenous seeds with high silicone and oil content.", amount);
	}
	
	public static ItemStack getCreepvineSample(int amount) {
		return easyItemStack("Creepvine Sample", Material.VINE, "Silicone-based plant material.", amount);
	}
	
	public static ItemStack getSiliconeRubber(int amount) {
		return easyItemStack("Silicone Rubber", Material.NETHER_BRICK, "Synthetic, silicone-based rubber.", amount);
	}
	
	public static ItemStack getBeacon(int amount) {
		return easyItemStack("Beacon", Material.HEART_OF_THE_SEA, "Navigation aid. Maintains and broadcasts its position. Configurable name.", amount);
	}
	
	public static ItemStack getKnife(int amount) {
		return easyItemStack("Survival Knife", Material.IRON_SWORD, "Standard survival tool. Multi-functional.", amount);
	}
	
	public static ItemStack getCopperOre(int amount) {
		return easyItemStack("Copper Ore", Material.ORANGE_DYE, "Cu. Essential wiring component.", amount);
	}
	
	public static ItemStack getGold(int amount) {
		return easyItemStack("Gold", Material.GOLD_NUGGET, "Au. Valuable conductive properties.", amount);
	}
	
	public static ItemStack getLead(int amount) {
		return easyItemStack("Lead", Material.FLINT, "Pb. Screening from radiation.", amount);
	}
	
	public static ItemStack getSilverOre(int amount) {
		return easyItemStack("Silver Ore", Material.BONE_MEAL, "Ag. Conductive element and microbicide.", amount);
	}
	
	public static ItemStack getDiamond(int amount) {
		return easyItemStack("Diamond", Material.DIAMOND, "C. Carbon allotrope with superlative physical properties.", amount);
	}
	
	public static ItemStack getLithium(int amount) {
		return easyItemStack("Lithium", Material.GRAY_DYE, "Li. Applications in high-strength alloys.", amount);
	}
	
	public static ItemStack getTableCoralSample(int amount) {
		return easyItemStack("Table Coral Sample", Material.FIRE_CORAL_FAN, "Contains trace precious metals used in computer fabrication.", amount);
	}
	
	public static ItemStack getCoralTubeSample(int amount) {
		return easyItemStack("Coral Tube Sample", Material.TUBE_CORAL_FAN, "Sample containing CaCO3. Calcium carbonate is a base ingredient for bleach.", amount);
	}
	
	public static ItemStack getSaltDeposit(int amount) {
		return easyItemStack("Salt Deposit", Material.SUGAR, "NaCl. Culinary and sanitation applications.", amount);
	}
	
	public static ItemStack getWiringKit(int amount) {
		return easyItemStack("Wiring Kit", Material.DAYLIGHT_DETECTOR, "Insulated silver wire. Essential electronic component.", amount);
	}
	
	public static ItemStack getCopperWire(int amount) {
		return easyItemStack("Copper Wire", Material.LEAD, "Copper atoms fabricated into basic electrical wiring.", amount);
	}
	
	public static ItemStack getComputerChip(int amount) {
		return easyItemStack("Computer Chip", Material.IRON_TRAPDOOR, "Multi-purpose CPU.", amount);
	}
	
	public static ItemStack getAcidMushroom(int amount) {
		return easyItemStack("Acid Mushroom", Material.DANDELION, "Purple fungus. Acidic flesh.", amount);
	}
	
	public static ItemStack getBattery(int amount) {
		return easyItemStack("Battery", Material.LANTERN, "Mobile power source.", amount);
	}
	
	public static ItemStack getHabitatBuilder(int amount) {
		return easyItemStack("Habitat Builder", Material.BELL, "Fabricates habitat compartments and appliances from raw materials.", amount);
	}
	
	public static ItemStack getRandomLimestoneDrop() {
		return new Random().nextBoolean() ? getCopperOre(1) : getTitanium(1);
	}
	
	public static ItemStack getRandomSandstoneDrop() {
		double d = new Random().nextDouble();
		if(d<0.33) {
			return getSilverOre(1);
		}else if(d<0.66) {
			return getLead(1);
		}else {
			return getGold(1);
		}
	}
	
	public static ItemStack getRandomShaleDrop() {
		double d = new Random().nextDouble();
		if(d<0.33) {
			return getDiamond(1);
		}else if(d<0.66) {
			return getLithium(1);
		}else {
			return getGold(1);
		}
	}
	
	public static boolean tryRemove(Player p, ItemStack[] resources) {
		for(ItemStack resource : resources) {
			int count = 0;
			for(int i = 0; i < 36; i++){
				if(p.getInventory().getItem(i) == null)continue;
				if(ItemUtils.isSimilar(p.getInventory().getItem(i), resource)){
					count++;
				}
			}
			if(count < resource.getAmount()){
				return false;
			}
		}
		for(ItemStack resource : resources){
			int count = resource.getAmount();
			for(int i = 0; i < 36; i++){
				if(count == 0)
					break;
				if(p.getInventory().getItem(i) == null)continue;
				if(ItemUtils.isSimilar(p.getInventory().getItem(i), resource)){
					p.getInventory().setItem(i, new ItemStack(Material.AIR));
					count--;
				}
			}
		}
		return true;
	}
	
	public static ItemStack addLore(ItemStack is, String... lore){
		is = is.clone();
		ItemMeta im = is.getItemMeta();
		List<String> newLore;
		if(im.hasLore()) {
			newLore = im.getLore();
		}else {
			newLore = new ArrayList<String>();
		}
		newLore.addAll(Arrays.asList(lore));
		im.setLore(newLore);
		is.setItemMeta(im);
		return is;
	}
	
	public static ItemStack giveUniqueUUID(ItemStack is){
		is = is.clone();
		removeLore(is);
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();
		lore.add(ChatColor.DARK_GRAY + UUID.randomUUID().toString());
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	private static void removeLore(ItemStack is){
		String firstLine = is.getItemMeta().getLore().get(0);
		List<String> newLore = new ArrayList<String>();
		newLore.add(firstLine);
		ItemMeta im = is.getItemMeta();
		im.setLore(newLore);
		is.setItemMeta(im);
	}
	
	public static boolean isSimilar(ItemStack is1, ItemStack is2){
		is1 = is1.clone();
		is2 = is2.clone();
		
		ItemMeta im1 = is1.getItemMeta();
		im1.setLore(new ArrayList<String>());
		is1.setItemMeta(im1);
		
		ItemMeta im2 = is2.getItemMeta();
		im2.setLore(new ArrayList<String>());
		is2.setItemMeta(im2);
		
		return is1.isSimilar(is2);
	}
	
	public static ItemStack easyItemStack(String display, Material m, String lore, int amount){
		ItemStack is = new ItemStack(m, amount);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.RESET + display);
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		if(!lore.isEmpty()) {
			List<String> loreArr = new ArrayList<String>();
			loreArr.add(ChatColor.WHITE + lore);
			im.setLore(loreArr);
		}
		is.setItemMeta(im);
		return is;
	}
	
}
