package mineward.core.chat;

import mineward.core.chat.messsagemodule.MessageModule;
import mineward.core.command.Muteable;
import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class MessageReplyCMD extends MyCommand implements Muteable {

	public MessageReplyCMD() {
		super("r", new String[] { "reply" },
				"Reply to the last person you messaged", Rank.Default);
	}

	@Override
	public void execute(Player p, String[] args) {
		if (args == null || args.length == 0) {
			F.help(p, "r <message>", "Reply to the last person you messaged",
					null);
		} else {
			if (!(MessageModule.replies.containsKey(p.getUniqueId()))) {
				F.message(p, "Messenger",
						"You have not messaged anyone recently.");
				return;
			}
			OfflinePlayer off = Bukkit.getOfflinePlayer(MessageModule.replies
					.get(p.getUniqueId()));
			if (!off.isOnline()) {
				F.message(p, "Messenger", C.STR_PLAYER + off.getName()
						+ C.STR_MAIN + " is no longer online.");
				MessageModule.replies.remove(p.getUniqueId());
				return;
			}
			String msg = "";
			for (int i = 0; i < (args.length - 1); i++) {
				msg += args[i] + " ";
			}
			msg += args[args.length - 1];
			MessageModule.message(p, off.getPlayer(), msg);
		}
	}

}
