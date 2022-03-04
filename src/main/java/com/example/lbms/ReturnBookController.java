package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.lbms.IssueBookController.isNumeric;

public class ReturnBookController implements Initializable {

    public TextField BookNumberTextField;
    public TextField StudentIDTextField;
    public Button ReturnButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void ClickOnReturnButton(ActionEvent actionEvent) throws SQLException {
        if(StudentIDTextField.getText()=="")
        {
            LoginController.showAlert(Alert.AlertType.INFORMATION,ReturnButton.getScene().getWindow(),"Input Error","Student ID is blank, Please input a valid ID");
            return ;
        }
        if(BookNumberTextField.getText()=="")
        {
            LoginController.showAlert(Alert.AlertType.INFORMATION,ReturnButton.getScene().getWindow(),"Input Error","Bood ID is blank, Please input a valid ID");
            return ;
        }
        if(!isNumeric(String.valueOf(StudentIDTextField.getText())))
        {
            LoginController.showAlert(Alert.AlertType.WARNING,ReturnButton.getScene().getWindow(),"Input ERROR","Student ID input is not number");
            return ;

        }
        if(!isNumeric(String.valueOf(BookNumberTextField.getText())))
        {
            LoginController.showAlert(Alert.AlertType.WARNING,ReturnButton.getScene().getWindow(),"Input ERROR","Book ID input is not number");
            return;
        }
        int status, borrownumber = Integer.parseInt(String.valueOf(StudentIDTextField.getText()));
        int itemnumber = Integer.parseInt(String.valueOf(BookNumberTextField.getText()));
        DatabaseConnection conn = new DatabaseConnection();
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
        String query = "DELETE FROM issues WHERE itemnumber = ? AND borrowernumber = ?";
        PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
        ps.setInt(1,itemnumber);
        ps.setInt(2,borrownumber);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you really want to remove this student ?");
        alert.initOwner(ReturnButton.getScene().getWindow());
        Optional<ButtonType> RES = alert.showAndWait();
        if(RES.get()==ButtonType.OK)
        {
            status = ps.executeUpdate();
            ps.close();

            if(status>0)
            {
                LoginController.showAlert(Alert.AlertType.INFORMATION,ReturnButton.getScene().getWindow(),"Operation Successful","Book "+BookNumberTextField.getText()+ " Successfully Returned from"+" Student ID "+StudentIDTextField.getText());
                System.out.println("Successul query Execution ");
                return ;
            }
            else
            {
                LoginController.showAlert(Alert.AlertType.INFORMATION,ReturnButton.getScene().getWindow(),"Operation Failed","Return Book Operation failed");

                System.out.println("Failed to Return");
                return ;
            }
        }

    }
}
