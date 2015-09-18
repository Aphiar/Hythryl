package mineward.core.shop;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mineward.core.common.Database;
import mineward.core.common.utils.F;
import mineward.core.common.utils.UtilCoin;
import mineward.core.common.utils.UtilMoney;
import mineward.core.gui.Button;
import mineward.core.gui.GUI;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Shop implements Listener {

	private String item;
	private String displayName;
	private Material mat;
	private int cost;
	private UUID id;
	private JavaPlugin plugin;
	private boolean rupees = false;

	public Shop(Player p, String displayName, String item, Material mat,
			int cost, JavaPlugin plugin) {
		id = p.getUniqueId();
		this.item = item;
		this.cost = cost;
		this.mat = mat;
		this.plugin = plugin;
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getItem() {
		return item;
	}

	public int getCost() {
		return cost;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(id);
	}

	public Material getItemStack() {
		return mat;
	}

	public void setRupees(boolean b) {
		this.rupees = b;
	}

	public String getEconomy() {
		return rupees ? "Rupees" : "Coins";
	}

	private List<Button> buildButtons(int slot) {
		List<Button> btns = new ArrayList<Button>();
		for (int i : getSlots(slot)) {
			btns.add(new Button(null, ChatColor.GREEN + "" + ChatColor.BOLD
					+ "Buy " + displayName, new String[] { ChatColor.GRAY
					+ "It will cost " + ChatColor.WHITE + cost + " "
					+ getEconomy() }, new ItemStack(Material.STAINED_CLAY, 1,
					(byte) 5), i));
		}
		for (int i : getSlots(slot + 6)) {
			btns.add(new Button(null, ChatColor.RED + "" + ChatColor.BOLD
					+ "Cancel", new String[0], new ItemStack(
					Material.STAINED_CLAY, 1, (byte) 14), i));
		}
		return btns;
	}

	public void Open() {
		if (!Bukkit.getOfflinePlayer(id).isOnline())
			return;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		GUI gui = new GUI("Confirm Purchase", 54);
		gui.AddButton(new Button(null, ChatColor.GOLD + "" + ChatColor.BOLD
				+ displayName, new String[] { ChatColor.WHITE + "" + cost + " "
				+ getEconomy() }, new ItemStack(mat), 13));
		int coins = UtilCoin.GetCoins(getPlayer());
		if (rupees) {
			coins = UtilMoney.GetMoney(getPlayer(), "rupees");
		}
		if (coins >= cost) {
			for (Button b : buildButtons(27)) {
				b.setInventory(gui.inv);
				b.show();
			}
		} else {
			gui.AddButton(new Button(null, ChatColor.RED + "" + ChatColor.BOLD
					+ "You cannot purchase this item.",
					new String[] { ChatColor.GRAY + "Insufficient Funds." },
					new ItemStack(Material.BARRIER, 1), 22));
		}
		gui.show(getPlayer());
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (p.getUniqueId().equals(id)) {
					if (e.getInventory().getTitle() != null) {
						if (e.getInventory().getTitle()
								.contains("Confirm Purchase")) {
							if (e.getCurrentItem() != null) {
								e.setCancelled(true);
								if (e.getCurrentItem().getType()
										.equals(Material.STAINED_CLAY)) {
									if (e.getCurrentItem().getItemMeta() != null) {
										if (e.getCurrentItem().getItemMeta()
												.getDisplayName() != null) {
											if (e.getCurrentItem()
													.getItemMeta()
													.getDisplayName()
													.contains("Cancel")) {
												p.closeInventory();
												p.updateInventory();
												return;
											} else if (e.getCurrentItem()
													.getItemMeta()
													.getDisplayName()
													.contains("Buy")) {
												if (rupees) {
													UtilMoney
															.RemoveMoney(
																	getPlayer(),
																	getCost(),
																	"rupees");
												} else {
													UtilCoin.RemoveCoins(p,
															cost);
												}
												Database.runUpdateStatement("INSERT INTO `Shop` (`uuid`,`item`,`cost`) VALUES('"
														+ p.getUniqueId()
																.toString()
														+ "','"
														+ item
														+ "','"
														+ cost + "');");
												F.message(
														p,
														"Merchant",
														"You bought "
																+ ChatColor.GREEN
																+ ""
																+ ChatColor.BOLD
																+ displayName
																+ ChatColor.GRAY
																+ " for "
																+ ChatColor.GOLD
																+ ""
																+ cost
																+ " "
																+ getEconomy()
																+ ChatColor.GRAY
																+ ".");
												Bukkit.getPluginManager()
														.callEvent(
																new GamePurchaseEvent(
																		getPlayer(),
																		getItem(),
																		getDisplayName(),
																		getCost()));
												p.closeInventory();
												p.updateInventory();
												return;
											}
										}
									}
								}
								p.updateInventory();
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player) {
			Player p = (Player) e.getPlayer();
			if (e.getInventory() != null) {
				if (e.getInventory().getTitle() != null) {
					if (e.getInventory().getTitle()
							.contains("Confirm Purchase")) {
						if (p.getUniqueId().equals(id)) {
							HandlerList.unregisterAll(this);
						}
					}
				}
			}
		}
	}

	private int[] getSlots(int slot) {
		return new int[] { slot, slot + 1, slot + 2, slot + 9, slot + 9 + 1,
				slot + 9 + 2, slot + 18, slot + 18 + 1, slot + 18 + 2 };
	}

	public static boolean hasItem(OfflinePlayer p, String item) {
		try {
			boolean val = false;
			PreparedStatement statement = Database.getConnection()
					.prepareStatement(
							"SELECT * FROM `Shop` WHERE `uuid`='"
									+ p.getUniqueId().toString() + "';");
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				if (rs.getString("item").equals(item)) {
					val = true;
					break;
				}
			}
			rs.close();
			statement.close();
			return val;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
