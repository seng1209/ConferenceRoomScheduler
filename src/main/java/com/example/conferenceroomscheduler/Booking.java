package com.example.conferenceroomscheduler;

public class Booking {
    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(String roomSize) {
        this.roomSize = roomSize;
    }

    private int roomNo;
    private String roomType;
    private String roomSize;

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(int numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    private int bookingID;
    private String guestName;
    private String checkIn;
    private String checkOut;
    private String times;
    private String start;
    private String stop;
    private int numberOfPerson;

    public Booking(int roomNo, String roomType, String roomSize){
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.roomSize = roomSize;
    }

    public Booking(int bookingID, String guestName, String checkIn, String checkOut, int numberOfPerson, String times,
                   String start, String stop, int roomNo)
    {
        this.bookingID = bookingID;
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfPerson = numberOfPerson;
        this.times = times;
        this.start = start;
        this.stop = stop;
        this.roomNo = roomNo;
    }

    public Booking(String guestName, String checkIn, String checkOut, int numberOfPerson, String times, String start,
                   String stop, int roomNo)
    {
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfPerson = numberOfPerson;
        this.times = times;
        this.start = start;
        this.stop = stop;
        this.roomNo = roomNo;
    }

    public Booking(){

    }
}
