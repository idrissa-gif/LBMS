package com.example.lbms;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public TableColumn<Book,String> EditDelTableColumn;
    public TextField SearchTextFiled;
    public FontAwesomeIconView AddBookButton;
    public FontAwesomeIconView DelBookButton;

    @FXML
    ObservableList<Book> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadDate();
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
    public void ClickOnAddBookButton(MouseEvent actionEvent) throws IOException {
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

    public void ClickOnDelBookButton(MouseEvent actionEvent) throws IOException, SQLException {
        SearchTextFiled.setText(null);
        BookTableView.getSelectionModel().getSelectedItems().clear();
        refreshTableView();
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

    private void loadDate() throws SQLException {

        refreshTableView();
        //add cell of button edit
        Callback<TableColumn<Book, String>, TableCell<Book, String>> cellFoctory = (TableColumn<Book, String> param) -> {
            // make cell containing buttons
            final TableCell<Book, String> cell = new TableCell<Book, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                "-fx-font-family: FontAwesome; -fx-fill: RED; -fx-font-size: 28px"
                        );
                        editIcon.setStyle(
                                "-fx-font-family: FontAwesome; -fx-fill: BLUE; -fx-font-size: 28px"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                Book book =BookTableView.getSelectionModel().getSelectedItem();
                                String query = "DELETE FROM items WHERE itemnumber  = '"+book.getBookID()+"'";
                                DatabaseConnection conn = new DatabaseConnection();
                                PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);

                                ps.execute();
                                refreshTableView();

                            } catch (SQLException ex) {
                                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Book book = BookTableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("EditBook.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            EditBookController editBookController = loader.getController();
                            editBookController.setTextField(book.getBookID(), book.getBookCell(),book.getBookAuthor(),book.getBibionumber(), book.getBookTitle());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };
            return cell;
        };
        EditDelTableColumn.setCellFactory(cellFoctory);
        BookTableView.setItems(oblist);
    }
}
