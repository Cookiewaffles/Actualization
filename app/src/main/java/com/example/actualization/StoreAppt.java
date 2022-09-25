package com.example.actualization;

public class StoreAppt {
    public String apptType;
    public String apptTime;
    public String apptDate;
    public String apptCost;
    public String apptDesc;


    public StoreAppt(String type, String time, String date, String cost, String desc){
        this.apptType = type;
        this.apptTime = time;
        this.apptDate = date;
        this.apptCost = cost;
        this.apptDesc = desc;
    }

    public String getType() {
        return apptType;
    }
    public void setType(String type) {
        this.apptType = type;
    }

    public String getTime() {
        return apptTime;
    }
    public void setTime(String time) {
        this.apptTime = time;
    }

    public String getDate() {
        return apptDate;
    }
    public void setDate(String date) {
        this.apptDate = date;
    }

    public String getCost() {
        return apptCost;
    }
    public void setCost(String cost) {
        this.apptCost = cost;
    }

    public String getDesc() {
        return apptDesc;
    }
    public void setDesc(String desc) {
        this.apptDesc = desc;
    }
}
