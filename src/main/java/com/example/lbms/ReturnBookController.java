package com.example.lbms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.lbms.IssueBookController.isNumeric;

public class ReturnBookController implements Initializable {

    public TextField BookNumberTextField;
    public TextField StudentIDTextField;
    public Button ReturnButton;
    public TableView <Book>BookTableView;
    public TableColumn<Book,String> BookIdTableColumn;
    public TableColumn <Book,String>TitleTableColumn;
    public TableColumn <Book,String>AuthorColumn;
    public TableColumn <Book,Integer> CopiesColumn;
    public TableColumn <Book,String> CopyrightTableColumn;

    // Student attributes
    public  TableView<Student> StudentTableView;
    public  TableColumn<Student, String> IdTableColumn;
    public  TableColumn<Student, String> FirstNameTableColumn;
    public  TableColumn<Student, String> LastNameTableColumn;
    public  TableColumn <Student, String> PhoneTableColumn;
    public  TableColumn <Student, String> AddressTableColumn;
    public  TableColumn <Student, String> CountryTableColumn;
    public  TableColumn<Student, String> EmailTableColumn;
    @FXML
    ObservableList<Student> StudentOblist = FXCollections.observableArrayList();
    ObservableList<Book> BookOblist = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    public void refreshStudentTableView() throws SQLException {
        StudentTableView.getItems().clear();
        try {
            String query = "SELECT cardnumber ,surname,firstname , phone , address ,country ,email from borrowers";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StudentOblist.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        FirstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        LastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        PhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        AddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        CountryTableColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        EmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        StudentTableView.setItems(StudentOblist);
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
        int status ;
        String cardnumber = StudentIDTextField.getText();
        int itemnumber = Integer.parseInt(String.valueOf(BookNumberTextField.getText()));
        DatabaseConnection conn = new DatabaseConnection();
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
        String query = "DELETE FROM issues WHERE itemnumber = ? AND borrowernumber = (SELECT borrowernumber FROM borrowers WHERE cardnumber = '"+cardnumber+"')";
        String query1 = "INSERT INTO statistics(datetime,branch,value,type,itemnumber,borrowernumber) VALUES('"+sqlTime+"','IUTL',0.00,'return','"+itemnumber+"',(SELECT borrowernumber FROM borrowers WHERE cardnumber = '"+cardnumber+"')) ";
        PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
        PreparedStatement ps1 = conn.getConnection("root", "admin123").prepareStatement(query1);
        ps.setInt(1,itemnumber);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Confirm return ?");
        alert.initOwner(ReturnButton.getScene().getWindow());
        Optional<ButtonType> RES = alert.showAndWait();
        if(RES.get()==ButtonType.OK)
        {
            status = ps.executeUpdate();
            int cnt = ps1.executeUpdate();
            ps.close();

            if(status>0&&cnt>0)
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

    public void MouseExitedStudent(MouseEvent mouseEvent) {

    }

    public void MouseExitedBook(MouseEvent mouseEvent) {
    }
}
