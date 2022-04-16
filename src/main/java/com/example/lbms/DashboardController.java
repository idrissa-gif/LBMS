package com.example.lbms;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Main.user);
        showImage(Main.user);

    }
}
