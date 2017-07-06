package mineward.core.achievement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mineward.core.common.Database;

import org.bukkit.OfflinePlayer;

public class AchievementManager {

    public static List<Achievement> aelist = new ArrayList<Achievement>();
    public static String splitter = "`~`, ";

    public static List<Achievement> getAllAchievements(OfflinePlayer p) {
        List<Achievement> ass = new ArrayList<Achievement>();
        try {
            PreparedStatement statement = Database.getConnection()
                    .prepareStatement(
                            "SELECT * FROM `Achievement` WHERE `uuid`='"
                                    + p.getUniqueId().toString() + "';");
            ResultSet rs = statement.executeQuery();
            String[] as = new String[]{};
            if (rs.next()) {
                String data = rs.getString("meta");
                as = data.split("`~`, ");
            }
            rs.close();
            statement.close();
            for (String s : as) {
                for (Achievement a : aelist) {
                    if (s.equalsIgnoreCase(a.Name)) {
                        ass.add(a);
                    }
                }
            }
            return ass;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getGameName(Achievement a) {
        if (a instanceof GameAchievement)
            return ((GameAchievement) a).GameName();
        return "General";
    }

    public static void removeAchievementData(OfflinePlayer p, Achievement a) {
        Database.runUpdateStatement("DELETE FROM `AchievementData` WHERE `uuid`='"
                + p.getUniqueId().toString()
                + "' AND `game`='"
                + getGameName(a) + "' AND `name`='" + a.Name + "';");
    }

    public static void addAchievementData(OfflinePlayer p, CountAchievement a,
                                          int num) {
        int data = getAchievementData(p, a);
        if (data == -1)
            Database.runUpdateStatement("INSERT INTO `AchievementData`(`uuid`,`game`,`name`,`num`) VALUES('"
                    + p.getUniqueId().toString()
                    + "','"
                    + getGameName(a)
                    + "','" + a.Name + "','" + num + "');");
        else
            Database.runUpdateStatement("UPDATE `AchievementData` SET `num`='"
                    + (data + num) + "' WHERE `uuid`='"
                    + p.getUniqueId().toString() + "' AND `game`='"
                    + getGameName(a) + "' AND `name`='" + a.Name + "';");
    }

    public static int getAchievementData(OfflinePlayer p, Achievement a) {
        int num = -1;
        try {
            PreparedStatement statement = Database.getConnection()
                    .prepareStatement(
                            "SELECT * FROM `AchievementData` WHERE `uuid`='"
                                    + p.getUniqueId().toString()
                                    + "' AND `game`='" + getGameName(a)
                                    + "' AND `name`='" + a.Name + "';");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                num = rs.getInt("num");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public static void addAchievement(OfflinePlayer p, Achievement a) {
        boolean created = false;
        try {
            PreparedStatement statement = Database.getConnection()
                    .prepareStatement(
                            "SELECT * FROM `Achievement` WHERE `uuid`='"
                                    + p.getUniqueId().toString() + "';");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                created = true;
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (created) {
            List<Achievement> as = getAllAchievements(p);
            String builder = "";
            for (Achievement aa : as) {
                builder += aa.Name + splitter;
            }
            builder += a.Name;
            Database.runUpdateStatement("UPDATE `Achievement` SET `meta`='"
                    + builder + "' WHERE `uuid`='" + p.getUniqueId().toString()
                    + "';");
        } else {
            Database.runUpdateStatement("INSERT INTO `Achievement` (`uuid`,`meta`) VALUES('"
                    + p.getUniqueId().toString() + "','" + a.Name + "');");
        }
    }

    public static boolean hasAchievement(OfflinePlayer p, Achievement a) {
        try {
            PreparedStatement statement = Database.getConnection()
                    .prepareStatement(
                            "SELECT * FROM `Achievement` WHERE `uuid`='"
                                    + p.getUniqueId().toString() + "';");
            ResultSet rs = statement.executeQuery();
            String[] as = new String[]{};
            if (rs.next()) {
                String data = rs.getString("meta");
                as = data.split(splitter);
            }
            rs.close();
            statement.close();
            for (String s : as) {
                if (s.equalsIgnoreCase(a.Name))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
