package mineward.core.listener.defaultlisteners;

import java.lang.reflect.Field;

import mineward.core.Core;
import mineward.core.achievement.general.JoinAchievement;
import mineward.core.achievement.time.TimeOnline;
import mineward.core.common.Database;
import mineward.core.common.Rank;
import mineward.core.common.database.account.AccountManager;
import mineward.core.listener.MyListener;
import mineward.core.listener.custom.SuccessfulJoinEvent;
import mineward.core.player.HPlayer;
import mineward.core.punish.Punish;
import mineward.core.punish.Punishment;
import mineward.core.scoreboard.SBManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.scoreboard.Team;

public class JoinListener extends MyListener {

	public JoinListener() {
		super("DefaultJoin");
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if (Database.isConnectionLost()) {
			return;
		}
		Punishment pun = Punish.getActiveBanPunishment(p.getUniqueId());
		if (pun != null) {
			e.disallow(Result.KICK_OTHER, Punish.getBanMessage(pun));
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.setCustomName("§7" + p.getName());
		if (Database.isConnectionLost()) {
			e.setJoinMessage(null);
			p.sendMessage("No database connection. Reloading...");
			Bukkit.getServer().dispatchCommand(
					Bukkit.getServer().getConsoleSender(), "reload");
		}
		if (!AccountManager.hasAccount(p.getUniqueId().toString())) {
			p.sendMessage("");
			p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD
					+ ">>==>> HYTHRYL NETWORK <<==<<");
			p.sendMessage("");
			p.sendMessage(ChatColor.GRAY
					+ "Welcome to Hythryl, a new Minecraft Minigames Server.");
			p.sendMessage("");
			p.sendMessage("Website: " + ChatColor.GREEN + ""
					+ ChatColor.UNDERLINE + "www.hythryl.com");
			p.sendMessage("Rules: " + ChatColor.GREEN + ""
					+ ChatColor.UNDERLINE + "www.hythryl.com/rules");
			p.sendMessage("");
			AccountManager.getAccount(p.getUniqueId().toString(), true);
		}
		HPlayer player = HPlayer.a(p);
		Core.AddPlayer(player);
		if (Rank.isPermissible(player, Rank.Master, false)) {
			e.setJoinMessage(ChatColor.BLUE + "Join: "
					+ player.getRank().getLabel(true, true) + ChatColor.GRAY
					+ " " + p.getName());
		} else {
			e.setJoinMessage(null);
		}
		TimeOnline.setOnline(p, System.currentTimeMillis());
		new JoinAchievement().Complete(p, true);
		for (Player pl : Bukkit.getOnlinePlayers()) {
			SBManager.getScoreboard(pl);
			for (Player pls : Bukkit.getOnlinePlayers()) {
				Rank rank = HPlayer.o(pls).getRank();
				Team team = SBManager.getTeam(pl, rank.name());
				if (rank != Rank.Default) {
					team.setPrefix(rank.getLabel(true, true) + ChatColor.GRAY
							+ " ");
				} else {
					team.setPrefix(ChatColor.GRAY + "");
				}
				team.addEntry(pls.getName());
				SBManager.updateScoreboard(pl, pl.getScoreboard());
			}
		}

		IChatBaseComponent header = ChatSerializer
				.a("{text:\"§6§lHythryl Network\"}");
		IChatBaseComponent footer = ChatSerializer
				.a("{text:\"You're playing on §bplay.hythryl.net\n§fWebsite: §awww.hythryl.net\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(
				header);
		try {
			Field bf = packet.getClass().getDeclaredField("b");
			bf.setAccessible(true);
			bf.set(packet, footer);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}

		Bukkit.getServer().getPluginManager()
				.callEvent(new SuccessfulJoinEvent(p));
	}

}
