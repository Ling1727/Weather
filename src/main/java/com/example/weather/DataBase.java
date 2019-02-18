package com.example.weather;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2019/2/17.
 */

public class DataBase {
    public static final String DB_NAME = "city.db"; //数据库名字
    private SQLiteDatabase db;
    private Context context;
    String PackageName="com.example.weather";
    private  List<Map> list=new ArrayList<>();
    Thread thread;
    private Boolean isOver=false;

    public DataBase(Context context){
        this.context=context;
    }

    //在子线程中进行对数据库的打开操作
    public void date(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                openCityDB();
                db = context.openOrCreateDatabase(DB_NAME,context.MODE_PRIVATE,null);
                Cursor cursor =db.query("city", new String[]{"city","number"}, null, null, null, null, null);
                String city,number;
                for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                    Map<String,String> map=new HashMap<>();
                    city= cursor.getString(cursor.getColumnIndex("city"));
                    number=cursor.getString(cursor.getColumnIndex("number"));
                    map.put("city",city);
                    map.put("number",number);
                    list.add(map);
                }
                isOver=true;
                db.close();
            }
        };
        thread=new Thread(runnable);
        thread.start();
    }

    public void openCityDB()
    {
        ///data/data/包名/databases/数据库名
        String path1 = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator +PackageName + File.separator+ "databases"+File.separator;
        String path = "/data"
                + Environment.getDataDirectory().getAbsolutePath()
                + File.separator +PackageName
                + File.separator + "databases"
                + File.separator + DB_NAME;
        File db = new File(path);
        if(db.exists()){
        }else{
            File db1 = new File(path1);
            db1.mkdir();
        }

        try {
            db.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is=null;
        FileOutputStream fos=null;
        try {
            is=context.getAssets().open(DB_NAME);
            fos= new FileOutputStream(db);
            int len = -1;
            byte[] buffer = new byte[1024];
            while((len = is.read(buffer))!=-1)
            {
                fos.write(buffer,0,len);
                fos.flush();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }finally {
            try {
                if(fos!=null){
                    fos.close();
                }
                if(is!=null){
                    is.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public String number(String city){
        for(int i=0;i<list.size();i++){
            if(list.get(i).get("city").equals(city)){
                return list.get(i).get("number")+"";
            }
        }
        return "101010100";
    }

    public Boolean getOver() {
        return isOver;
    }

    public void setOver(Boolean over) {
        isOver = over;
    }

    public List<Map> getList() {
        return list;
    }

    public void setList(List<Map> list) {
        this.list = list;
    }
}
