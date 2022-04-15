package com.example.lbms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import static com.example.lbms.DatabaseConnection.printSQLException;

public class AddStudentController implements Initializable {
    @FXML
    public CheckBox MaleCheckBox;
    public CheckBox FemaleCheckBox;
    public TextField FNameTextField;
    public TextField LNameTextField;
    public TextField StudentID;
    public TextField EmailTextField;
    public TextField PhoneTextField;
    public TextField CountryTextField;
    public TextField AddressTextField;
    public TextField CityTextField;
    public Button Enroll;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    String  PasswordGenerator()
    {
        int len = 8;
        String  lower_case = "abcdefghijklmnopqrstuvwxyz";
        String upper_case = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String Specialchars="!@#$%&*+";
        String combination = lower_case+upper_case+digits+Specialchars;
        char[] password = new char[len];
        Random r=new Random();
        for(int i=0 ; i<len ; i++)
        {
            password[i]=combination.charAt(r.nextInt(combination.length()));
        }
        //System.out.println(password);
        return String.valueOf(password);
    }



    public void insertStudentInfo()
    {
        DatabaseConnection conn =  new DatabaseConnection();
        String INSERT_QUERY = "INSERT INTO borrowers (cardnumber, surname , firstname, title,email,phone,branchcode,categorycode,dateenrolled,country,sex,password,address,city) VALUES ( ?, ?, ?,?,?,?,'IUTL','ST',?,?,?,?,?,?)";
        try {
            String Std_Password = DigestUtils.sha3_256Hex((PasswordGenerator()));
            PreparedStatement preparedStatement = conn.getConnection("root","admin123").prepareStatement(INSERT_QUERY);
            preparedStatement.setInt(1, Integer.parseInt(StudentID.getText()));
            preparedStatement.setString(2, LNameTextField.getText());
            preparedStatement.setString(3, FNameTextField.getText());
            preparedStatement.setString(5,EmailTextField.getText());
            preparedStatement.setString(6,PhoneTextField.getText());
            String pattern = "yyyy-MM-dd hh:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            preparedStatement.setTimestamp(7, Timestamp.valueOf(date));
            preparedStatement.setString(8,CountryTextField.getText());
            preparedStatement.setString(10,Std_Password);
            preparedStatement.setString(11,AddressTextField.getText()==null?"":AddressTextField.getText());
            preparedStatement.setString(12,CityTextField.getText()==null?"":CityTextField.getText());
            if(MaleCheckBox.isSelected())
            {
                preparedStatement.setString(4,"Mr");
                preparedStatement.setString(9,"M");
                FemaleCheckBox.setSelected(false);
            }
            else if(FemaleCheckBox.isSelected())
            {
                preparedStatement.setString(4,"Ms");
                preparedStatement.setString(9,"F");
                MaleCheckBox.setSelected(false);
            }
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            printSQLException((SQLException) e);
            showAlert(Alert.AlertType.ERROR,Enroll.getScene().getWindow(),"ERROR!!", e.getMessage());
        }
    }
    public void showAlert(Alert.AlertType error, Window wind, String Tittle, String message) {
        Alert alert = new Alert(error);
        alert.setTitle(Tittle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(wind);
        alert.show();
    }
}
