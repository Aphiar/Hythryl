package mineward.core.common.utils;

import mineward.core.common.Database;

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
                words.add(res.getString("word"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return words;
    }

}