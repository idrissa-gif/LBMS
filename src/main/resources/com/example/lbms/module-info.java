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
	requires de.jensd.fx.fontawesomefx.fontawesome;
	opens application to javafx.graphics, javafx.fxml;
}
