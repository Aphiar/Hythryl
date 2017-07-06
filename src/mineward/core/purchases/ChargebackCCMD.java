package mineward.core.purchases;

import mineward.core.command.ConsoleCommand;
import mineward.core.punish.Punish;
import mineward.core.punish.PunishCategory;
import mineward.core.punish.PunishType;
import mineward.core.punish.Punishment;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;

public class ChargebackCCMD extends ConsoleCommand {

    public ChargebackCCMD() {
        super("console::chargeback", new String[]{}, "Chargeback player");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(ConsoleCommandSender s, String[] args) {
        if (args.length != 1)
            return;
        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
        Punish.PunishPlayer(new Punishment(PunishType.Ban, -1,
                "Chargeback - No Appeal - Contact Support",
                "Chargeback Watcher", p, System.currentTimeMillis(),
                PunishCategory.General), true);
    }

}
