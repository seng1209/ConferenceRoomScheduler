package com.example.conferenceroomscheduler;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private AnchorPane anchorPaneCenter;

    @FXML
    private Button btnBooking;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnScheduler;

    @FXML
    private Button btnExit;
    private AnchorPane anchorPaneOverview, anchorPaneScheduler, anchorPaneBooking;


    public void BookingScene(ActionEvent event){
        anchorPaneCenter.getChildren().remove(anchorPaneScheduler);
        anchorPaneCenter.getChildren().remove(anchorPaneOverview);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("booking.fxml"));
        BookingController bookingController = fxmlLoader.getController();
        try {
            anchorPaneBooking = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        anchorPaneCenter.getChildren().add(anchorPaneBooking);
    }

    public void OverviewButton(ActionEvent event){
        anchorPaneCenter.getChildren().remove(anchorPaneScheduler);
        anchorPaneCenter.getChildren().remove(anchorPaneBooking);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("overview.fxml"));
        try {
            anchorPaneOverview = fxmlLoader.load();
            OverviewController overviewController = fxmlLoader.getController();
            overviewController.TableView();
            overviewController.getValueToMySQL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        anchorPaneCenter.getChildren().add(anchorPaneOverview);
    }

    public void SchedulerScene(ActionEvent event){
        anchorPaneCenter.getChildren().remove(anchorPaneBooking);
        anchorPaneCenter.getChildren().remove(anchorPaneOverview);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("scheduler.fxml"));
        try {
            anchorPaneScheduler = fxmlLoader.load();
            SchedulerController schedulerController = fxmlLoader.getController();
            schedulerController.TableView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        anchorPaneCenter.getChildren().add(anchorPaneScheduler);
    }

    public void Exit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Do you want to exit the program?");
        alert.setContentText("");

        Optional<ButtonType> rs = alert.showAndWait();
        if (rs.isEmpty()){
            System.out.println("Alert closed");
        }
        else if (rs.get() == ButtonType.OK){
            Platform.exit();
            System.out.println("OK");
        }
        else if (rs.get() == ButtonType.CANCEL){
            System.out.println("Never!");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("overview.fxml"));
            anchorPaneOverview = fxmlLoader.load();
            OverviewController overviewController = fxmlLoader.getController();
            overviewController.TableView();
            overviewController.getValueToMySQL();
            anchorPaneCenter.getChildren().add(anchorPaneOverview);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        btnExit.setOnMouseClicked(event -> {
            Exit();
        });
    }
}