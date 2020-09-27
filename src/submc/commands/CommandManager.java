package submc.commands;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

public class CommandManager {
	
	public static void registerCommands() {
		registerCommand(new CMDMakeStructure());
		registerCommand(new CMDSetGroup());
	}
	
	private static void registerCommand(SMCCommand command) {
		try {
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

			commandMap.register(command.getName(), command);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
