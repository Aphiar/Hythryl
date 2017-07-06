package mineward.core.rupee;

import mineward.core.command.ConsoleCommand;

import mineward.core.common.utils.UtilCoin;
import mineward.core.common.utils.UtilMoney;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;

public class RupeeCCMD extends ConsoleCommand {

    public RupeeCCMD() {
        super("console::purchase::rupee", new String[0], "Add rupees to a user");
    }

    @Override
    public void execute(ConsoleCommandSender s, String[] args) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
        UtilMoney.AddMoney(p, Integer.parseInt(args[1]), "rupees");
    }

}
