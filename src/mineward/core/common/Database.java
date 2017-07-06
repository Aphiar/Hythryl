package mineward.core.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mineward.core.Core;
import mineward.core.common.database.DBTableColumn;
import mineward.core.common.database.TableMaker;

public class Database {

    private static Connection connection;
    private static String URL_HOST;
    private static String USER;
    private static String PASS;
    private static boolean dbDown = false;

    public static void PrepareConnection() {
        URL_HOST = "jdbc:mysql://" + Core.Config.getString("database-host") + ":" + Core.Config.getInt("database-port") + "/" + Core.Config.getString("database-name") + "?autoReconnect=true&useSSL=false";
        USER = Core.Config.getString("database-username");
        PASS = Core.Config.getString("database-password");
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL_HOST, USER, PASS);
                dbDown = false;
                return connection;
            } catch (SQLException e) {
                dbDown = true;
                e.printStackTrace();
                return null;
            }
        } else {
            dbDown = false;
            return connection;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null || !(connection.isClosed())) {
                connection.close();
                dbDown = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnectionLost() {
        return dbDown;
    }

    public static boolean doesTableExist(String name) {
        Connection con = Database.getConnection();
        try {
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, name, null);
            boolean exists = tables.next();
            tables.close();
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean safetyCheck(String tableName, DBTableColumn[] columns) {
        if (isConnectionLost()) {
            return false;
        }
        if (!(doesTableExist(tableName))) {
            boolean made = TableMaker.createTable(tableName, columns);
            return made ? true : false;
        }
        return true;
    }

    public static boolean runUpdateStatement(String sql) {
        try {
            PreparedStatement statement = Database.getConnection()
                    .prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
