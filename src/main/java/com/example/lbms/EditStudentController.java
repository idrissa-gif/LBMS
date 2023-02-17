package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.lbms.LoginController.showAlert;

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
    public void ClickOnEditButton(ActionEvent actionEvent) throws SQLException {
        DatabaseConnection conn = new DatabaseConnection();
        String Query = "UPDATE borrowers set cardnumber = '" + IdStudentTextField.getText() + "' , surname = '" + FirstnameTextField.getText() + "', firstname = '"+LastnameTextField.getText()+"', address = '"+AddressTextField.getText()+"', phone = '"+PhoneTextFiled.getText()+"', country = '"+CountryTextFiled.getText()+"', email = '"+EmailTextField.getText()+"' WHERE cardnumber = '" + Integer.parseInt(IdStudentTextField.getText()) + "'";
        PreparedStatement preparedStbooksatement = conn.getConnection("root", "admin123").prepareStatement(Query);
        int cnt = preparedStbooksatement.executeUpdate();

        if (cnt > 0) {
            showAlert(Alert.AlertType.INFORMATION, EditButton.getScene().getWindow(), "Successful", "Book successfully Edited");
        } else {
            showAlert(Alert.AlertType.WARNING, EditButton.getScene().getWindow(), "Failed", "Book would not be Edited");
        }

    }
}
