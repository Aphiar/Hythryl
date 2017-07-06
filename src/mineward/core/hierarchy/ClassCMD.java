package mineward.core.hierarchy;

import mineward.core.command.MyCommand;
import mineward.core.common.Database;
import mineward.core.common.Prefix.PrefixColor;
import mineward.core.common.PrefixBuilder;
import mineward.core.common.Rank;
import mineward.core.common.database.account.AccountManager;
import mineward.core.common.database.account.AccountManager.Account;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.player.HPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClassCMD extends MyCommand {

    public ClassCMD() {
        super("class", new String[]{"rank"},
                "Set the rank/class of a player.", Rank.Admin);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(Player p, String[] args) {
        if (args == null || args.length == 0 || args.length > 2) {
            F.help(p, "rank <player>", "Checks a player's rank", Rank.Admin);
            F.help(p, "rank <player> <rank>", "Sets the rank of a player",
                    Rank.Admin);
        } else if (args.length == 1) {
            Account acc = AccountManager.getAccount(
                    Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString(),
                    false);
            if (acc == null) {
                F.message(
                        p,
                        PrefixBuilder.getPrefixBuilder(false, "Account Lookup",
                                PrefixColor.Normal).build(),
                        "The Account Lookup for user " + C.STR_PLAYER + args[0]
                                + C.STR_MAIN + " failed.");
                return;
            }
            F.message(p, "Hierarchy", C.STR_PLAYER + args[0] + C.STR_MAIN
                    + "'s rank is " + acc.rank.getLabel(true) + C.STR_MAIN
                    + ".");
            return;
        } else if (args.length == 2) {
            Account acc = AccountManager.getAccount(
                    Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString(),
                    false);
            if (acc == null) {
                F.message(
                        p,
                        PrefixBuilder.getPrefixBuilder(false, "Account Lookup",
                                PrefixColor.Normal).build(),
                        "The Account Lookup for user " + C.STR_PLAYER + args[0]
                                + C.STR_MAIN + " failed.");
                return;
            }
            Rank rank = Rank.Default;
            try {
                rank = Rank.valueOf(((Character) args[1].toCharArray()[0])
                        .toString().toUpperCase()
                        + args[1].substring(1, args[1].length()).toLowerCase());
            } catch (Exception e) {
                F.message(p, "Rank", "There is no rank called " + C.STR_ELEMENT
                        + args[1] + C.STR_MAIN + ".");
                return;
            }
            Database.runUpdateStatement("UPDATE `Account` SET `rank`='"
                    + rank.name() + "' WHERE `uuid`='"
                    + Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString()
                    + "';");
            if (Bukkit.getOfflinePlayer(args[0]).isOnline()) {
                HPlayer.o(Bukkit.getPlayer(args[0])).setRank(rank);
                F.message(Bukkit.getPlayer(args[0]), "Hierarchy", C.STR_PLAYER
                        + p.getName() + C.STR_MAIN + " set your rank to "
                        + rank.getLabel(true) + C.STR_MAIN + ".");
            }
            F.message(p, "Hierarchy", "You set " + C.STR_PLAYER + args[0]
                    + C.STR_MAIN + "'s rank to " + rank.getLabel(true)
                    + C.STR_MAIN + ".");
        }
    }
}
