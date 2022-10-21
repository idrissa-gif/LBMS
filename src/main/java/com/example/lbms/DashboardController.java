package com.example.lbms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    public BarChart Adminchart;
    public javafx.scene.chart.CategoryAxis CategoryAxis;
    public javafx.scene.chart.NumberAxis NumberAxis;
    public Label RoleLabel;
    public Label AddressLabel;
    public Label phoneLabel;
    public Label emailLabel;
    public Label Namelabel;
    public AnchorPane Profile;
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
        //System.out.println(Main.user);
        showImage(Main.user);
        try {
            showChart();
            getUserInfo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void showAlert(Alert.AlertType error, Window wind, String Tittle, String message) {
        Alert alert = new Alert(error);
        alert.setTitle(Tittle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(wind);
        alert.show();
    }
    public void showChart() throws SQLException {
        XYChart.Series<String , Integer> Borrowedseries = new XYChart.Series<>();
        XYChart.Series<String , Integer> Returnseries = new XYChart.Series<>();
        XYChart.Series<String , Integer> Pendingseries = new XYChart.Series<>();
        Borrowedseries.setName("Books Borrowed");
        Returnseries.setName("Books Returned");
        Pendingseries.setName("Books Pending");
        DatabaseConnection conn = new DatabaseConnection();
        String query1 = "SELECT COUNT(*) FROM issues";
        String query2 = "SELECT COUNT(*) FROM old_issues";
        PreparedStatement ps1 = conn.getConnection("root","admin123").prepareStatement(query1);
        PreparedStatement ps2 = conn.getConnection("root","admin123").prepareStatement(query2);
        ResultSet rs1 = ps1.executeQuery();
        ResultSet rs2 = ps2.executeQuery();
        if(rs1.next()&& rs2.next())
        {
            Borrowedseries.getData().add(new XYChart.Data<>("Borrowed",rs1.getInt(1)));
            Returnseries.getData().add(new XYChart.Data<>("Returned",rs2.getInt(1)));
            Pendingseries.getData().add(new XYChart.Data<>("Pending",rs1.getInt(1)-rs2.getInt(1)));
        }
        Adminchart.getData().addAll(Borrowedseries,Returnseries,Pendingseries);
    }
    void getUserInfo() throws SQLException {
        DatabaseConnection conn = new DatabaseConnection();
        String query = "SELECT * FROM Librarian WHERE Lib_name = '"+Main.user+"'";
        PreparedStatement ps = conn.getConnection("root","admin123").prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            Namelabel.setText(rs.getString(2));
            AddressLabel.setText(rs.getString(3));
            phoneLabel.setText(rs.getString(4));
            emailLabel.setText(rs.getString(5));
        }
    }
}
