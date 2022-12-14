package com.example.lbms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookBorrowedbySTController implements Initializable {
    public TableView<Student> StudentTableView;
    public TableColumn<Student, String> StudentIdTableColumn;
    public  TableColumn<Student, String> FirstNameTableColumn;
    public TableColumn<Student, String> LastNameTableColumn;
    public TableColumn <Student, String> PhoneTableColumn;
    public TableColumn <Student, String> AddressTableColumn;
    public TableColumn <Student, String> CountryTableColumn;
    public TableColumn<Student, String> EmailTableColumn;
    public Label TitleField;
    @FXML
    ObservableList<Student> Borrowedoblist = FXCollections.observableArrayList();

    public AnchorPane HeaderPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            String query = "SELECT B.cardnumber, B.surname , B.firstname ,B.phone ,B.address , B.country , B.email  FROM koha_iutlib.borrowers B , koha_iutlib.issues I WHERE I.borrowernumber = B.borrowernumber and I.itemnumber = (SELECT book_id from books where book_id = '"+BookController.bookid+"')";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Borrowedoblist.add(new Student(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        StudentIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        FirstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        LastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        PhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        AddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        CountryTableColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        EmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        StudentTableView.setItems(Borrowedoblist);
    }

}
