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

public class AddBookController implements Initializable {

    public TextField TitleTextField;
    public TextField AuthorTextField;
    public TextField ISBNTextField;
    public Button CancelButton;
    public Button AddBookButton;
    public TextField CopyrightTextField;
    public TextField FrameworkcodeTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void ClickAddBookButton(ActionEvent actionEvent) throws SQLException {
        DatabaseConnection conn = new DatabaseConnection();
        String Check = "SELECT book_name INTO ? FROM books where book_name = '?'";
        String title = "";
        PreparedStatement preparedStatement = conn.getConnection("root", "admin123").prepareStatement(Check);
        preparedStatement.setString(1,title);
        preparedStatement.setString(2, TitleTextField.getText());
        int cnt = preparedStatement.executeUpdate();
        if (TitleTextField.getText()!=title) {
            String query = "INSERT INTO books(book_author, book_name , copyrightdate) VALUES( ? , ? , ? )";
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            /*Date date=new java.util.Date();
            Date sqlDate=new java.sql.Date(date.getTime());
            java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());*/
            ps.setString(1, AuthorTextField.getText());
            ps.setString(2, TitleTextField.getText());
            ps.setString(3, CopyrightTextField.getText());
            /*ps.setTimestamp(5,  sqlTime);
            ps.setDate(6, (java.sql.Date) sqlDate);*/
            int count = ps.executeUpdate();
            if (count > 0) {
                showAlert(Alert.AlertType.INFORMATION, AddBookButton.getScene().getWindow(), "Successful", "Book successfully added");
            } else {
                showAlert(Alert.AlertType.WARNING, AddBookButton.getScene().getWindow(), "Failed", "Book would not be added");
            }

        } else {
            String query = "UPDATE books SET book_num = book_num + 1 where book_name = '?'";
            PreparedStatement preparedStatement1 = conn.getConnection("root","admin123").prepareStatement(query);
            preparedStatement1.setString(1,TitleTextField.getText());
            int count = preparedStatement1.executeUpdate();
            if (count > 0) {
                showAlert(Alert.AlertType.INFORMATION, AddBookButton.getScene().getWindow(), "Successful", "Book successfully added");
            } else {
                showAlert(Alert.AlertType.WARNING, AddBookButton.getScene().getWindow(), "Failed", "Book would not be added");
            }
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
