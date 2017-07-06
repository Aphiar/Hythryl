package mineward.core.news;

import mineward.core.common.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alexc on 28/07/2016.
 */
public class NewsManager {

    public static String GetNews() {
        try {
            ResultSet rs = Database.getConnection().prepareStatement("SELECT * FROM Settings WHERE `name`='News'").executeQuery();
            return rs.getString("value");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Welcome to Hythryl!";
    }


    public static void SetNews(String news) {
        Database.runUpdateStatement("UPDATE Settings SET `value`='"+news+"' WHERE `name`='News'");
    }

}
