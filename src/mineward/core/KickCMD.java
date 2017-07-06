package mineward.core;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class KickCMD extends MyCommand {

    public KickCMD() {
        super("kick", new String[]{}, "(DEPRECATED) (DO NOT USE).", Rank.Mod);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(Player p, String[] args) {
        if (args == null || args.length < 2) {
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "WARNING: "
                    + ChatColor.RESET + "This command is deprecated.");
            p.sendMessage(ChatColor.RED
                    + ""
                    + ChatColor.BOLD
                    + "WARNING: "
                    + ChatColor.RESET
                    + "Use of this command without proper reason can result in demotion.");
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "WARNING: "
                    + ChatColor.RESET
                    + "Use the GUI to kick players, for the sake of records.");
            F.help(p, "kick <player> <reason>",
                    "Kick a player from the server (Deprecated)", Rank.Mod);
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "WARNING: "
                    + ChatColor.RESET + "This command is deprecated.");
            p.sendMessage(ChatColor.RED
                    + ""
                    + ChatColor.BOLD
                    + "WARNING: "
                    + ChatColor.RESET
                    + "Use of this command without proper reason can result in demotion.");
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "WARNING: "
                    + ChatColor.RESET
                    + "Use the GUI to kick players, for the sake of records.");
        } else {
            if (Bukkit.getOfflinePlayer(args[0]).isOnline()) {
                Player target = Bukkit.getPlayer(args[0]);
                String msg = "";
                for (int i = 1; i < (args.length - 1); i++) {
                    msg += args[i] + " ";
                }
                msg += args[args.length - 1];
                target.kickPlayer(ChatColor.WHITE + "" + ChatColor.BOLD
                        + "You have been kicked!" + "\n\n" + ChatColor.WHITE
                        + msg + "\n\n" + ChatColor.BLUE + "" + ChatColor.BOLD
                        + "You were kicked by: " + ChatColor.YELLOW
                        + p.getName());
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    F.message(pl, "Punish", C.STR_PLAYER + p.getName()
                            + C.STR_MAIN
                            + " issued a level 2 warning (kick) for "
                            + C.STR_PLAYER + target.getName() + C.STR_MAIN
                            + ".");
                    F.message(pl, "Punish Reason", msg);
                }
            } else {
                F.message(p, "Online Players",
                        "There are no online players by the name of "
                                + C.STR_PLAYER + args[0] + C.STR_MAIN + ".");
            }
        }
    }

}
