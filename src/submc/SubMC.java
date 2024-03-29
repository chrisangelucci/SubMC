package submc;

import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import submc.base.Base;
import submc.base.HabitatBuilderManager;
import submc.beacons.BeaconManager;
import submc.commands.CommandManager;
import submc.fabricator.FabricatorMain;
import submc.fabricator.FabricatorRecipe;
import submc.listeners.GameruleDefiner;
import submc.listeners.HarvestableListener;
import submc.listeners.LifepodCreator;
import submc.listeners.MachineListener;
import submc.listeners.SpawnBlocker;
import submc.oxygen.OxygenManager;
import submc.slapi.StateSaver;
import submc.structures.StructureLoader;
import submc.structures.StructureMaker;
import submc.utils.ItemUtils;
import submc.worldgen.OceanGenerator;

public class SubMC extends JavaPlugin{
	
	public static boolean lifepodCreated = false;
	
	private static BeaconManager beaconManager;
	private static OxygenManager oxygenManager;
	
	private static StructureMaker structureMaker;
	
	private static StateSaver stateSaver;
	
	private static SubMC plugin;
	
	public void onEnable() {
		plugin = this;
		StructureLoader.loadStructures();
		beaconManager = new BeaconManager(this);
		oxygenManager = new OxygenManager(this);
		structureMaker = new StructureMaker();
		stateSaver = new StateSaver(this);
		
		Bukkit.clearRecipes();
		registerRecipes();
		registerListeners(getServer().getPluginManager());
		
		ItemSinker.sinkItems(this);
		HabitatBuilderManager.showOutlines(this);
		Base.update(this);
		CommandManager.registerCommands();
	}
	
	public void onDisable() {
		stateSaver.saveState();
	}
	
	public static BeaconManager getBeaconManager() {
		return beaconManager;
	}
	
	public static OxygenManager getOxygenManager() {
		return oxygenManager;
	}

	public static StructureMaker getStructureMaker() {
		return structureMaker;
	}
	
	private void registerRecipes() {
		new FabricatorRecipe(ItemUtils.getTitaniumIngot(1), ItemUtils.getTitanium(10));
		new FabricatorRecipe(ItemUtils.getCookedBladderfish(1), ItemUtils.getBladderfish(1));
		new FabricatorRecipe(ItemUtils.getSiliconeRubber(2), ItemUtils.getSeedCluster(1));
		new FabricatorRecipe(ItemUtils.getKnife(1), ItemUtils.getSiliconeRubber(1), ItemUtils.getTitanium(1));
		new FabricatorRecipe(ItemUtils.getBeacon(1), ItemUtils.getCopperOre(1), ItemUtils.getTitanium(1));
		new FabricatorRecipe(ItemUtils.getWiringKit(1), ItemUtils.getSilverOre(2));
		new FabricatorRecipe(ItemUtils.getCopperWire(1), ItemUtils.getCopperOre(2));
		new FabricatorRecipe(ItemUtils.getComputerChip(1), ItemUtils.getTableCoralSample(2), ItemUtils.getGold(1), ItemUtils.getCopperWire(1));
		new FabricatorRecipe(ItemUtils.getBattery(1), ItemUtils.getAcidMushroom(2), ItemUtils.getCopperOre(1));
		new FabricatorRecipe(ItemUtils.getHabitatBuilder(1), ItemUtils.getWiringKit(1), ItemUtils.getComputerChip(1), ItemUtils.getBattery(1));
	}

	private void registerListeners(PluginManager pm){
		pm.registerEvents(new SpawnBlocker(), this);
		pm.registerEvents(new HarvestableListener(), this);
		pm.registerEvents(new MachineListener(), this);
		pm.registerEvents(new FabricatorMain(this), this);
		pm.registerEvents(new LifepodCreator(), this);
		pm.registerEvents(new GameruleDefiner(), this);
		pm.registerEvents(new HabitatBuilderManager(), this);
		pm.registerEvents(structureMaker, this);
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
	    return new OceanGenerator();
	}

	public static SubMC getInstance() {
		return plugin;
	}
	
}
