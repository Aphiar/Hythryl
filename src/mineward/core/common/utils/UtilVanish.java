package mineward.core.common.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mineward.core.common.Database;
import mineward.core.listener.MyListener;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class UtilVanish extends MyListener implements Listener {

	public UtilVanish() {
		super("vanishjoin");
	}

	public static boolean getVanished(Player player) {
		boolean vanishedbool = false;
		try {
			PreparedStatement statement = Database.getConnection()
					.prepareStatement(
							"SELECT vanished FROM Account WHERE uuid='"
									+ player.getUniqueId().toString() + "';");

			ResultSet res = statement.executeQuery();
			if (res.next()) {
				String vanished = res.getString("vanished");

				if (vanished.equalsIgnoreCase("true")) {
					vanishedbool = true;
				} else {
					vanishedbool = false;
				}
				statement.close();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			player.sendMessage(ChatColor.RED + "Error connecting to database.");
		}
		return vanishedbool;
	}

	public static void setUnVanished(Player player) {
		try {
			PreparedStatement statement = Database.getConnection()
					.prepareStatement(
							"UPDATE Account SET vanished='false' WHERE uuid='"
									+ player.getUniqueId().toString() + "'");

			statement.executeUpdate();
			statement.close();
			F.message(player, "Vanish", "Left vanish mode!");
			for (Player onlinep : Bukkit.getOnlinePlayers()) {
				onlinep.showPlayer(player);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			player.sendMessage(ChatColor.RED + "Error connecting to database.");
		}
	}

	public static void setVanished(Player player) {
		try {
			PreparedStatement statement = Database.getConnection()
					.prepareStatement(
							"UPDATE Account SET vanished='true' WHERE uuid='"
									+ player.getUniqueId().toString() + "'");

			statement.executeUpdate();
			statement.close();
			F.message(player, "Vanish", "Entered Vanish mode!");
			for (Player onlinep : Bukkit.getOnlinePlayers()) {
				onlinep.hidePlayer(player);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			player.sendMessage(ChatColor.RED + "Error connecting to database!");
		}
	}

	/*@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (getVanished(p)) {
			setVanished(p);
		}
		for (Player onlinep : Bukkit.getOnlinePlayers()) {
			if (getVanished(onlinep)) {
				p.hidePlayer(onlinep);
			}
		}
	}
	*/
}