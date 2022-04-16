package com.example.lbms;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public ImageView ImageViewAdmin;

    public void showImage(String id)
    {
        try
        {
            DatabaseConnection conn = new DatabaseConnection();
            //String query = "SELECT image FROM Librarian WHERE Lib_name = ?";
            String query = "SELECT imagefile FROM patronimage WHERE borrowernumber = 1";
            PreparedStatement preparedStatement = conn.getConnection("root","admin123").prepareStatement(query);
            //preparedStatement.setString(1,id);
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
    void chooseFile_and_changeImage()
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(ImageViewAdmin.getScene().getWindow());
        String ChangeQuery = "INSERT INTO Librarian(image) VALUES(?) WHERE Lib_name = ?";
        try {
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root","admin123").prepareStatement(ChangeQuery);
            FileInputStream fileInputStream = new FileInputStream(file);
            ps.setBlob(1,(Blob) fileInputStream);
            int count= ps.executeUpdate();
            if(count>0)
            {
                showAlert(Alert.AlertType.INFORMATION,ImageViewAdmin.getScene().getWindow(),"Failed to Update the image ?","Try again please");
            }
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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
