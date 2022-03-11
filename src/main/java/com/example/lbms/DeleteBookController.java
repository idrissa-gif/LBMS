package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class DeleteBookController implements Initializable {
    public  Label BookID;
    public Label BookNumber;
    public Label Author;
    public Label BookTitle;
    public Label ISBN;
    public Button CancelButton;
    public Button DeleteButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void ClickOnCancelButton(ActionEvent actionEvent) {
        Stage window=(Stage) CancelButton.getScene().getWindow();
        window.close();
    }

    public void ClickOnDeleteButton(ActionEvent actionEvent) {
    }
}
