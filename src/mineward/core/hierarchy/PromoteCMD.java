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

public class PromoteCMD extends MyCommand {

    public PromoteCMD() {
        super("promote", new String[]{},
                "Promote a player on the rank ladder", Rank.Admin);
    }

    @SuppressWarnings("deprecation")
    public void execute(Player p, String[] args) {
        if (args == null || args.length != 1) {
            F.help(p, "promote <player>",
                    "Promote a player on the rank ladder", Rank.Admin);
        } else {
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
            if (acc.rank.ordinal() == 0) {
                F.message(p, "Hierarchy", "Player " + args[0]
                        + " is already at the highest rank!");
                return;
            }
            Rank rank = Rank.values()[acc.rank.ordinal() - 1];
            Database.runUpdateStatement("UPDATE `Account` SET `rank`='"
                    + rank.name() + "' WHERE `uuid`='"
                    + Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString()
                    + "';");
            if (Bukkit.getOfflinePlayer(args[0]).isOnline()) {
                HPlayer.o(Bukkit.getPlayer(args[0])).setRank(rank);
                F.message(Bukkit.getPlayer(args[0]), "Hierarchy",
                        C.STR_PLAYER + p.getName() + C.STR_MAIN
                                + " promoted you to " + rank.getLabel(true)
                                + C.STR_MAIN + ".");
            }
            F.message(p, "Hierarchy", "You promoted " + C.STR_PLAYER + args[0]
                    + C.STR_MAIN + " to " + rank.getLabel(true) + C.STR_MAIN
                    + ".");
        }
    }

}
