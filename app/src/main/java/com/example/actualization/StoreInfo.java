package com.example.actualization;

public class StoreInfo {
    public String name;
    public String desc;

    public StoreInfo(String name, String desc){
        this.name = name;
        this.desc = desc;
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
}
