package mineward.core.common.utils;

import mineward.core.common.Database;
import mineward.core.common.database.account.AccountManager;
import mineward.core.common.database.account.AccountManager.Account;
import mineward.core.player.HPlayer;

import org.bukkit.OfflinePlayer;

public class UtilMoney {

	public static int GetMoney(OfflinePlayer p, String columnName) {
		Account account = AccountManager.getAccount(p.getUniqueId().toString(),
				false);
		if (account == null)
			return -1;
		if (!(account.moneyTypes.containsKey(columnName)))
			return 0;
		return account.moneyTypes.get(columnName);
	}

	public static boolean RemoveMoney(OfflinePlayer p, int a, String columnName) {
		try {
			int coins = GetMoney(p, columnName);
			SetMoney(p, coins - a, columnName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean SetMoney(OfflinePlayer p, int a, String columnName) {
		try {
			GetMoney(p, columnName);
			Database.runUpdateStatement("UPDATE `Account` SET `" + columnName
					+ "`='" + a + "' WHERE `uuid`='"
					+ p.getUniqueId().toString() + "';");
			if (p.isOnline()) {
				HPlayer.o(p.getPlayer()).setMoney(columnName, a);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean AddMoney(OfflinePlayer p, int a, String columnName) {
		try {
			int coins = GetMoney(p, columnName);
			SetMoney(p, coins + a, columnName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
