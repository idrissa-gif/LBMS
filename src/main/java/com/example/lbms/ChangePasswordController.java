package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    public Button CancelButton;
    public PasswordField currentPasswordTextField;
    public Button ChangeButton;
    public PasswordField NewPasswordTextField;
    public PasswordField ConformationTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void ClickOnCancelButton(ActionEvent actionEvent) {
        Stage window = (Stage) CancelButton.getScene().getWindow();
        window.close();
    }

    public void ClickOnChangeButton(ActionEvent actionEvent) throws SQLException {
        DatabaseConnection conn = new DatabaseConnection();
        if(NewPasswordTextField.getText().equals(ConformationTextField.getText()))
        {
            String query = "UPDATE Librarian SET password = '"+ DigestUtils.sha3_256Hex(NewPasswordTextField.getText())+"' WHERE Lib_name = '"+Main.user+"'";
            PreparedStatement preparedStatement = conn.getConnection("root","admin123").prepareStatement(query);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Do you really want to change your password ?");
            alert.initOwner(ChangeButton.getScene().getWindow());
            Optional<ButtonType> RES = alert.showAndWait();
            if(RES.get()==ButtonType.OK)
            {
                int count = preparedStatement.executeUpdate();

                if(count>0)
                {
                    showAlert(Alert.AlertType.INFORMATION,ChangeButton.getScene().getWindow(),"Successfully","Password successfully Updated");
                }
            }

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
