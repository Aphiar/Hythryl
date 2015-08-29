package mineward.core.punish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mineward.core.common.Database;
import mineward.core.common.Prefix.PrefixColor;
import mineward.core.common.PrefixBuilder;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.common.utils.TimeUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Punish {

	private static JavaPlugin plugin;

	public static void Enable(JavaPlugin pl) {
		plugin = pl;
	}

	public static String getBanMessage(Punishment pun) {
		String time = pun.time == -1 ? "Forever" : TimeUtil
				.toString((pun.time + pun.timepunished)
						- System.currentTimeMillis());
		return "\n" + ChatColor.AQUA + "" + ChatColor.BOLD
				+ "You were accused of " + ChatColor.WHITE + pun.reason
				+ "\n\n" + ChatColor.WHITE + "" + ChatColor.BOLD + ">> Judge "
				+ pun.punisher + " has decided your fate <<" + "\n\n"
				+ ChatColor.RED + "" + ChatColor.BOLD
				+ "You have been banned from this server!" + "\n"
				+ ChatColor.AQUA + "" + ChatColor.BOLD
				+ "Your sentence will last " + ChatColor.WHITE + time
				+ ChatColor.AQUA + "" + ChatColor.BOLD + "." + "\n\n"
				+ ChatColor.GREEN
				+ "To reverse this action, you may appeal at " + ChatColor.AQUA
				+ "" + ChatColor.UNDERLINE + "www.hythryl.com/appeal"
				+ ChatColor.GREEN + ".";
	}

	public static Punishment getActiveMutePunishment(UUID id) {
		Punishment mute = null;
		for (Punishment pun : getActivePunishments(id)) {
			if (pun.type == PunishType.Mute) {
				mute = pun;
				break;
			}
		}
		return mute;
	}

	public static Punishment getActiveBanPunishment(UUID id) {
		Punishment ban = null;
		for (Punishment pun : getActivePunishments(id)) {
			if (pun.type == PunishType.Ban) {
				ban = pun;
				break;
			}
		}
		return ban;
	}

	public static boolean isActive(Punishment pun) {
		if ((pun.time == -1)
				|| (pun.time + pun.timepunished >= System.currentTimeMillis())) {
			if (pun.inactive == 0) {
				return true;
			}
		}
		return false;
	}

	public static List<Punishment> getActivePunishments(UUID id) {
		List<Punishment> puns = new ArrayList<Punishment>();
		Punishment mute = null;
		Punishment ban = null;
		for (Punishment pun : getPunishments(id)) {
			if ((pun.time == -1)
					|| (pun.time + pun.timepunished >= System
							.currentTimeMillis())) {
				if (pun.inactive == 0) {
					if (pun.type == PunishType.Mute) {
						if (mute == null) {
							mute = pun;
						} else if (pun.timepunished > mute.timepunished) {
							mute = pun;
						}
					} else if (pun.type == PunishType.Ban) {
						if (ban == null) {
							ban = pun;
						} else if (pun.timepunished > ban.timepunished) {
							ban = pun;
						}
					}
				}
			}
		}
		if (mute != null) {
			puns.add(mute);
		}
		if (ban != null) {
			puns.add(ban);
		}
		return puns;
	}

	public static List<Punishment> getPunishments(UUID id) {
		List<Punishment> punishments = new ArrayList<Punishment>();
		try {
			Connection conn = Database.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT * FROM `Punish` WHERE `uuid`='"
							+ id.toString() + "';");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				String punisher = rs.getString("punisher");
				long time = rs.getLong("time");
				long timepunished = rs.getLong("timepunished");
				String reason = rs.getString("reason");
				PunishType type = PunishType.valueOf(rs.getString("type"));
				PunishCategory category = PunishCategory.valueOf(rs
						.getString("category"));
				Punishment p = new Punishment(type, time, reason, punisher,
						Bukkit.getOfflinePlayer(id), timepunished, category);
				p.inactive = rs.getInt("inactive");
				punishments.add(p);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return punishments;
	}

	public static void PunishPlayer(Punishment p, boolean announce) {
		Database.runUpdateStatement("INSERT INTO `Punish`(`uuid`,`punisher`,`time`,`timepunished`,`reason`,`type`,`inactive`,`category`) VALUES('"
				+ p.punished.getUniqueId().toString()
				+ "','"
				+ p.punisher
				+ "','"
				+ p.time
				+ "','"
				+ p.timepunished
				+ "','"
				+ p.reason
				+ "','" + p.type.name() + "','0','" + p.category.name() + "');");
		if (p.type == PunishType.Ban) {
			if (p.punished.isOnline()) {
				p.punished.getPlayer().kickPlayer(Punish.getBanMessage(p));
			}
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("KickPlayer");
			out.writeUTF(p.punished.getName());
			out.writeUTF(Punish.getBanMessage(p));
			Iterables.getFirst(Bukkit.getOnlinePlayers(), null)
					.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		}
		if (announce) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				String time = p.time == -1 ? "Forever" : TimeUtil
						.toString(p.time);
				if (p.type == PunishType.Ban) {
					F.message(
							pl,
							PrefixBuilder.getPrefixBuilder(false, "Punish",
									PrefixColor.Important).build(),
							C.STR_PLAYER + p.punisher + C.STR_MAIN + " banned "
									+ C.STR_PLAYER + p.punished.getName()
									+ C.STR_MAIN + " for " + C.STR_ELEMENT
									+ time + C.STR_MAIN + ".");
				} else if (p.type == PunishType.Mute) {
					F.message(
							pl,
							PrefixBuilder.getPrefixBuilder(false, "Punish",
									PrefixColor.Important).build(),
							C.STR_PLAYER + p.punisher + C.STR_MAIN + " muted "
									+ C.STR_PLAYER + p.punished.getName()
									+ C.STR_MAIN + " for " + C.STR_ELEMENT
									+ time + C.STR_MAIN + ".");
				} else if (p.type == PunishType.Warn) {
					F.message(
							pl,
							PrefixBuilder.getPrefixBuilder(false, "Punish",
									PrefixColor.Important).build(),
							C.STR_PLAYER + p.punisher + C.STR_MAIN + " warned "
									+ C.STR_PLAYER + p.punished.getName()
									+ C.STR_MAIN + ".");
				}
				F.message(
						pl,
						PrefixBuilder.getPrefixBuilder(false, "Reason",
								PrefixColor.Important).build(), p.reason);
			}
		}
	}

}
