package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.lbms.DatabaseConnection.printSQLException;

public class AddBookController implements Initializable {

    public TextField TitleTextField;
    public TextField AuthorTextField;
    public Button CancelButton;
    public Button AddBookButton;
    public TextField CopyrightTextField;
    public TextField CopiesTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void ClickAddBookButton(ActionEvent actionEvent) throws SQLException {
        try {
            DatabaseConnection conn = new DatabaseConnection();
            String query = "INSERT INTO books(book_author, book_name , copyrightdate,book_num) VALUES( ? , ? , ? , ? )";
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ps.setString(1, AuthorTextField.getText());
            ps.setString(2, TitleTextField.getText());
            ps.setString(3, CopyrightTextField.getText());
            ps.setInt(4, Integer.parseInt(CopiesTextField.getText()));
            int count = ps.executeUpdate();
            if (count > 0) {
                showAlert(Alert.AlertType.INFORMATION, AddBookButton.getScene().getWindow(), "Successful", "Book successfully added");
            } else {
                showAlert(Alert.AlertType.WARNING, AddBookButton.getScene().getWindow(), "Failed", "Book would not be added");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            printSQLException((SQLException) e);
            showAlert(Alert.AlertType.ERROR, AddBookButton.getScene().getWindow(), "ERROR!!", e.getMessage());
        }


    }

    public void ClickonCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    public void showAlert(Alert.AlertType error, Window wind, String Tittle, String message) {
        Alert alert = new Alert(error);
        alert.setTitle(Tittle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(wind);
        alert.show();
    }
}
