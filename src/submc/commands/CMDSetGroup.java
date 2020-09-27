package submc.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import submc.commands.Parameter.ParameterType;
import submc.permissions.Group;
import submc.permissions.Permission;
import submc.slapi.PlayerProfile;

public class CMDSetGroup extends SMCCommand {

	protected CMDSetGroup() {
		super("setgroup");
	}

	@Override
	public Permission getSMCPermission() {
		return Permission.SET_GROUP;
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.BOTH;
	}
	
	@Override
	public Parameter[] getParameters() {
		return new Parameter[] {
			new Parameter(ParameterType.PLAYER, "<player> - Online player name"),
			new Parameter(ParameterType.GROUP, "<group> - Group name")
		};
	}
	
	@Override
	public String getDescription() {
		return "Used to set a player's group.";
	}
	
	@Override
	public String getSMCUsage() {
		return "/setgroup <player> <group>";
	}
	
	@Override
	public void execute(CommandSender sender, Object[] args) {
		Player player = (Player) args[0];
		Group group = (Group) args[1];
		PlayerProfile.getProfile(player).getPermissionProfile().setGroup(group);
		sender.sendMessage(ChatColor.RED + player.getName() + ChatColor.GREEN + " is now in the group " + ChatColor.RED + group.toString());
	}

}
