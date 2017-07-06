package mineward.core.common.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

import mineward.core.common.Database;
import mineward.core.common.utils.UtilConsoleLog;

public class TableMaker {

    public static boolean createTable(String name, DBTableColumn[] columns) {
        try {
            Connection c = Database.getConnection();
            String sql = "CREATE TABLE " + name + " (";
            for (int i = 0; i < (columns.length - 1); i++) {
                DBTableColumn col = columns[i];
                sql += col.toString() + ",";
            }
            sql += columns[columns.length - 1].toString() + ");";
            PreparedStatement statement = c.prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
            UtilConsoleLog.Log("Database Tables", "Added new table " + name + " with " + columns.length + " columns.");
            return true;
        } catch (Exception e) {
            UtilConsoleLog.Log("�4Database Tables", "Failed to add table " + name + " with " + columns.length + " columns.");
            return false;
        }
    }

}
