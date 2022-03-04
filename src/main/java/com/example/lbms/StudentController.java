package com.example.lbms;

import com.gluonhq.charm.glisten.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public TableView<Student> StudentTableView;
    public TableColumn<Student, String> IdTableColumn;
    public TableColumn<Student, String> FirstNameTableColumn;
    public TableColumn<Student, String> LastNameTableColumn;
    public TableColumn <Student, String> PhoneTableColumn;
    public TableColumn <Student, String> AddressTableColumn;
    public TableColumn <Student, String> CountryTableColumn;
    public TableColumn<Student, String> EmailTableColumn;
    public Button SearchButton;
    public Button AddStudentButton;
    public Button DeleteStudentButton;
    public TextField SearchTextFiled;

    @FXML
    ObservableList<Student> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refreshTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshTableView() throws SQLException {
        try {
            String query = "SELECT cardnumber ,surname,firstname , phone , address ,country ,email from borrowers";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                oblist.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        FirstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        LastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        PhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        AddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        CountryTableColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        EmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        StudentTableView.setItems(oblist);
    }

    public void ClickOnSearchButton(ActionEvent actionEvent) {
        StudentTableView.getItems().clear();
        try {
            String query = "SELECT cardnumber ,surname,firstname , phone , address ,country ,email from borrowers where cardnumber LIKE '%"+SearchTextFiled.getText()+"%' OR surname LIKE '%"+SearchTextFiled.getText()+"%'OR firstname LIKE '%"+SearchTextFiled.getText()+"%'OR email LIKE '%"+SearchTextFiled.getText()+"%' ";
            DatabaseConnection conn = new DatabaseConnection();
            PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                oblist.add(new Student(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        FirstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        LastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        PhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        AddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        CountryTableColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        EmailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        StudentTableView.setItems(oblist);
    }

    public void ClickOnAddStudentButton(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) AddStudentButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddBook.fxml"));
        Scene scene = new Scene(root);
        //Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(window);
        stage.setScene(scene);
        stage.show();
    }

    public void ClickOnDeleteStudentButton(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) DeleteStudentButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("DeleteStudent.fxml"));
        Scene scene = new Scene(root);
        //Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(window);
        stage.setScene(scene);
        stage.show();
    }

    public void PressOnKeyStrokes(KeyEvent keyEvent) {
        if(keyEvent.getCode()== keyEvent.getCode().ENTER)
        {
            ClickOnSearchButton(new ActionEvent());
        }
    }
}
