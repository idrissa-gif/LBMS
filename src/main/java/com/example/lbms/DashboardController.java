package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public ImageView ImageViewAdmin;
    public Hyperlink Newpassword;
    private String id;

    public void showImage(String id)
    {
        this.id = id;
        try
        {
            DatabaseConnection conn = new DatabaseConnection();
            String query = "SELECT image FROM Librarian WHERE Lib_name = ?";
            //String query = "SELECT imagefile FROM patronimage WHERE borrowernumber = 1";
            PreparedStatement preparedStatement = conn.getConnection("root","admin123").prepareStatement(query);
            preparedStatement.setString(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next())
            {
                Blob blob = resultSet.getBlob(1);
                InputStream inputStream = blob.getBinaryStream();
                //, ImageViewAdmin.getFitWidth(),ImageViewAdmin.getFitHeight(),true,true
                Image image = new Image(inputStream);
                ImageViewAdmin.setImage(image);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void chooseFile_and_changeImage(ActionEvent actionEvent) throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(ImageViewAdmin.getScene().getWindow());
        String ChangeQuery = "UPDATE Librarian SET image = ? WHERE Lib_name = ?";
        try {
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root","admin123").prepareStatement(ChangeQuery);
            FileInputStream fileInputStream = new FileInputStream(file);
            ps.setBlob(1, fileInputStream);
            ps.setString(2,id);
            int count= ps.executeUpdate();
            if(count>0)
            {
                showAlert(Alert.AlertType.INFORMATION,ImageViewAdmin.getScene().getWindow(),"Updated","Successfully Updated");
                showImage(id);
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void changePassword() throws IOException {
        Stage window = (Stage) Newpassword.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ChangePassword.fxml"));
        Scene scene = new Scene(root);
        //Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(window);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Main.user);
        showImage(Main.user);

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
