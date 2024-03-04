package com.example.conferenceroomscheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class OverviewController implements Initializable {
    @FXML
    private TableColumn<Overview, Integer> bookingID;

    @FXML
    private TableColumn<Overview, String> checkIn;

    @FXML
    private TableColumn<Overview, String> checkOut;

    @FXML
    private TableColumn<Overview, String> guestName;

    @FXML
    private TableColumn<Overview, Integer> roomNo;

    @FXML
    private TableColumn<Overview, String> start;

    @FXML
    private TableColumn<Overview, String> stop;

    @FXML
    private TableView<Overview> tableView;

    @FXML
    private TableColumn<Overview, String> times;

    @FXML
    private TableColumn<Overview, Integer> numberOfPerson;

    @FXML
    private TableColumn<Overview, Integer> Size;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtGuestName;

    @FXML
    private TextField txtPersons;

    @FXML
    private TextField txtStartTime;

    @FXML
    private TextField txtTime;

    @FXML
    private ComboBox<String> roomNoComboBox;

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private TextField txtBookingID;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtRoomSize;

    private int RoomSize;

    private String[] roomID = {"1","2","3","4","5"};
    private ObservableList<Overview> list = FXCollections.observableArrayList();
    private javafx.scene.input.MouseEvent MouseEvent;

    public void getValueToMySQL(){
        try{
            /*String query = "SELECT * FROM tbbooking;";*/
            String query = "SELECT BK.BookingID, BK.GuestName, BK.CheckIn, BK.CheckOut, BK.NumberOfPerson, BK.Times, " +
                    "BK.Form_Hour, BK.To_Hour, BK.RoomNo, RN.Size FROM tbRoom RN INNER JOIN tbBooking BK " +
                    "ON RN.RoomNo = BK.RoomNo;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                list.add(new Overview(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getInt(5),rs.getString(6),rs.getString(7),
                        rs.getString(8),rs.getInt(9),rs.getInt(10)));
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void TableView(){

        for ( int i = 0; i<tableView.getItems().size(); i++) {
            tableView.getItems().clear();
        }

        try{
            bookingID.setCellValueFactory(new PropertyValueFactory<Overview, Integer>("bookingID"));
            guestName.setCellValueFactory(new PropertyValueFactory<Overview, String>("guestName"));
            checkIn.setCellValueFactory(new PropertyValueFactory<Overview, String>("checkIn"));
            checkOut.setCellValueFactory(new PropertyValueFactory<Overview, String>("checkOut"));
            numberOfPerson.setCellValueFactory(new PropertyValueFactory<Overview, Integer>("numberOfPerson"));
            times.setCellValueFactory(new PropertyValueFactory<Overview, String>("times"));
            start.setCellValueFactory(new PropertyValueFactory<Overview, String>("start"));
            stop.setCellValueFactory(new PropertyValueFactory<Overview, String>("stop"));
            roomNo.setCellValueFactory(new PropertyValueFactory<Overview, Integer>("roomNo"));
            Size.setCellValueFactory(new PropertyValueFactory<Overview, Integer>("Size"));

            tableView.setItems(list);
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }

    @FXML
    public int getItems(javafx.scene.input.MouseEvent event) {
        int index = tableView.getSelectionModel().getSelectedIndex();

        if (index <= -1)
            return -1;

        String s = start.getCellData(index).toString();
        String e = stop.getCellData(index).toString();
        txtBookingID.setText(bookingID.getCellData(index).toString());
        txtGuestName.setText(guestName.getCellData(index).toString());
        checkInDatePicker.setValue(LocalDate.parse(checkIn.getCellData(index).toString()));
        checkOutDatePicker.setValue(LocalDate.parse(checkOut.getCellData(index).toString()));
        txtPersons.setText(numberOfPerson.getCellData(index).toString());
        txtTime.setText(times.getCellData(index).toString());
        txtStartTime.setText(s);
        txtEndTime.setText(e);
        roomNoComboBox.getSelectionModel().select(Integer.parseInt(roomNo.getCellData(index).toString())-1);
        txtRoomSize.setText(Size.getCellData(index).toString());
        RoomSize = Integer.parseInt(txtRoomSize.getText());
        return index;
    }

    public void UpdateStatement(){
        String GuestName;
        String CheckIn;
        String CheckOut;
        int Persons;
        String Time;
        String StartHour;
        String EndHour;
        int RoomNo;

        try{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (IsValidData()){
                GuestName = txtGuestName.getText();
                if (GuestName.equals("")){
                    alert.setContentText("GuestName is Empty Please Input Guest Name!");
                    Optional<ButtonType> rs = alert.showAndWait();
                }
                CheckIn = checkInDatePicker.getValue().toString();
                if (CheckIn.equals("")){
                    alert.setContentText("CheckIn is Empty Please Input CheckIn Date!");
                    Optional<ButtonType> rs = alert.showAndWait();
                }
                CheckOut = checkOutDatePicker.getValue().toString();
                if (CheckOut.equals("")){
                    alert.setContentText("CheckOut is Empty Please Input CheckOut Date!");
                    Optional<ButtonType> rs = alert.showAndWait();
                }
                Persons = Integer.parseInt(txtPersons.getText());
                if (Persons <= 0 || Persons == 0){
                    alert.setContentText("Persons is must be more than 0 Please Input Persons!");
                    Optional<ButtonType> rs = alert.showAndWait();
                }
                Time = txtTime.getText();
                if (Time.equals("")){
                    alert.setContentText("Time is Empty Please Input Time!");
                    Optional<ButtonType> rs = alert.showAndWait();
                }
                StartHour = txtStartTime.getText();
                if (StartHour.equals("")){
                    alert.setContentText("Start Hour is Empty Please Input Start Hour!");
                    Optional<ButtonType> rs = alert.showAndWait();
                }
                EndHour = txtEndTime.getText();
                if (EndHour.equals("")){
                    alert.setContentText("End Hour is Empty Please Input End Hour!");
                    Optional<ButtonType> rs = alert.showAndWait();
                }
                RoomNo = Integer.parseInt(roomNoComboBox.getValue());
                alert.setTitle("Add Room");
                alert.setContentText("This is an alert");

                if (IsOverlapping(StartHour, EndHour) == true){
                    UpdateRoom();
                    Optional<ButtonType> rs = alert.showAndWait();
                    if (rs.isEmpty()){
                        System.out.println("Alert closed");
                    }
                    else if (rs.get() == ButtonType.OK){
                        System.out.println("OK");
                    }
                    else if (rs.get() == ButtonType.CANCEL){
                        System.out.println("Never!");
                    }
                }
                else {
                    alert.setContentText("RoomNo "+RoomNo+" With "+StartHour+" to "+EndHour+" Ready Booking!");
                    Optional<ButtonType> rs = alert.showAndWait();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Delete(){
        String query = "DELETE FROM tbbooking WHERE BookingID = ?;";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(txtBookingID.getText()));
            int o = preparedStatement.executeUpdate();
            alert.setContentText("Delete one Row by BookingID : "+txtBookingID.getText());
            Optional<ButtonType> rs = alert.showAndWait();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void UpdateRoom(){
        try{
            String query = "UPDATE tbBooking SET GuestName = ?, CheckIn = ?, CheckOut = ?, " +
                    "NumberOfPerson = ?, Times = ?, Form_Hour = ?, To_Hour = ?, RoomNo = ? WHERE BookingID = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, txtGuestName.getText());
            preparedStatement.setString(2, checkInDatePicker.getValue().toString());
            preparedStatement.setString(3, checkOutDatePicker.getValue().toString());
            preparedStatement.setInt(4, Integer.parseInt(txtPersons.getText()));
            preparedStatement.setString(5, txtTime.getText());
            preparedStatement.setString(6, txtStartTime.getText());
            preparedStatement.setString(7, txtEndTime.getText());
            preparedStatement.setInt(8, Integer.parseInt(roomNoComboBox.getValue()));
            preparedStatement.setInt(9, Integer.parseInt(txtBookingID.getText()));

            int ok = preparedStatement.executeUpdate();
            System.out.println(ok+" add one row");
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String calculateTime(Date date1, Date date2){
        // Calculating the difference in milliseconds
        long differenceInMilliSeconds
                = Math.abs(date2.getTime() - date1.getTime());
        // Calculating the difference in Hours
        long differenceInHours
                = (differenceInMilliSeconds / (60 * 60 * 1000))
                % 24;
        // Calculating the difference in Minutes
        long differenceInMinutes
                = (differenceInMilliSeconds / (60 * 1000)) % 60;
        // Calculating the difference in Seconds
        long differenceInSeconds
                = (differenceInMilliSeconds / 1000) % 60;
        return differenceInHours + ":"
                + differenceInMinutes/* + ""
                + differenceInSeconds + " Seconds. "*/;
    }

    public void comboBoxRoomNo(){
        try{
            String query = "SELECT * FROM tbroom WHERE RoomNo = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(roomNoComboBox.getValue()));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                txtRoomSize.setText(rs.getString(3));
            }
            connection.close(); // close mysql server
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*Exception Function*/
    public boolean CheckRoomSize(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try{
            String query = "SELECT * FROM tbroom WHERE RoomNo = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(roomNoComboBox.getValue()));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                txtRoomSize.setText(rs.getString(3));
            }
            connection.close(); // close mysql server
            if (Integer.parseInt(txtRoomSize.getText()) < Integer.parseInt(txtPersons.getText())){
                alert.setContentText("RoomNo "+roomNoComboBox.getValue()+" Size "+RoomSize+" unavailable!....");
                Optional<ButtonType> r = alert.showAndWait();
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            alert.setContentText("RoomNo "+roomNoComboBox.getValue()+" Size "+RoomSize+" unavailable!....");
            Optional<ButtonType> rs = alert.showAndWait();
            return false;
        }
    }
    public boolean IsString(TextField text, String name){
        try{
            if (text.equals(null) || text.getText().equals("") || text.getText().equals(null)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("String Type");
                alert.setContentText(name + " NullPointerException");
                Optional<ButtonType> rs = alert.showAndWait();
                return false;
            }
            String.valueOf(text.getText());
            return true;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("String Type");
            alert.setContentText(name + " must be String!");
            Optional<ButtonType> rs = alert.showAndWait();
            e.printStackTrace();
            return false;
        }
    }
    public boolean IsString(DatePicker datePicker, String name){
        try {
            if (datePicker.getValue().equals(null)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("String Type");
                alert.setContentText(name + " NullPointerException");
                Optional<ButtonType> rs = alert.showAndWait();
                return false;
            }
            String.valueOf(datePicker.getValue());
            return true;
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("String Type");
            alert.setContentText(name + " NullPointerException");
            Optional<ButtonType> rs = alert.showAndWait();
            e.printStackTrace();
            return false;
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("String Type");
            alert.setContentText(name + " must be String");
            Optional<ButtonType> rs = alert.showAndWait();
            e.printStackTrace();
            return false;
        }
    }
    public boolean IsInteger(TextField text, String name){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Integer Type");
        alert.setContentText(name + " must be Integer");
        try{
            Integer.parseInt(text.getText());
            return true;
        }catch (NumberFormatException e){
            Optional<ButtonType> rs = alert.showAndWait();
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            Optional<ButtonType> rs = alert.showAndWait();
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean IsInteger(ComboBox<String> comboBox, String name){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Integer Type");
        alert.setContentText(name + " must be Integer");
        try {
            Integer.parseInt(comboBox.getValue());
            return true;
        }catch (NumberFormatException e){
            Optional<ButtonType> rs = alert.showAndWait();
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }catch (Exception e){
            Optional<ButtonType> rs = alert.showAndWait();
            return false;
        }
    }

    public boolean IsOverlapping(String start, String end){
        ArrayList<String> startTime = new ArrayList<>();
        ArrayList<String> endTime = new ArrayList<>();
        ArrayList<String> times = new ArrayList<>();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String query = "SELECT Form_Hour, To_Hour, Times FROM tbBooking WHERE CheckIN = ? AND RoomNo = ? AND " +
                "BookingID <> ?;";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, checkInDatePicker.getValue().toString());
            preparedStatement.setInt(2, Integer.parseInt(roomNoComboBox.getValue()));
            preparedStatement.setInt(3, Integer.parseInt(txtBookingID.getText()));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                startTime.addAll(Collections.singleton(rs.getString(1)));
                endTime.addAll(Collections.singleton(rs.getString(2)));
                times.addAll(Collections.singleton(rs.getString(3)));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(startTime+"\n"+endTime+"\n"+times); /****************************/
        System.out.println("Start\tEnd\tTimes"); /*************************/
        String bookingStart = start;
        String bookingEnd = end;
        try{
            for (int i=0;i<startTime.size();i++){
                System.out.println(startTime.get(i)+" "+endTime.get(i)+" "+times.get(i));

                String[] partsStart1 = startTime.get(i).split(":");
                String[] partsEnd1 = endTime.get(i).split(":");
                String[] partsStart2 = bookingStart.split(":");
                String[] partsEnd2 = bookingEnd.split(":");

                int hourStart1 = Integer.parseInt(partsStart1[0]);
                int minStart1 = Integer.parseInt(partsStart1[1]);

                int hourEnd1 = Integer.parseInt(partsEnd1[0]);
                int minEnd1 = Integer.parseInt(partsEnd1[1]);

                int hourStart2 = Integer.parseInt(partsStart2[0]);
                int minStart2 = Integer.parseInt(partsStart2[1]);

                int hourEnd2 = Integer.parseInt(partsEnd2[0]);
                int minEnd2 = Integer.parseInt(partsEnd2[1]);

                if ((hourStart1 <= hourStart2 && hourStart2 <= hourEnd1)) /*&& (minStart2 <= minEnd1)) */
                {
                    alert.setContentText("In"+ bookingStart +" to "+ bookingEnd +" Ready Booking");
                    Optional<ButtonType> rs = alert.showAndWait();
                    /*System.out.println("Hour Start Error");*/
                    return false;
                } else if ((hourStart1 <= hourEnd2 && hourEnd2 <= hourEnd1)) /*&& (minEnd2 >= minStart1)) */
                {
                    alert.setContentText("In"+ bookingStart +" to "+ bookingEnd +" Ready Booking");
                    Optional<ButtonType> rs = alert.showAndWait();
                    /*System.out.println("Hour End Error");*/
                    return false;
                }else if ((hourStart1 <= hourEnd2 && hourEnd2 <= hourEnd1) && (minEnd2 >= minStart1)) {
                    alert.setContentText("In"+ bookingStart +" to "+ bookingEnd +" Ready Booking");
                    Optional<ButtonType> rs = alert.showAndWait();
                    /*System.out.println("Hour Start Error");*/
                    return false;
                }else if ((hourStart1 <= hourEnd2 && hourEnd2 <= hourEnd1) && (minEnd2 >= minStart1)) {
                    alert.setContentText("In"+ bookingStart +" to "+ bookingEnd +" Ready Booking");
                    Optional<ButtonType> rs = alert.showAndWait();
                    /*System.out.println("Hour End Error");*/
                    return false;
                }else {
                    /*System.out.println("JKJ");*/
                    return true;
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean IsValidData(){
        return  IsString(txtGuestName, "Guest Name") &&
                IsString(checkInDatePicker,"Check In") &&
                IsString(checkOutDatePicker, "Check Out") &&
                IsInteger(txtPersons, "Person") &&
                IsString(txtTime, "Time") &&
                IsString(txtStartTime, "Start Time") &&
                IsString(txtEndTime, "End Time") &&
                IsInteger((ComboBox<String>) roomNoComboBox, "Room No") &&
                CheckRoomSize();
    }
    /********************************************************************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomNoComboBox.getItems().addAll(roomID);
        btnDelete.setOnMouseClicked(event -> {
            System.out.println("Delete");
        });
        btnUpdate.setOnMouseClicked(event -> {
            this.UpdateStatement();
            this.TableView();
            this.getValueToMySQL();
        });
        btnDelete.setOnMouseClicked(event -> {
            this.Delete();
            this.TableView();
            this.getValueToMySQL();
        });
        txtEndTime.setOnAction(event -> {
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("HH:mm");
            java.util.Date date1;
            java.util.Date date2;
            try {
                date1 = simpleDateFormat.parse(
                        txtStartTime.getText()
                );
                date2 = simpleDateFormat.parse(txtEndTime.getText());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String time = calculateTime(date1, date2);
            txtTime.setText(time);
        });
        txtStartTime.setOnAction(event -> {
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("HH:mm");
            java.util.Date date1;
            Date date2;
            try {
                date1 = simpleDateFormat.parse(
                        txtStartTime.getText()
                );
                date2 = simpleDateFormat.parse(txtEndTime.getText());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String time = calculateTime(date1, date2);
            txtTime.setText(time);
        });
        checkInDatePicker.setOnAction(event -> {
            checkOutDatePicker.setValue(checkInDatePicker.getValue());
        });
        checkOutDatePicker.setOnAction(event -> {
            checkInDatePicker.setValue(checkOutDatePicker.getValue());
        });
        roomNoComboBox.setOnAction(event -> {
            comboBoxRoomNo();
        });
    }

}
