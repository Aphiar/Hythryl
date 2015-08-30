package mineward.core.teleportation;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TPCMD extends MyCommand {

    public TPCMD() {
        super("tp", new String[0], "Teleport to another player", Rank.Jrmod);
    }

    public void execute(Player p, String[] args) {
        if (args.length == 0) {
            F.help(p, "tp <player>", "Teleport to another player!", Rank.Jrmod);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            p.teleport(target);
            F.message(p, "Teleport", "Teleported to " + C.STR_PLAYER + args[0]);
        } else {
            F.message(p, "Teleport", "Player not online!");
        }
    }
}