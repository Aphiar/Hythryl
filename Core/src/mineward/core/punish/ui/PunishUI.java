package mineward.core.punish.ui;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.TimeUtil;
import mineward.core.gui.Button;
import mineward.core.gui.GUI;
import mineward.core.player.HPlayer;
import mineward.core.punish.Punish;
import mineward.core.punish.PunishCategory;
import mineward.core.punish.PunishType;
import mineward.core.punish.Punishment;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PunishUI {

	public static void open(Player p, OfflinePlayer t, String reason) {
		GUI gui = GUI.createGUI("Punish: " + t.getName(), 54);
		gui.AddButton(new Button(null, C.STR_PLAYER + t.getName(),
				new String[] { reason }, new ItemStack(Material.SKULL_ITEM, 1,
						(byte) 3), 4));
		gui.AddButton(new Button(null, ChatColor.BOLD + "Chat Offense",
				new String[] {}, new ItemStack(Material.BOOK, 1), 9));
		gui.AddButton(new Button(null, ChatColor.BOLD + "General Offense",
				new String[] {}, new ItemStack(Material.IRON_CHESTPLATE, 1), 18));
		gui.AddButton(new Button(null, ChatColor.BOLD + "Hacking",
				new String[] {}, new ItemStack(Material.TNT, 1), 27));

		String gBold = ChatColor.GREEN + "" + ChatColor.BOLD;
		String bBold = ChatColor.AQUA + "" + ChatColor.BOLD;
		String bbBold = ChatColor.BLUE + "" + ChatColor.BOLD;
		String yBold = ChatColor.YELLOW + "" + ChatColor.BOLD;
		String oBold = ChatColor.GOLD + "" + ChatColor.BOLD;
		String rBold = ChatColor.RED + "" + ChatColor.BOLD;
		String drBold = ChatColor.DARK_RED + "" + ChatColor.BOLD;

		gui.AddButton(new Button(null, gBold + "Severity 1",
				new String[] { ChatColor.GRAY + "(30.0 Minute Mute)" },
				new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5), 10));
		gui.AddButton(new Button(null, gBold + "Severity 1",
				new String[] { ChatColor.GRAY + "(30.0 Minute Ban)" },
				new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5), 19));
		gui.AddButton(new Button(null, gBold + "Severity 1",
				new String[] { ChatColor.GRAY + "(1.0 Hour Ban)" },
				new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5), 28));

		gui.AddButton(new Button(null, bBold + "Severity 2",
				new String[] { ChatColor.GRAY + "(2.0 Hour Mute)" },
				new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 3), 11));
		gui.AddButton(new Button(null, bBold + "Severity 2",
				new String[] { ChatColor.GRAY + "(1.0 Hour Ban)" },
				new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 3), 20));
		gui.AddButton(new Button(null, bBold + "Severity 2",
				new String[] { ChatColor.GRAY + "(12.0 Hour Ban)" },
				new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 3), 29));
		gui.AddButton(new Button(null, bbBold + "Warn (Sev. 1)",
				new String[] { ChatColor.GRAY + "Warn in chat" },
				new ItemStack(Material.MAP, 1), 15));
		gui.AddButton(new Button(null, bbBold + "Warn (Sev. 2)",
				new String[] { ChatColor.GRAY + "Kick Player" },
				new ItemStack(Material.LEATHER_BOOTS, 1), 24));
		if (HPlayer.o(p).getRank().isPermissible(Rank.Mod)) {
			gui.AddButton(new Button(null, yBold + "Severity 3",
					new String[] { ChatColor.GRAY + "(12.0 Hour Mute)" },
					new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 4), 12));
			gui.AddButton(new Button(null, yBold + "Severity 3",
					new String[] { ChatColor.GRAY + "(12.0 Hour Ban)" },
					new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 4), 21));
			gui.AddButton(new Button(null, yBold + "Severity 3",
					new String[] { ChatColor.GRAY + "(24.0 Hour Ban)" },
					new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 4), 30));

			gui.AddButton(new Button(null, oBold + "Severity 4",
					new String[] { ChatColor.GRAY + "(24.0 Hour Mute)" },
					new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 1), 13));
			gui.AddButton(new Button(null, oBold + "Severity 4",
					new String[] { ChatColor.GRAY + "(24.0 Hour Ban)" },
					new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 1), 22));
			gui.AddButton(new Button(null, oBold + "Severity 4",
					new String[] { ChatColor.GRAY + "(7.0 Day Ban)" },
					new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 1), 31));

			gui.AddButton(new Button(null, rBold + "Severity 5",
					new String[] { ChatColor.GRAY + "(2.0 Day Mute)" },
					new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14),
					14));
			gui.AddButton(new Button(null, rBold + "Severity 5",
					new String[] { ChatColor.GRAY + "(2.0 Day Ban)" },
					new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14),
					23));
			gui.AddButton(new Button(null, rBold + "Severity 5",
					new String[] { ChatColor.GRAY + "(30.0 Day Ban)" },
					new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14),
					32));

			gui.AddButton(new Button(null, drBold + "Permanent Mute",
					new String[] {}, new ItemStack(Material.PAPER, 1), 16));
			gui.AddButton(new Button(null, drBold + "(General) Permanent Ban",
					new String[] {}, new ItemStack(Material.NAME_TAG, 1), 25));
			gui.AddButton(new Button(null, drBold + "(Hacking) Permanent Ban",
					new String[] {}, new ItemStack(Material.REDSTONE_BLOCK, 1),
					34));

			Punishment mute = Punish.getActiveMutePunishment(t.getUniqueId());
			Punishment ban = Punish.getActiveBanPunishment(t.getUniqueId());
			if (mute != null) {
				gui.AddButton(new Button(null, ChatColor.RESET + ""
						+ ChatColor.BOLD + "Unmute", new String[] {},
						new ItemStack(Material.APPLE, 1), 17));
			}
			if (ban != null) {
				gui.AddButton(new Button(null, ChatColor.RESET + ""
						+ ChatColor.BOLD + "Unban", new String[] {},
						new ItemStack(Material.GOLDEN_APPLE, 1), 26));
			}
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
			if (listPun.size() <= 9) {
				if (count <= 9) {
					gui.inv.setItem(44 + count, item);
				}
			} else {
				if (count <= 8) {
					gui.inv.setItem(44 + count, item);
				}
				int pnum = listPun.size();
				gui.AddButton(new Button(null, ChatColor.YELLOW + "View all "
						+ pnum + " punishments >>", new String[] {},
						new ItemStack(Material.ARROW, 1), 53));
			}
		}

		gui.show(p);
	}

}
