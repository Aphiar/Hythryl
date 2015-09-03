package mineward.core.cooldown;

import java.util.ArrayList;
import java.util.List;

import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.common.utils.TimeUtil;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public class Cooldown {

	private static JavaPlugin plugin;

	public static void Enable(JavaPlugin plugin) {
		Cooldown.plugin = plugin;
	}

	public static List<CooldownToken> tokens = new ArrayList<CooldownToken>();

	@SuppressWarnings("deprecation")
	public static boolean Cool(final String cfor, final String data, long time) {
		long ctime = System.currentTimeMillis();
		for (CooldownToken tkn : tokens) {
			if (tkn.Coolfor.equals(cfor) && tkn.Data.equals(data)) {
				long until = (tkn.StartTime + tkn.Time);
				if (until > ctime) {
					OfflinePlayer op = Bukkit.getOfflinePlayer(cfor);
					if (op.isOnline())
						F.message(op.getPlayer(), "Cooldown",
								"You can't use " + C.STR_ELEMENT + tkn.Data
										+ C.STR_MAIN + " for " + C.STR_ELEMENT
										+ TimeUtil.toString(until - ctime)
										+ C.STR_MAIN + ".");
					return true;
				}
			}
		}
		CooldownToken tkn = new CooldownToken(time, ctime, data, cfor);
		tokens.add(tkn);
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				OfflinePlayer op = Bukkit.getOfflinePlayer(cfor);
				if (op.isOnline())
					F.message(op.getPlayer(), "Cooldown", "You can use "
							+ C.STR_ELEMENT + data + C.STR_MAIN + " again.");
			}
		}, (20L * (time / 1000)));
		return false;
	}

}
