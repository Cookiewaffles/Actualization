package com.example.actualization;

public class Appointment {
    public String apptName;
    public String apptDate;
    public String apptTime;
    public String apptLocation;
    public int apptCost;


    public Appointment(){

    }

    public Appointment(String name, String date, String time, String location, int cost){
        this.apptName = name;
        this.apptDate = date;
        this.apptTime = time;
        this.apptLocation = location;
        this.apptCost = cost;
    }

    public String getName() {
        return apptName;
    }
    public void setName(String name) {
        this.apptName = name;
    }

    public String getDate() {
        return apptDate;
    }
    public void setDate(String date) {
        this.apptDate = date;
    }

    public String getTime() {
        return apptTime;
    }
    public void setTime(String time) {
        this.apptTime = time;
    }

    public String getLocation() {
        return apptLocation;
    }
    public void setLocation(String location) {
        this.apptLocation = location;
    }

    public int getCost() {
        return apptCost;
    }
    public void setCost(int cost) {
        this.apptCost = cost;
    }
}
