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
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            showAlert(Alert.AlertType.WARNING,wind,"Form Error !", "Failed to connect to the database! ");
            return ;
        }
        try
        {
            Main.user = DBUserName1.getText();
            Main.Passwd = DigestUtils.sha3_256Hex(DBPassword1.getText());
            System.out.println(DigestUtils.sha3_256Hex(DBPassword1.getText()));
            String query = "SELECT * FROM Librarian where Lib_name = '"+DBUserName1.getText()+"' and password = '"+ DigestUtils.sha3_256Hex(DBPassword1.getText())+"'";
            PreparedStatement preparedStatement = conn.getConnection("root","admin123").prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Stage stage = new Stage();
                Stage window = (Stage) ConnectButton1.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                Scene scene = new Scene(root,900,600);
                stage.setScene(scene);
                window.close();
                stage.show();
                showAlert(Alert.AlertType.INFORMATION, scene.getWindow(), "Info","Welcome sir !!");
            }
            else
            {
                showAlert(Alert.AlertType.WARNING,wind,"Form Error !", "Invalid Userid or Password!!!");
                return ;
            }

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
