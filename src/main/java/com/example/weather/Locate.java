package com.example.weather;

import android.content.Context;
import android.util.Log;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


/**
 * Created by hasee on 2018/7/8.
 */

public class Locate {
    private static String cityName=null;
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;
    private Boolean run=false;
    private Context context;

    public Locate(Context context){
        this.context=context;
    }

    public void runTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLocationClient = new LocationClient(context);
                myLocationListener = new MyLocationListener();
                mLocationClient.registerLocationListener(myLocationListener);
                initLocation();
                    mLocationClient.start();
            }
        }).start();
    }

    void initLocation()
    {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean getRun() {
        return run;
    }

    public void setRun(Boolean run) {
        this.run = run;
    }

    class MyLocationListener implements BDLocationListener {
       String city=null;
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
          /*  String addr = bdLocation.getAddrStr();    //获取详细地址信息
            String country = bdLocation.getCountry();    //获取国家
            Log.d("city", ":" + cityName);
            String province = bdLocation.getProvince();    //获取省份
            Log.d("city", ":" + cityName);
            String district = bdLocation.getDistrict();    //获取区县
            String street = bdLocation.getStreet();    //*/
              city=bdLocation.getCity();
                if(!(city==null))
            {
                //int x = city.indexOf("市");
                String y1 = city.substring(0,city.length()-1);
                setCityName(y1);
                run=true;
            }
                Log.d("new",city);
            }
        }

}




