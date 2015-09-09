package mineward.core;

import java.util.ArrayList;

import mineward.core.achievement.general.ChatAchievement;
import mineward.core.achievement.general.HelpAchievement;
import mineward.core.achievement.general.JoinAchievement;
import mineward.core.achievement.general.MsgAchievement;
import mineward.core.achievement.time.TimeOnline;
import mineward.core.chat.BroadcastCMD;
import mineward.core.chat.CommandsCMD;
import mineward.core.chat.HelpdeskCMD;
import mineward.core.chat.HelpdeskReplyCMD;
import mineward.core.chat.MessageCMD;
import mineward.core.chat.MessageReplyCMD;
import mineward.core.chat.StaffChatCMD;
import mineward.core.coins.SetcoinsCMD;
import mineward.core.common.Database;
import mineward.core.common.Rank;
import mineward.core.common.SubPlugin;
import mineward.core.common.utils.UtilVanish;
import mineward.core.console.SendMessageCMD;
import mineward.core.fun.FartCMD;
import mineward.core.hierarchy.ClassCMD;
import mineward.core.hierarchy.PromoteCMD;
import mineward.core.hierarchy.RankCCMD;
import mineward.core.level.LevelCMD;
import mineward.core.listener.defaultlisteners.ChatListener;
import mineward.core.listener.defaultlisteners.JoinListener;
import mineward.core.listener.defaultlisteners.QuitListener;
import mineward.core.listener.defaultlisteners.WeatherListener;
import mineward.core.moderation.GamemodeCMD;
import mineward.core.moderation.VanishCMD;
import mineward.core.npc.NPCBinder;
import mineward.core.npc.NPCCommand;
import mineward.core.player.HPlayer;
import mineward.core.punish.Punish;
import mineward.core.punish.PunishCMD;
import mineward.core.punish.PunishUIListener;
import mineward.core.punish.ui.RecordUI;
import mineward.core.purchases.ChargebackCCMD;
import mineward.core.purchases.CoinCCMD;
import mineward.core.scoreboard.SBManager;
import mineward.core.stats.StatsCMD;
import mineward.core.stats.StatsGUI;
import mineward.core.teleportation.TPAllCMD;
import mineward.core.teleportation.TPCMD;
import mineward.core.teleportation.TPHereCMD;
import mineward.core.we.BlockProtect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

public class Core extends JavaPlugin {

	private static ArrayList<SubPlugin> plugins = new ArrayList<SubPlugin>();
	private static ArrayList<HPlayer> onlinePlayers = new ArrayList<HPlayer>();
	private static JavaPlugin plugin;

	public void onEnable() {
		plugin = this;
		Database.getConnection();
		// MyFactory.RegisterCommand(new TestCMD(), this);

		AddPlayers();

		this.getServer().getMessenger()
				.registerOutgoingPluginChannel(this, "BungeeCord");

		Punish.Enable(this);

		MyFactory.RegisterCommand(new ClassCMD(), this);
		MyFactory.RegisterCommand(new PromoteCMD(), this);
		MyFactory.RegisterCommand(new BroadcastCMD(), this);
		// MyFactory.RegisterCommand(new KickCMD(), this);
		MyFactory.RegisterCommand(new StaffChatCMD(), this);
		MyFactory.RegisterCommand(new HelpdeskCMD(), this);
		MyFactory.RegisterCommand(new HelpdeskReplyCMD(), this);
		MyFactory.RegisterCommand(new MessageCMD(), this);
		MyFactory.RegisterCommand(new MessageReplyCMD(), this);
		MyFactory.RegisterCommand(new PunishCMD(), this);
		// MyFactory.RegisterCommand(new SkyRocket(), this);
		MyFactory.RegisterCommand(new FartCMD(), this);
		MyFactory.RegisterCommand(new CommandsCMD(), this);
		MyFactory.RegisterCommand(new StatsCMD(), this);
		MyFactory.RegisterCommand(new LevelCMD(), this);

		MyFactory.RegisterCommand(new RankCCMD(), this);
		MyFactory.RegisterCommand(new CoinCCMD(), this);
		MyFactory.RegisterCommand(new ChargebackCCMD(), this);
		MyFactory.RegisterCommand(new NPCCommand(), this);
		MyFactory.RegisterCommand(new SetcoinsCMD(), this);

		MyFactory.RegisterListener(new ChatListener(), this);
		MyFactory.RegisterListener(new JoinListener(), this);
		MyFactory.RegisterListener(new QuitListener(), this);
		MyFactory.RegisterListener(new PunishUIListener(), this);
		MyFactory.RegisterListener(new RecordUI(), this);
		MyFactory.RegisterListener(new StatsGUI(), this);
		MyFactory.RegisterListener(new WeatherListener(), this);
		MyFactory.RegisterListener(new BlockProtect(), this);
		MyFactory.RegisterCommand(new TPCMD(), this);
		MyFactory.RegisterCommand(new TPHereCMD(), this);
		MyFactory.RegisterCommand(new VanishCMD(), this);
		MyFactory.RegisterListener(new UtilVanish(), this);
		MyFactory.RegisterCommand(new GamemodeCMD(), this);
		MyFactory.RegisterCommand(new TPAllCMD(), this);

		MyFactory.RegisterListener(new NPCBinder(), this);
		MyFactory.RegisterCommand(new SendMessageCMD(), this);

		AddAchievements();

		long now = System.currentTimeMillis();

		for (Player p : Bukkit.getOnlinePlayers()) {
			TimeOnline.setOnline(p, now);
			p.setCustomName(ChatColor.GRAY + p.getName());
			SBManager.getScoreboard(p);
			for (Player pl : Bukkit.getOnlinePlayers()) {
				Rank rank = HPlayer.o(pl).getRank();
				Team team = SBManager.getTeam(p, rank.name());
				if (rank != Rank.Default) {
					team.setPrefix(rank.getLabel(true, true) + ChatColor.GRAY
							+ " ");
				} else {
					team.setPrefix(ChatColor.GRAY + "");
				}
				team.addEntry(pl.getName());
				SBManager.updateScoreboard(p, p.getScoreboard());
			}
		}
	}

	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			TimeOnline.uploadToDatabase(p);
			TimeOnline.setOffline(p);
		}
		Database.closeConnection();
	}

	public static void registerSubPlugin(SubPlugin plugin) {
		plugins.add(plugin);
	}

	public static void AddPlayer(HPlayer p) {
		onlinePlayers.add(p);
	}

	public static void RemovePlayer(HPlayer p) {
		onlinePlayers.remove(p);
	}

	public static ArrayList<HPlayer> getOnlinePlayers() {
		return onlinePlayers;
	}

	private void AddPlayers() {
		for (Player plt : Bukkit.getOnlinePlayers()) {
			AddPlayer(HPlayer.a(plt));
		}
	}

	private void AddAchievements() {
		new JoinAchievement();
		new ChatAchievement();
		new HelpAchievement();
		new MsgAchievement();
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

}
