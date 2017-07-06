package mineward.core.purchases;

import mineward.core.command.ConsoleCommand;
import mineward.core.common.database.account.AccountManager;
import mineward.core.common.utils.UtilCoin;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;

public class CoinCCMD extends ConsoleCommand {

    public CoinCCMD() {
        super("console::purchase::coin", new String[]{}, "Add coins to a user");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(ConsoleCommandSender s, String[] args) {
        if (args.length != 2)
            return;
        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
        Integer amount = null;
        try {
            amount = Integer.valueOf(args[1]);
        } catch (Exception e) {
            return;
        }
        if (amount == null)
            return;
        AccountManager.getAccount(p.getUniqueId().toString(), true);
        UtilCoin.AddCoins(p, amount);
    }

}
