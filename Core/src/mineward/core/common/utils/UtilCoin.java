package mineward.core.common.utils;

import mineward.core.common.Database;
import mineward.core.common.database.account.AccountManager;
import mineward.core.common.database.account.AccountManager.Account;
import mineward.core.player.HPlayer;

import org.bukkit.OfflinePlayer;

public class UtilCoin {

	public static int GetCoins(OfflinePlayer p) {
		Account account = AccountManager.getAccount(p.getUniqueId().toString(),
				false);
		if (account == null)
			return -1;
		return account.money;
	}

	public static boolean RemoveCoins(OfflinePlayer p, int a) {
		try {
			int coins = GetCoins(p);
			SetCoins(p, coins - a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean SetCoins(OfflinePlayer p, int a) {
		try {
			GetCoins(p);
			Database.runUpdateStatement("UPDATE `Account` SET `money`='" + a
					+ "' WHERE `uuid`='" + p.getUniqueId().toString() + "';");
			if (p.isOnline()) {
				HPlayer.o(p.getPlayer()).setMoney(a);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean AddCoins(OfflinePlayer p, int a) {
		try {
			int coins = GetCoins(p);
			SetCoins(p, coins + a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}