package mineward.core.rupee;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.common.utils.UtilMoney;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class RupeeCMD extends MyCommand {

    public RupeeCMD() {
        super("rupee", new String[]{"rupees"}, "Manage Rupees", Rank.Admin);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(Player p, String[] args) {
        if (args.length == 0 || args.length > 3) {
            F.help(p, "Rupees <set|add|remove|reset> <#>",
                    "Set your own Rupees amount", Rank.Admin);
            F.help(p, "Rupees <set|add|remove|reset> <player> <#>",
                    "Set a player's Rupees amount", Rank.Admin);
            return;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 2) {
                try {
                    int Rupees = Integer.valueOf(args[1]);
                    UtilMoney.SetMoney(p, Rupees, "rupees");
                    F.message(p, "Rupees", "You set your Rupee amount to "
                            + args[1] + "!");
                } catch (Exception e) {
                    F.message(p, "Error", args[1]
                            + " is not a number (or is too big)!");
                }
                return;
            } else if (args.length == 3) {
                try {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    int Rupees = Integer.valueOf(args[2]);
                    UtilMoney.SetMoney(player, Rupees, "rupees");
                    F.message(p, "Rupees",
                            "You set " + C.STR_PLAYER + player.getName()
                                    + C.STR_MAIN + "'s Rupee amount to "
                                    + args[2] + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[2]
                            + " is not a number (or is too big)!");
                }
            }
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (args.length == 2) {
                try {
                    int addamount = Integer.valueOf(args[1]);
                    UtilMoney.AddMoney(p, addamount, "rupees");
                    F.message(p, "Rupees", "You added " + args[1]
                            + " Rupees to your balance! New balance: "
                            + UtilMoney.GetMoney(p, "rupees") + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[1]
                            + " is not a number (or is too big)!");
                }
                return;
            } else if (args.length == 3) {
                try {
                    OfflinePlayer pl = Bukkit.getOfflinePlayer(args[1]);
                    int addamount = Integer.valueOf(args[2]);
                    UtilMoney.AddMoney(pl, addamount, "rupees");
                    F.message(p, "Rupees", "You added " + args[2]
                            + " Rupees to " + C.STR_PLAYER + args[1]
                            + C.STR_MAIN + "'s balance. New balance: "
                            + UtilMoney.GetMoney(pl, "rupees") + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[2]
                            + " is not a number (or is too big)!");
                }
            }
        }
        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length == 2) {
                try {
                    int removeamount = Integer.valueOf(args[1]);
                    UtilMoney.RemoveMoney(p, removeamount, "rupees");
                    F.message(p, "Rupees",
                            "You removed " + args[1]
                                    + " from your balance! New balance: "
                                    + UtilMoney.GetMoney(p, "rupees") + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[1]
                            + " is not a number (or is too big)!");
                }
                return;
            } else if (args.length == 3) {
                try {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    int removeamount = Integer.valueOf(args[2]);
                    UtilMoney.RemoveMoney(player, removeamount, "rupees");
                    F.message(
                            p,
                            "Rupees",
                            "You removed " + args[2] + " from " + C.STR_PLAYER
                                    + args[1] + C.STR_MAIN
                                    + "'s balance. New balance: "
                                    + UtilMoney.GetMoney(player, "rupees")
                                    + "!");
                } catch (Exception ex) {
                    F.message(p, "Error", args[2]
                            + " is not a number (or is too big)!");
                }
            }
        }
        if (args[0].equalsIgnoreCase("reset")) {
            if (args.length == 1) {
                UtilMoney.SetMoney(p, 0, "rupees");
                F.message(p, "Rupees",
                        "You have reset your Rupees successfully!");
                return;
            } else if (args.length == 2) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                UtilMoney.SetMoney(player, 0, "rupees");
                F.message(p, "Rupees", "You have successfully reset "
                        + C.STR_PLAYER + args[0] + C.STR_MAIN + "'s amount!");
            }
        }
    }

}
