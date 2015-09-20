package mineward.core.common.utils;

import mineward.core.common.Prefix;
import mineward.core.common.Prefix.PrefixColor;
import mineward.core.common.PrefixBuilder;
import mineward.core.common.Rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class F {

	public static void message(Player p, Prefix pr, String str) {
		p.sendMessage(pr.toString() + C.STR_MAIN + "" + str);
	}

	public static void message(Player p, String prefix, String str) {
		message(p,
				PrefixBuilder.getPrefixBuilder(false, prefix,
						PrefixColor.Normal).build(), str);
	}

	public static void message(Player p, PrefixColor c, String str) {
		message(p, UtilPrefix.buildPrefix(c), str);
	}

	public static void message(Player p, PrefixColor c, String prefix,
			String str) {
		message(p, UtilPrefix.buildPrefix(prefix, c), str);
	}

	public static void broadcastMessage(Prefix pr, String str) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			message(p, pr, str);
		}
	}

	public static void broadcastRaw(String str) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(str);
		}
	}

	public static String elem(String c, String m) {
		return c + m + C.STR_MAIN;
	}

	public static ChatColor[] rainbow() {
		return new ChatColor[] { ChatColor.RED, ChatColor.GOLD,
				ChatColor.YELLOW, ChatColor.GREEN, ChatColor.AQUA,
				ChatColor.BLUE, ChatColor.DARK_PURPLE };
	}

	public static void help(Player p, String cmd, String desc, Rank rank) {
		String rankMSG = rank == null ? "" : " " + rank.getLabel(false);
		p.sendMessage(ChatColor.GREEN + "/" + cmd + ChatColor.GRAY + ": "
				+ desc + rankMSG);
	}

}
