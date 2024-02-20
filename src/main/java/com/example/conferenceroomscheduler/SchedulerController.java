package com.example.conferenceroomscheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SchedulerController implements Initializable {
    @FXML
    private TableColumn<Scheduler, String> EndTimeCol;

    @FXML
    private TableColumn<Scheduler, String> GuestNameCol;

    @FXML
    private TableColumn<Scheduler, Integer> RoomNoCol;

    @FXML
    private TableColumn<Scheduler, String> StartTimeCol;

    @FXML
    private TableColumn<Scheduler, String> TimeCol;

    @FXML
    private BorderPane borderLayoutPane;

    @FXML
    private Button btnViewToday;

    @FXML
    private Button btnViewTomorrow;

    @FXML
    private Button btnViewYesterday;

    @FXML
    private Label lblBookingToday;

    @FXML
    private Label lblBookingTomorrow;

    @FXML
    private Label lblBookingYesterday;

    @FXML
    private Label lblDay;

    @FXML
    private TableView<Scheduler> tableViewScheduler;

    private ObservableList<Scheduler> list = FXCollections.observableArrayList();
    private LocalDate dateToday = LocalDate.now();
    private LocalDate dateYesterday = dateToday.minusDays(1);
    private LocalDate dateTomorrow = dateToday.plusDays(1);


    public void viewYesterday(){
        try{
            tableViewScheduler.getItems().removeAll(list);
            String query = "SELECT GuestName, Form_Hour,To_Hour, Times, RoomNo FROM tbBooking WHERE CheckIn = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dateYesterday.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                list.add(new Scheduler(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getInt(5)));
            connection.close(); // close connection
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        try {
            String query = "SELECT COUNT(BookingID) as count FROM tbBooking WHERE CheckIn = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,dateYesterday.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                lblBookingYesterday.setText(rs.getInt(1)+" Booking");
            }
            connection.close(); // close connection
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("View Yesterday "+dateYesterday.toString());
    }

    public void viewToday(){
        try{
            tableViewScheduler.getItems().removeAll(list);
            String query = "SELECT GuestName, Form_Hour,To_Hour, Times, RoomNo FROM tbBooking WHERE CheckIn = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dateToday.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                list.add(new Scheduler(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getInt(5)));
            connection.close(); // close connection
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        try {
            String query = "SELECT COUNT(BookingID) as count FROM tbBooking WHERE CheckIn = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,dateToday.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                lblBookingToday.setText(rs.getInt(1)+" Booking");
            }
            connection.close(); // close connection
        }catch (SQLDataException e){
            e.printStackTrace();
            e.getMessage();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("View Today "+dateToday.toString());
    }

    public void viewTomorrow(){
        try{
            tableViewScheduler.getItems().removeAll(list);
            String query = "SELECT GuestName, Form_Hour,To_Hour, Times, RoomNo FROM tbBooking WHERE CheckIn = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dateTomorrow.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
                list.add(new Scheduler(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getInt(5)));
            connection.close(); // close connection
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        try {
            String query = "SELECT COUNT(BookingID) as count FROM tbBooking WHERE CheckIn = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,dateTomorrow.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                lblBookingTomorrow.setText(String.valueOf(rs.getInt(1))+" Booking");
            }
            connection.close(); // close connection
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("View Tomorrow "+dateTomorrow.toString());
    }

    public void TableView(){
        GuestNameCol.setCellValueFactory(new PropertyValueFactory<Scheduler, String>("guestName"));
        StartTimeCol.setCellValueFactory(new PropertyValueFactory<Scheduler, String>("startHour"));
        EndTimeCol.setCellValueFactory(new PropertyValueFactory<Scheduler, String>("endHour"));
        TimeCol.setCellValueFactory(new PropertyValueFactory<Scheduler, String>("times"));
        RoomNoCol.setCellValueFactory(new PropertyValueFactory<Scheduler, Integer>("roomNo"));

        tableViewScheduler.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnViewYesterday.setOnMouseClicked(event -> {
            viewYesterday();
            lblDay.setText("Yesterday");
        });
        btnViewToday.setOnMouseClicked(event -> {
            viewToday();
            lblDay.setText("Today");
        });
        btnViewTomorrow.setOnMouseClicked(event -> {
            viewTomorrow();
            lblDay.setText("Tomorrow");
        });
    }
}
