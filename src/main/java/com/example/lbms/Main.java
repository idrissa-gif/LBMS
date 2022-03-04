/*package com.example.lbms;
let test the git
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

public class Main extends Application {

    public static Boolean isSplashLoaded = false;
    @Override
	public void start(Stage primaryStage) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root,770,500);
        //primaryStage.setMaximized(true);
        primaryStage.setTitle("Library Management System Admin App");
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        launch(args);
    }

}*/
package com.example.lbms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    static public String Passwd , user;
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("admin.fxml"));

        Scene scene = new Scene(root,900,650);
        //Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
