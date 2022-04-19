package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;


public class IssueBookController implements Initializable {
    public Button IssueButton;
    public TextField BookNumberTextField;
    public TextField StudentIDTextField;
    private Stage stage;
    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void ClickOnIssueButton(ActionEvent actionEvent) throws SQLException {
        if(StudentIDTextField.getText()=="")
        {
            LoginController.showAlert(Alert.AlertType.INFORMATION,IssueButton.getScene().getWindow(),"Input Error","Student ID is blank, Please input a valid ID");
            return ;
        }
        if(BookNumberTextField.getText()=="")
        {
            LoginController.showAlert(Alert.AlertType.INFORMATION,IssueButton.getScene().getWindow(),"Input Error","Bood ID is blank, Please input a valid ID");
            return ;
        }
        if(!isNumeric(String.valueOf(StudentIDTextField.getText())))
        {
            LoginController.showAlert(Alert.AlertType.WARNING,IssueButton.getScene().getWindow(),"Input ERROR","Student ID input is not number");
            return ;

        }
        if(!isNumeric(String.valueOf(BookNumberTextField.getText())))
        {
            LoginController.showAlert(Alert.AlertType.WARNING,IssueButton.getScene().getWindow(),"Input ERROR","Book ID input is not number");
            return;
        }
        int status, issue_id;
        String cardnumber =StudentIDTextField.getText();
        int itemnumber = Integer.parseInt(String.valueOf(BookNumberTextField.getText()));
        Random random = new Random();
        String Query = "INSERT issue_id,borrowernumber FROM issues";
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
        String branch ="IUTL";
        String query = "insert into issues (borrowernumber,itemnumber,date_due,branchcode,returndate) values((SELECT borrowernumber FROM borrowers WHERE cardnumber = '"+cardnumber+"'),?,curdate()+interval 4 month,'UITLIB',?)";
        String query1 = "INSERT INTO statistics(datetime,branch,value,type,itemnumber,borrowernumber) VALUES('"+sqlTime+"','"+branch+"',0.00,'issue','"+itemnumber+"',(SELECT borrowernumber FROM borrowers WHERE cardnumber = '"+cardnumber+"')) ";
        DatabaseConnection conn = new DatabaseConnection();
        PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
        PreparedStatement ps1 = conn.getConnection("root", "admin123").prepareStatement(query1);
        ps.setDate(2, sqlDate);
        ps.setInt(1, itemnumber);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to issue to this student ?");
        alert.initOwner(IssueButton.getScene().getWindow());
        Optional<ButtonType> RES = alert.showAndWait();
        if(RES.get()==ButtonType.OK)
        {
            status = ps.executeUpdate();
            int cnt = ps1.executeUpdate();
            ps.close();
            if(status>0&&cnt>0)
            {
                System.out.println("Successul query Execution");
                LoginController.showAlert(Alert.AlertType.INFORMATION,IssueButton.getScene().getWindow(),"Operation Successful","Book "+BookNumberTextField.getText()+ " Successfully Issued to"+" Student ID "+StudentIDTextField.getText());
                return ;
            }
            else
            {
                System.out.println("Failed to insert");
                LoginController.showAlert(Alert.AlertType.INFORMATION,IssueButton.getScene().getWindow(),"Operation Failed","Issue Book Operation failed");
                return ;
            }
        }
    }
}
