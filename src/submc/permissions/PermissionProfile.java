package submc.permissions;

import java.util.ArrayList;
import java.util.List;

public class PermissionProfile {
	
	private Group group;
	private List<Permission> permissions;
	
	public PermissionProfile(Group group) {
		this(group, new ArrayList<Permission>());
	}
	
	public PermissionProfile(Group group, List<Permission> permissions) {
		this.group = group;
		this.permissions = permissions;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public void givePermission(Permission permission) {
		if(!this.permissions.contains(permission))
			this.permissions.add(permission);
	}
	
	public void removePermission(Permission permission) {
		this.permissions.remove(permission);
	}
	
	public boolean hasPermission(Permission permission) {
		return this.group.hasPermission(permission) || this.permissions.contains(permission);
	}
}