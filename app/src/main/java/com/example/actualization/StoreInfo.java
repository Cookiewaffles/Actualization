package com.example.actualization;

public class StoreInfo {
    public String name;
    public String desc;
    public String apptLocation;

    public StoreInfo(String name, String desc, String location){
        this.name = name;
        this.desc = desc;
        this.apptLocation = location;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return apptLocation;
    }
    public void setLocation(String location) {
        this.apptLocation = location;
    }
}
