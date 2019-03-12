package com.example.weather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import useless.DataBase;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    //屏幕高度
    private int hight;
    //状态栏高度
    private int stateHight;
    CityApi cityApi;
    Locate locate;
    DataBase dataBase;
    private boolean isLacate=false;
    private List<Map> data=new ArrayList<>();
    private ViewPager vp1;
    private List<Fragment> fragmentList;
    FragmentAdapter fragmentAdapter;
    private TextView tvTitle;
    private String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取屏幕、状态栏高度
        hight=windowHight();
        stateHight=getStateBar();
        cityApi=new CityApi();
        dataBase=new DataBase(MainActivity.this);
        locate=new Locate(getContext());
        welcome();
        handler.sendEmptyMessage(0);
        UIThing();
    }

    public void welcome(){
        SharedPreferences sp = getSharedPreferences("set", 0);
        Boolean isWelcome = sp.getBoolean("iswelcome", true);
        if(isWelcome){
            Intent intent=new Intent(MainActivity.this,WelcomeActivity.class);
            startActivity(intent);
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(isLacate){
                        locate.runTask();
                        handler.removeMessages(0);
                        handler.sendEmptyMessage(1);
                    }else{
                        SharedPreferences sp= getSharedPreferences("set", 0);
                        isLacate=sp.getBoolean("isLacate",false);
                        handler.sendEmptyMessageDelayed(0,100);
                    }
                    break;
                case 1:
                    if(locate.getRun()){
                        cityApi.getWeatherDateForNet(locate.getCityName());
                        Log.d("new",locate.getCityName()+"定位城市");
                        locate.setRun(false);
                        handler.removeMessages(1);
                        handler.sendEmptyMessage(2);
                    }else{
                        handler.sendEmptyMessageDelayed(1,100);
                    }
                    break;
                case 2:
                    if(cityApi.getOver()){
                        Log.d("test","哈哈哈");
                        setData(cityApi.getMap());
                        cityApi.setOver(false);
                        handler.removeMessages(2);
                        Log.d("new",data.toString());
                    }else{
                        handler.sendEmptyMessageDelayed(2,100);
                    }
                    break;
                case 3:
                    cityApi.getWeatherDateForNet(city);
                    handler.sendEmptyMessage(2);
                    break;
            }

        }
    };

    //获取屏幕宽度
    public int windowHight(){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        return height;
    }

    //获取状态栏高度
    private int getStateBar(){
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void UIThing(){
        //toolbar设置
        toolbar=findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.inflateMenu(R.menu.menu1);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case 111:
                            break;
                        case 222:
                            break;
                        case 333:
                            break;
                        case 444:
                            break;
                    }
                    return false;
                }
            });
        }
        tvTitle=findViewById(R.id.tvTitle);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CityChoseActivity.class);
                startActivityForResult(intent,001);
                vp1.setCurrentItem(fragmentList.size()-1);
            }
        });

        vp1=findViewById(R.id.vp1);
        fragmentList=new ArrayList<>();
        WeatherFragment weatherFragment=new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Hight",hight);
        bundle.putInt("StateHight",stateHight);
        weatherFragment.setArguments(bundle);
        fragmentList.add(weatherFragment);
        FragmentManager fm=getSupportFragmentManager();
        fragmentAdapter=new FragmentAdapter(fm,fragmentList);
        vp1.setAdapter(fragmentAdapter);
        vp1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText(data.get(position).get("city")+"");
                WeatherFragment weatherFragment=(WeatherFragment) fragmentList.get(position);
                weatherFragment.initData(data.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setData(Map map){
        Boolean no=true;
        if(data.size()==0){
            data.add(map);
            WeatherFragment weatherFragment=(WeatherFragment) fragmentList.get(0);
            weatherFragment.initData(data.get(0));
            tvTitle.setText(data.get(0).get("city")+"");
        }else{
            for(int i=0;i<data.size();i++){
                if(data.get(i).get("city").equals(map.get("city"))){
                    data.set(i,map);
                    no=false;
                    WeatherFragment weatherFragment=(WeatherFragment) fragmentList.get(i);
                    weatherFragment.initData(data.get(i));
                    break;
                }
            }
            if(no){
                    WeatherFragment weatherFragment=new WeatherFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Hight",hight);
                    bundle.putInt("StateHight",stateHight);
                    weatherFragment.setArguments(bundle);
                    fragmentList.add(weatherFragment);
                    fragmentAdapter.notifyDataSetChanged();
                    data.add(map);
                    weatherFragment.initData(map);
            }
        }
    }

    private Context getContext(){
        return getApplicationContext();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==001&&resultCode==001){
            city=data.getExtras().getString("result");
            if(!(city==null)){
                handler.sendEmptyMessage(3);
            }
        }
    }
}
