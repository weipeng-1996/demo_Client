package com.example.http_test;


import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UserService {
    public static boolean signIn(String name, String password) {
        MyThread myThread = new MyThread("http://192.168.3.6:8080/mybatis/signIn",name,password);
//        MyThread myThread = new MyThread("https://www.baidu.com",name,password);
        try
        {
//            Log.d("wp","hahahaahahah");
            myThread.start();
            myThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        return myThread.getResult();
//        return false;
    }

    public static boolean signUp(String name, String password) {
        MyThread myThread = new MyThread("http://192.168.3.6:8080/mybatis/signUp",name,password);
        try
        {
            myThread.start();
            myThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return myThread.getResult();
//        return false;
    }
}

class MyThread extends Thread
{
    private String path;
    private String name;
    private String password;
    private boolean res = false;
    private String result;
    public MyThread(String path,String name,String password)
    {
        this.path = path;
        this.name = name;
        this.password = password;
    }
    @Override
    public void run()
    {
        try {

            path += "?username=" + name + "&password=" + password;
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setConnectTimeout(8000);//设置连接超时时间
//            httpURLConnection.setReadTimeout(8000);//设置读取超时时间
            httpURLConnection.setRequestMethod("GET");//设置请求方法,post
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                result = "";
                while((line = in.readLine())!=null){
                    result += line;
                }
                in.close();
            }

            Log.d("wp",result);
            JSONObject object = new JSONObject(result);
//            System.out.println(!object.getJSONArray("data").isNull(0));
            res = !(object.getString("data").equals("null"));
            String temp = object.getString("data");
            System.out.println(object.getString("data"));
            System.out.println("res:"+res);
            System.out.println(temp.equals("null"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getResult()
    {
        return res;
    }
}
class SignUpThread extends Thread
{
    private String path;
    private String name;
    private String password;
    private boolean res = false;
    private String result;
    public SignUpThread(String path,String name,String password)
    {
        this.path = path;
        this.name = name;
        this.password = password;
    }
    @Override
    public void run()
    {
        try {

            path += "?username=" + name + "&password=" + password;
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setConnectTimeout(8000);//设置连接超时时间
//            httpURLConnection.setReadTimeout(8000);//设置读取超时时间
            httpURLConnection.setRequestMethod("GET");//设置请求方法,post
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                result = "";
                while((line = in.readLine())!=null){
                    result += line;
                }
                in.close();
            }

            Log.d("wp",result);
            JSONObject object = new JSONObject(result);
            System.out.println(!object.getJSONArray("data").isNull(0));
            res = !object.getJSONArray("data").isNull(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getResult()
    {
        return res;
    }
}