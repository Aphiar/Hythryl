package mineward.core.chat;

import mineward.core.chat.messsagemodule.MessageModule;
import mineward.core.command.Muteable;
import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageCMD extends MyCommand implements Muteable {

    public MessageCMD() {
        super("msg", new String[]{"m", "message", "w", "whisper", "t",
                "tell", "pm"}, "Private message a player", Rank.Default);
    }

    @Override
    public void execute(Player p, String[] args) {
        if (args == null || args.length < 2) {
            F.help(p, "msg <player> <message>", "Private message a player",
                    null);
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
            Player target = Bukkit.getPlayer(args[0]);
            MessageModule.message(p, target, msg);
        }
    }

}
