package mineward.core.punish;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import mineward.core.player.HPlayer;
import mineward.core.punish.ui.PunishUI;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PunishCMD extends MyCommand {

    public PunishCMD() {
        super("punish", new String[]{"p"},
                "Punish a player if they broke the rules", Rank.Jrmod);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(Player p, String[] args) {
        if (HPlayer.o(p).getRank().equals(Rank.Admin)) {
            F.message(p, "Punish", "Sorry, punish is broken at the moment.");
            return;
        }
        if (args == null || args.length < 2) {
            F.help(p, "p <player> <Reason>", "Ban/mute/warn a player.",
                    Rank.Jrmod);
        } else {
            String msg = "";
            for (int i = 1; i < (args.length - 1); i++) {
                msg += args[i] + " ";
            }
            msg += args[args.length - 1];
            try {
                Player pl = Bukkit.getPlayer(args[0]);
                PunishUI.open(p, pl, msg);
            } catch (Exception e) {
                OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
                PunishUI.open(p, op, msg);
            }
        }
    }

}
