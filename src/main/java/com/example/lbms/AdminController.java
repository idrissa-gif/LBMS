package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public Button DashBoardButton;
    public Button IssueBookButton;
    public Button returnbookButton;
    public Button LogoutButton;
    public Button StudentButton;
    public Button BookButton;
    public AnchorPane MainPage;

    @FXML
    private ImageView ImageViewadmin;
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private  Button Backbutton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AnchorPane node =  FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            AnchorPane.setLeftAnchor(node,0.0);
            AnchorPane.setRightAnchor(node,0.0);
            AnchorPane.setTopAnchor(node,0.0);
            AnchorPane.setBottomAnchor(node,0.0);
            MainPage.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*final WebEngine web =  webview.getEngine();
        web.load("http://127.0.0.1:8080/");*/
    }


    public void BackFunction(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("AddStudent.fxml"));
        Stage window = (Stage) Backbutton.getScene().getWindow();
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        window.close();
    }

    public void ClickOnDashBoardButton(ActionEvent actionEvent) throws IOException {

        AnchorPane node =  FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        AnchorPane.setLeftAnchor(node,0.0);
        AnchorPane.setRightAnchor(node,0.0);
        AnchorPane.setTopAnchor(node,0.0);
        AnchorPane.setBottomAnchor(node,0.0);
        MainPage.getChildren().setAll(node);
       /* AnchorPane pane = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        pane.prefHeightProperty().bind(MainPage.prefHeightProperty());
        pane.prefWidthProperty().bind(MainPage.widthProperty());
        MainPage.getChildren().add((Node) pane);*/

    }

    public void ClickOnLogoutButton(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage window = (Stage) LogoutButton.getScene().getWindow();
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        window.close();
    }




    public void ClickOnStudentButton(ActionEvent actionEvent) throws IOException {
        AnchorPane node =  FXMLLoader.load(getClass().getResource("Student.fxml"));
        AnchorPane.setLeftAnchor(node,0.0);
        AnchorPane.setRightAnchor(node,0.0);
        AnchorPane.setTopAnchor(node,0.0);
        AnchorPane.setBottomAnchor(node,0.0);
        MainPage.getChildren().setAll(node);
    }
    public void ClickOnBooksButton(ActionEvent actionEvent) throws IOException {
        AnchorPane node =  FXMLLoader.load(getClass().getResource("Books.fxml"));
        AnchorPane.setLeftAnchor(node,0.0);
        AnchorPane.setRightAnchor(node,0.0);
        AnchorPane.setTopAnchor(node,0.0);
        AnchorPane.setBottomAnchor(node,0.0);
        MainPage.getChildren().setAll(node);
    }

    public void ClickOnReturnBookButton(ActionEvent actionEvent) throws IOException {
        AnchorPane node =  FXMLLoader.load(getClass().getResource("ReturnBook.fxml"));
        AnchorPane.setLeftAnchor(node,0.0);
        AnchorPane.setRightAnchor(node,0.0);
        AnchorPane.setTopAnchor(node,0.0);
        AnchorPane.setBottomAnchor(node,0.0);
        MainPage.getChildren().setAll(node);
    }

    public void ClickOnIssueBookButton(ActionEvent actionEvent) throws IOException {
        AnchorPane node =  FXMLLoader.load(getClass().getResource("IssueBook.fxml"));
        AnchorPane.setLeftAnchor(node,0.0);
        AnchorPane.setRightAnchor(node,0.0);
        AnchorPane.setTopAnchor(node,0.0);
        AnchorPane.setBottomAnchor(node,0.0);
        MainPage.getChildren().setAll(node);
    }

}