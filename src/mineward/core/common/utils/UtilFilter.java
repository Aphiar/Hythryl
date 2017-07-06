package mineward.core.common.utils;

import mineward.core.common.Database;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtilFilter {

    public static ArrayList<String> getBannedWords() {
        ArrayList<String> words = new ArrayList<>();
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT * FROM Filter");

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                words.add(res.getString("word").toUpperCase());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return words;
    }

    public static void addWord(Player p, String word) {
        Database.runUpdateStatement("INSERT INTO Filter(`word`) VALUES ('" + word + "')");
        F.message(p, "Filter", "Added §e" + word + " §7to the filter!");
    }

}