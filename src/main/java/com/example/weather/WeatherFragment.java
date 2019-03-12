package com.example.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2019/3/11.
 */

public class WeatherFragment extends Fragment {
    private TextView tvWundu,tvFeng,tvHint,tvWeather0,tvWundu0,tvTomorrow,tvWeather1,tvWundu1,tvTitle;
    private ImageView ivWeather0,ivWeather1;
    private LinearLayout llDown;
    private Space space1;
    private Map map;
    private int hight;
    //状态栏高度
    private int stateHight;
    private String city;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_weather,container,false);
        hight=getArguments().getInt("Hight");
        stateHight=getArguments().getInt("StateHight");
        space1=view.findViewById(R.id.space1);
        space1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(hight-stateHight)/2));
        llDown=view.findViewById(R.id.llDown);
        llDown.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,hight-stateHight-40-(hight-stateHight)/2));
        return view;
    }

    public void initData(Map map){
        String fengxiang,type,fengli;
        Calendar date = Calendar.getInstance();
        date.setTime(strToDate( map.get("updatetime")+""));
        Calendar end = Calendar.getInstance();
        end.setTime(strToDate("18:30"));
        if (date.before(end)) {
            fengxiang=map.get("winddirect0d")+"";
            type=map.get("weather0d")+"";
            fengli=map.get("windpower0d")+"";
        }else{
            fengxiang=map.get("winddirect0")+"";
            type=map.get("weather0")+"";
            fengli=map.get("windpower0")+"";
        }

        tvWundu=view.findViewById(R.id.tvWendu);
        tvFeng=view.findViewById(R.id.tvFeng);
        tvHint=view.findViewById(R.id.tvHint);
        tvWeather0=view.findViewById(R.id.tvWeather0);
        tvWundu0=view.findViewById(R.id.tvWundu0);
        tvTomorrow=view.findViewById(R.id.tvTomorrow);
        tvWeather1=view.findViewById(R.id.tvWeather1);
        tvWundu1=view.findViewById(R.id.tvWudu1);
        tvWundu.setText(map.get("temp")+"°");
        tvFeng.setText(fengxiang+" "+fengli+" 湿度"+map.get("humidity")+"…");
        String date0=map.get("date")+"";
        String date1=date0.substring(5);
        tvHint.setText(date1+" "+map.get("week"));
        tvWeather0.setText(type);
        tvWundu0.setText(map.get("templow")+"/"+map.get("temphigh"));
        tvTomorrow.setText(map.get("week1")+"");
        tvWeather1.setText(map.get("weather1d")+"");
        tvWundu1.setText(map.get("templow1")+"/"+map.get("temphigh1d"));
        city=(String) map.get("city");

        ivWeather0=view.findViewById(R.id.ivWeather0);
        ivWeather1=view.findViewById(R.id.ivWeather1);
        List<ImageView> li=new ArrayList<>();
        li.add(ivWeather0);
        li.add(ivWeather1);
        String[] x=new String[]{type,map.get("weather1d")+""};
        for(int i=0;i<2;i++) {
            switch (x[i]) {
                case "晴":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_sunnny);
                    break;
                case "阴":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_overcast);
                    break;
                case "多云":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_clound);
                    break;
                case "小雨":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_rain);
                    break;
                case "大雨":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_heavyrain);
                    break;
                case "暴雨":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_rainstorm);
                    break;
                case "中雨":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_moderaterain);
                    break;
                case "阵雨":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_daytimesnow);
                    break;
                case "雷阵雨":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_thundershower);
                    break;
                case "雨夹雪":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_sleet);
                    break;
                case "小雪":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_snow);
                    break;
                case "中雪":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_morderatesnow);
                    break;
                case "暴雪":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_snowstorm);
                    break;
                case "大雪":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_heavysnow);
                    break;
                case "雾":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_help);
                    break;
                case "冰雹":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_help);
                    break;
                case "沙尘暴":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_sandstorm);
                    break;
                case "雾霾":
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_haze);
                    break;
                default:
                    li.get(i).setImageResource(R.drawable.forecast_notification_bar_icon_help);
                    break;
            }
        }
    }

    public static Date strToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
        }
        return date;
    }
}
