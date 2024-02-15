package com.example.conferenceroomscheduler;

public class Scheduler {
    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    private String guestName;
    private String startHour;
    private String endHour;
    private String times;
    private int roomNo;

    public Scheduler(String guestName, String startHour, String endHour, String times, int roomNo){
        this.guestName = guestName;
        this.startHour = startHour;
        this.endHour = endHour;
        this.times = times;
        this.roomNo = roomNo;
    }
}
