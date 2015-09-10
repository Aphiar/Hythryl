package mineward.core.coins;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.common.utils.UtilCoin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CoinsCMD extends MyCommand {

    public CoinsCMD() {
        super("coins", new String[0], "Manipulate a players coins amount.", Rank.Admin);
    }

    @SuppressWarnings("deprecation")
    public void execute(Player p, String[] args) {
        if (args.length <= 1 || args.length > 3) {
            F.help(p, "coins <set|add|remove> <#>", "Set your own coins amount", Rank.Admin);
            F.help(p, "coins <set|add|remove> <player> <#>", "Set a player's coins amount", Rank.Admin);
            return;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 2) {
                try {
                    int coins = Integer.valueOf(args[1]);
                    UtilCoin.SetCoins(p, coins);
                    F.message(p, "Coins", "You set your coin amount to " + args[1] + "!");
                } catch (Exception e) {
                    F.message(p, "Error", args[1] + " is not a number (or is too big)!");
                }
                return;
            } else if (args.length == 3) {
                try {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    int coins = Integer.valueOf(args[2]);
                    UtilCoin.SetCoins(player, coins);
                    F.message(p, "Coins", "You set " + C.STR_PLAYER + player.getName() + C.STR_MAIN + "'s coin amount to " + args[2] + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[2] + " is not a number (or is too big)!");
                }
            }
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (args.length == 2) {
                try {
                    int addamount = Integer.valueOf(args[1]);
                    UtilCoin.AddCoins(p, addamount);
                    F.message(p, "Coins", "You added " + args[1] + " coins to your balance! New balance: " + UtilCoin.GetCoins(p) + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[1] + " is not a number (or is too big)!");
                }
                return;
            } else if (args.length == 3) {
                try {
                    OfflinePlayer pl = Bukkit.getOfflinePlayer(args[1]);
                    int addamount = Integer.valueOf(args[2]);
                    UtilCoin.AddCoins(pl, addamount);
                    F.message(p, "Coins", "You added " + args[2] + " coins to " + C.STR_PLAYER + args[1] + C.STR_MAIN + "'s balance. New balance: " + UtilCoin.GetCoins(pl) + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[2] + " is not a number (or is too big)!");
                }
            }
        }
        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length == 2) {
                try {
                    int removeamount = Integer.valueOf(args[1]);
                    UtilCoin.RemoveCoins(p, removeamount);
                    F.message(p, "Coins", "You removed " + args[1] + " from your balance! New balance: " + UtilCoin.GetCoins(p) + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[1] + " is not a number (or is too big)!");
                }
                return;
            } else if(args.length == 3) {
                try {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    int removeamount = Integer.valueOf(args[2]);
                    UtilCoin.RemoveCoins(player, removeamount);
                    F.message(p, "Coins", "You removed " + args[2] + " from " + C.STR_PLAYER + args[1] + C.STR_MAIN + "'s balance. New balance: " + UtilCoin.GetCoins(player) + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[2] + " is not a number (or is too big)!");
                }
            }
        }
    }
}