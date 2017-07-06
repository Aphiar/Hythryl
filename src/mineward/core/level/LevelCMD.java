package mineward.core.level;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.common.utils.UtilLevel;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class LevelCMD extends MyCommand {

    public LevelCMD() {
        super("setlevel", new String[]{}, "Set your level", Rank.Admin);
    }

    @SuppressWarnings("deprecation")
    public void execute(Player p, String[] args) {
        if (args.length == 0 || args.length > 2) {
            F.help(p, "setlevel <#>", "Set your own level", Rank.Admin);
            F.help(p, "setlevel <player> <#>", "Set a player's level",
                    Rank.Admin);
        } else if (args.length == 1) {
            try {
                int level = Integer.valueOf(args[0]);
                UtilLevel.setXP(p, ((3000 * (level + 1)) + (1500 * level)));
                F.message(p, "Level",
                        "You set your level to [" + UtilLevel.getColor(level)
                                + level + C.STR_MAIN + "].");
            } catch (Exception e) {
                F.message(p, "Error", args[0]
                        + " is not a number (or is too big)");
            }
        } else if (args.length == 2) {
            try {
                OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
                try {
                    pl = Bukkit.getPlayer(args[0]);
                } catch (Exception x) {
                }
                int level = Integer.valueOf(args[1]);
                UtilLevel.setXP(pl.getUniqueId(),
                        ((3000 * (level + 1)) + (1500 * level)));
                F.message(p, "Level", "You set " + pl.getName()
                        + "'s level to [" + UtilLevel.getColor(level) + level
                        + C.STR_MAIN + "].");
            } catch (Exception e) {
                F.message(p, "Error", args[1]
                        + " is not a number (or is too big)");
            }
        }
    }
}
