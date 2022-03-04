package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RemoveStudentController implements Initializable {

    public Button Removebutton;
    public TextField Std_IDTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void RemovebuttonClick(ActionEvent actionEvent) {
        DatabaseConnection connectionNow = new DatabaseConnection();
        Window wind = Removebutton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you really want to remove this student ?");
        alert.initOwner(wind);
        Optional<ButtonType> RES = alert.showAndWait();
        if(RES.get()==ButtonType.OK)
        {
            connectionNow.RemoveStudent(Std_IDTextField.getText());
            return;
        }
        alert.show();

    }
}
