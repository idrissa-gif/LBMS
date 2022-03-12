package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {
    public CheckBox MaleCheckBox;
    public CheckBox FemaleCheckBox;
    @FXML
    private Button Enroll;
    @FXML
    private TextField NameText;
    @FXML
    private TextField EmailText;
    @FXML
    private TextField StudentIDText;

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

    public void EnrollbuttonOnclick(ActionEvent actionEvent) {
        DatabaseConnection connectionNow = new DatabaseConnection();
        String pass = null;
        String passwd= PasswordGenerator();
        try {
            pass = connectionNow.toHexString(connectionNow.getHash(passwd));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        connectionNow.insertStudentInfo(StudentIDText.getText(),NameText.getText(),EmailText.getText(),pass,passwd);
        StudentIDText.setText("");
        NameText.setText("");
        EmailText.setText("");
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
