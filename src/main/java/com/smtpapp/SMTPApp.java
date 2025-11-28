package com.smtpapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for the SMTP Mail Application.
 * A lightweight JavaFX desktop application for sending emails via SMTP.
 */
public class SMTPApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SMTPApp.class.getResource("email-view.fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root, 600, 550);
        scene.getStylesheets().add(SMTPApp.class.getResource("styles.css").toExternalForm());
        
        stage.setTitle("SMTP Mail Application");
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(450);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
