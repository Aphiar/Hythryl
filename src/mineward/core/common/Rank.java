package mineward.core.common;

import mineward.core.common.Prefix.PrefixColor;
import mineward.core.common.utils.C;
import mineward.core.player.HPlayer;

import org.bukkit.ChatColor;

public enum Rank {

	Owner(ChatColor.DARK_RED, "Owner", 100.0D), // Ordinal of 0
	CM(ChatColor.YELLOW, "VIP", 99.0D), Dev(ChatColor.RED, "Dev", 26.0D), Admin(
			ChatColor.RED, "Admin", 25.0D), Coolkid(ChatColor.DARK_AQUA,
			"CoolKid", 17.0D), Srmod(ChatColor.GOLD, "Sr.Mod", 16.0D), Mod(
			ChatColor.DARK_GREEN, "Mod", 15.0D), Jrmod(ChatColor.GREEN,
			"Jr.Mod", 10.0D), Builder(ChatColor.DARK_PURPLE, "Builder", 9.0D), Vip(
			ChatColor.YELLOW, "VIP", 8.0D), Master(ChatColor.LIGHT_PURPLE,
			"Master", 6.0D), Pro(ChatColor.BLUE, "Pro", 5.0D), Default(
			ChatColor.GRAY, "Default", 0.0D);

	private ChatColor color;
	private String label;
	private double r;

	private Rank(ChatColor c, String l, double r) {
		this.color = c;
		this.label = l;
		this.r = r;
	}

	public ChatColor getColor() {
		return color;
	}

	public String getOldLabel(boolean uppercase, boolean bold) {
		String name = getName();
		if (uppercase)
			name = getName().toUpperCase();
		if (bold)
			return getColor() + "" + ChatColor.BOLD + name;
		return getColor() + name;
	}

	public String getLabel(boolean uppercase) {
		String name = getName();
		if (uppercase)
			name = getName().toUpperCase();
		return getColor() + "[" + name + "]";
	}

	public String getName() {
		return label;
	}

	public double getPermissionsRank() {
		return r;
	}

	public boolean isPermissible(Rank comparedTo) {
		if (comparedTo.getPermissionsRank() > getPermissionsRank()) {
			return false;
		}
		return true;
	}

	public static boolean isPermissible(HPlayer p, Rank rank, boolean inform) {
		if (p.getRank().getPermissionsRank() < rank.getPermissionsRank()) {
			if (inform) {
				p.getPlayer().sendMessage(
						PrefixBuilder
								.getPrefixBuilder(false, "Hierarchy",
										PrefixColor.Normal).build().toString()
								+ C.STR_MAIN
								+ "You need to be at least rank "
								+ rank.getLabel(false)
								+ ChatColor.GRAY
								+ " to do this.");
			}
			return false;
		}
		return true;
	}

}
