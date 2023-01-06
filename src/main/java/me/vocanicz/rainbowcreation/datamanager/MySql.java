package me.vocanicz.rainbowcreation.datamanager;

import me.vocanicz.rainbowcreation.Rainbowcreation;
import me.vocanicz.rainbowcreation.chat.Console;

import java.sql.*;

public class MySql {
    private Connection conn;
    private final String url;
    private final String usr;
    private final String psswd;
    public MySql() {
        Console.info("Create new connection to SQL network");
        Rainbowcreation plugin = Rainbowcreation.getInstance();
        url = plugin.defaultSql;
        usr = plugin.account.get("user").toString();
        psswd = plugin.account.get("password").toString();
        connect();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, usr, psswd);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void set(String table, String key, Object value,String wkey, Object wvalue) {
        String query = "UPDATE " + table + " SET " + key + " = ? WHERE " + wkey + " = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setObject(1, value);
            stmt.setObject(2, wvalue);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String table, String key, String wkey, Object wvalue) {
        String query = "SELECT * FROM " + table + " WHERE " + wkey + " = ?;";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setObject(1, wvalue);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getString(key);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}