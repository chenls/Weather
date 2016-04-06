package com.cqupt.weather.bean;

import cn.bmob.v3.BmobObject;

public class historyData extends BmobObject {
    private String time, temp, humd;

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumd() {
        return humd;
    }

    @Override
    public String toString() {
        return "historyData{" +
                "time='" + time + '\'' +
                ", temp='" + temp + '\'' +
                ", humd='" + humd + '\'' +
                '}';
    }
}
