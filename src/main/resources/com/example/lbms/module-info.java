module com.example.lbms {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.web;
	requires jfoenix;
	requires  java.sql;
	requires mysql.connector.java;
	requires fontawesomefx.glyphsbrowser.all;
	requires fontawesomefx;
	opens application to javafx.graphics, javafx.fxml;
}
