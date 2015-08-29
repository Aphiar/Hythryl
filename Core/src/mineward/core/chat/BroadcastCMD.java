package mineward.core.chat;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import mineward.core.player.HPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadcastCMD extends MyCommand {

	public BroadcastCMD() {
		super("br", new String[] { "say" },
				"Broadcast a message to your server", Rank.Mod);
	}

	@Override
	public void execute(Player p, String[] args) {
		if (args == null || args.length == 0) {
			F.help(p, "br <message>", "Broadcast a message to the server",
					Rank.Jrmod);
		} else {
			String msg = "";
			for (int i = 0; i < (args.length - 1); i++) {
				msg += args[i] + " ";
			}
			msg += args[args.length - 1];
			String darkredline = ChatColor.DARK_RED + ""
					+ ChatColor.STRIKETHROUGH
					+ "---------------------------------------------";
			for (Player target : Bukkit.getOnlinePlayers()) {
				target.sendMessage(new String[] {
						darkredline,
						ChatColor.GRAY + "Server Message From "
								+ HPlayer.o(p).getRank().getLabel(false, true)
								+ ChatColor.GREEN + " " + p.getName()
								+ ChatColor.GRAY + ":",
						ChatColor.YELLOW + "  " + msg, darkredline });
			}
		}
	}

}
