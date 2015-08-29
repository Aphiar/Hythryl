package mineward.core.common.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class UtilConsoleLog {

	public static void Log(String prefix, String msg) {
		Bukkit.getServer()
				.getConsoleSender()
				.sendMessage(
						ChatColor.BLUE + prefix + " > " +  ChatColor.GRAY + " " + msg);
	}

}
