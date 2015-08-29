package mineward.core.common.database.account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import mineward.core.common.Database;
import mineward.core.common.Rank;

public class AccountManager {

	public static boolean hasAccount(String uuid) {
		try {
			boolean hasaccount = false;
			PreparedStatement statement = Database.getConnection()
					.prepareStatement(
							"SELECT * FROM `Account` WHERE `uuid`='" + uuid
									+ "';");
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				hasaccount = true;
			}
			rs.close();
			statement.close();
			return hasaccount;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Account getAccount(String uuid,
			boolean createAccountIfNotExists) {
		try {
			boolean hasaccount = false;
			Rank rank = Rank.Default;
			int money = 0;
			Timestamp firstjoined = null;
			long timeonline = 0;
			long lastseen = 0;
			long xp = 0;
			PreparedStatement statement = Database.getConnection()
					.prepareStatement(
							"SELECT * FROM `Account` WHERE `uuid`='" + uuid
									+ "';");
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				hasaccount = true;
				rank = Rank.valueOf(rs.getString("rank"));
				money = rs.getInt("money");
				firstjoined = rs.getTimestamp("firstjoined");
				timeonline = rs.getLong("timeonline");
				lastseen = rs.getLong("lastseen");
				xp = rs.getLong("xp");
			}
			rs.close();
			statement.close();
			if (!hasaccount) {
				if (createAccountIfNotExists) {
					firstjoined = new Timestamp(System.currentTimeMillis());
					PreparedStatement s2 = Database
							.getConnection()
							.prepareStatement(
									"INSERT INTO `Account`(`uuid`,`rank`,`money`,`firstjoined`,`timeonline`,`lastseen`,`xp`) VALUES('"
											+ uuid
											+ "','"
											+ rank.name()
											+ "','0','"
											+ firstjoined
											+ "','0','0','0');");
					s2.executeUpdate();
					s2.close();
					return new AccountManager().new Account(uuid, rank, 0,
							firstjoined, 0, 0, 0);
				}
				return null;
			}
			return new AccountManager().new Account(uuid, rank, money,
					firstjoined, timeonline, lastseen, xp);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public class Account {

		public String uuid;
		public Rank rank;
		public int money;
		public Timestamp firstjoined;
		public long timeonline;
		public long lastseen;
		public long xp;

		public Account(String uuid, Rank rank, int money,
				Timestamp firstjoined, long timeonline, long lastseen, long xp) {
			this.uuid = uuid;
			this.rank = rank;
			this.money = money;
			this.firstjoined = firstjoined;
			this.timeonline = timeonline;
			this.lastseen = lastseen;
			this.xp = xp;
		}

	}

}