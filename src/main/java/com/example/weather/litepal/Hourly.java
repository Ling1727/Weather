package com.example.weather.litepal;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by hasee on 2019/3/24.
 */

public class Hourly extends LitePalSupport implements Serializable{
    private String city;
    private String time;
    private String weather;
    private String temp;
    private String img;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Hourly{" +
                "city='" + city + '\'' +
                ", time='" + time + '\'' +
                ", weather='" + weather + '\'' +
                ", temp='" + temp + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
