package com.example.weather.litepal;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by hasee on 2019/3/24.
 */

public class Daily extends LitePalSupport implements Serializable{
    private String city; //城市
    private String data;
    private String week;
    private String sunrise;
    private String sunset;
    private String night_weather;
    private String night_templow;
    private String night_img;
    private String night_winddirect;
    private String night_windpower;
    private String day_weather;
    private String day_temphigh;
    private String day_img;
    private String day_winddirect;
    private String day_windpower;
    private String quality;

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getNight_weather() {
        return night_weather;
    }

    public void setNight_weather(String night_weather) {
        this.night_weather = night_weather;
    }

    public String getNight_templow() {
        return night_templow;
    }

    public void setNight_templow(String night_templow) {
        this.night_templow = night_templow;
    }

    public String getNight_img() {
        return night_img;
    }

    public void setNight_img(String night_img) {
        this.night_img = night_img;
    }

    public String getNight_winddirect() {
        return night_winddirect;
    }

    public void setNight_winddirect(String night_winddirect) {
        this.night_winddirect = night_winddirect;
    }

    public String getNight_windpower() {
        return night_windpower;
    }

    public void setNight_windpower(String night_windpower) {
        this.night_windpower = night_windpower;
    }

    public String getDay_weather() {
        return day_weather;
    }

    public void setDay_weather(String day_weather) {
        this.day_weather = day_weather;
    }

    public String getDay_temphigh() {
        return day_temphigh;
    }

    public void setDay_temphigh(String day_temphigh) {
        this.day_temphigh = day_temphigh;
    }

    public String getDay_img() {
        return day_img;
    }

    public void setDay_img(String day_img) {
        this.day_img = day_img;
    }

    public String getDay_winddirect() {
        return day_winddirect;
    }

    public void setDay_winddirect(String day_winddirect) {
        this.day_winddirect = day_winddirect;
    }

    public String getDay_windpower() {
        return day_windpower;
    }

    public void setDay_windpower(String day_windpower) {
        this.day_windpower = day_windpower;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "city='" + city + '\'' +
                ", data='" + data + '\'' +
                ", week='" + week + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", night_weather='" + night_weather + '\'' +
                ", night_templow='" + night_templow + '\'' +
                ", night_img='" + night_img + '\'' +
                ", night_winddirect='" + night_winddirect + '\'' +
                ", night_windpower='" + night_windpower + '\'' +
                ", day_weather='" + day_weather + '\'' +
                ", day_temphigh='" + day_temphigh + '\'' +
                ", day_img='" + day_img + '\'' +
                ", day_winddirect='" + day_winddirect + '\'' +
                ", day_windpower='" + day_windpower + '\'' +
                ", quality='" + quality + '\'' +
                '}';
    }
}



