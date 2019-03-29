package com.example.weather.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.weather.litepal.Hourly;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2019/3/28.
 */

public class HourlyLineView extends View {
    private int width,height,min,max;
    private String city;
    private Paint paint1,paint2,paint3,paint4,paintText;
    private PointF[] pointFS=new PointF[24];
    private int[] temp=new int[24];
    private float[] tempf=new float[24];
    private List<Hourly> hourlyList=new ArrayList<>();
    private String[] time=new String[24];
    private String[] tempS=new String[24];
    private Path path,path1;

    public HourlyLineView(Context context,int width,int height,String city) {
        super(context);
        this.width=width;
        this.height=height;
        this.city=city;
        initView();
    }

    public HourlyLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HourlyLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView(){
        paint1=new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(4);
        paint1.setColor(Color.YELLOW);

        paint2=new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(Color.YELLOW);
        paint2.setStyle(Paint.Style.FILL);

        paint3=new Paint();
        paint3.setColor(Color.parseColor("#1E90FF"));
        paint3.setAntiAlias(true);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(3);
        PathEffect pathEffect = new DashPathEffect(new float[]{10,10,10,10},5);
        paint3.setPathEffect(pathEffect);

        paint4=new Paint();
        paint4.setColor(Color.parseColor("#1E90FF"));
        paint4.setAntiAlias(true);
        paint4.setStyle(Paint.Style.STROKE);
        paint4.setStrokeWidth(3);

        paintText=new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setAntiAlias(true);
        paintText.setTextSize(30);

        path=new Path();
        path1=new Path();

        hourlyList= LitePal.where("city=?",city).find(Hourly.class);
        for(int a=0;a<hourlyList.size();a++){
            tempS[a]=hourlyList.get(a).getTemp();
            temp[a]=Integer.parseInt(hourlyList.get(a).getTemp());
            tempf[a]=Float.parseFloat(hourlyList.get(a).getTemp());
            time[a]=hourlyList.get(a).getTime();
        }
        for(int i=0;i<temp.length;i++){
            for(int j=0;j<temp.length-i-1;j++){
                if(temp[j]>temp[j+1]){
                    int t=temp[j];
                    temp[j]=temp[j+1];
                    temp[j+1]=t;
                }
            }
        }
        min=temp[0];
        max=temp[temp.length-1];
        for(int b=0;b<tempf.length;b++){
            pointFS[b]=new PointF((2*b+1)*(width/12),(height/6)+(1-(tempf[b]-min)/(max-min))*(5*height/18));
        }

        path.moveTo(pointFS[0].x,pointFS[0].y);
        for(int d=1;d<pointFS.length;d++){
            path.lineTo(pointFS[d].x,pointFS[d].y);
        }
        path1.moveTo(0,5*height/6);
        path1.lineTo(4*width,5*height/6);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(4*width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint1);
        canvas.drawPath(path1,paint3);
        for(int c=0;c<pointFS.length;c++){
            canvas.drawCircle(pointFS[c].x,pointFS[c].y,8,paint2);
            canvas.drawText(tempS[c]+"°",pointFS[c].x-15,pointFS[c].y-height/18,paintText);
            canvas.drawText(time[c],pointFS[c].x-35,17*height/18,paintText);
            canvas.drawLine(pointFS[c].x,5*height/6,pointFS[c].x,31*height/36,paint4);
        }
    }
}
