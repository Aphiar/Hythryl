package mineward.core.teleportation;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TPHereCMD extends MyCommand {

    public TPHereCMD() {
        super("tphere", new String[0], "Teleport a player to you!", Rank.Admin);
    }

    public void execute(Player p, String[] args) {
        if (args.length == 0 || args.length > 1) {
            F.help(p, "tphere <player>", "Teleport a player to you!", Rank.Admin);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            target.teleport(p);
            F.message(p, "Teleport", "Teleported player " + C.STR_PLAYER + args[0] + C.STR_MAIN + " to you!");
        } else {
            F.message(p, "Teleport", "Player not online!");
        }
    }
}