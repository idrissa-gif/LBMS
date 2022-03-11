package com.example.lbms;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditStudentController implements Initializable {
    public TextField IdStudentTextField;
    public TextField FirstnameTextField;
    public TextField LastnameTextField;
    public TextField AddressTextField;
    public TextField PhoneTextFiled;
    public TextField EmailTextField;
    public TextField CountryTextFiled;
    public Button EditButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void setTextField(String id, String fname, String lname, String address, String phone, String email, String country) {
        IdStudentTextField.setText(id);
        FirstnameTextField.setText(fname);
        LastnameTextField.setText(lname);
        AddressTextField.setText(address);
        PhoneTextFiled.setText(phone);
        EmailTextField.setText(email);
        CountryTextFiled.setText(country);
    }
}
