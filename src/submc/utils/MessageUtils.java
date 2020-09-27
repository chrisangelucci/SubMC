package submc.utils;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class MessageUtils {

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
}
