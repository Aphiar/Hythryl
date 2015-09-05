package mineward.core.moderation;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCMD extends MyCommand {

    public GamemodeCMD() {
        super("gamemode", new String[] { "gm" }, "Enter a different gamemode.", Rank.Admin);
    }

    public void execute(Player p, String[] args) {
        if (args.length == 0 || args.length > 2) {
            F.help(p, "gamemode <mode>", "Enter a different gamemode.", Rank.Admin);
            return;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                p.setGameMode(GameMode.SURVIVAL);
                F.message(p, "GameMode", "Entered gamemode survival!");
            }

            if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                p.setGameMode(GameMode.CREATIVE);
                F.message(p, "GameMode", "Entered gamemode creative!");
            }

            if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                p.setGameMode(GameMode.ADVENTURE);
                F.message(p, "GameMode", "Entered gamemode adventure!");
            }

            if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
                p.setGameMode(GameMode.SPECTATOR);
                F.message(p, "GameMode", "Entered gamemode spectator!");
            }
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            p.sendMessage(ChatColor.RED + "That player is not online.");
            return;
        }

        if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
            target.setGameMode(GameMode.SURVIVAL);
            F.message(p, "GameMode", "Set " + C.STR_PLAYER + args[1] + C.STR_MAIN + "'s gamemode to survival!");
        }

        if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
            target.setGameMode(GameMode.CREATIVE);
            F.message(p, "GameMode", "Set " + C.STR_PLAYER + args[1] + C.STR_MAIN + "'s gamemode to creative!");
        }

        if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
            target.setGameMode(GameMode.ADVENTURE);
            F.message(p, "GameMode", "Set " + C.STR_PLAYER + args[1] + C.STR_MAIN + "'s gamemode to adventure!");
        }

        if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
            target.setGameMode(GameMode.SPECTATOR);
            F.message(p, "GameMode", "Set " + C.STR_PLAYER + args[1] + C.STR_MAIN + "'s gamemode to spectator!");
        }
    }
}