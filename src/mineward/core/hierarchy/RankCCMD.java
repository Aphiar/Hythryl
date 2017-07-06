package mineward.core.hierarchy;

import mineward.core.command.ConsoleCommand;
import mineward.core.common.Database;
import mineward.core.common.Rank;
import mineward.core.common.database.account.AccountManager;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;

public class RankCCMD extends ConsoleCommand {

    public RankCCMD() {
        super("console::purchase::rank", new String[]{}, "Rank somebody.");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(ConsoleCommandSender s, String[] args) {
        if (args.length != 2)
            return;
        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
        Rank rank = Rank.valueOf(args[1]);
        if (AccountManager.hasAccount(p.getUniqueId().toString())) {
            Database.runUpdateStatement("UPDATE `Account` SET `rank`='"
                    + rank.name() + "' WHERE `uuid`='"
                    + Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString()
                    + "';");
            return;
        }
        AccountManager.getAccount(p.getUniqueId().toString(), true);
        Database.runUpdateStatement("UPDATE `Account` SET `rank`='"
                + rank.name() + "' WHERE `uuid`='"
                + Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString()
                + "';");
    }
}
