package com.example.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.weather.litepal.Daily;
import com.example.weather.litepal.Exponent;
import com.example.weather.litepal.Hourly;
import com.example.weather.litepal.WeatherData;
import com.example.weather.tool.CityApi;
import com.example.weather.adapter.FragmentAdapter;
import com.example.weather.tool.Locate;
import com.example.weather.R;
import com.example.weather.fragmemt.WeatherFragment;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private int height,width;  //屏幕高度
    private int stateHeight;  //状态栏高度
    private CityApi cityApi;
    private Locate locate;
    private boolean isLacate=false;
    private ViewPager vp1;
    private List<Fragment> fragmentList;
    private FragmentAdapter fragmentAdapter;
    private TextView tvTitle;
    private String citycode;
    private ProgressBar progressBar;
    private LinearLayout linear,llPoint,llLoading;
    private List<WeatherData> weatherData;
    private List<View> point;
    private List<CityApi> cityApiList=new ArrayList<>();
    private List<String> cityList;
    private List<Boolean> booleanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcome();
        initView();
        initData();
        initControl();
    }

    public void welcome(){
        SharedPreferences sp = getSharedPreferences("set", 0);
        Boolean isWelcome = sp.getBoolean("iswelcome", true);
        if(isWelcome){
            Intent intent=new Intent(MainActivity.this,WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void initView(){
        linear=findViewById(R.id.linear);
        linear.setBackgroundColor(00000000);
        progressBar=findViewById(R.id.progressBar);
        toolbar=findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu1);
        tvTitle=findViewById(R.id.tvTitle);
        vp1=findViewById(R.id.vp1);
        llPoint=findViewById(R.id.llPoint);
        llLoading=findViewById(R.id.llLoading);
    }

    public void initData(){
        //获取屏幕、状态栏高度
        windowHeight();
        stateHeight=getStateBar();
        cityApi=new CityApi();
        locate=new Locate(getContext());
        fragmentList=new ArrayList<>();
        point=new ArrayList<>();
        cityList=new ArrayList<>();
    }

    public void initControl(){
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.red:
                            break;
                        case R.id.delete:
                            subCity();
                            break;
                        case 333:
                            break;
                        case 444:
                            break;
                    }
                    return false;
                }
            });

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CityChoseActivity.class);
                startActivityForResult(intent,001);
            }
        });

        SharedPreferences sp = getSharedPreferences("set", 0);
        Boolean isWelcome = sp.getBoolean("iswelcome", true);
        if(!isWelcome){
            loading();
            upData();
        }
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(isLacate){
                        progressBar.setVisibility(View.VISIBLE);
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
                        weatherData= LitePal.findAll(WeatherData.class);
                        loading();
                        cityApi.setOver(false);
                        progressBar.setVisibility(View.INVISIBLE);
                        handler.removeMessages(2);
                    }else{
                        handler.sendEmptyMessageDelayed(2,100);
                    }
                    break;
                case 3:
                    if(cityApi.getOver()){
                        addCity();
                        cityApi.setOver(false);
                        handler.removeMessages(3);
                    }else{
                        handler.sendEmptyMessageDelayed(3,100);
                    }
                    break;
                case 4:
                    boolean isover=true;
                    for(int i=0;i<cityApiList.size();i++){
                        if(!cityApiList.get(i).getOver()){
                            isover=false;
                        }
                    }
                    if(isover){
                        cityApiList.clear();
                        boolean boo=true;
                        for(int a=0;a<booleanList.size();a++){
                            if(!booleanList.get(a)){
                                boo=false;
                                break;
                            }
                        }
                        if(boo){
                            loading();
                            Toast.makeText(MainActivity.this, "更新完成！", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "更新失败！", Toast.LENGTH_SHORT).show();
                        }
                        llLoading.setVisibility(View.GONE);
                    }else {
                        handler.sendEmptyMessageDelayed(4,100);
                    }
                    break;
            }
            return false;
        }
    });

    public void loading(){
        SharedPreferences sp= getSharedPreferences("set", 0);
        isLacate=sp.getBoolean("isLacate",false);
        if(isLacate){
            weatherData=LitePal.findAll(WeatherData.class);
            if(weatherData.size()==0){
                handler.sendEmptyMessage(0);
            }else{
                View view;
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(40, 50);
                if(fragmentList.size()==weatherData.size()){
                    fragmentList.clear();
                    point.clear();
                    llPoint.removeAllViews();
                    cityList.clear();
                }
                for(int i=0;i<weatherData.size();i++){
                    WeatherFragment weatherFragment=new WeatherFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Hight",height);
                    bundle.putInt("width",width);
                    bundle.putInt("StateHight",stateHeight);
                    bundle.putSerializable("weatherData",weatherData.get(i));
                    weatherFragment.setArguments(bundle);
                    fragmentList.add(weatherFragment);
                    view=new View(MainActivity.this);
                    view.setBackgroundResource(R.drawable.point);
                    view.setLayoutParams(layoutParams);
                    if(i==0){
                        point.add(view);
                        llPoint.addView(view);
                        view.setEnabled(true);
                    }else{
                        layoutParams.leftMargin=10;
                        point.add(view);
                        llPoint.addView(view);
                        view.setEnabled(false);
                    }
                    cityList.add(weatherData.get(i).getCity());
                }
                FragmentManager fm=getSupportFragmentManager();
                fragmentAdapter=new FragmentAdapter(fm,fragmentList);
                vp1.setAdapter(fragmentAdapter);
                vp1.setOffscreenPageLimit(3);
                tvTitle.setText(cityList.get(0));
                vp1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }
                    @Override
                    public void onPageSelected(int position) {
                        tvTitle.setText(cityList.get(position));
                        for(int i=0;i<point.size();i++){
                            if(i==position){
                                point.get(i).setEnabled(true);
                            }else{
                                point.get(i).setEnabled(false);
                            }
                        }
                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
            }
        }else{
            Toast.makeText(MainActivity.this,"定位权限未开启！无法进行定位！请手动选择城市",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(MainActivity.this,CityChoseActivity.class);
            startActivityForResult(intent,001);
        }
    }

    public void addCity(){
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Hight", height);
        bundle.putInt("width",width);
        bundle.putInt("StateHight", stateHeight);
        WeatherData weatherData1 = LitePal.where("citycode=?", citycode).find(WeatherData.class).get(0);
        bundle.putSerializable("weatherData", weatherData1);
        weatherFragment.setArguments(bundle);
        fragmentList.add(weatherFragment);
        fragmentAdapter.notifyDataSetChanged();
        cityList.add(weatherData1.getCity());
        View view=new View(MainActivity.this);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(40,50);
        layoutParams.leftMargin=10;
        view.setLayoutParams(layoutParams);
        view.setBackgroundResource(R.drawable.point);
        view.setEnabled(false);
        point.add(view);
        llPoint.addView(view);
        weatherData = LitePal.findAll(WeatherData.class);
        vp1.setCurrentItem(fragmentList.size()-1);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void subCity(){
        if(weatherData.size()==1){
            Toast.makeText(MainActivity.this,"请至少添加一个城市",Toast.LENGTH_LONG).show();
        }else{
            String delete=tvTitle.getText().toString();
            LitePal.deleteAll(WeatherData.class,"city=?",delete);
            fragmentList.clear();
            point.clear();
            llPoint.removeAllViews();
            cityList.clear();
            loading();
            LitePal.deleteAll(Daily.class,"city=?",delete);
            LitePal.deleteAll(Exponent.class,"city=?",delete);
            LitePal.deleteAll(Hourly.class,"city=?",delete);
        }
    }

    public void upData(){
        if(!(weatherData.size()==0)){
            booleanList=new ArrayList<>();
            llLoading.setVisibility(View.VISIBLE);
            for(int i=0;i<weatherData.size();i++){
                CityApi cityApi1=new CityApi();
                booleanList.add(cityApi1.getWeatherDateForNet(weatherData.get(i).getCity()));
                cityApiList.add(cityApi1);
            }
            handler.sendEmptyMessage(4);
        }
    }

    //获取屏幕宽度
    public void windowHeight(){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;         // 屏幕宽度（像素）
        height = dm.heightPixels;       // 屏幕高度（像素）
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

    private Context getContext(){
        return getApplicationContext();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==001&&resultCode==001){
            citycode=data.getExtras().getString("result");
            String city=data.getExtras().getString("city");
            if(!(citycode==null)){
                boolean ishave=false;
                for(int i=0;i<weatherData.size();i++){
                    if(citycode.equals(weatherData.get(i).getCitycode())){
                        ishave=true;
                        break;
                    }
                }
                if(ishave){
                    Toast.makeText(MainActivity.this,"已添加该城市！请选择别的城市",Toast.LENGTH_LONG).show();
                    ishave=false;
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    cityApi.getWeatherDateForNet(city);
                    handler.sendEmptyMessage(3);
                }
            }
        }
    }

}
