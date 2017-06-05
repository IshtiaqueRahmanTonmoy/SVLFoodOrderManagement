package com.example.administrator.svlfoodordermanagement;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on 9/4/2016.
 */
public class ConnectionClass {
    Connection conn = null;
    //  tools:showIn="@layout/activity_main">
    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String ConnURL = null;
        Log.d("ERR", "start connect SQL SERVER");
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");//.newInstance();
            String connString = "jdbc:jtds:sqlserver://192.168.56.1/ASSDB;user=sa;password=sa@123;instance=SQLEXPRESS;";
            //String connString = "jdbc:odbc:connection_DSN";
            conn = DriverManager.getConnection(connString);
            Log.d("SUCCESS", "Connection successful ");
            return conn;
        } catch (SQLException e) {
            Log.d("ERR", "ERROR CONNECTION 1 = " + e.getMessage());
            e.printStackTrace();
            // return false ;
        } catch (Exception ex) {
            Log.d("ERR", "ERROR CONNECTION 2 = " + ex.getMessage());
            ex.printStackTrace();
            //return false ;
        }
        return conn;
    }
}
