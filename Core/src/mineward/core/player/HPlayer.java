package mineward.core.player;

import mineward.core.Core;
import mineward.core.common.Rank;
import mineward.core.common.database.account.AccountManager;
import mineward.core.common.database.account.AccountManager.Account;

import org.bukkit.entity.Player;

public class HPlayer {

	private Player p;
	private Rank rank;
	private int money;
	private Account acc;
	private long xp;

	public static HPlayer a(Player p) {
		HPlayer player = new HPlayer();
		player.p = p;
		Account acc = AccountManager.getAccount(p.getUniqueId().toString(),
				false);
		player.rank = acc.rank;
		player.money = acc.money;
		player.acc = acc;
		player.xp = acc.xp;
		return player;
	}

	public static HPlayer o(Player p) {
		for (HPlayer pl : Core.getOnlinePlayers()) {
			// Bukkit.broadcastMessage(pl.getPlayer().getName() + " - "
			// + pl.getPlayer().getUniqueId().toString() + ", "
			// + p.getName() + " - " + p.getUniqueId().toString());
			if (pl.getPlayer().getUniqueId().equals(p.getUniqueId())) {
				return pl;
			}
		}
		return null;
	}

	public Player getPlayer() {
		return p;
	}

	public Rank getRank() {
		return rank;
	}

	public int getMoney() {
		return money;
	}

	public Account getAccount() {
		return acc;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public void setMoney(int a) {
		this.money = a;
	}

	public long getXP() {
		return xp;
	}

	public void setXP(long a) {
		this.xp = a;
	}

}
