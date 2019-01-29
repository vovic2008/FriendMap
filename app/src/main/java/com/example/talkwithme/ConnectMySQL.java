package com.example.talkwithme;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ConnectMySQL {
    private final String PAGETAG = "ConnectMySQL";

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    public static final String MYSQL_IP = "db.fast.md";
    public static final String MYSQL_DBNAME = "C288DB";
    public static final String MYSQL_USERNAME = "C288dbuRW";
    public static final String MYSQL_PASSWORD = "CaeqX_8R";

    public ArrayList<MyObj> getList() throws Exception {
        ArrayList<MyObj> results = new ArrayList<MyObj>();
        try {
            String script = "SELECT * FROM map";

            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");

            String path = "jdbc:mysql://"+MYSQL_IP+"/"+MYSQL_DBNAME+"?"+ "user="+MYSQL_USERNAME+"&password="+MYSQL_PASSWORD;
            Log.e(PAGETAG, path);

            // Setup the connection with the DB
            connect = (Connection) DriverManager.getConnection(path);

            Log.e(PAGETAG, "connection is success");

            // Statements allow to issue SQL queries to the database
            statement = (Statement) connect.createStatement();

            // Result set get the result of the SQL query
            resultSet = (ResultSet) statement.executeQuery(script);

            while (resultSet.next()) {
                MyObj obj = new MyObj();
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String lat = resultSet.getString("lat");
                String lng = resultSet.getString("lng");

                obj.id = Integer.parseInt(id);
                obj.name = name;
                obj.lat = Float.parseFloat(lat);
                obj.lng = Float.parseFloat(lng);
                results.add(obj);
            }

            Log.e(PAGETAG, "results size = "+results.size());
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

        return results;
    }

    /* close connect */
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

    public class MyObj{
        int id = 0;
        String name = "";
        float lat = 0;
        float lng = 0;
    }
}