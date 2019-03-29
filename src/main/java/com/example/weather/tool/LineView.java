package com.example.weather.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.weather.litepal.Daily;
import org.litepal.LitePal;
import java.util.List;

/**
 * Created by hasee on 2019/3/27.
 */

public class LineView extends View {
    private static final int TYPE_UP=0;
    private static final int TYPE_DOWN=1;
    private Paint paint,paint1,paintText;
    private int[] temp=new int[7];
    private float[] temperature=new float[7];
    private String[] tempString=new String[7];
    private String city;
    private List<Daily> dailyList;
    private int width,hight;
    private int min,max;
    private PointF[] pointF=new PointF[7];
    private Path path;
    private int type;

    public LineView(Context context,String city,int width,int hight,int type) {
        super(context);
        this.city=city;
        this.width=width;
        this.hight=hight;
        this.type=type;
        initData();
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void initData(){
        paint=new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint1=new Paint();
        paint1.setColor(Color.YELLOW);
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.FILL);
        paintText=new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setAntiAlias(true);
        paintText.setTextSize(30);
        path=new Path();

        dailyList=LitePal.where("city=?",city).find(Daily.class);
        if(type==TYPE_UP){
            for(int i=0;i<dailyList.size();i++){
                temp[i]=Integer.parseInt(dailyList.get(i).getDay_temphigh());
                temperature[i]=Float.parseFloat(dailyList.get(i).getDay_temphigh());
                tempString[i]=temp[i]+"";
            }
        }else if(type==TYPE_DOWN){
            paint.setColor(Color.parseColor("#1E90FF"));
            paint1.setColor(Color.parseColor("#1E90FF"));
            for(int i=0;i<dailyList.size();i++){
                temp[i]=Integer.parseInt(dailyList.get(i).getNight_templow());
                temperature[i]=Float.parseFloat(dailyList.get(i).getNight_templow());
                tempString[i]=temp[i]+"";
            }
        }

        for(int a=0;a<temp.length;a++){
            for(int b=0;b<temp.length-a-1;b++){
                if(temp[b]>temp[b+1]){
                    int t=temp[b];
                    temp[b]=temp[b+1];
                    temp[b+1]=t;
                }
            }
        }
        min=temp[0];
        max=temp[temp.length-1];
        if(type==TYPE_UP){
            for(int c=0;c<temperature.length;c++){
                pointF[c]=new PointF((float) (width/7*(c+0.5)),(3*hight/7+(4*hight/7*(1-(temperature[c]-min)/(max-min))))-15);
            }
        }else if(type==TYPE_DOWN){
            for(int c=0;c<temperature.length;c++){
                pointF[c]=new PointF((float) (width/7*(c+0.5)),((4*hight/7*(1-(temperature[c]-min)/(max-min))))+15);
            }
        }
        path.moveTo(pointF[0].x,pointF[0].y);
        for(int d=1;d<7;d++){
            path.lineTo(pointF[d].x,pointF[d].y);
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
        for(int i=0;i<pointF.length;i++){
            canvas.drawCircle(pointF[i].x,pointF[i].y,10,paint1);
        }
        if(type==TYPE_UP){
            for(int i=0;i<pointF.length;i++){
                canvas.drawText(tempString[i]+"°",pointF[i].x-15,pointF[i].y-hight/6,paintText);
            }
        }else if(type==TYPE_DOWN){
            for(int i=0;i<pointF.length;i++){
                canvas.drawText(tempString[i]+"°",pointF[i].x-15,pointF[i].y+hight/6+15,paintText);
            }
        }

    }
}
