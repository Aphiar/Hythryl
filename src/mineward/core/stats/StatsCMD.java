package mineward.core.stats;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.database.account.AccountManager;
import mineward.core.common.utils.F;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class StatsCMD extends MyCommand {

    public StatsCMD() {
        super("stats", new String[]{},
                "View your own, or someone else's stats.", Rank.Default);
    }

    @SuppressWarnings("deprecation")
    public void execute(Player p, String[] args) {
        if (args == null || args.length == 0) {
            StatsGUI.open(p, p);
        } else {
            OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
            if (AccountManager.getAccount(pl.getUniqueId().toString(), false) == null) {
                F.message(p, "Player Finder", "Could not find target player.");
                return;
            }
            StatsGUI.open(p, pl);
        }
    }

}
