package mineward.core.achievement.time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

import mineward.core.common.Database;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TimeOnline {

	public static HashMap<UUID, Long> Mem = new HashMap<UUID, Long>();

	public static void setOnline(Player p, long time) {
		Mem.put(p.getUniqueId(), Long.valueOf(time));
	}

	public static void setOffline(OfflinePlayer p) {
		Mem.remove(p.getUniqueId());
	}

	public static long getTime(OfflinePlayer p, boolean addLocal) {
		try {
			long localTime = addLocal && Mem.containsKey(p.getUniqueId()) ? System
					.currentTimeMillis() - Mem.get(p.getUniqueId())
					: 0;
			long dbTime = 0;
			PreparedStatement statement = Database.getConnection()
					.prepareStatement(
							"SELECT * FROM `Account` WHERE `uuid`='"
									+ p.getUniqueId().toString() + "';");
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				dbTime = rs.getLong("timeonline");
			}
			rs.close();
			statement.close();
			dbTime = addLocal ? dbTime + localTime : dbTime;
			return dbTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	public static void uploadToDatabase(OfflinePlayer p) {
		Database.runUpdateStatement("UPDATE `Account` SET `timeonline`='"
				+ getTime(p, true) + "' WHERE `uuid`='"
				+ p.getUniqueId().toString() + "';");
	}

}
