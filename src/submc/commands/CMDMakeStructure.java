package submc.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import submc.SubMC;
import submc.permissions.Permission;

public class CMDMakeStructure extends SMCCommand{

	protected CMDMakeStructure() {
		super("makestructure");
	}

	@Override
	public Permission getSMCPermission() {
		return Permission.MAKE_STRUCTURE;
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
		return "Used to start the structure making process.";
	}

	@Override
	public String getSMCUsage() {
		return "/makestructure";
	}

	@Override
	public void execute(CommandSender sender, Object[] args) {
		Player p = (Player)sender;
		SubMC.getStructureMaker().makeStructure(p);
	}

	

}
