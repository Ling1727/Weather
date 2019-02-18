package com.example.weather;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    //屏幕高度
    private int hight;
    //状态栏高度
    private int stateHight;
    private LinearLayout llFirst,llDown,llWelcome;
    private Space space1;
    private ViewPager vpWelcome;
    private List<View> views=new ArrayList<View>();
    private Button button;
    private View iv1,iv2;
    private RelativeLayout rlWelcome;
    private static final int BAIDU_READ_PHONE_STATE =100;
    private String city="101010100";
    LoadingData loadingData;
    Locate locate;
    DataBase dataBase;
    private boolean isLacate=false;
    private List<Map> data;
    private TextView tvWundu,tvFeng,tvHint,tvWeather0,tvWundu0,tvTomorrow,tvWeather1,tvWundu1,tvTitle;
    private ImageView ivWeather0,ivWeather1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取屏幕、状态栏高度
        hight=windowHight();
        stateHight=getStateBar();
        loadingData=new LoadingData();
        dataBase=new DataBase(MainActivity.this);
        locate=new Locate(getContext());
        //获取权限
        showContacts();
        handler.sendEmptyMessage(0);
        welcome();
        UIThing();
    }


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(isLacate){
                        locate.runTask();
                        if(!dataBase.getOver()){
                            dataBase.date();
                        }
                        handler.removeMessages(0);
                        handler.sendEmptyMessage(1);
                    }else{
                        SharedPreferences sp= getSharedPreferences("set", 0);
                        isLacate=sp.getBoolean("isLacate",false);
                        handler.sendEmptyMessageDelayed(0,100);
                    }
                    break;
                case 1:
                    if(locate.getRun()&&dataBase.getOver()){
                        loadingData.getWeatherDateForNet(dataBase.number(locate.getCityName()));
                        Log.d("new",locate.getCityName()+"定位城市");
                        locate.setRun(false);
                        handler.removeMessages(1);
                        handler.sendEmptyMessage(2);
                    }else{
                        handler.sendEmptyMessageDelayed(1,100);
                    }
                    break;
                case 2:
                    if(loadingData.getIsFinish()){
                        data=loadingData.getList();
                        loadingData.setIsFinish(false);
                        handler.removeMessages(2);
                        handler.sendEmptyMessage(3);
                        Log.d("new",data.toString());
                    }else{
                        handler.sendEmptyMessageDelayed(2,100);
                    }
                    break;
                case 3:
                    initData();
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

        llFirst=findViewById(R.id.llFirst);
        space1=findViewById(R.id.space1);
        space1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(hight-stateHight)/2));
        llDown=findViewById(R.id.llDown);
        llDown.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,hight-stateHight-40-(hight-stateHight)/2));
    }

    //欢迎界面
    public void welcome(){
        SharedPreferences sp= getSharedPreferences("set", 0);
        Boolean isWelcome = sp.getBoolean("iswelcome", true);
        iv1=findViewById(R.id.iv1);
        iv2=findViewById(R.id.iv2);
        if(isWelcome){
            vpWelcome=findViewById(R.id.vpWelcome);
            llWelcome=findViewById(R.id.llWelcome);
            rlWelcome=findViewById(R.id.rlWelcome);
            LayoutInflater inflater=getLayoutInflater().from(this);
            views.add(inflater.inflate(R.layout.viewpager_1,null));
            views.add(inflater.inflate(R.layout.viewpager_2,null));
            Log.d("new",views.size()+"");
            views.get(1).findViewById(R.id.btWelcome).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rlWelcome.setVisibility(View.GONE);
                    SharedPreferences sp= getSharedPreferences("set", 0);
                    SharedPreferences.Editor editor1=sp.edit();
                    editor1.putBoolean("iswelcome",false);
                    editor1.commit();
                }
            });
            vpWelcome.setAdapter(new ViewPagerAdapter(views));
            vpWelcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageSelected(int position) {
                        if(position==0){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                iv1.setBackgroundColor(Color.RED);
                                iv2.setBackgroundColor(Color.parseColor("#dfdedf"));
                            }
                        }else {
                            iv2.setBackgroundColor(Color.RED);
                            iv1.setBackgroundColor(Color.parseColor("#dfdedf"));
                        }
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }else{
            iv1.setVisibility(View.GONE);
            iv2.setVisibility(View.GONE);
        }
    }

    //动态获取权限
    public void showContacts(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE},
                    BAIDU_READ_PHONE_STATE);
        }
    }
    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    SharedPreferences sp= getSharedPreferences("set", 0);
                    SharedPreferences.Editor editor1=sp.edit();
                    editor1.putBoolean("isLacate",true);
                    editor1.commit();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    public void initData(){
        String fengxiang,type,fengli;
         Calendar date = Calendar.getInstance();
         date.setTime(strToDate(data.get(0).get("updatetime")+""));
         Calendar end = Calendar.getInstance();
         end.setTime(strToDate("18:30"));
        if (date.before(end)) {
            fengxiang=data.get(0).get("fengxiang_1")+"";
            type=data.get(0).get("type_0")+"";
            fengli=data.get(0).get("fengli_1")+"";
        }else{
            fengxiang=data.get(0).get("fengxiang_2")+"";
            type=data.get(0).get("type_1")+"";
            fengli=data.get(0).get("fengli_2")+"";
        }

        tvWundu=findViewById(R.id.tvWendu);
        tvFeng=findViewById(R.id.tvFeng);
        tvHint=findViewById(R.id.tvHint);
        tvWeather0=findViewById(R.id.tvWeather0);
        tvWundu0=findViewById(R.id.tvWundu0);
        tvTomorrow=findViewById(R.id.tvTomorrow);
        tvWeather1=findViewById(R.id.tvWeather1);
        tvWundu1=findViewById(R.id.tvWudu1);
        tvTitle=findViewById(R.id.tvTitle);
        tvWundu.setText(data.get(0).get("wendu")+"°");
        tvFeng.setText(fengxiang+" "+fengli+" 湿度"+data.get(0).get("shidu")+"…");
        tvHint.setText(data.get(0).get("date_1")+" "+data.get(0).get("date_0"));
        tvWeather0.setText(type);
        tvWundu0.setText(data.get(0).get("low_1")+"/"+data.get(0).get("high"));
        tvTomorrow.setText(data.get(1).get("date_0")+"");
        tvWeather1.setText(data.get(1).get("type_0")+"");
        tvWundu1.setText(data.get(1).get("low_1")+"/"+data.get(1).get("high"));
        tvTitle.setText(data.get(0).get("city")+"");

        ivWeather0=findViewById(R.id.ivWeather0);
        ivWeather1=findViewById(R.id.ivWeather1);
        List<ImageView> li=new ArrayList<>();
        li.add(ivWeather0);
        li.add(ivWeather1);
        String[] x=new String[]{type,data.get(1).get("type_0")+""};
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

    private Context getContext(){
        return getApplicationContext();
    }

}
