package mineward.core.rupee;

import mineward.core.command.ConsoleCommand;

import org.bukkit.command.ConsoleCommandSender;

public class RupeeCCMD extends ConsoleCommand {

	public RupeeCCMD() {
		super("console::purchase::rupee", new String[0], "Add rupees to a user");
	}

	@Override
	public void execute(ConsoleCommandSender s, String[] args) {
		try {

		} catch (Exception e) {
			return;
		}
	}

}
