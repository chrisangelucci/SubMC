package submc.slapi;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import submc.permissions.Group;
import submc.permissions.PermissionProfile;

public class PlayerProfile {

	private static HashMap<UUID, PlayerProfile> playerProfiles = new HashMap<UUID, PlayerProfile>();
	
	private PermissionProfile permissionProfile;
	private boolean fourth_wall;
	
	private PlayerProfile(Player player) {
		this.permissionProfile = new PermissionProfile(Group.DEFAULT);
		this.fourth_wall = false;
	}
	
	public PermissionProfile getPermissionProfile() {
		return this.permissionProfile;
	}
	
	public boolean breakingFourthWall() {
		return this.fourth_wall;
	}
	
	public void toggleFourthWall() {
		this.fourth_wall = !this.fourth_wall;
	}
	
	public static PlayerProfile getProfile(Player player) {
		if(!playerProfiles.containsKey(player.getUniqueId())) {
			playerProfiles.put(player.getUniqueId(), new PlayerProfile(player));
		}
		return playerProfiles.get(player.getUniqueId());
	}
	
	public static HashMap<UUID, PlayerProfile> getProfiles() {
		return playerProfiles;
	}
	
	public static void setProfiles(HashMap<UUID, PlayerProfile> profiles){
		playerProfiles = profiles;
	}
}
