package com.example.lbms;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditBookController implements Initializable {
    public TextField BookIDTextField;
    public TextField BookTitleTextField;
    public TextField AuthorTextField;
    public TextField ISBNTextField;
    public TextField CellTextField;
    public Button EditButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    void setTextField(String Booknumber, String title, String author, String isbn,String cellno) {
        BookIDTextField.setText(Booknumber); ;
        BookTitleTextField.setText(title);
        AuthorTextField.setText(author);
        ISBNTextField.setText(isbn);
    }
}
