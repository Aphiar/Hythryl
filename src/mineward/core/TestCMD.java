package mineward.core;

import mineward.core.common.Prefix.PrefixColor;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TestCMD extends mineward.core.command.MyCommand {

	public TestCMD() {
		super("br", new String[] { "broadcast" }, "Broadcast a message",
				Rank.Default);
	}

	public void execute(Player p, String[] args) {
		if (args.length == 0) {
			F.message(p, PrefixColor.Easy,
					"Correct Usage: " + F.elem(C.STR_ELEMENT, "/br <message>"));
			return;
		}
		String reason = "";
		for (String arg : args) {
			reason += arg + " ";
		}
		reason = reason.substring(0, reason.length() - 1);
		F.broadcastRaw(ChatColor.translateAlternateColorCodes('&', reason));
	}

}
