package com.example.conferenceroomscheduler;

public class Overview {
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

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    private int bookingID;
    private String guestName;
    private String checkIn;
    private String checkOut;
    private String times;
    private String start;
    private String stop;
    private int roomNo;

    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    public void setNumberOfPerson(int numberOfPerson) {
        this.numberOfPerson = numberOfPerson;
    }

    private int numberOfPerson;

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    private int Size;

    public Overview(int bookingID, String guestName, String checkIn, String checkOut, int numberOfPerson, String times,
                    String start, String stop, int roomNo, int Size){
        this.bookingID = bookingID;
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfPerson = numberOfPerson;
        this.times = times;
        this.start = start;
        this.stop = stop;
        this.roomNo = roomNo;
        this.Size = Size;
    }

    public Overview(String guestName, String checkIn, String checkOut, int numberOfPerson, String times, String start,
                    String stop, int roomNo){
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.numberOfPerson = numberOfPerson;
        this.times = times;
        this.start = start;
        this.stop = stop;
        this.roomNo = roomNo;
    }

}
