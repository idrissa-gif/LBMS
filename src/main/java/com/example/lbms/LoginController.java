package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public static Connection connectionNow;
    public AnchorPane LoginAnchorpane;
    @FXML
    private TextField DBUserName1;
    @FXML
    private Label Labeltouch;
    @FXML
    private PasswordField DBPassword1;
    @FXML
    public Button ConnectButton1;
    Stage stage;
    Parent root;
    public static String pass , username;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void ConnectDB(ActionEvent actionEvent) throws SQLException {
        DatabaseConnection conn = new DatabaseConnection();
        Window wind = ConnectButton1.getScene().getWindow();
        if(DBUserName1.getText().isEmpty())
        {
            showAlert(Alert.AlertType.WARNING,wind,"Form Error !", "Please Enter your Username");
            return ;
        }
        if (DBPassword1.getText().isEmpty())
        {
            showAlert(Alert.AlertType.WARNING,wind,"Form Error !", "Please Enter your Password");
            return ;
        }
        connectionNow = conn.getConnection(DBUserName1.getText(),DBPassword1.getText());
        if(!isDbConnected(connectionNow))
        {
            showAlert(Alert.AlertType.WARNING,wind,"Form Error !", "Username or Password is invalid");
            return ;
        }
        try
        {
            Stage stage = new Stage();
            Stage window = (Stage) ConnectButton1.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("admin.fxml"));
            Scene scene = new Scene(root,900,600);
            stage.setScene(scene);
            window.close();
            showAlert(Alert.AlertType.INFORMATION,window,"Info","Welcome sir !!");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(Alert.AlertType error, Window wind, String Tittle, String message) {
        Alert alert = new Alert(error);
        alert.setTitle(Tittle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(wind);
        alert.show();
    }
    public boolean isDbConnected(Connection con) {
        try {
            return con != null && !con.isClosed();
        } catch (SQLException ignored) {}

        return false;
    }
}
