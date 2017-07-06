package mineward.core.moderation;

import mineward.core.Core;
import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.TimeUtil;
import mineward.core.common.utils.UtilSys;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.lang.management.ManagementFactory;

public class SystemCMD extends MyCommand {

    public static String ServerName = "Unknown";

    public SystemCMD() {
        super("system", new String[]{"sysinfo", "mem", "cpu", "lag", "gc", "memory", "tps"}, "Display Memory Usage/Uptime/TPS", Rank.Admin);
    }

    public void execute(Player p, String[] args) {
        String prefix = "&7[&5System&7]";
        ServerName = new File(Core.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParentFile().getName();
        double lag = Math.round((1.0D - UtilSys.getTPS() / 20.0D) * 100.0D);

        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &3----- &6System &3-----"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Server name: &e" + ServerName));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6TPS: &e" + UtilSys.getTPS()));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Lag Percentage: &e" + lag + "%"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Uptime: &e" + TimeUtil.formateDateDiff(ManagementFactory.getRuntimeMXBean().getStartTime())));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Max Memory: &e" + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + " MB"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Total Memory: &e" + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + " MB"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &6Free Memory: &e" + (Runtime.getRuntime().freeMemory() / 1024 / 1024) + " MB"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " &3----- &6System &3-----"));
    }
}