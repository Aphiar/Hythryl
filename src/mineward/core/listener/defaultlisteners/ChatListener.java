package mineward.core.listener.defaultlisteners;

import java.util.ArrayList;
import java.util.UUID;

import mineward.core.achievement.general.ChatAchievement;
import mineward.core.common.Prefix.PrefixColor;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.common.utils.TimeUtil;
import mineward.core.common.utils.UtilFilter;
import mineward.core.common.utils.UtilLevel;
import mineward.core.listener.MyListener;
import mineward.core.player.HPlayer;
import mineward.core.punish.Punish;
import mineward.core.punish.Punishment;
import mineward.core.siri.HythrylBot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener extends MyListener {

	public ChatListener() {
		super("DefaultChat");
	}

	public static ArrayList<UUID> staffChat = new ArrayList<UUID>();

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		if (p.getCustomName() == null) {
			p.setCustomName("§7" + p.getName());
		}
		String name = p.getCustomName();
		Rank rank = Rank.Default;
		try {
			rank = HPlayer.o(p).getRank();
		} catch (Exception ex) {

		}
		if (staffChat.contains(p.getUniqueId())) {
			e.setCancelled(true);
			for (UUID uuid : staffChat) {
				Player pl = Bukkit.getPlayer(uuid);
				pl.sendMessage(ChatColor.AQUA + "[STAFF] " + ChatColor.GREEN
						+ rank.getName() + " " + p.getName() + ": "
						+ ChatColor.LIGHT_PURPLE + msg);
			}
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if (!(staffChat.contains(pl.getUniqueId()))) {
					if (HPlayer.o(pl).getRank().isPermissible(Rank.Jrmod)) {
						pl.sendMessage(ChatColor.AQUA + "[STAFF] "
								+ ChatColor.GREEN + rank.getName() + " "
								+ p.getName() + ": " + ChatColor.LIGHT_PURPLE
								+ msg);
					}
				}
			}
			return;
		}
		Punishment pun = Punish.getActiveMutePunishment(p.getUniqueId());
		if (pun != null) {
			e.setCancelled(true);
			String time = pun.time == -1 ? "Forever" : TimeUtil
					.toString((pun.time + pun.timepunished)
							- System.currentTimeMillis());
			F.message(p, PrefixColor.Important, C.STR_PLAYER + pun.punisher
					+ C.STR_MAIN + " forbids you from speaking for "
					+ C.STR_ELEMENT + time + C.STR_MAIN + " because you were "
					+ C.STR_ELEMENT + pun.reason);
			return;
		}

		ArrayList<String> bannedwords = UtilFilter.getBannedWords();

		for (String words : bannedwords) {
			if (msg.contains(words)) {
				e.setCancelled(true);
				F.message(p, "Chat",
						"Please do not swear! It is against the rules!");
				return;
			}
		}

		msg = msg.replace("%", "%%");
		if (msg.toUpperCase().startsWith("SIRI")) {
			HythrylBot.Siri(p, msg);
			e.setCancelled(true);
			return;
		}
		int level = UtilLevel.getLevel(HPlayer.o(p).getXP());
		if (rank.equals(Rank.Default)) {
			e.setFormat(ChatColor.GRAY + "[" + UtilLevel.getColor(level)
					+ level + ChatColor.GRAY + "]" + " " + name
					+ ChatColor.GRAY + ": " + ChatColor.WHITE + msg);
		} else {
			if (rank.isPermissible(Rank.Admin)) {
				msg = ChatColor.translateAlternateColorCodes('&', msg);
			}
			e.setFormat(ChatColor.GRAY + "[" + UtilLevel.getColor(level)
					+ level + ChatColor.GRAY + "]" + " " + rank.getLabel(true)
					+ " " + name + ChatColor.GRAY + ": " + ChatColor.WHITE
					+ msg);
		}
		new ChatAchievement().Complete(p, true);
	}

}
