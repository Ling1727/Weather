package com.example.weather;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2019/3/11.
 */

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager vpWelcome;
    private List<View> views=new ArrayList<View>();
    private View iv1,iv2;
    private RelativeLayout rlWelcome;
    private LinearLayout llWelcome;
    private static final int BAIDU_READ_PHONE_STATE =100;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //获取权限
        showContacts();
        welcome();
    }


    //欢迎界面
    public void welcome() {
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);

            vpWelcome = findViewById(R.id.vpWelcome);
            llWelcome = findViewById(R.id.llWelcome);
            rlWelcome = findViewById(R.id.rlWelcome);
            LayoutInflater inflater = getLayoutInflater().from(this);
            views.add(inflater.inflate(R.layout.viewpager_1, null));
            views.add(inflater.inflate(R.layout.viewpager_2, null));
            Log.d("new", views.size() + "");
            views.get(1).findViewById(R.id.btWelcome).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = getSharedPreferences("set", 0);
                    SharedPreferences.Editor editor1 = sp.edit();
                    editor1.putBoolean("iswelcome", false);
                    editor1.commit();
                    finish();
                }
            });
            vpWelcome.setAdapter(new ViewPagerAdapter(views));
            vpWelcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            iv1.setBackgroundColor(Color.RED);
                            iv2.setBackgroundColor(Color.parseColor("#dfdedf"));
                        }
                    } else {
                        iv2.setBackgroundColor(Color.RED);
                        iv1.setBackgroundColor(Color.parseColor("#dfdedf"));
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
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
            ActivityCompat.requestPermissions(WelcomeActivity.this,
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

}
