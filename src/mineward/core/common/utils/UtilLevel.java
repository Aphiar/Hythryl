package mineward.core.common.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import mineward.core.common.Database;
import mineward.core.player.HPlayer;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class UtilLevel {
	static ChatColor[] colors = new ChatColor[] { ChatColor.GRAY,
			ChatColor.DARK_GRAY, ChatColor.WHITE, ChatColor.YELLOW,
			ChatColor.GREEN, ChatColor.DARK_GREEN, ChatColor.AQUA,
			ChatColor.DARK_AQUA, ChatColor.BLUE, ChatColor.DARK_BLUE,
			ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE, ChatColor.GOLD,
			ChatColor.BLACK, ChatColor.RED, ChatColor.DARK_RED };

	public static void setXP(UUID uuid, long xp) {
		Database.runUpdateStatement("UPDATE `Account` SET `xp`='" + xp
				+ "' WHERE `uuid`='" + uuid.toString() + "';");
	}

	public static void setXP(Player p, long xp) {
		Database.runUpdateStatement("UPDATE `Account` SET `xp`='" + xp
				+ "' WHERE `uuid`='" + p.getUniqueId().toString() + "';");
		HPlayer.o(p).setXP(xp);
	}

	public static long getXP(UUID uuid) {
		try {
			long xp = 0;
			PreparedStatement statement = Database.getConnection()
					.prepareStatement(
							"SELECT * FROM `Account` WHERE `uuid`='"
									+ uuid.toString() + "';");
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				xp = rs.getLong("xp");
			}
			rs.close();
			statement.close();
			return xp;
		} catch (Exception e) {
			return 0;
		}
	}

	public static void addXP(UUID uuid, long xp) {
		setXP(uuid, getXP(uuid) + xp);
	}

	public static long addXP(OfflinePlayer p, long xp) {
		long oldXP = getXP(p.getUniqueId());
		long newXP = oldXP + xp;
		setXP(p.getUniqueId(), newXP);
		return newXP;
	}

	public static long addXP(Player p, long xp) {
		long oldXP = getXP(p.getUniqueId());
		long newXP = oldXP + xp;
		setXP(p.getUniqueId(), newXP);
		return newXP;
	}

	public static int getLevel(long xp) {
		long level = 0;
		while (xp > ((3000 * (level + 1)) + (1500 * level))) {
			level++;
		}
		return Long.valueOf(level).intValue();
	}

	public static long getXPToNextLevel(int level) {
		return (3000 * (level + 1)) + (level * 1500);
	}

	public static String getColor(int level) {
		int b = Double.valueOf(Math.floor(level / 10)).intValue();
		if (b > (colors.length - 1)) {
			return colors[colors.length - 1] + "" + ChatColor.BOLD;
		}
		return colors[b] + "";
	}
}