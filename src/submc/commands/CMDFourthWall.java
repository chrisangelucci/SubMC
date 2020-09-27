package submc.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import submc.permissions.Permission;
import submc.slapi.PlayerProfile;

public class CMDFourthWall extends SMCCommand {

	public CMDFourthWall() {
		super("fourthwall");
		List<String> aliases = new ArrayList<String>();
		aliases.add("fw");
		setAliases(aliases);
	}
	
	@Override
	public Permission getSMCPermission() {
		return Permission.FOURTH_WALL;
	}

	@Override
	public SenderType getSenderType() {
		return SenderType.PLAYER;
	}

	@Override
	public Parameter[] getParameters() {
		return new Parameter[] {};
	}

	@Override
	public String getDescription() {
		return "Used to toggle an admin state. When you are in fourth wall mode, the server does not count you as a player.";
	}

	@Override
	public String getSMCUsage() {
		return "/fourthwall";
	}

	@Override
	public void execute(CommandSender sender, Object[] args) {
		Player player = (Player)sender;
		PlayerProfile.getProfile(player).toggleFourthWall();
	}

}
