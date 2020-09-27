package submc.commands;

import org.bukkit.Bukkit;

import submc.permissions.Group;

public class Parameter {

	private ParameterType type;
	private String description;
	
	public Parameter(ParameterType type, String description) {
		this.type = type;
		this.description = description;
	}
	
	public ParameterType getParameterType() {
		return this.type;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	enum ParameterType {
		PLAYER{
			@Override
			public boolean matches(String arg) {
				return Bukkit.getPlayer(arg) != null;
			}
			@Override
			public Object convert(String arg) {
				return Bukkit.getPlayer(arg);
			}
		},
		GROUP{
			@Override
			public boolean matches(String arg) {
				return Group.isGroup(arg);
			}
			@Override
			public Object convert(String arg) {
				return Group.getGroup(arg);
			}
		};

		public abstract boolean matches(String arg);
		public abstract Object convert(String arg);
	}
	
}
