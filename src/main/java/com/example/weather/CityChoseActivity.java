package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2019/3/12.
 */

public class CityChoseActivity extends AppCompatActivity {
    List<HotCity> hotCities = new ArrayList<>();
    Handler handler;
    CityPicker cityPicker;
    Locate locate;
    private String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_city);
        initdata();
    }

    public void initdata(){
        hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        if(locate.getRun()){
                            cityPicker.locateComplete(new LocatedCity(locate.getCityName(),"广东","101280601"),LocateState.SUCCESS);
                            handler.removeMessages(0);
                        }else{
                            handler.sendEmptyMessageDelayed(0,100);
                        }
                        break;
                    case 1:
                        break;
                }
            }
        };
        cityPicker=CityPicker.from(CityChoseActivity.this) ;
        //CityPicker.from(CityChoseActivity.this) //activity或者fragment
        cityPicker.enableAnimation(true)	//启用动画效果，默认无
                //.setAnimationStyle(anim)	//自定义动画
                .setLocatedCity(null)  //APP自身已定位的城市，传null会自动定位（默认）//new LocatedCity("杭州", "浙江", "101210101")
                .setHotCities(hotCities)	//指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Toast.makeText(getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();
                        city=data.getName();
                        //数据是使用Intent返回
                        Intent intent = new Intent();
                        //把返回数据存入Intent
                        intent.putExtra("result", city);
                        //设置返回数据
                        CityChoseActivity.this.setResult(001, intent);
                        //关闭Activity
                        CityChoseActivity.this.finish();
                    }
                    @Override
                    public void onCancel(){
                        Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                        city=null;
                        //数据是使用Intent返回
                        Intent intent = new Intent();
                        //把返回数据存入Intent
                        intent.putExtra("result", city);
                        //设置返回数据
                        CityChoseActivity.this.setResult(001, intent);
                        //关闭Activity
                        CityChoseActivity.this.finish();
                    }

                    @Override
                    public void onLocate() {
                        locate=new Locate(CityChoseActivity.this);
                        locate.runTask();
                        handler.sendEmptyMessage(0);
                        /*//定位接口，需要APP自身实现，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.getInstance()
                                        .locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                            }
                        }, 3000);*/
                    }
                })
                .show();
    }

}
