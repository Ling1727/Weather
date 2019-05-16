package com.example.weather.fragmemt;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.weather.R;
import com.example.weather.activity.MainActivity;
import com.example.weather.litepal.Hourly;
import com.example.weather.litepal.WeatherData;
import com.example.weather.tool.HourlyLineView;
import com.example.weather.tool.HourlyView;
import com.example.weather.tool.LineView;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2019/3/11.
 */

public class WeatherFragment extends Fragment {
    private TextView tvWundu,tvFeng,tvHint,tvWeather0,tvWundu0,tvTomorrow,tvWeather1,tvWundu1,tv16,tv20,tvTime,tv02,tv03;
    private ImageView ivWeather0,ivWeather1;
    private LinearLayout llDown,llHourly;
    private RelativeLayout space1,rlLine,rlLine2,rlHourly;
    private int height,width;
    //状态栏高度
    private int stateHeight;
    private String city;
    private View view;
    private WeatherData weatherData;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static Map<String,Integer> map=new HashMap<>();
    private static int[] rl=new int[]{R.id.rl11,R.id.rl12,R.id.rl13,R.id.rl14,R.id.rl15,R.id.rl16,R.id.rl17};
    private List<Hourly> hourlyList=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_weather,container,false);
        height=getArguments().getInt("Hight");
        width=getArguments().getInt("width");
        stateHeight=getArguments().getInt("StateHight");
        space1=view.findViewById(R.id.space1);
        space1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(height-stateHeight)/3));
        llDown=view.findViewById(R.id.llDown);
        llDown.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height-stateHeight-40-(height-stateHeight)/3));
        weatherData=(WeatherData) getArguments().getSerializable("weatherData");
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        /*// 设置刷新时进度动画变换的颜色，接收参数为可变长度数组。也可以使用setColorSchemeColors()方法。
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R
                .color.holo_green_light);
        // 设置刷新时圆形图标的大小。只可传递2个参数，SwipeRefreshLayout.LARGE或SwipeRefreshLayout.DEFAULT。
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        // 设置刷新时圆形图标的背景色。也可以使用setProgressBackgroundColorSchemeColor()方法。
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.background_light);
        // 设置刷新时的圆形图标。scale：是否缩放；start：圆形图标在刷新前的起始位置；end：圆形图标的偏移量。
        swipeRefreshLayout.setProgressViewOffset(true, 100, 200);*/
        // 设置会触发下拉刷新的手势滑动距离
        swipeRefreshLayout.setDistanceToTriggerSync(150);
        //设置在listview上下拉刷新的监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里可以做一下下拉刷新的操作
                //例如去请求后台接口啥的。。。
                MainActivity mainActivity=(MainActivity) getActivity();
                mainActivity.upData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initView(weatherData);
        return view;
    }

    public void initView(WeatherData weatherData) {
        String fengxiang,type,fengli,img,fengxiang1,type1,fengli1,img1;
        Calendar date = Calendar.getInstance();
        date.setTime(strToDate(weatherData.getUpdatetime()));
        Calendar end = Calendar.getInstance();
        end.setTime(strToDate("18:30"));
        if (date.before(end)) {
            fengxiang = weatherData.getDaily().get(0).getDay_winddirect();
            type = weatherData.getDaily().get(0).getDay_weather();
            fengli = weatherData.getDaily().get(0).getDay_windpower();
            img=weatherData.getDaily().get(0).getDay_img();
            fengxiang1 = weatherData.getDaily().get(1).getDay_winddirect();
            type1 = weatherData.getDaily().get(1).getDay_weather();
            fengli1 = weatherData.getDaily().get(1).getDay_windpower();
            img1=weatherData.getDaily().get(1).getDay_img();
        } else {
            fengxiang = weatherData.getDaily().get(0).getNight_winddirect();
            type = weatherData.getDaily().get(0).getNight_weather();
            fengli = weatherData.getDaily().get(0).getNight_windpower();
            img=weatherData.getDaily().get(0).getNight_img();
            fengxiang1= weatherData.getDaily().get(1).getNight_winddirect();
            type1= weatherData.getDaily().get(1).getNight_weather();
            fengli1= weatherData.getDaily().get(1).getNight_windpower();
            img1=weatherData.getDaily().get(1).getNight_img();
        }
        tvTime = view.findViewById(R.id.tvTime);
        tv16 = view.findViewById(R.id.textView16);
        tv20 = view.findViewById(R.id.textView20);
        tvWundu = view.findViewById(R.id.tvWendu);
        tvFeng = view.findViewById(R.id.tvFeng);
        tvHint = view.findViewById(R.id.tvHint);
        tvWeather0 = view.findViewById(R.id.tvWeather0);
        tvWundu0 = view.findViewById(R.id.tvWundu0);
        tvTomorrow = view.findViewById(R.id.tvTomorrow);
        tvWeather1 = view.findViewById(R.id.tvWeather1);
        tvWundu1 = view.findViewById(R.id.tvWudu1);
        ivWeather0 = view.findViewById(R.id.ivWeather0);
        ivWeather1 = view.findViewById(R.id.ivWeather1);
        tv02=view.findViewById(R.id.tv02);
        tv03=view.findViewById(R.id.tv03);

        tvTime.setText("更新时间:" + weatherData.getTime());
        tv16.setText(weatherData.getQuality());
        tv20.setText(weatherData.getQuality());
        tvWundu.setText(weatherData.getTemp() + "°");
        tvFeng.setText(fengxiang + " " + fengli + " 湿度" + weatherData.getHumidity() + "%");
        String date0 = weatherData.getDate();
        String date1 = date0.substring(5);
        tvHint.setText(date1 + " " + weatherData.getWeek());
        tvWeather0.setText(type);
        tvWundu0.setText(weatherData.getTemplow() + "°/" + weatherData.getTemphigh()+"°");
        tvTomorrow.setText(weatherData.getDaily().get(1).getWeek());
        tvWeather1.setText(type1);
        tvWundu1.setText(weatherData.getDaily().get(1).getNight_templow() + "°/" + weatherData.getDaily().get(1).getDay_temphigh()+"°");
        city = weatherData.getCity();
        ivWeather0.setImageResource(map.get(img));
        ivWeather1.setImageResource(map.get(img1));
        tv02.setText(weatherData.getDaily().get(0).getSunset());
        tv03.setText(weatherData.getDaily().get(0).getSunrise());

        for(int i=0;i<7;i++){
            DayFragment dayFragment=new DayFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("day",i);
            bundle.putString("upDatetime",weatherData.getUpdatetime());
            bundle.putString("city",city);
            dayFragment.setArguments(bundle);
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(rl[i],dayFragment,"tag"+i).commit();
        }

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        rlLine=view.findViewById(R.id.rlLine);
        rlLine.measure(w,h);
        rlLine.addView(new LineView(getActivity(),city,rlLine.getMeasuredWidth(),rlLine.getMeasuredHeight(),0));
        rlLine2=view.findViewById(R.id.rlLine2);
        rlLine2.measure(w,h);
        rlLine2.addView(new LineView(getActivity(),city,rlLine.getMeasuredWidth(),rlLine.getMeasuredHeight(),1));

        rlHourly=view.findViewById(R.id.rlHourly);
        rlHourly.measure(w,h);
        rlHourly.addView(new HourlyLineView(getActivity(),width,rlHourly.getMeasuredHeight(),city));
        llHourly=view.findViewById(R.id.llHourly);
        hourlyList= LitePal.where("city=?",city).find(Hourly.class);
        for(int y=0;y<hourlyList.size();y++){
            llHourly.addView(new HourlyView(getActivity(),hourlyList.get(y).getWeather(),map.get(hourlyList.get(y).getImg()),width,rlHourly.getMeasuredHeight()));
        }

    }

    public static Date strToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
        }
        return date;
    }

    static {
        map.put("0",R.drawable.w0);map.put("1",R.drawable.w1);map.put("2",R.drawable.w2);map.put("3",R.drawable.w3);
        map.put("4",R.drawable.w4);map.put("5",R.drawable.w5);map.put("6",R.drawable.w6);map.put("7",R.drawable.w7);
        map.put("8",R.drawable.w8);map.put("9",R.drawable.w9);map.put("10",R.drawable.w10);map.put("11",R.drawable.w11);
        map.put("12",R.drawable.w12);map.put("13",R.drawable.w13);map.put("14",R.drawable.w14);map.put("15",R.drawable.w15);
        map.put("16",R.drawable.w16);map.put("17",R.drawable.w17);map.put("18",R.drawable.w18);map.put("19",R.drawable.w19);
        map.put("20",R.drawable.w20);map.put("21",R.drawable.w21);map.put("22",R.drawable.w22);map.put("23",R.drawable.w23);
        map.put("24",R.drawable.w24);map.put("25",R.drawable.w25);map.put("26",R.drawable.w26);map.put("27",R.drawable.w27);
        map.put("28",R.drawable.w28);map.put("29",R.drawable.w29);map.put("30",R.drawable.w30);map.put("31",R.drawable.w31);
        map.put("32",R.drawable.w32);map.put("39",R.drawable.w39);map.put("49",R.drawable.w49);map.put("53",R.drawable.w53);
        map.put("54",R.drawable.w54);map.put("55",R.drawable.w55);map.put("56",R.drawable.w56);map.put("57",R.drawable.w57);
        map.put("58",R.drawable.w58);map.put("99",R.drawable.w99);map.put("301",R.drawable.w301);map.put("302",R.drawable.w302);
    }

}
