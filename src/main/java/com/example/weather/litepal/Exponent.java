package com.example.weather.litepal;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by hasee on 2019/3/24.
 */

public class Exponent extends LitePalSupport implements Serializable{
    private String city; //城市
    private String iname;
    private String ivalue;
    private String detail;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getIvalue() {
        return ivalue;
    }

    public void setIvalue(String ivalue) {
        this.ivalue = ivalue;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Exponent{" +
                "city='" + city + '\'' +
                ", iname='" + iname + '\'' +
                ", ivalue='" + ivalue + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
