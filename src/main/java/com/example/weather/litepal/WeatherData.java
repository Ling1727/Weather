package com.example.weather.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hasee on 2019/3/24.
 */

public class WeatherData extends LitePalSupport implements Serializable{
    private String city; //城市
    private int	cityid;//城市ID
    private String	citycode;//城市天气代号
    private String	date;//日期
    private String	week;//星期
    private String	weather;//天气
    private String	temp;//气温
    private String	temphigh;//最高气温
    private String	templow;//templow
    private String	img;//windpower
    private String	humidity;//湿度
    private String	pressure;//气压
    private String	windspeed;//风速
    private String	winddirect;//风向
    private String	windpower;//风级
    private String	updatetime;//更新时间
    private String	so2;//二氧化硫1小时平均
    private String  so224;//二氧化硫24小时平均
    private String	no2;//二氧化氮1小时平均
    private String	no224;//二氧化氮24小时平均
    private String	co;// 一氧化碳1小时平均mg/m3
    private String	co24;//一氧化碳24小时平均 mg/m3
    private String	o3;//臭氧1小时平均
    private String	o38;//臭氧8小时平均
    private String	o324;//臭氧24小时平均
    private String	pm10;//PM10 1小时平均
    private String	pm1024;//PM10 24小时平均
    private String	pm2_5;//PM2.5 1小时平均
    private String	pm2_524;//PM2.5 24小时平均
    private String	iso2;//PM2.5 24小时平均
    private String	ino2;//二氧化氮指数
    private String	ico;//一氧化碳指数
    private String	io3;//臭氧指数
    private String io38; //臭氧8小时指数
    private String ipm10; //PM10指数
    private String ipm2_5; //PM2.5指数
    private String aqi; //AQI指数
    private String primarypollutant; //首要污染物
    private String quality; //空气质量指数类别，有“优、良、轻度污染、中度污染、重度污染、严重污染”6类
    private String timepoint; //发布时间
    private String aqiinfo; //AQI指数信息
    private String level; //等级
    private String color; //指数颜色值
    private String affect; //对健康的影响
    private String measure; //建议采取的措施
    private String night; //夜间
    private String sunset; //日落时间
    private String day; //白天
    private String time; //时间
    private String soncity; //城市 有些地级市取市府的天气
    private int soncityid; //城市ID
    private String soncitycode; //城市代号
    private String sunrise; //日出时间
    private List<Exponent> exponents;
    private List<Daily> daily;
    private List<Hourly> hourly;

    public List<Exponent> getExponents() {
        exponents =LitePal.where("city=?",city).find(Exponent.class);
        return exponents;
    }

    public void setExponents(List<Exponent> exponents) {
        this.exponents = exponents;
    }

    public List<Daily> getDaily() {
        daily=LitePal.where("city=?",city).find(Daily.class);
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public List<Hourly> getHourly() {
        hourly= LitePal.where("city=?",city).find(Hourly.class);
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
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

    public String getTemphigh() {
        return temphigh;
    }

    public void setTemphigh(String temphigh) {
        this.temphigh = temphigh;
    }

    public String getTemplow() {
        return templow;
    }

    public void setTemplow(String templow) {
        this.templow = templow;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(String windspeed) {
        this.windspeed = windspeed;
    }

    public String getWinddirect() {
        return winddirect;
    }

    public void setWinddirect(String winddirect) {
        this.winddirect = winddirect;
    }

    public String getWindpower() {
        return windpower;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }


    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getNo224() {
        return no224;
    }

    public void setNo224(String no224) {
        this.no224 = no224;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getCo24() {
        return co24;
    }

    public void setCo24(String co24) {
        this.co24 = co24;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO38() {
        return o38;
    }

    public void setO38(String o38) {
        this.o38 = o38;
    }

    public String getO324() {
        return o324;
    }

    public void setO324(String o324) {
        this.o324 = o324;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm1024() {
        return pm1024;
    }

    public void setPm1024(String pm1024) {
        this.pm1024 = pm1024;
    }

    public String getPm2_524() {
        return pm2_524;
    }

    public void setPm2_524(String pm2_524) {
        this.pm2_524 = pm2_524;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIno2() {
        return ino2;
    }

    public void setIno2(String ino2) {
        this.ino2 = ino2;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getIo3() {
        return io3;
    }

    public void setIo3(String io3) {
        this.io3 = io3;
    }

    public String getIo38() {
        return io38;
    }

    public void setIo38(String io38) {
        this.io38 = io38;
    }

    public String getIpm10() {
        return ipm10;
    }

    public void setIpm10(String ipm10) {
        this.ipm10 = ipm10;
    }

    public String getIpm2_5() {
        return ipm2_5;
    }

    public void setIpm2_5(String ipm2_5) {
        this.ipm2_5 = ipm2_5;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getPrimarypollutant() {
        return primarypollutant;
    }

    public void setPrimarypollutant(String primarypollutant) {
        this.primarypollutant = primarypollutant;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getTimepoint() {
        return timepoint;
    }

    public void setTimepoint(String timepoint) {
        this.timepoint = timepoint;
    }

    public String getAqiinfo() {
        return aqiinfo;
    }

    public void setAqiinfo(String aqiinfo) {
        this.aqiinfo = aqiinfo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAffect() {
        return affect;
    }

    public void setAffect(String affect) {
        this.affect = affect;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSoncity() {
        return soncity;
    }

    public void setSoncity(String soncity) {
        this.soncity = soncity;
    }

    public int getSoncityid() {
        return soncityid;
    }

    public void setSoncityid(int soncityid) {
        this.soncityid = soncityid;
    }

    public String getSoncitycode() {
        return soncitycode;
    }

    public void setSoncitycode(String soncitycode) {
        this.soncitycode = soncitycode;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSo224() {
        return so224;
    }

    public void setSo224(String so224) {
        this.so224 = so224;
    }

    public String getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(String pm2_5) {
        this.pm2_5 = pm2_5;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "city='" + city + '\'' +
                ", cityid=" + cityid +
                ", citycode='" + citycode + '\'' +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", weather='" + weather + '\'' +
                ", temp='" + temp + '\'' +
                ", temphigh='" + temphigh + '\'' +
                ", templow='" + templow + '\'' +
                ", img='" + img + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", windspeed='" + windspeed + '\'' +
                ", winddirect='" + winddirect + '\'' +
                ", windpower='" + windpower + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", so2='" + so2 + '\'' +
                ", so224='" + so224 + '\'' +
                ", no2='" + no2 + '\'' +
                ", no224='" + no224 + '\'' +
                ", co='" + co + '\'' +
                ", co24='" + co24 + '\'' +
                ", o3='" + o3 + '\'' +
                ", o38='" + o38 + '\'' +
                ", o324='" + o324 + '\'' +
                ", pm10='" + pm10 + '\'' +
                ", pm1024='" + pm1024 + '\'' +
                ", pm2_5='" + pm2_5 + '\'' +
                ", pm2_524='" + pm2_524 + '\'' +
                ", iso2='" + iso2 + '\'' +
                ", ino2='" + ino2 + '\'' +
                ", ico='" + ico + '\'' +
                ", io3='" + io3 + '\'' +
                ", io38='" + io38 + '\'' +
                ", ipm10='" + ipm10 + '\'' +
                ", ipm2_5='" + ipm2_5 + '\'' +
                ", aqi='" + aqi + '\'' +
                ", primarypollutant='" + primarypollutant + '\'' +
                ", quality='" + quality + '\'' +
                ", timepoint='" + timepoint + '\'' +
                ", aqiinfo='" + aqiinfo + '\'' +
                ", level='" + level + '\'' +
                ", color='" + color + '\'' +
                ", affect='" + affect + '\'' +
                ", measure='" + measure + '\'' +
                ", night='" + night + '\'' +
                ", sunset='" + sunset + '\'' +
                ", day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", soncity='" + soncity + '\'' +
                ", soncityid=" + soncityid +
                ", soncitycode='" + soncitycode + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", exponents=" + exponents +
                ", daily=" + daily +
                ", hourly=" + hourly +
                '}';
    }
}
