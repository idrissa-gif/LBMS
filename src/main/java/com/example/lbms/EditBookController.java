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
    public Button EditButton;
    public TextField CopyrightdateTextField;
    public TextField CopiesTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    void setTextField(Integer Bookid, String title, String author, String copyrightdate,Integer Booknum) {
        BookIDTextField.setText(String.valueOf(Bookid)); ;
        BookTitleTextField.setText(title);
        AuthorTextField.setText(author);
        CopyrightdateTextField.setText(copyrightdate);
        CopiesTextField.setText(String.valueOf(Booknum));
    }
}
