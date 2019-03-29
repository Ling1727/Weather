package com.example.weather.tool;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.weather.R;

/**
 * Created by hasee on 2019/3/28.
 */

public class HourlyView extends RelativeLayout {
    public HourlyView(Context context,String weather,int imageId,int width,int height) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.hourly_view,HourlyView.this,true);
        TextView textView4=findViewById(R.id.textView4);
        ImageView imageView=findViewById(R.id.imageView4);
        RelativeLayout rlHourly=findViewById(R.id.rlHourly1);
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(width/6,5*height/18);
        rlHourly.setLayoutParams(layoutParams);
        textView4.setText(weather);
        imageView.setImageResource(imageId);
    }

    public HourlyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HourlyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
