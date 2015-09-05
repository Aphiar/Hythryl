package mineward.core.stats;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mineward.core.achievement.Achievement;
import mineward.core.achievement.AchievementManager;
import mineward.core.achievement.CountAchievement;
import mineward.core.achievement.GameAchievement;
import mineward.core.achievement.time.TimeOnline;
import mineward.core.common.Database;
import mineward.core.common.Rank;
import mineward.core.common.database.account.AccountManager;
import mineward.core.common.utils.TimeUtil;
import mineward.core.common.utils.UtilItem;
import mineward.core.common.utils.UtilLevel;
import mineward.core.gui.Button;
import mineward.core.gui.GUI;
import mineward.core.listener.MyListener;
import mineward.core.player.HPlayer;
import mineward.core.punish.ui.RecordUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class StatsGUI extends MyListener {

	public StatsGUI() {
		super("StatsListener");
	}

	public static void open(Player viewer, OfflinePlayer p) {
		GUI gui = new GUI(ChatColor.DARK_BLUE + "Stats: " + p.getName(), 27);
		gui.AddButton(new Button(
				null,
				ChatColor.GREEN + "General Achievements",
				new String[] {
						ChatColor.GRAY + "Click this to open a new GUI",
						ChatColor.GRAY
								+ "with information about general achievements." },
				new ItemStack(Material.BOOK_AND_QUILL, 1), 11));
		gui.AddButton(new Button(null, ChatColor.AQUA + "Server Information",
				new String[] { ChatColor.GRAY + "Click this to open a new GUI",
						ChatColor.GRAY + "with info about this server." },
				new ItemStack(Material.BOOKSHELF, 1), 12));
		gui.AddButton(new Button(
				null,
				ChatColor.YELLOW + "General Information",
				new String[] {
						ChatColor.GRAY + "Click this to open a new GUI",
						ChatColor.GRAY
								+ "with general information about the player." },
				new ItemStack(Material.SKULL_ITEM, 1, (byte) 3), 13));
		gui.AddButton(new Button(
				null,
				ChatColor.AQUA + "Server Administration",
				new String[] { ChatColor.GRAY + "Click this to open a new GUI",
						ChatColor.GRAY + "with info about the administration." },
				new ItemStack(Material.ARMOR_STAND, 1), 14));
		gui.AddButton(new Button(
				null,
				ChatColor.GREEN + "Game Achievements",
				new String[] {
						ChatColor.GRAY + "Click this to open a new GUI",
						ChatColor.GRAY
								+ "with information about game achievements." },
				new ItemStack(Material.BEACON, 1), 15));
		if (HPlayer.o(viewer).getRank().isPermissible(Rank.Jrmod)) {
			gui.AddButton(new Button(
					null,
					ChatColor.RED + "Punishment Record",
					new String[] {
							Rank.Jrmod.getLabel(true, true) + ChatColor.GRAY
									+ "+",
							ChatColor.GRAY
									+ "View this player's punishment record." },
					new ItemStack(Material.LAVA_BUCKET, 1), 17));
		}
		gui.show(viewer);
	}

	private static void gAchUI(Player viewer, OfflinePlayer p) {
		GUI gui = new GUI(ChatColor.DARK_BLUE + "Stats: " + p.getName(), 54);
		gui.AddButton(new Button(null, ChatColor.YELLOW + "Back to Main Page",
				new String[] { ChatColor.GRAY + "Click this to go back to ",
						ChatColor.GRAY + "the main page." }, new ItemStack(
						Material.BOOK, 1), 4));
		gui.AddButton(new Button(null,
				ChatColor.GREEN + "General Achievements", new String[] {},
				new ItemStack(Material.BOOK_AND_QUILL, 1), 0));
		int num = 9;
		for (Achievement a : AchievementManager.aelist) {
			if (!(a instanceof GameAchievement)) {
				boolean completed = AchievementManager.hasAchievement(p, a);
				ItemStack item = completed ? new ItemStack(Material.EMERALD, 1)
						: new ItemStack(Material.BARRIER, 1);
				ChatColor color = completed ? ChatColor.GREEN : ChatColor.RED;
				String lastline = completed ? ChatColor.AQUA + "Completed!"
						: ChatColor.LIGHT_PURPLE + "" + a.getReward()
								+ " Coins";
				String[] lore = new String[] {
						ChatColor.GRAY + a.getDescription(), "", lastline };
				if (a instanceof CountAchievement) {
					CountAchievement ca = (CountAchievement) a;
					if (!(completed)) {
						int data = AchievementManager.getAchievementData(p, ca);
						if (data == -1)
							data = 0;
						lore = new String[] {
								ChatColor.GRAY + a.getDescription(), "",
								data + "/" + ca.getMaxAmount(), "", lastline };
					}
				}
				gui.AddButton(new Button(null, color + "" + ChatColor.BOLD
						+ a.getName(), lore, item, num));
				num++;
			}
		}
		gui.show(viewer);
	}

	private static void staffUI(Player viewer, OfflinePlayer p) {
		GUI gui = new GUI(ChatColor.DARK_BLUE + "Stats: " + p.getName(), 54);
		gui.AddButton(new Button(null, ChatColor.YELLOW + "Back to Main Page",
				new String[] { ChatColor.GRAY + "Click this to go back to ",
						ChatColor.GRAY + "the main page." }, new ItemStack(
						Material.BOOK, 1), 4));
		gui.AddButton(new Button(null,
				ChatColor.AQUA + "Server Administration", new String[] {},
				new ItemStack(Material.ARMOR_STAND, 1), 0));

		// STAFF LIST

		// JHeifetz (Zeh)
		gui.AddButton(new Button(null, ChatColor.DARK_RED + "" + ChatColor.BOLD
				+ "Zeh", new String[] { "Lead Developer for the server.",
				"Managing Director & Flounder.",
				ChatColor.GRAY + "Also known as: ",
				ChatColor.GRAY + "JHeifetz", ChatColor.GRAY + "Zehr_",
				ChatColor.GRAY + "jollie000l" }, UtilItem.GetSkull("Zeh"), 9));

		// GUNZxNxROSEZ
		gui.AddButton(new Button(null, ChatColor.DARK_RED + "" + ChatColor.BOLD
				+ "GUNZxNxROSEZ", new String[] {
				"Head of Support, Forums Nerd.",
				"Community Management & Flounder." }, UtilItem
				.GetSkull("GUNZxNxROSEZ"), 10));
		// Will
		gui.AddButton(new Button(null, ChatColor.DARK_RED + "" + ChatColor.BOLD
				+ "Will", new String[] { "Community Manager",
				"A rapper who raps about ", "wrapping wraps. Not a wrapper." },
				UtilItem.GetSkull("Will"), 18));
		// FelixT
		gui.AddButton(new Button(null, ChatColor.RED + "" + ChatColor.BOLD
				+ "FelixT", new String[] { "Some kid." }, UtilItem
				.GetSkull("FelixT"), 19));
		// SirRegan
		gui.AddButton(new Button(null, ChatColor.RED + "" + ChatColor.BOLD
				+ "SirRegan", new String[] { "Another random kid" }, UtilItem
				.GetSkull("SirRegan"), 27));
		// InstanceOfDev
		gui.AddButton(new Button(null, ChatColor.RED + "" + ChatColor.BOLD
				+ "InstanceOfDev", new String[] { "Pudding Lover",
				"Developer and sick kid" }, UtilItem.GetSkull("InstanceOfDev"),
				28));

		gui.show(viewer);
	}

	private static void gameAchUI(Player viewer, OfflinePlayer p,
			String gameName) {
		GUI gui = new GUI(ChatColor.DARK_BLUE + "Stats: " + p.getName(), 54);
		gui.AddButton(new Button(null, ChatColor.YELLOW + "Back to Main Page",
				new String[] { ChatColor.GRAY + "Click this to go back to ",
						ChatColor.GRAY + "the main page." }, new ItemStack(
						Material.BOOK, 1), 4));
		gui.AddButton(new Button(null, ChatColor.GREEN + gameName
				+ " Achievements", new String[] {}, new ItemStack(
				Material.BEACON, 1), 0));
		int num = 9;
		for (Achievement a : AchievementManager.aelist) {
			if (a instanceof GameAchievement) {
				if (((GameAchievement) a).GameName().equalsIgnoreCase(gameName)) {
					boolean completed = AchievementManager.hasAchievement(p, a);
					ItemStack item = completed ? new ItemStack(
							Material.EMERALD, 1) : new ItemStack(
							Material.BARRIER, 1);
					ChatColor color = completed ? ChatColor.GREEN
							: ChatColor.RED;
					String lastline = completed ? ChatColor.AQUA + "Completed!"
							: ChatColor.LIGHT_PURPLE + "" + a.getReward()
									+ " Coins";
					String[] lore = new String[] {
							ChatColor.GRAY + a.getDescription(), "", lastline };
					if (a instanceof CountAchievement) {
						CountAchievement ca = (CountAchievement) a;
						if (!(completed)) {
							int data = AchievementManager.getAchievementData(p,
									ca);
							if (data == -1)
								data = 0;
							lore = new String[] {
									ChatColor.GRAY + a.getDescription(), "",
									data + "/" + ca.getMaxAmount(), "",
									lastline };
						}
					}
					gui.AddButton(new Button(null, color + "" + ChatColor.BOLD
							+ a.getName(), lore, item, num));
					num++;
				}
			}
		}
		gui.show(viewer);
	}

	public static void topMenu(Player viewer, OfflinePlayer p, String name,
			String data, boolean highest) {
		GUI gui = new GUI(ChatColor.DARK_BLUE + "Stats: " + p.getName(), 54);
		gui.AddButton(new Button(null, ChatColor.YELLOW + "Back to Main Page",
				new String[] { ChatColor.GRAY + "Click this to go back to ",
						ChatColor.GRAY + "the main page." }, new ItemStack(
						Material.BOOK, 1), 4));
		gui.AddButton(new Button(null,
				ChatColor.GREEN + "Top Players: " + name, new String[] {},
				new ItemStack(Material.PAPER, 1), 0));
		gui.AddButton(new Button(null, ChatColor.BLUE + "" + ChatColor.BOLD
				+ "Loading...", new String[] { "We are loading the top",
				"players for this stat." }, new ItemStack(
				Material.REDSTONE_ORE, 1), 22));
		long one = -1;
		UUID id1 = null;
		long two = -1;
		UUID id2 = null;
		long three = -1;
		UUID id3 = null;
		try {
			PreparedStatement statement = Database.getConnection()
					.prepareStatement("SELECT * FROM `Account`;");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				long d = rs.getLong(data);
				UUID id = UUID.fromString(rs.getString("uuid"));
				if (Bukkit.getOfflinePlayer(id).isOnline()) {
					if (HPlayer.o(Bukkit.getPlayer(id)).getRank()
							.isPermissible(Rank.Admin))
						continue;
				} else {
					if (AccountManager.getAccount(id.toString(), false).rank
							.isPermissible(Rank.Admin))
						continue;
				}
				if (highest) {
					if (d > one) {
						three = two;
						id2 = id3;
						two = one;
						id2 = id1;
						one = d;
						id1 = id;
					} else if (d > two) {
						three = two;
						id2 = id3;
						two = d;
						id2 = id;
					} else if (d > three) {
						three = d;
						id3 = id;
					}
				} else {
					if (d < one) {
						three = two;
						id2 = id3;
						two = one;
						id2 = id1;
						one = d;
						id1 = id;
					} else if (d < two) {
						three = two;
						id2 = id3;
						two = d;
						id2 = id;
					} else if (d < three) {
						three = d;
						id3 = id;
					}
				}
			}
			rs.close();
			statement.close();
		} catch (Exception e) {
			gui.AddButton(new Button(null, ChatColor.RED + "" + ChatColor.BOLD
					+ "No Games Found", new String[] {
					"We found no games with ", "achievements." },
					new ItemStack(Material.BARRIER, 1), 22));
			return;
		}
		if (id1 == null) {
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
			gui.AddButton(new Button(null, ChatColor.BLUE + "" + ChatColor.BOLD
					+ "Nobody Here...", new String[] {}, item, 22));
		} else {
			String oname = Bukkit.getOfflinePlayer(id1).getName();
			ItemStack item = UtilItem.GetSkull(oname);
			String lore = "No Data Available";
			if (data.equalsIgnoreCase("xp")) {
				lore = "Level: " + ChatColor.AQUA + UtilLevel.getLevel(one);
			} else if (data.equalsIgnoreCase("timeonline")) {
				lore = TimeUtil.toString(TimeOnline.getTime(
						Bukkit.getOfflinePlayer(id1), true));
			}
			gui.AddButton(new Button(null, ChatColor.BLUE + "" + ChatColor.BOLD
					+ oname, new String[] { lore }, item, 22));
		}
		if (id2 == null) {
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
			gui.AddButton(new Button(null, ChatColor.BLUE + "" + ChatColor.BOLD
					+ "Nobody Here...", new String[] {}, item, 31));
		} else {
			String oname = Bukkit.getOfflinePlayer(id2).getName();
			ItemStack item = UtilItem.GetSkull(oname);
			String lore = "No Data Available";
			if (data.equalsIgnoreCase("xp")) {
				lore = "Level: " + ChatColor.AQUA + UtilLevel.getLevel(two);
			} else if (data.equalsIgnoreCase("timeonline")) {
				lore = TimeUtil.toString(TimeOnline.getTime(
						Bukkit.getOfflinePlayer(id2), true));
			}
			gui.AddButton(new Button(null, ChatColor.BLUE + "" + ChatColor.BOLD
					+ oname, new String[] { lore }, item, 31));
		}
		if (id3 == null) {
			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
			gui.AddButton(new Button(null, ChatColor.BLUE + "" + ChatColor.BOLD
					+ "Nobody Here...", new String[] {}, item, 40));
		} else {
			String oname = Bukkit.getOfflinePlayer(id3).getName();
			ItemStack item = UtilItem.GetSkull(oname);
			String lore = "No Data Available";
			if (data.equalsIgnoreCase("xp")) {
				lore = "Level: " + ChatColor.AQUA + UtilLevel.getLevel(three);
			} else if (data.equalsIgnoreCase("timeonline")) {
				lore = TimeUtil.toString(TimeOnline.getTime(
						Bukkit.getOfflinePlayer(id3), true));
			}
			gui.AddButton(new Button(null, ChatColor.BLUE + "" + ChatColor.BOLD
					+ oname, new String[] { lore }, item, 40));
		}
		gui.show(viewer);
	}

	private static void gameMenu(Player viewer, OfflinePlayer p) {
		GUI gui = new GUI(ChatColor.DARK_BLUE + "Stats: " + p.getName(), 54);
		gui.AddButton(new Button(null, ChatColor.YELLOW + "Back to Main Page",
				new String[] { ChatColor.GRAY + "Click this to go back to ",
						ChatColor.GRAY + "the main page." }, new ItemStack(
						Material.BOOK, 1), 4));
		gui.AddButton(new Button(null, ChatColor.GREEN + "Game Achievements",
				new String[] {}, new ItemStack(Material.BEACON, 1), 0));
		int num = 9;
		List<String> games = new ArrayList<String>();
		for (Achievement a : AchievementManager.aelist) {
			if (a instanceof GameAchievement) {
				GameAchievement ga = (GameAchievement) a;
				if (!games.contains(ga.GameName())) {
					games.add(ga.GameName());
				}
			}
		}
		if (games.isEmpty()) {
			gui.AddButton(new Button(null, ChatColor.RED + "" + ChatColor.BOLD
					+ "No Games Found", new String[] {
					"We found no games with ", "achievements." },
					new ItemStack(Material.BARRIER, 1), 22));
		}
		ItemStack is = new ItemStack(Material.DIAMOND);
		for (String s : games) {
			new Button(gui.inv, ChatColor.AQUA + "" + ChatColor.BOLD + s,
					new String[] { ChatColor.YELLOW + "Game Achievements",
							ChatColor.GRAY + "Click to view all ",
							ChatColor.GRAY + s + " achievements." }, is, num)
					.show();
			num++;
		}
		gui.show(viewer);
	}

	private static void generalUI(Player viewer, OfflinePlayer p) {
		GUI gui = new GUI(ChatColor.DARK_BLUE + "Stats: " + p.getName(), 54);
		gui.AddButton(new Button(null, ChatColor.YELLOW + "Back to Main Page",
				new String[] { ChatColor.GRAY + "Click this to go back to ",
						ChatColor.GRAY + "the main page." }, new ItemStack(
						Material.BOOK, 1), 4));
		gui.AddButton(new Button(null, ChatColor.YELLOW + p.getName(),
				new String[] {},
				new ItemStack(Material.SKULL_ITEM, 1, (byte) 3), 22));
		Timestamp firstjoined = null;
		String timeonline = TimeUtil.toString(TimeOnline.getTime(p, true));
		Timestamp lastseen = null;
		Rank rank = Rank.Default;
		int money = 0;
		long xp = 0;
		try {
			PreparedStatement statement = Database.getConnection()
					.prepareStatement(
							"SELECT * FROM `Account` WHERE `uuid`='"
									+ p.getUniqueId().toString() + "';");
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				firstjoined = rs.getTimestamp("firstjoined");
				long a = rs.getLong("lastseen");
				System.out.println(a);
				lastseen = new Timestamp(a);
				System.out.println(lastseen.toString());
				rank = Rank.valueOf(rs.getString("rank"));
				money = rs.getInt("money");
				xp = rs.getLong("xp");
			}
			rs.close();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fj1 = firstjoined == null ? "Never" : firstjoined.toString();
		String fj2 = firstjoined == null ? "0.0 Seconds Ago" : TimeUtil
				.toString(System.currentTimeMillis() - firstjoined.getTime())
				+ " Ago";
		String ls1 = p.isOnline() ? "Online" : (lastseen == null ? "Never"
				: lastseen.toString());
		String ls2 = p.isOnline() ? "Now"
				: (lastseen == null ? "0.0 Seconds Ago" : TimeUtil
						.toString(System.currentTimeMillis()
								- lastseen.getTime()))
						+ " Ago";
		gui.AddButton(new Button(null, ChatColor.YELLOW + "First Joined: ",
				new String[] { fj1, fj2 }, new ItemStack(
						Material.BOOK_AND_QUILL, 1), 31));
		gui.AddButton(new Button(null,
				ChatColor.YELLOW + "Time Spent Online: ",
				new String[] { timeonline }, new ItemStack(Material.WATCH, 1),
				32));
		gui.AddButton(new Button(null, ChatColor.YELLOW + "Last Seen: ",
				new String[] { ls1, ls2 }, new ItemStack(Material.COMPASS, 1),
				30));
		gui.AddButton(new Button(null, ChatColor.YELLOW + "Rank: ",
				new String[] { rank.getLabel(true, true) }, new ItemStack(
						Material.NAME_TAG, 1), 40));
		gui.AddButton(new Button(null, ChatColor.YELLOW + "Money: ",
				new String[] { money + " Coins" }, new ItemStack(
						Material.GOLD_INGOT, 1), 39));
		int level = UtilLevel.getLevel(xp);
		gui.AddButton(new Button(
				null,
				ChatColor.YELLOW + "Experience & Level: ",
				new String[] {
						propValue("Level", level),
						propValue("XP",
								xp + "/" + UtilLevel.getXPToNextLevel(level)) },
				new ItemStack(Material.EXP_BOTTLE, 1), 41));
		gui.show(viewer);
	}

	private static String propValue(String prop, Object val) {
		return ChatColor.GREEN + prop + ": " + ChatColor.WHITE + val;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem() != null) {
				if (e.getCurrentItem().getType() != Material.AIR) {
					if (ChatColor.stripColor(e.getInventory().getTitle())
							.startsWith("Stats: ")) {
						ItemStack item = e.getCurrentItem();
						e.setCancelled(true);
						OfflinePlayer target = Bukkit
								.getOfflinePlayer(ChatColor.stripColor(
										e.getInventory().getTitle()).replace(
										"Stats: ", ""));
						if (e.getSlot() == 13
								&& item.getItemMeta().getDisplayName()
										.contains("General Information")) {
							generalUI(p, target);
						}
						if (e.getSlot() == 11
								&& item.getItemMeta().getDisplayName()
										.contains("General Achievements")) {
							gAchUI(p, target);
						}
						if (e.getSlot() == 15
								&& item.getItemMeta().getDisplayName()
										.contains("Game Achievements")) {
							gameMenu(p, target);
						}
						if (e.getSlot() == 14
								&& item.getItemMeta().getDisplayName()
										.contains("Server Administration")) {
							staffUI(p, target);
						}
						if (e.getSlot() == 4
								&& item.getItemMeta().getDisplayName()
										.contains("Back to Main Page")) {
							open(p, target);
						}
						if (e.getSlot() == 17
								&& item.getItemMeta().getDisplayName()
										.contains("Punishment Record")) {
							RecordUI.open(p, target, "", e.getInventory());
						}
						if (item.getItemMeta().getDisplayName()
								.contains("Time Spent Online")) {
							StatsGUI.topMenu(p, target, "Time Online",
									"timeonline", true);
						}
						if (item.getItemMeta().getDisplayName()
								.contains("Experience & Level")) {
							StatsGUI.topMenu(p, target, "Level", "xp", true);
						}
						if (e.getCurrentItem().getItemMeta().getLore() != null) {
							if (e.getCurrentItem().getItemMeta().getLore()
									.get(0).contains("Game Achievements")) {
								String gameName = ChatColor.stripColor(e
										.getCurrentItem().getItemMeta()
										.getDisplayName());
								gameAchUI(p, target, gameName);
							}
						}
					}
				}
			}
		}
	}
}
