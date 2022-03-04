package com.example.lbms;

import javafx.scene.control.Alert;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
        private Connection datalink;
        static private String UserName , Password ;

    public byte[] getHash(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public  String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
    public void insertStudentInfo(String sid,String fname, String email ,String password,String p)
        {
            String url = "jdbc:mysql://localhost:3306/koha_iutlib?AutoReconnect=true&&useSSL=false";
            String INSERT_QUERY = "INSERT INTO Student (stu_id, Name, Email , Password) VALUES (?, ?, ?, ?)";
            try {
                System.out.println(UserName +" "+Password);
                datalink = DriverManager.getConnection(url,UserName,Password);
                PreparedStatement preparedStatement = datalink.prepareStatement(INSERT_QUERY);
                preparedStatement.setString(1, sid );
                preparedStatement.setString(2, fname);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, password);
                preparedStatement.executeUpdate();
                show("The Student Successfully enrolled and his/her Password is " + p);
            }
            catch (SQLException e) {
                e.printStackTrace();
                printSQLException(e);
            }
        }
        public void RemoveStudent(String stu)
        {
            String url = "jdbc:mysql://localhost:3306/koha_library?AutoReconnect=true&&useSSL=false";
            String REMOVE_QUERY = "DELETE FROM Student where stu_id= ?";
            try
            {
                datalink = DriverManager.getConnection(url,UserName,Password);
                PreparedStatement statement = datalink.prepareStatement(REMOVE_QUERY);
                statement.setInt(1,Integer.parseInt(stu));
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                printSQLException(e);
            }
        }
        public Connection getConnection(String userName , String password)
        {
            String url = "jdbc:mysql://localhost:3306/koha_iutlib?AutoReconnect=true&&useSSL=false";
            try
            {
                UserName=userName;
                Password=password;
                datalink = DriverManager.getConnection(url,UserName,Password);
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
        public void show(String contain)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Conformation");
            alert.setHeaderText(null);
            alert.setContentText(contain);
            alert.show();
        }

}
