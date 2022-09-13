package com.example.actualization;

public class StoreAppt {
    public String apptType;
    public String apptTime;
    public String apptDesc;


    public StoreAppt(String type, String time, String desc){
        this.apptType = type;
        this.apptTime = time;
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

    public String getDesc() {
        return apptDesc;
    }
    public void setDesc(String desc) {
        this.apptDesc = desc;
    }
}
