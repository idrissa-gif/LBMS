package com.example.lbms;

import com.gluonhq.charm.glisten.control.TextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentController implements Initializable {
    public  TableView<Student> StudentTableView;
    public  TableColumn<Student, String> IdTableColumn;
    public  TableColumn<Student, String> FirstNameTableColumn;
    public  TableColumn<Student, String> LastNameTableColumn;
    public  TableColumn <Student, String> PhoneTableColumn;
    public  TableColumn <Student, String> AddressTableColumn;
    public  TableColumn <Student, String> CountryTableColumn;
    public  TableColumn<Student, String> EmailTableColumn;
    public Button SearchButton;
    public Button AddStudentButton;
    public Button DeleteStudentButton;
    public TextField SearchTextFiled;
    public TableColumn EditDelTableColumn;

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
    private void loadDate() throws SQLException {

        refreshTableView();
        //add cell of button edit
        Callback<TableColumn<Book, String>, TableCell<Book, String>> cellFoctory = (TableColumn<Book, String> param) -> {
            // make cell containing buttons
            final TableCell<Book, String> cell = new TableCell<Book, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                "-fx-font-family: FontAwesome; -fx-fill: RED; -fx-font-size: 28px"
                        );
                        editIcon.setStyle(
                                "-fx-font-family: FontAwesome; -fx-fill: BLUE; -fx-font-size: 28px"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                Student student =StudentTableView.getSelectionModel().getSelectedItem();
                                String query = "DELETE FROM items WHERE itemnumber  = '"+student.getId()+"'";
                                DatabaseConnection conn = new DatabaseConnection();
                                PreparedStatement ps = conn.getConnection("root", "admin123").prepareStatement(query);

                                ps.execute();
                                refreshTableView();

                            } catch (SQLException ex) {
                                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Student student = StudentTableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("EditBook.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            EditStudentController editStudentController = loader.getController();
                            editStudentController.setTextField(student.getId(), student.getFname(),student.getLname(),student.getAddress(),student.getPhone(), student.getEmail(),student.getCountry());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };
            return cell;
        };
        EditDelTableColumn.setCellFactory(cellFoctory);
        StudentTableView.setItems(oblist);
    }
}
