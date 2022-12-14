package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.lbms.LoginController.showAlert;

public class EditBookController implements Initializable {
    public TextField BookIDTextField;
    public TextField BookTitleTextField;
    public TextField AuthorTextField;
    public TextField CopyrightdateTextField;
    public TextField CopiesTextField;
    public Button EditbookButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    void setTextField(Integer Bookid, String title, String author, String copyrightdate, Integer Booknum) {
        BookIDTextField.setText(String.valueOf(Bookid));
        BookTitleTextField.setText(title);
        AuthorTextField.setText(author);
        CopyrightdateTextField.setText(copyrightdate);
        CopiesTextField.setText(String.valueOf(Booknum));
    }

    public void ClickOnEditButton(ActionEvent actionEvent) throws SQLException {
        DatabaseConnection conn = new DatabaseConnection();
        String Query = "UPDATE books set book_name = '" + BookTitleTextField.getText() + "' , book_author = '" + AuthorTextField.getText() + "', copyrightdate = '"+CopyrightdateTextField.getText()+"', book_num = '"+CopiesTextField.getText()+"' WHERE book_id = '" + Integer.parseInt(BookIDTextField.getText()) + "'";
        PreparedStatement preparedStatement = conn.getConnection("root", "admin123").prepareStatement(Query);
        int cnt = preparedStatement.executeUpdate();

        if (cnt > 0) {
            showAlert(Alert.AlertType.INFORMATION, BookIDTextField.getScene().getWindow(), "Successful", "Book successfully Edited");
        } else {
            showAlert(Alert.AlertType.WARNING, BookIDTextField.getScene().getWindow(), "Failed", "Book would not be Edited");
        }
    }
}
