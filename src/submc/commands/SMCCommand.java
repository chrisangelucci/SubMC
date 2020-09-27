package submc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import submc.permissions.Permission;
import submc.slapi.PlayerProfile;
import submc.utils.MessageUtils;

public abstract class SMCCommand extends Command{

	public SMCCommand(String name) {
		super(name);
	}

	public abstract Permission getSMCPermission();
	public abstract SenderType getSenderType();
	public abstract Parameter[] getParameters();
	public abstract String getDescription();
	public abstract String getSMCUsage();
	public abstract void execute(CommandSender sender, Object[] args);

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		SenderType type = getSenderType();
		if(sender instanceof Player) {
			if(type == SenderType.PLAYER || type == SenderType.BOTH) {
				if(PlayerProfile.getProfile((Player)sender).getPermissionProfile().hasPermission(getSMCPermission())) {
					paramCheck(sender, args);
				}
			}
		}else if(sender instanceof ConsoleCommandSender) {
			if(type == SenderType.CONSOLE || type == SenderType.BOTH) {
				paramCheck(sender, args);
			}
		}
		return true;
	}

	private void paramCheck(CommandSender sender, String[] args) {
		Parameter[] parameters = getParameters();
		Object[] objs = new Object[args.length];
		boolean goodParameters = true;
		if(args.length == parameters.length){
			if(args.length != 0) {
				for(int i = 0; i < args.length; i++) {
					if(!parameters[i].getParameterType().matches(args[i])) {
						goodParameters = false;
					}else {
						objs[i] = parameters[i].getParameterType().convert(args[i]);
					}
				}
			}
		}else {
			sendUsage(sender);
			return;
		}
		if(goodParameters) {
			execute(sender, objs);
		}
	}

	public void sendUsage(CommandSender sender) {
		MessageUtils.sendMessage(sender, "&a&l--- SubMC Command ---");
		MessageUtils.sendMessage(sender, "&c/"+getName());
		MessageUtils.sendMessage(sender, "&a"+getDescription());
		MessageUtils.sendMessage(sender, "&aUsage: " + getSMCUsage());
		if(getParameters().length != 0) {
			MessageUtils.sendMessage(sender, "&bParameters");
			for(Parameter p : getParameters()) {
				MessageUtils.sendMessage(sender, p.getDescription());
			}
		}
	}

	enum SenderType {
		PLAYER,CONSOLE,BOTH;
	}
}
