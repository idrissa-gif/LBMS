package com.example.lbms;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
        private Connection datalink;
        public static String UserName , Password ;
        public Connection getConnection(String userName , String password)
        {
            String url = "jdbc:mysql://localhost:3306/koha_iutlib?AutoReconnect=true&&useSSL=false";
            try
            {
                UserName=userName;
                Password= DigestUtils.sha3_256Hex(password);
                datalink = DriverManager.getConnection(url,"root","admin123");
            }
            catch (SQLException e) {
                e.printStackTrace();
                printSQLException(e);

            }
            return datalink;
        }
        public static void printSQLException(SQLException ex) {
            for (Throwable e: ex) {
                if (e instanceof SQLException) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                    System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
}
