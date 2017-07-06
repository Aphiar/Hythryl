package mineward.core.common.database;

public class DBTableColumn {

    public String name;
    public String type;
    public boolean notnull;
    public int length;

    public DBTableColumn(String name, String type, boolean notnull, int length) {
        this.name = name;
        this.type = type;
        this.notnull = notnull;
        this.length = length;
    }

    public static DBTableColumn getColumn(String name, String type, boolean notnull, int length) {
        return new DBTableColumn(name, type, notnull, length);
    }

    public String toString() {
        String str = name + " " + type.toUpperCase();
        if (length > -1) {
            str += "(" + length + ")";
        }
        if (notnull) {
            str += " NOT NULL";
        }
        return str;
    }

}
