package com.example.weather;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/3/11.
 */

public class CityApi {
    private String xmlData;
    private Map<String,Object> map;
    private Boolean isOver;

    public void getWeatherDateForNet(String city){
        isOver=false;
        final String website="http://api.jisuapi.com/weather/query?appkey=2c370c8ab5b36926&city="+city;
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(website).method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        final Call call = okHttpClient.newCall(request);
        //4.同步调用会阻塞主线程,这边在子线程进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步调用,返回Response,会抛出IO异常
                    Response response = call.execute();
                    xmlData=response.body().string();
                    JSONObject json = JSONObject.fromObject(xmlData);
                    Get(json);
                    isOver=true;
                    System.out.println(map.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void Get(JSONObject json) throws Exception {
        map=new HashMap<>();
        try {
            if (json.getInt("status") != 0) {
                System.out.println(json.getString("msg"));
            } else {
                JSONObject resultarr = json.optJSONObject("result");
                map.put("city",resultarr.getString("city"));
                map.put("cityid",resultarr.getString("cityid"));
                map.put("citycode",resultarr.getString("citycode"));
                map.put("date",resultarr.getString("date"));
                map.put("week",resultarr.getString("week"));
                map.put("weather",resultarr.getString("weather"));
                map.put("temp",resultarr.getString("temp"));
                map.put("temphigh",resultarr.getString("temphigh"));
                map.put("templow",resultarr.getString("templow"));
                map.put("img",resultarr.getString("img"));
                map.put("humidity",resultarr.getString("humidity"));
                map.put("pressure",resultarr.getString("pressure"));
                map.put("windspeed",resultarr.getString("windspeed"));
                map.put("winddirect", resultarr.getString("winddirect"));
                map.put("windpower",resultarr.getString("windpower"));
                String updatatime0=(String) resultarr.get("updatetime");
                String updatatime=updatatime0.substring(11,16);
                map.put("updatetime",updatatime);

                if (resultarr.opt("index") != null) {
                    JSONArray index = resultarr.optJSONArray("index");
                    for (int i = 0; i < index.size(); i++) {
                        JSONObject obj = (JSONObject) index.opt(i);
                        String iname = obj.getString("iname");
                        String ivalue = obj.getString("ivalue");
                        String detail = obj.getString("detail");
                        map.put(iname,ivalue+" "+detail);
                    }
                }
                if (resultarr.opt("aqi") != null) {
                    JSONObject aqi = resultarr.optJSONObject("aqi");
                    map.put("so2" ,aqi.getString("so2"));
                    map.put( "so224" , aqi.getString("so224"));
                    map.put( "no2" ,aqi.getString("no2"));
                    map.put( "no224" , aqi.getString("no224"));
                    map.put( "co" , aqi.getString("co"));
                    map.put( "co24" , aqi.getString("co24"));
                    map.put( "o3" ,aqi.getString("o3"));
                    map.put( "o38" , aqi.getString("o38"));
                    map.put( "o324" , aqi.getString("o324"));
                    map.put( "pm10" , aqi.getString("pm10"));
                    map.put( "pm1024" , aqi.getString("pm1024"));
                    map.put( "pm2_5" , aqi.getString("pm2_5"));
                    map.put( "pm2_524" , aqi.getString("pm2_524"));
                    map.put( "iso2" , aqi.getString("iso2"));
                    map.put( "ino2" , aqi.getString("ino2"));
                    map.put( "ico" ,aqi.getString("ico"));
                    map.put( "io3" , aqi.getString("io3"));
                    map.put( "io38" , aqi.getString("io38"));
                    map.put( "ipm10" , aqi.getString("ipm10"));
                    map.put( "ipm2_5" , aqi.getString("ipm2_5"));
                    map.put( "aqi1" , aqi.getString("aqi"));
                    map.put( "primarypollutant" , aqi.getString("primarypollutant"));
                    map.put( "quality" , aqi.getString("quality"));
                    map.put( "timepoint" , aqi.getString("timepoint"));

                    if (aqi.opt("aqiinfo") != null) {
                        JSONObject aqiinfo = aqi.optJSONObject("aqiinfo");
                        map.put("level",aqiinfo.getString("level"));
                        map.put("color",aqiinfo.getString("color"));
                        map.put("affect",aqiinfo.getString("affect"));
                        map.put("measure",aqiinfo.getString("measure"));
                    }
                }
                if (resultarr.opt("daily") != null) {
                    JSONArray daily = resultarr.optJSONArray("daily");
                    for (int i = 0; i < daily.size(); i++) {
                        JSONObject obj = (JSONObject) daily.opt(i);
                        String date="data"+i;
                        String week="week"+i;
                        String sunrise="sunrise"+i;
                        String sunset="sunset"+i;
                        map.put(date,obj.getString("date"));
                        map.put(week,obj.getString("week"));
                        map.put(sunrise,obj.getString("sunrise"));
                        map.put(sunset,obj.getString("sunset"));
                        if (obj.opt("night") != null) {
                            JSONObject night = (JSONObject) obj.opt("night");
                            String weather="weather"+i;
                            String templow="templow"+i;
                            String img="img"+i;
                            String winddirect="winddirect"+i;
                            String windpower="windpower"+i;
                            if(night.has("weather")){
                                map.put(weather,night.getString("weather"));
                            }
                            map.put(templow,night.getString("templow"));
                            map.put(img,night.getString("img"));
                            map.put(winddirect,night.getString("winddirect"));
                            map.put(windpower,night.getString("windpower"));

                        }
                        if (obj.opt("day") != null) {
                            JSONObject day = obj.optJSONObject("day");
                            String weather = "weather" + i + "d";
                            String temphigh = "temphigh" + i + "d";
                            String img = "img" + i + "d";
                            String winddirect = "winddirect" + i + "d";
                            String windpower = "windpower" + i + "d";
                            map.put(weather, day.getString("weather"));
                            map.put(img, day.getString("img"));
                            map.put(winddirect, day.getString("winddirect"));
                            map.put(windpower, day.getString("windpower"));
                            if (day.has("temphigh")) {
                                map.put(temphigh, day.getString("temphigh"));
                            }
                        }
                    }
                }
                if (resultarr.opt("hourly") != null) {
                    JSONArray hourly = resultarr.optJSONArray("hourly");
                    String[][] hour=new String[24][4];
                    for (int i = 0; i < hourly.size(); i++) {
                        JSONObject obj = (JSONObject) hourly.opt(i);
                        hour[i][0]=obj.getString("time");
                        hour[i][1]=obj.getString("weather");
                        hour[i][2]=obj.getString("temp");
                        hour[i][3]=obj.getString("img");
                    }
                    map.put("hour",hour);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Map<String, Object> getMap() {
        return map;
    }

    public Boolean getOver() {
        return isOver;
    }

    public void setOver(Boolean over) {
        isOver = over;
    }
}
