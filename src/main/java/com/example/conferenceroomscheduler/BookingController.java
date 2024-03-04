package com.example.conferenceroomscheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Date;

public class BookingController implements Initializable
{
    @FXML
    private Button btnAddRoom;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnNext;

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

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
    private Label lblRoomType;

    @FXML
    private Label lblSize;

    public String getGuestName() {
        return GuestName;
    }

    public void setGuestName(String guestName) {
        GuestName = guestName;
    }

    public String getCheckIn() {
        return CheckIn;
    }

    public void setCheckIn(String checkIn) {
        CheckIn = checkIn;
    }

    public String getCheckOut() {
        return CheckOut;
    }

    public void setCheckOut(String checkOut) {
        CheckOut = checkOut;
    }

    public int getPersons() {
        return Persons;
    }

    public void setPersons(int persons) {
        Persons = persons;
    }

    public String getTimes() {
        return Times;
    }

    public void setTimes(String times) {
        Times = times;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    private String GuestName;
    private String CheckIn;
    private String CheckOut;
    private int Persons;
    private String Times;
    private  String Start;
    private String End;
    private int RoomID;
    private  int RoomSize;
    private String[] roomNo = {"1","2","3","4","5"};

    public void comboBoxRoomNo(ActionEvent event){
        try{
            String query = "SELECT * FROM tbroom WHERE RoomNo = ?;";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(roomNoComboBox.getValue()));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                lblRoomType.setText(rs.getString(2));
                lblSize.setText(rs.getString(3));
                RoomSize = rs.getInt(3);
            }
            connection.close(); // close mysql server
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pushRoom(){
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

                if (IsOverlapping(StartHour, EndHour)){
                    addRow();
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

    public void addRow() {
        try{
            String query = "INSERT INTO tbBooking(GuestName, CheckIn, CheckOut, NumberOfPerson, Times, Form_Hour, " +
                    "To_Hour, RoomNo) VALUES(?,?,?,?,?,?,?,?);";
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
            int ok = preparedStatement.executeUpdate();
            System.out.println(ok+" add one row");
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        catch (Exception e){
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

    /*Exception Function*/

    public boolean CheckRoomSize(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try{
            if (RoomSize < Integer.parseInt(txtPersons.getText())){
                alert.setContentText("RoomNo "+roomNoComboBox.getValue()+" Size "+RoomSize+" unavailable!....");
                Optional<ButtonType> rs = alert.showAndWait();
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
                text.requestFocus();
                return false;
            }
            String.valueOf(text.getText());
            return true;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("String Type");
            alert.setContentText(name + " must be String!");
            Optional<ButtonType> rs = alert.showAndWait();
            text.requestFocus();
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
        try{
            Integer.parseInt(text.getText());
            return true;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Integer Type");
            alert.setContentText(name + " must be Integer");
            Optional<ButtonType> rs = alert.showAndWait();
            e.printStackTrace();
            return false;
        }
    }
    public boolean IsInteger(ComboBox<String> comboBox, String name){
        try {
            Integer.parseInt(comboBox.getValue());
            return true;
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Integer Type");
            alert.setContentText(name + " must be Integer");
            Optional<ButtonType> rs = alert.showAndWait();
            return false;
        }
    }

    public boolean IsOverlapping(String start, String end){
        ArrayList<String> startTime = new ArrayList<>();
        ArrayList<String> endTime = new ArrayList<>();
        ArrayList<String> times = new ArrayList<>();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String query = "SELECT Form_Hour, To_Hour, Times FROM tbBooking WHERE CheckIN = ? AND RoomNo = ?;";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3308/BookingConferenceRoomScheduler","root","hellomysql");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, checkInDatePicker.getValue().toString());
            preparedStatement.setInt(2, Integer.parseInt(roomNoComboBox.getValue()));
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

//        System.out.println(startTime+"\n"+endTime+"\n"+times); /****************************/
//        System.out.println("Start\tEnd\tTimes"); /*************************/
        String bookingStart = start;
        String bookingEnd = end;
        String[] partsStart2 = bookingStart.split(":");
        String[] partsEnd2 = bookingEnd.split(":");
        try{
            for (int i=0;i<startTime.size();i++){
//                System.out.println(startTime.get(i)+" "+endTime.get(i)+" "+times.get(i));

                String[] partsStart1 = startTime.get(i).split(":");
                String[] partsEnd1 = endTime.get(i).split(":");

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
                IsInteger(roomNoComboBox, "Room No") &&
                CheckRoomSize();
    }
    /********************************************************************************/
    public void addRoomButton(ActionEvent event){
        pushRoom();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Default Values */
        roomNoComboBox.getItems().addAll(roomNo);
        checkInDatePicker.setValue(LocalDate.now());
        checkOutDatePicker.setValue(LocalDate.now());
        String S = String.valueOf(LocalTime.now());
        String E = String.valueOf(LocalTime.now());
        txtStartTime.setText(S.substring(0, S.length()-13));
        txtEndTime.setText(E.substring(0, E.length()-13));
        /***************************************/
        btnCancel.setOnMouseClicked(event -> {
            txtGuestName.setText("");
            checkInDatePicker.setValue(null);
            checkOutDatePicker.setValue(null);
            txtPersons.setText("");
            txtTime.setText("");
            txtStartTime.setText("");
            txtEndTime.setText("");
        });
        btnNext.setOnMouseClicked(event -> {
            pushRoom();
        });
        txtEndTime.setOnAction(event -> {
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("HH:mm");
            Date date1;
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
        txtStartTime.setOnAction(event -> {
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("HH:mm");
            Date date1;
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
    }
}
