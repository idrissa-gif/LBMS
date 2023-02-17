package com.example.lbms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentBorrowedBkController implements Initializable {
    public TableView<Book> BookTableView;
    public TableColumn<Book,String> BookIdTableColumn;

    public TableColumn <Book,String>TitleTableColumn;
    public TableColumn <Book,String>AuthorColumn;
    public TableColumn <Book,Integer> CopiesColumn;
    public TableColumn <Book,String> CopyrightTableColumn;

    ObservableList<Book> Borrowedoblist = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String query = "SELECT B.book_id, B.book_name, B.book_author, B.copyrightdate, B.book_num FROM books B , issues I WHERE B.book_id  = I.itemnumber AND I.borrowernumber = (SELECT borrowernumber from borrowers where cardnumber = '"+StudentController.borrowernum+"')";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Borrowedoblist.add(new Book(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3),rs.getString(4),Integer.parseInt(rs.getString(5))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        TitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookTitle"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));
        CopyrightTableColumn.setCellValueFactory(new PropertyValueFactory<>("Copyrightdate"));
        CopiesColumn.setCellValueFactory(new PropertyValueFactory<>("BookNum"));
        BookTableView.setItems(Borrowedoblist);
    }
}
