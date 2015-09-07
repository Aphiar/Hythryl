package mineward.core.console;

import mineward.core.command.ConsoleCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class SendMessageCMD extends ConsoleCommand {

	public SendMessageCMD() {
		super("sendmessage", new String[0], "Send a message to a player");
	}

	@Override
	public void execute(ConsoleCommandSender s, String[] args) {
		if (args.length < 2)
			return;
		try {
			String msg = "";
			for (int i = 1; i < args.length - 1; i++) {
				msg += args[i];
			}
			msg += args[args.length - 1];
			msg = ChatColor.translateAlternateColorCodes('&', msg);
			Bukkit.getPlayer(args[0]).sendMessage(msg);
		} catch (Exception e) {
			return;
		}
	}

}
