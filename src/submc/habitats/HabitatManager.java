package submc.habitats;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import submc.SubMC;
import submc.habitats.pieces.BasePiece;

public class HabitatManager {

	private List<Habitat> habitats = new ArrayList<Habitat>();
	
	public HabitatManager(SubMC plugin) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Habitat h : habitats)
					h.tick();
			}
		}.runTaskTimer(plugin, 1, 1);
	}
	
	public boolean hasHabitat(Player p) {
		for(Habitat h : habitats)
			if(h.getUUID() == p.getUniqueId())
				return true;
		return false;
	}
	
	public Habitat getHabitat(Player p) {
		for(Habitat h : habitats)
			if(h.getUUID() == p.getUniqueId())
				return h;
		return null;
	}
	
	public void createHabitat(Player owner, BasePiece base) {
		this.habitats.add(new Habitat(owner, base));
	}
	
	public void removeHabitat(Habitat h) {
		this.habitats.remove(h);
	}
	
}
