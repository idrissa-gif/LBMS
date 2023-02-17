package com.example.lbms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class
IssueBookController implements Initializable {
    public Button IssueButton;
    public TextField BookNumberTextField;
    public TextField StudentIDTextField;
    public Button ReturnButton;
    public TableView<Book> BookTableView;
    public TableColumn<Book, String> BookIdTableColumn;
    public TableColumn<Book, String> TitleTableColumn;
    public TableColumn<Book, String> AuthorColumn;
    public TableColumn<Book, Integer> CopiesColumn;
    public TableColumn<Book, String> CopyrightTableColumn;

    // Student attributes
    public TableView<Student> StudentTableView;
    public TableColumn<Student, String> IdTableColumn;
    public TableColumn<Student, String> FirstNameTableColumn;
    public TableColumn<Student, String> LastNameTableColumn;
    public TableColumn<Student, String> PhoneTableColumn;
    public TableColumn<Student, String> AddressTableColumn;
    public TableColumn<Student, String> CountryTableColumn;
    public TableColumn<Student, String> EmailTableColumn;
    @FXML
    ObservableList<Student> StudentOblist = FXCollections.observableArrayList();
    ObservableList<Book> BookOblist = FXCollections.observableArrayList();

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
        DatabaseConnection conn = new DatabaseConnection();
        int booknum = -1;
        String verification = "SELECT book_num FROM books where book_id='"+itemnumber+"'";
        PreparedStatement ps_verify = conn.getConnection("root","admin123").prepareStatement(verification);
        ResultSet rs= ps_verify.executeQuery();
        if(rs.next()) booknum=rs.getInt(1);
        if(booknum<=0){
            LoginController.showAlert(Alert.AlertType.WARNING,IssueButton.getScene().getWindow(),"Not Available","Book is not avalaible");
            return;
        }
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());

        String branch ="IUTL";
        String query = "insert into issues (borrowernumber,itemnumber,date_due,returndate) values((SELECT borrowernumber FROM borrowers WHERE cardnumber = '"+cardnumber+"'),?,curdate()+interval 4 month,?)";
        String query1 = "INSERT INTO statistics(datetime,branch,value,type,itemnumber,borrowernumber) VALUES('"+sqlTime+"','"+branch+"',0.00,'issue','"+itemnumber+"',(SELECT borrowernumber FROM borrowers WHERE cardnumber = '"+cardnumber+"')) ";
        String query2 = "UPDATE books set book_num=book_num-1 where book_id = '"+itemnumber+"'";

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
                PreparedStatement preparedStatement = conn.getConnection("root","admin123").prepareStatement(query2);
                preparedStatement.executeUpdate();
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
    public void MouseExitedStudent(MouseEvent mouseEvent) {
        StudentTableView.getItems().clear();
        try {
            String query = "SELECT cardnumber ,surname,firstname , phone , address ,country ,email from borrowers where cardnumber = '" + StudentIDTextField.getText() +"' ";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StudentOblist.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
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

    public void MouseExitedBook(MouseEvent mouseEvent) {
        BookTableView.getItems().clear();
        try {
            String query = "SELECT book_id, book_name,book_author,copyrightdate,book_num FROM books where  book_id = '"+ BookNumberTextField.getText() + "%' OR book_name = '"+ BookNumberTextField.getText() +"%'";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                BookOblist.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BookIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        TitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookTitle"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));
        CopyrightTableColumn.setCellValueFactory(new PropertyValueFactory<>("Copyrightdate"));
        CopiesColumn.setCellValueFactory(new PropertyValueFactory<>("BookNum"));
        BookTableView.setItems(BookOblist);
    }

}
