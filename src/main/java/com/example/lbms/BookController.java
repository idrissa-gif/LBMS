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
import javafx.scene.input.MouseButton;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookController implements Initializable {
    public Button SearchButton;
    public TableView <Book>BookTableView;
    public TableColumn<Book,String> BookIdTableColumn;

    public TableColumn <Book,String>TitleTableColumn;
    public TableColumn <Book,String>AuthorColumn;
    public TableColumn <Book,Integer> CopiesColumn;
    public TableColumn <Book,String> CopyrightTableColumn;
    public TableColumn<Book,String> EditDelTableColumn;
    public TextField SearchTextFiled;
    public FontAwesomeIconView AddBookButton;
    public FontAwesomeIconView DelBookButton;
    public static Integer bookid = 1;


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
        BookTableView.getItems().clear();
        SearchTextFiled.clear();
        try {
            String query = "SELECT book_id, book_name, book_author, copyrightdate, book_num FROM books";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                oblist.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BookIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        TitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookTitle"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));
        CopyrightTableColumn.setCellValueFactory(new PropertyValueFactory<>("Copyrightdate"));
        CopiesColumn.setCellValueFactory(new PropertyValueFactory<>("BookNum"));
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
            String query = "SELECT book_id, book_name,book_author,copyrightdate,book_num FROM books where  book_name LIKE '%"+SearchTextFiled.getText()+"%' OR book_id LIKE '%"+SearchTextFiled.getText()+"%' OR book_author LIKE '%"+SearchTextFiled.getText()+"%' OR copyrightdate LIKE '%"+SearchTextFiled.getText()+"%' OR book_num LIKE '%"+SearchTextFiled.getText()+"%'";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                oblist.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BookIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        TitleTableColumn.setCellValueFactory(new PropertyValueFactory<>("BookTitle"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("BookAuthor"));
        CopyrightTableColumn.setCellValueFactory(new PropertyValueFactory<>("Copyrightdate"));
        CopiesColumn.setCellValueFactory(new PropertyValueFactory<>("BookNum"));
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
                                String query = "DELETE FROM books WHERE book_id='"+book.getBookID()+"'";
                                DatabaseConnection conn = new DatabaseConnection();
                                PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Delete Conformation?");
                                alert.setHeaderText(null);
                                alert.setContentText("Do you really can to delete the Book ID "+book.getBookID());
                                alert.initOwner(BookTableView.getScene().getWindow());
                                Optional<ButtonType> RES = alert.showAndWait();
                                if(RES.get() == ButtonType.OK)
                                {
                                    ps.execute();
                                    refreshTableView();
                                }
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
                            editBookController.setTextField(book.getBookID(), book.getBookTitle(),book.getBookAuthor(), book.getCopyrightdate(),book.getBookNum());
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

    public void GetBookIdValues() {
        Book book = BookTableView.getItems().get(BookTableView.getSelectionModel().getSelectedIndex());
        bookid = Integer.valueOf(book.getBookID());
    }
    public void ViewBookBorrowedbySt(MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if(mouseEvent.getClickCount()==2) {
                GetBookIdValues();
                Stage window = (Stage) BookTableView.getScene().getWindow();
                Parent page = FXMLLoader.load(getClass().getResource("BookBorrowedBkBySt.fxml"));
                Scene scene = new Scene(page);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(window);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
}
