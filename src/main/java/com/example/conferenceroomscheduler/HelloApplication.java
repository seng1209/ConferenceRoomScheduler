package com.example.conferenceroomscheduler;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        try{
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("CSS/Style.css").toExternalForm());
            stage.setTitle("Conference Room Scheduler!");
            stage.setOnCloseRequest(event -> {
                event.consume();
                Exit(stage);
            });
            stage.setScene(scene);
            stage.show();
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    KeyCode key = keyEvent.getCode();
                    if (key == KeyCode.ESCAPE){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Do you want to exit the program?");
                        alert.setContentText("");

                        Optional<ButtonType> rs = alert.showAndWait();
                        if (rs.isEmpty()){
                            /*System.out.println("Alert closed");*/
                        }
                        else if (rs.get() == ButtonType.OK){
                            stage.close();
                            /*System.out.println("OK");*/
                        }
                        else if (rs.get() == ButtonType.CANCEL){
                            /*System.out.println("Never!");*/
                        }
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void Exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Do you want to exit the program?");
        alert.setContentText("");

        Optional<ButtonType> rs = alert.showAndWait();
        if (rs.isEmpty()){
            /*System.out.println("Alert closed");*/
        }
        else if (rs.get() == ButtonType.OK){
            Platform.exit();
            /*System.out.println("OK");*/
        }
        else if (rs.get() == ButtonType.CANCEL){
            /*System.out.println("Never!");*/
        }
    }

    public static void main(String[] args) {
        launch();
    }
}