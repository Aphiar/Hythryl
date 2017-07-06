package mineward.core.punish.ui;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import mineward.core.common.Database;
import mineward.core.common.Rank;
import mineward.core.common.utils.TimeUtil;
import mineward.core.gui.Button;
import mineward.core.gui.GUI;
import mineward.core.listener.MyListener;
import mineward.core.player.HPlayer;
import mineward.core.punish.Punish;
import mineward.core.punish.PunishCategory;
import mineward.core.punish.PunishType;
import mineward.core.punish.Punishment;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RecordUI extends MyListener {

    public RecordUI() {
        super("PunishmentRecordListener");
    }

    public static HashMap<UUID, Inventory> backs = new HashMap<UUID, Inventory>();

    public static void open(Player p, OfflinePlayer t, String reason,
                            Inventory backTo) {
        GUI gui = new GUI("Record: " + t.getName(), 54);
        if (backTo != null) {
            backs.put(p.getUniqueId(), backTo);
            gui.AddButton(new Button(null, ChatColor.YELLOW + "<< Back",
                    new String[]{}, new ItemStack(Material.ARROW, 1), 4));
        }
        List<Punishment> listPun = Punish.getPunishments(t.getUniqueId());
        int count = 0;
        for (Punishment pun : listPun) {
            count++;
            boolean active = Punish.isActive(pun);
            ItemStack item = pun.category == PunishCategory.Chat ? new ItemStack(
                    Material.BOOK, 1)
                    : (pun.category == PunishCategory.General ? new ItemStack(
                    Material.IRON_CHESTPLATE) : new ItemStack(
                    Material.TNT));
            if (pun.type == PunishType.Warn) {
                item = pun.category == PunishCategory.Chat ? new ItemStack(
                        Material.MAP, 1) : new ItemStack(
                        Material.LEATHER_BOOTS, 1);
            }
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName((active ? ChatColor.GREEN : ChatColor.RED) + ""
                    + ChatColor.BOLD + "Offense #" + count);
            List<String> lore = new ArrayList<String>();
            String time = pun.time == -1 ? "Permanent" : TimeUtil
                    .toString(pun.time);
            if (pun.type == PunishType.Warn) {
                int sev = pun.category == PunishCategory.Chat ? 1 : 2;
                lore.add(ChatColor.GRAY + "Severity " + sev + " Warning");
            } else {
                lore.add(ChatColor.GRAY + time);
            }
            lore.add(ChatColor.YELLOW + "Issued By: " + ChatColor.WHITE
                    + pun.punisher);
            Timestamp date = new Timestamp(pun.timepunished);
            String sdt = date.toString();
            String actual = sdt.split(" ")[0];
            String[] ttime2 = sdt.split(" ")[1].split(":");
            actual += " " + ttime2[0] + ":" + ttime2[1];
            lore.add(ChatColor.YELLOW + "Issued On: " + ChatColor.WHITE
                    + actual);
            if (pun.type == PunishType.Warn) {
                String sev = pun.category == PunishCategory.Chat ? "In Chat"
                        : "Kick";
                lore.add(ChatColor.YELLOW + "Warn Type: " + ChatColor.WHITE
                        + sev);
            } else {
                lore.add(ChatColor.YELLOW + "Category: " + ChatColor.WHITE
                        + pun.category.name());
            }
            lore.add(ChatColor.YELLOW + "Reason: ");
            int temp = 0;
            String nextr = "";
            for (String str : pun.reason.split(" ")) {
                if (temp < 32) {
                    nextr += str + " ";
                } else {
                    lore.add(ChatColor.WHITE
                            + nextr.substring(0, nextr.length() - 1));
                    nextr = str + " ";
                    temp = 0;
                }
                temp = temp + str.length() + 1;
            }
            if (!nextr.replace(" ", "").isEmpty()) {
                lore.add(ChatColor.WHITE
                        + nextr.substring(0, nextr.length() - 1));
            }
            temp = 0;
            String status = active ? "Active" : (pun.inactive != 0 ? "Removed"
                    : "Expired");
            if (pun.type != PunishType.Warn) {
                lore.add(ChatColor.YELLOW + "Status: " + ChatColor.WHITE
                        + status);
            }
            lore.add(ChatColor.GRAY + "ID" + pun.timepunished);
            meta.setLore(lore);
            item.setItemMeta(meta);
            if (count <= 45) {
                gui.inv.setItem(8 + count, item);
            }
        }
        if (count == 0) {
            gui.AddButton(new Button(null, ChatColor.RED + "" + ChatColor.BOLD
                    + "No Punishments Found", new String[]{
                    "We found no past punishments ", "for this player."},
                    new ItemStack(Material.BARRIER, 1), 22));
        }
        gui.show(p);
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        if (e.getInventory().getTitle().contains("Record: ")) {
            backs.remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem() != null) {
                if (ChatColor.stripColor(e.getInventory().getTitle()).contains(
                        "Record: ")) {
                    ItemStack item = e.getCurrentItem();
                    e.setCancelled(true);
                    if ((e.getSlot() == 4)
                            && (e.getCurrentItem().getType() == Material.ARROW)) {
                        Inventory inv = RecordUI.backs.get(p.getUniqueId());
                        RecordUI.backs.remove(p.getUniqueId());
                        p.openInventory(inv);
                        return;
                    }

                    if (HPlayer.o(p).getRank().isPermissible(Rank.Admin)) {
                        if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                            if (e.getSlot() >= 9) {
                                if (item.getType() != Material.ARROW) {
                                    if (item.getItemMeta().getLore() != null) {
                                        if (!(item.getItemMeta().getLore()
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
                }
            }
        }
    }

}
