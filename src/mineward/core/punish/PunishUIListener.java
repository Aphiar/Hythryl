package mineward.core.punish;

import mineward.core.common.Database;
import mineward.core.common.Prefix.PrefixColor;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.common.utils.TimeUtil.TimeUnit;
import mineward.core.listener.MyListener;
import mineward.core.player.HPlayer;
import mineward.core.punish.ui.RecordUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PunishUIListener extends MyListener {

    public PunishUIListener() {
        super("PunishUIListener");
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().getType() != Material.AIR) {
                    if (e.getInventory().getTitle().contains("Punish: ")) {
                        if (HPlayer.o(p).getRank().isPermissible(Rank.Jrmod)) {
                            OfflinePlayer pl = Bukkit.getOfflinePlayer(e
                                    .getInventory().getTitle().split(": ")[1]);
                            String reason = ChatColor.stripColor(e
                                    .getInventory().getItem(4).getItemMeta()
                                    .getLore().get(0));
                            String punisher = p.getName();
                            ItemStack item = e.getCurrentItem();
                            e.setCancelled(true);
                            if (item.getType().equals(
                                    Material.STAINED_GLASS_PANE)) {
                                String tLore = ChatColor.stripColor(item
                                        .getItemMeta().getLore().get(0));
                                TimeUnit unit = TimeUnit.valueOf(tLore
                                        .split(" ")[1] + "s");
                                long time = Long.valueOf(tLore.split(" ")[0]
                                        .replace("(", "").replace(".0", ""))
                                        * unit.getSeconds() * 1000;
                                long timepunished = System.currentTimeMillis();
                                PunishType type = PunishType.valueOf(tLore
                                        .split(" ")[2].replace(")", ""));
                                PunishCategory c = PunishCategory.Chat;
                                if ((e.getSlot() >= 18) && (e.getSlot() < 27)) {
                                    c = PunishCategory.General;
                                } else if ((e.getSlot() >= 27)
                                        && (e.getSlot() < 36)) {
                                    c = PunishCategory.Hacking;
                                }
                                Punishment pun = new Punishment(type, time,
                                        reason, punisher, pl, timepunished, c);
                                p.closeInventory();
                                Punish.PunishPlayer(pun, true);
                            } else {
                                if (e.getSlot() == 17) {
                                    Punishment pun = Punish
                                            .getActiveMutePunishment(pl
                                                    .getUniqueId());
                                    Database.runUpdateStatement("UPDATE `Punish` SET `inactive`='1' WHERE `uuid`='"
                                            + pun.punished.getUniqueId()
                                            + "' AND `timepunished`='"
                                            + pun.timepunished + "';");
                                    p.closeInventory();
                                    F.message(p, "Punish", "You unmuted "
                                            + C.STR_PLAYER + pl.getName()
                                            + C.STR_MAIN + ".");
                                    if (pl.isOnline()) {
                                        F.message(pl.getPlayer(), "Punish",
                                                C.STR_PLAYER + p.getName()
                                                        + C.STR_MAIN
                                                        + " unmuted you.");
                                    }
                                }
                                if (e.getSlot() == 26) {
                                    Punishment pun = Punish
                                            .getActiveBanPunishment(pl
                                                    .getUniqueId());
                                    Database.runUpdateStatement("UPDATE `Punish` SET `inactive`='1' WHERE `uuid`='"
                                            + pun.punished.getUniqueId()
                                            + "' AND `timepunished`='"
                                            + pun.timepunished + "';");
                                    p.closeInventory();
                                    F.message(p, "Punish", "You unbanned "
                                            + C.STR_PLAYER + pl.getName()
                                            + C.STR_MAIN + ".");
                                }
                                if (e.getSlot() == 16) {
                                    long timepunished = System
                                            .currentTimeMillis();
                                    PunishType type = PunishType.Mute;
                                    PunishCategory c = PunishCategory.Chat;
                                    Punishment pun = new Punishment(type, -1,
                                            reason, punisher, pl, timepunished,
                                            c);
                                    p.closeInventory();
                                    Punish.PunishPlayer(pun, true);
                                }
                                if (e.getSlot() == 25) {
                                    long timepunished = System
                                            .currentTimeMillis();
                                    PunishType type = PunishType.Ban;
                                    PunishCategory c = PunishCategory.General;
                                    Punishment pun = new Punishment(type, -1,
                                            reason, punisher, pl, timepunished,
                                            c);
                                    p.closeInventory();
                                    Punish.PunishPlayer(pun, true);
                                }
                                if (e.getSlot() == 34) {
                                    long timepunished = System
                                            .currentTimeMillis();
                                    PunishType type = PunishType.Ban;
                                    PunishCategory c = PunishCategory.Hacking;
                                    Punishment pun = new Punishment(type, -1,
                                            reason, punisher, pl, timepunished,
                                            c);
                                    p.closeInventory();
                                    Punish.PunishPlayer(pun, true);
                                }

                                if ((e.getSlot() == 53)
                                        && (item.getType() == Material.ARROW)) {
                                    RecordUI.open(p, pl, reason,
                                            e.getInventory());
                                    return;
                                }

                                if (HPlayer.o(p).getRank()
                                        .isPermissible(Rank.Admin)) {
                                    if (e.getClick().equals(
                                            ClickType.SHIFT_RIGHT)) {
                                        if (e.getSlot() >= 45) {
                                            if (item.getType() != Material.ARROW) {
                                                if (item.getItemMeta()
                                                        .getLore() != null) {
                                                    if (!(item.getItemMeta()
                                                            .getLore()
                                                            .isEmpty())) {
                                                        long id = Long
                                                                .valueOf(
                                                                        ChatColor
                                                                                .stripColor(
                                                                                        item.getItemMeta()
                                                                                                .getLore()
                                                                                                .get(item
                                                                                                        .getItemMeta()
                                                                                                        .getLore()
                                                                                                        .size() - 1))
                                                                                .replace(
                                                                                        "ID",
                                                                                        ""))
                                                                .longValue();
                                                        Database.runUpdateStatement("DELETE FROM `Punish` WHERE `timepunished`='"
                                                                + id + "';");
                                                        p.closeInventory();
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                //
                                //
                                // =======================================
                                // DO NOT CODE UNDERNEATH
                                // =======================================
                                //
                                //

                                if ((e.getSlot() == 15 || e.getSlot() == 24)
                                        && (!(pl.isOnline()))) {
                                    F.message(p, PrefixColor.Normal,
                                            "Player must be online!");
                                    return;
                                }
                                if (e.getSlot() == 15) {
                                    Punish.PunishPlayer(
                                            new Punishment(PunishType.Warn, 0,
                                                    reason, punisher, pl,
                                                    System.currentTimeMillis(),
                                                    PunishCategory.Chat), false);
                                    for (Player pls : Bukkit.getOnlinePlayers()) {
                                        F.message(
                                                pls,
                                                "Punish",
                                                C.STR_PLAYER
                                                        + p.getName()
                                                        + C.STR_MAIN
                                                        + " issued a level 1 warning for "
                                                        + C.STR_PLAYER
                                                        + pl.getName()
                                                        + C.STR_MAIN + ".");
                                        F.message(pls, "Reason", reason);
                                    }
                                    p.closeInventory();
                                }
                                if (e.getSlot() == 24) {
                                    Punish.PunishPlayer(
                                            new Punishment(PunishType.Warn, 0,
                                                    reason, punisher, pl,
                                                    System.currentTimeMillis(),
                                                    PunishCategory.General),
                                            false);
                                    pl.getPlayer().kickPlayer(
                                            ChatColor.WHITE + ""
                                                    + ChatColor.BOLD
                                                    + "You have been kicked!"
                                                    + "\n\n" + ChatColor.WHITE
                                                    + reason + "\n\n"
                                                    + ChatColor.BLUE + ""
                                                    + ChatColor.BOLD
                                                    + "You were kicked by: "
                                                    + ChatColor.YELLOW
                                                    + p.getName());
                                    for (Player pls : Bukkit.getOnlinePlayers()) {
                                        F.message(
                                                pls,
                                                "Punish",
                                                C.STR_PLAYER
                                                        + p.getName()
                                                        + C.STR_MAIN
                                                        + " issued a level 2 warning (kick) for "
                                                        + C.STR_PLAYER
                                                        + pl.getName()
                                                        + C.STR_MAIN + ".");
                                        F.message(pls, "Reason", reason);
                                    }
                                    p.closeInventory();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
