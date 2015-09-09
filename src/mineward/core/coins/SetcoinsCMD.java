package mineward.core.coins;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.common.utils.UtilCoin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SetcoinsCMD extends MyCommand {

    public SetcoinsCMD() {
        super("setcoins", new String[0], "Set a players coins.", Rank.Admin);
    }

    public void execute(Player p, String[] args) {
        if (args.length == 0 || args.length > 2) {
            F.help(p, "setcoins <#>", "Set your own coins amount", Rank.Admin);
            F.help(p, "setcoins <player> <#>", "Set a player's coins amount", Rank.Admin);
        } else if (args.length == 1) {
            try {
                int coins = Integer.valueOf(args[0]);
                UtilCoin.SetCoins(p, coins);
                F.message(p, "Coins", "You set your coin amount to " + args[0] + "!");
            } catch (Exception e) {
                F.message(p, "Error", args[0] + " is not a number (or is too big)");
            }
        } else if (args.length == 2) {
            try {
                OfflinePlayer pl = Bukkit.getOfflinePlayer(args[0]);
                try {
                    pl = Bukkit.getPlayer(args[0]);
                } catch (Exception ex) {

                }
                int coins = Integer.valueOf(args[1]);
                UtilCoin.SetCoins(pl, coins);
                F.message(p, "Coins", "You set " + C.STR_PLAYER + pl.getName() + C.STR_MAIN + "'s coin amount to " + args[1] + "!");
            } catch (Exception ex) {
                F.message(p, "Error", args[1] + " is not a number (or is too big)!");
            }
        }
    }
}