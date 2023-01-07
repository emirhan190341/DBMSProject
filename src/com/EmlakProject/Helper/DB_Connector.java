package com.EmlakProject.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connector {
    private Connection connect = null;


    public Connection connectDB() throws SQLException, ClassNotFoundException {
        //Helper.getClassForName();
        this.connect = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
        return this.connect;
    }

    public static Connection getInstance() throws SQLException, ClassNotFoundException {
        DB_Connector db = new DB_Connector();
        return db.connectDB();
    }

}
