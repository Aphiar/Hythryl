package mineward.core.chat;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.player.HPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpdeskReplyCMD extends MyCommand {

	public HelpdeskReplyCMD() {
		super("hr", new String[] {}, "Reply to a helpdesk request",
				Rank.Jrmod);
	}

	@Override
	public void execute(Player p, String[] args) {
		if (args == null || args.length < 2) {
			F.help(p, "hr <player> <message>", "Reply to a helpdesh request",
					Rank.Jrmod);
		} else {
			try {
				Bukkit.getPlayer(args[0]).getUniqueId();
			} catch (Exception e) {
				F.message(p, "Online Player Search", "Could not find player "
						+ C.STR_PLAYER + args[0] + C.STR_MAIN + ".");
				return;
			}
			String msg = "";
			for (int i = 1; i < (args.length - 1); i++) {
				msg += args[i] + " ";
			}
			msg += args[args.length - 1];
			Bukkit.getPlayer(args[0]).sendMessage(
					ChatColor.BOLD + ">> [HD] "
							+ HPlayer.o(p).getRank().getLabel(false, true)
							+ " " + HPlayer.o(p).getRank().getColor()
							+ p.getName() + ": " + ChatColor.DARK_AQUA + msg);
			for (Player pl : Bukkit.getOnlinePlayers()) {
				if ((HPlayer.o(pl).getRank()
						.isPermissible(Rank.Jrmod))
						&& (!(pl.getUniqueId().equals(Bukkit.getPlayer(args[0])
								.getUniqueId())))) {
					pl.sendMessage(ChatColor.BOLD + "<-" + ChatColor.GREEN
							+ Bukkit.getPlayer(args[0]).getName()
							+ ChatColor.WHITE + "" + ChatColor.BOLD
							+ " >> [HD] "
							+ HPlayer.o(p).getRank().getLabel(false, true)
							+ " " + HPlayer.o(p).getRank().getColor()
							+ p.getName() + ": " + ChatColor.DARK_AQUA + msg);
				}
			}
		}
	}

}
