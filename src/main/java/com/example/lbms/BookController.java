package com.example.lbms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    public Button SearchButton;
    public TableView <Book>BookTableView;
    public TableColumn<Book,String> BookIdTableColumn;
    public TableColumn <Book,String>BookNumberTableColumn;
    public TableColumn <Book,String>TitleTableColumn;
    public TableColumn <Book,String>AuthorColumn;
    public TableColumn <Book,String>ISBNColumn;
    public TableColumn <Book,String>BibioTableColumn;
    public TableColumn <Book,String>StatusTableColumn;
    public Button DelBookButton;
    public Button AddBookButton;
    public TextField SearchTextFiled;

    @FXML
    ObservableList<Book> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            refreshTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void refreshTableView() throws SQLException {
        try {
            String query = "SELECT I.itemnumber , B.title, I.itemcallnumber, B.author,BI.isbn,I.biblionumber, I.barcode from items I , biblio B ,biblioitems BI where (I.biblionumber=B.biblionumber AND BI.biblionumber=I.biblionumber)";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                oblist.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        BookNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookTitle"));
        TitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookCell"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        BibioTableColumn.setCellValueFactory(new PropertyValueFactory<>("bibionumber"));
        StatusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        BookTableView.setItems(oblist);
    }


    public void ClickOnAddBookButton(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) AddBookButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
        Scene scene = new Scene(root);
        //Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(window);
        stage.setScene(scene);
        stage.show();
    }

    public void ClickOnDelBookButton(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) DelBookButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("DeleteBook.fxml"));
        Scene scene = new Scene(root);
        //Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(window);
        stage.setScene(scene);
        stage.show();
    }
    public void PressOnKeyStrokes(KeyEvent keyEvent) {
        if(keyEvent.getCode()== keyEvent.getCode().ENTER)
        {
            ClickOnSearchButton(new ActionEvent());
        }
    }
    public void ClickOnSearchButton(ActionEvent actionEvent) {
        BookTableView.getItems().clear();
        try {
            String query = "SELECT I.itemnumber , B.title, I.itemcallnumber, B.author,BI.isbn,I.biblionumber, I.barcode from items I , biblio B ,biblioitems BI where ((I.biblionumber=B.biblionumber AND BI.biblionumber=I.biblionumber) AND ( I.biblionumber LIKE '%"+SearchTextFiled.getText()+"%' OR B.title LIKE '%"+SearchTextFiled.getText()+"%' OR I.itemcallnumber LIKE '%"+SearchTextFiled.getText()+"%' OR B.author LIKE '%"+SearchTextFiled.getText()+"%' OR B.biblionumber LIKE '%"+SearchTextFiled.getText()+"%' OR I.barcode LIKE '%"+SearchTextFiled.getText()+"%'))";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                oblist.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        BookNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookTitle"));
        TitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookCell"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        BibioTableColumn.setCellValueFactory(new PropertyValueFactory<>("bibionumber"));
        StatusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        BookTableView.setItems(oblist);
    }
}
