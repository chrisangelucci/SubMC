package submc.permissions;

import java.util.Arrays;

import submc.base.pieces.BasePiece.Type;

public enum Group {
	DEFAULT(new Permission[] {
			
	}),
	MOD(new Permission[] {
			Permission.KICK
	}, DEFAULT),
	ADMIN(new Permission[] {
			Permission.MAKE_STRUCTURE,
			Permission.FOURTH_WALL
	}, MOD);
	
	private Permission[] permissions;
	private Group inherits;
	
	Group(Permission[] permissions) {
		this(permissions, null);
	}
	
	Group(Permission[] permissions, Group inherits){
		this.permissions = permissions;
		this.inherits = inherits;
	}
	
	protected boolean hasPermission(Permission permission) {
		boolean hasParent = this.inherits != null;
		return Arrays.asList(this.permissions).contains(permission) || (hasParent ? inherits.hasPermission(permission) : false);
	}
	
	public static boolean isGroup(String group) {
		for(Group g : Group.values()) {
			if(g.toString().equals(group.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
	
	public static Group getGroup(String group) {
		return Group.valueOf(group.toUpperCase());
	}
	
}
