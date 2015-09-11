package mineward.core.vote;

import mineward.core.command.ConsoleCommand;
import mineward.core.common.utils.F;
import mineward.core.common.utils.UtilCoin;
import mineward.core.common.utils.UtilLevel;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AddvoteCCMD extends ConsoleCommand {

    public AddvoteCCMD() {
        super("console::vote::add", new String[0], "Add a vote reward to a player.");
    }

    @SuppressWarnings("deprecation")
    public void execute(ConsoleCommandSender sender, String[] args) {
        if (args.length > 1) return;
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
            UtilCoin.AddCoins(offlinePlayer, 1000);
            UtilLevel.addXP(offlinePlayer, 500);
        } else {
            UtilCoin.AddCoins(player, 1000);
            UtilLevel.addXP(player, 500);
            F.message(player, "Vote", "Thank you for voting! You have received 1000 coins and 500 experience!");
        }
    }
}