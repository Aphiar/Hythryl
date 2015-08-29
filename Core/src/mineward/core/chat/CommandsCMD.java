package mineward.core.chat;

import mineward.core.command.CommandCenter;
import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import mineward.core.player.HPlayer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandsCMD extends MyCommand {

	public CommandsCMD() {
		super("help", new String[] { "?", "bukkit:help", "minecraft:help",
				"bukkit:?", "minecraft:?", "cmds", "cmd", "command" },
				"View all of the commands you are able to use", Rank.Default);
	}

	@Override
	public void execute(Player p, String[] args) {
		F.message(p, "Help", "Command Help: ");
		for (int i = (Rank.values().length - 1); i >= HPlayer.o(p).getRank()
				.ordinal(); i--) {
			for (MyCommand command : CommandCenter.CommandMap) {
				if (i == command.getRank().ordinal()) {
					p.sendMessage(ChatColor.GOLD + "/" + command.getLabel()
							+ ChatColor.GRAY + " ("
							+ command.getRank().getLabel(false, true)
							+ ChatColor.GRAY + "): " + command.getDescription());
				}
			}
		}
	}

}
