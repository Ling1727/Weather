package useless;

import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2019/2/16.
 */

public class LoadingData {
    private boolean isFinish=false;
    private String xmlData;
    private Map map0,map1,map2,map3,map4;
    private List<Map> list=new ArrayList<>();


    //从网上获取天气数据
    public void getWeatherDateForNet(String city){
        final String website="http://wthrcdn.etouch.cn/WeatherApi?citykey="+city;
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(website).method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        final Call call = okHttpClient.newCall(request);
        //4.同步调用会阻塞主线程,这边在子线程进行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步调用,返回Response,会抛出IO异常
                    Response response = call.execute();
                    xmlData=response.body().string();
                    Log.d("new",xmlData);
                    parseXML(xmlData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(website);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuffer sb = new StringBuffer();
                    String str;
                    while ((str = reader.readLine()) != null) {
                        sb.append(str);
                    }
                    xmlData= sb.toString();
                    parseXML(xmlData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

    //解析xml
    private void parseXML(String xmlData)
    {
        int fengliCount = 0;
        int fengxiangCount = 0;
        int dateCount = 0;
        int highCount = 0;
        int lowCount = 0;
        int typeCount = 0;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));

            int eventType = xmlPullParser.getEventType();

            while(eventType!=xmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    //文档开始位置
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    //标签元素开始位置
                    case XmlPullParser.START_TAG:
                        if(xmlPullParser.getName().equals("resp"))
                        {
                            map0=new HashMap();
                            map1=new HashMap();
                            map2=new HashMap();
                            map3=new HashMap();
                            map4=new HashMap();
                            list.add(map0);
                            list.add(map1);
                            list.add(map2);
                            list.add(map3);
                            list.add(map4);
                        }
                            if (xmlPullParser.getName().equals("city")) {
                                eventType = xmlPullParser.next();
                                map0.put("city",xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("updatetime")) {
                                eventType = xmlPullParser.next();
                                map0.put("updatetime",xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {
                                eventType = xmlPullParser.next();
                                map0.put("wendu",xmlPullParser.getText());
                                Log.d("test",xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("shidu")) {
                                eventType = xmlPullParser.next();
                                map0.put("shidu",xmlPullParser.getText());
                            } else if(xmlPullParser.getName().equals("sunrise_1")){
                                eventType=xmlPullParser.next();
                                map0.put("sunrise",xmlPullParser.getText());
                            } else if(xmlPullParser.getName().equals("sunset_1")){
                                eventType=xmlPullParser.next();
                                map0.put("sunset",xmlPullParser.getText());
                            }
                            //
                            else if (xmlPullParser.getName().equals("fengli") ) {
                                eventType = xmlPullParser.next();
                                if(fengliCount<3){
                                    list.get(0).put("fengli_"+fengliCount,xmlPullParser.getText());
                                }else if(3==fengliCount||fengliCount==4){
                                    list.get(1).put("fengli_"+(fengliCount-3),xmlPullParser.getText());
                                }else if(5==fengliCount||fengliCount==6){
                                    list.get(2).put("fengli_"+(fengliCount-5),xmlPullParser.getText());
                                } else if(7==fengliCount||fengliCount==8){
                                    list.get(3).put("fengli_"+(fengliCount-7),xmlPullParser.getText());
                                }else if(9==fengliCount||fengliCount==10){
                                    list.get(4).put("fengli_"+(fengliCount-9),xmlPullParser.getText());
                                }
                                fengliCount++;
                            }  else if (xmlPullParser.getName().equals("fengxiang") ) {
                                eventType = xmlPullParser.next();
                                if(fengxiangCount<3){
                                    list.get(0).put("fengxiang_"+fengxiangCount,xmlPullParser.getText());
                                }else if(3==fengxiangCount||fengxiangCount==4){
                                    list.get(1).put("fengxiang_"+(fengxiangCount-3),xmlPullParser.getText());
                                }else if(5==fengxiangCount||fengxiangCount==6){
                                    list.get(2).put("fengxiang_"+(fengxiangCount-5),xmlPullParser.getText());
                                } else if(7==fengxiangCount||fengxiangCount==8){
                                    list.get(3).put("fengxiang_"+(fengxiangCount-7),xmlPullParser.getText());
                                }else if(9==fengxiangCount||fengxiangCount==10){
                                    list.get(4).put("fengxiang_"+(fengxiangCount-9),xmlPullParser.getText());
                                }
                                fengxiangCount++;
                            }  else if (xmlPullParser.getName().equals("date") ) {
                                eventType = xmlPullParser.next();
                                int x=xmlPullParser.getText().indexOf("星");
                                String y1=xmlPullParser.getText().substring(x);
                                list.get(dateCount).put("date_0",y1);
                                String y=xmlPullParser.getText().substring(0,x);
                                list.get(dateCount).put("date_1",y);
                                dateCount++;
                            }else if (xmlPullParser.getName().equals("high") ) {
                                eventType = xmlPullParser.next();
                                String str = xmlPullParser.getText();
                                String removeStr = "高温 ";
                                list.get(highCount).put("high",str.replace(removeStr, ""));
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") ) {
                                eventType = xmlPullParser.next();
                                String str = xmlPullParser.getText();
                                String removeStr = "低温 ";
                                list.get(lowCount).put("low_1",str.replace(removeStr, ""));
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") ) {
                                eventType = xmlPullParser.next();
                                if(typeCount<2){
                                    list.get(0).put("type_"+typeCount,xmlPullParser.getText());
                                }else if(2==typeCount||typeCount==3){
                                    list.get(1).put("type_"+(typeCount-2),xmlPullParser.getText());
                                }else if(5==typeCount||typeCount==4){
                                    list.get(2).put("type_"+(typeCount-4),xmlPullParser.getText());
                                } else if(7==typeCount||typeCount==6){
                                    list.get(3).put("type_"+(typeCount-6),xmlPullParser.getText());
                                }else if(9==typeCount||typeCount==8){
                                    list.get(4).put("type_"+(typeCount-8),xmlPullParser.getText());
                                }
                                typeCount++;
                            }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType=xmlPullParser.next();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        isFinish=true;
    }

    public List<Map> getList() {
        return list;
    }
    public void setList(List<Map> list) {
        this.list = list;
    }

    public boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }
}
