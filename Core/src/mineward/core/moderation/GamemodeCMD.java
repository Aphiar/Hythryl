package mineward.core.moderation;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.F;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeCMD extends MyCommand {

    public GamemodeCMD() {
        super("gamemode", new String[] { "gm" }, "Enter a different gamemode.", Rank.Admin);
    }

    public void execute(Player p, String[] args) {
        if (args.length == 0 || args.length > 1) {
            F.help(p, "gamemode <mode>", "Enter a different gamemode.", Rank.Admin);
            return;
        }

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
    }
}