package mineward.core.chat;

import mineward.core.achievement.general.HelpAchievement;
import mineward.core.command.Muteable;
import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import mineward.core.player.HPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpdeskCMD extends MyCommand implements Muteable {

    public HelpdeskCMD() {
        super("h", new String[]{}, "Ask for help using our helpdesk",
                Rank.Default);
    }

    @Override
    public void execute(Player p, String[] args) {
        if (args == null || args.length == 0) {
            F.help(p, "h <message>",
                    "Send a message to all online staff through the helpdesk.",
                    null);
        } else {
            String msg = "";
            for (int i = 0; i < (args.length - 1); i++) {
                msg += args[i] + " ";
            }
            msg += args[args.length - 1];
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if ((HPlayer.o(pl).getRank().isPermissible(Rank.Jrmod))
                        || (p.getUniqueId().equals(pl.getUniqueId()))) {
                    pl.sendMessage(ChatColor.BOLD + "[HD] "
                            + HPlayer.o(p).getRank().getLabel(false)
                            + HPlayer.o(p).getRank().getColor() + " "
                            + p.getName() + ": " + ChatColor.DARK_AQUA + msg);
                }
            }
            new HelpAchievement().Complete(p, true);
        }
    }

}
