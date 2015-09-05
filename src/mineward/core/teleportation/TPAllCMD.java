package mineward.core.teleportation;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TPAllCMD extends MyCommand {

    public TPAllCMD() {
        super("tpall", new String[] { "teleportall" }, "Teleport all players to yourself!", Rank.Admin);
    }

    public void execute(Player p, String[] args) {
        for (Player onlinep : Bukkit.getOnlinePlayers()) {
            onlinep.teleport(p);
        }
        F.message(p, "Teleport", "Teleported all players to you!");
    }
}