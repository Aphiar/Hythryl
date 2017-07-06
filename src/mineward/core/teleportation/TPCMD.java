package mineward.core.teleportation;

import mineward.core.command.HostCommand;
import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.player.HPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TPCMD extends MyCommand implements HostCommand {

    public TPCMD() {
        super("tp", new String[0], "Teleport to another player", Rank.Jrmod);
    }

    public void execute(Player p, String[] args) {
        if (args.length == 0 || args.length > 2) {
            F.help(p, "tp <player> (player)", "Teleport to another player!", Rank.Jrmod);
            return;
        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                p.teleport(target);
                F.message(p, "Teleport", "Teleported to " + C.STR_PLAYER + args[0]);
            } else {
                F.message(p, "Teleport", "Player not online!");
            }
            return;
        }

        if (HPlayer.o(p).getRank().isPermissible(Rank.Admin)) {
            Player player1 = Bukkit.getPlayer(args[0]);
            Player player2 = Bukkit.getPlayer(args[1]);

            player1.teleport(player2);
            F.message(player1, "Teleport", C.STR_PLAYER + p.getName() + C.STR_MAIN + " teleported you to " + C.STR_PLAYER + player2.getName());
            F.message(p, "Teleport", "You teleported " + C.STR_PLAYER + player1.getName() + C.STR_MAIN + " to " + C.STR_PLAYER + player2.getName());
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&5Hierarchy&7] &7You need to be at least rank &c&lADMIN &7to do this."));
        }
    }
}