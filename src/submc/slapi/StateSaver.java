package submc.slapi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import submc.SubMC;
import submc.base.Base;

public class StateSaver {

	private SubMC plugin;
	
	public StateSaver(SubMC plugin) {
		this.plugin = plugin;
		if(!plugin.getDataFolder().exists()){
			plugin.getDataFolder().mkdir();
		}else{
			loadState();
		}
	}

	public void saveState(){
		try {
			save(SubMC.getBeaconManager().getBeacons(), plugin.getDataFolder() + File.separator + "beacons.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			save(SubMC.getOxygenManager().getOxygen(), plugin.getDataFolder() + File.separator + "oxygen.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			save(SubMC.lifepodCreated, plugin.getDataFolder() + File.separator + "lifepod.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			save(Base.getBases(), plugin.getDataFolder() + File.separator + "bases.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			save(PlayerProfile.getProfiles(), plugin.getDataFolder() + File.separator + "profiles.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadState(){
		try{
			String path = plugin.getDataFolder() + File.separator + "beacons.bin";
			File file = new File(path);

			if (file.exists()){
				SubMC.getBeaconManager().setBeacons(load(path));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			String path = plugin.getDataFolder() + File.separator + "oxygen.bin";
			File file = new File(path);

			if (file.exists()){
				SubMC.getOxygenManager().setOxygen(load(path));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			String path = plugin.getDataFolder() + File.separator + "lifepod.bin";
			File file = new File(path);

			if (file.exists()){
				SubMC.lifepodCreated  = load(path);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			String path = plugin.getDataFolder() + File.separator + "bases.bin";
			File file = new File(path);

			if (file.exists()){
				Base.setBases(load(path));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			String path = plugin.getDataFolder() + File.separator + "profiles.bin";
			File file = new File(path);

			if (file.exists()){
				PlayerProfile.setProfiles(load(path));
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private <T extends Object> void save(T obj,String path) throws Exception{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}
	
	@SuppressWarnings("unchecked")
	private <T extends Object> T load(String path) throws Exception{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		T result = (T)ois.readObject();
		ois.close();
		return result;
	}
	
}
