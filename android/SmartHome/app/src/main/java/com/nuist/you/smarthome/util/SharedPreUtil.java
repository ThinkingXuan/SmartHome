package com.nuist.you.smarthome.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.nuist.you.smarthome.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by youxuan on 2017/3/30 0030.
 */

public class SharedPreUtil {

    public static final String PREF_NAME = "UserInfo";
    public static final String TEMPLET_NAME = "Template";

    public static String getString(Context mContext, String key, String defaultValue) {
        //将数据存进sharepreferences

        SharedPreferences sp = mContext.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);//创建一个名字叫config的(key value)键值对 的轻量数据库
        return sp.getString(key, defaultValue);//调用这个方法去取出里面的键值对
    }

    public static String getString(String key, String defaultValue) {
        //将数据存进sharepreferences
        Context mContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sp = mContext.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);//创建一个名字叫config的(key value)键值对 的轻量数据库
        return sp.getString(key, defaultValue);//调用这个方法去取出里面的键值对
    }


    public static void setString(Context mContext, String key,
                                 String value) {
        // TODO Auto-generated method stub
        SharedPreferences sp = mContext.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();//将配置文件进行编辑

    }


    public static void setString(String key, String value) {

        Context mContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sp = mContext.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();//将配置文件进行编辑

    }

    public static int getInt(String key, int defaultValue) {

        Context mContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sp = mContext.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void setInt(String key, int value) {
        Context mContext = MyApplication.getInstance().getApplicationContext();
        SharedPreferences sp = mContext.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }




    public static void removeString(Context mContext, String key
    ) {
        // TODO Auto-generated method stub
        if (key != null && key.length() > 0) {
            SharedPreferences sp = mContext.getSharedPreferences(PREF_NAME,
                    Context.MODE_PRIVATE);
            sp.edit().remove(key).apply();//移除掉配置文件
        } else {
            Log.i("TAG", "delete sharepreference key value error");
        }
    }

//    public static String getUserId(Context context) {
//        return getString(context, Constants.USERID, "");
//    }
//
//    public static String getUserHeadImageUrl(Context context){
//        return getString(context,Constants.HEAD_URL,"");
//    }
//
//    TemplateGroup person = null;
    static long startTime = 0l;
    static long endTime = 0l;

    /**
     * 序列化对象
     *
     * @param obj
     * @return
     * @throws IOException
     */
    private static String serialize(Object obj) throws IOException {
        startTime = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Log.d("serial", "serialize str =" + serStr);
        endTime = System.currentTimeMillis();
        Log.d("serial", "序列化耗时为:" + (endTime - startTime));
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Object deSerialization(String str) throws IOException,
            ClassNotFoundException {


        startTime = System.currentTimeMillis();
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Object person = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        endTime = System.currentTimeMillis();
        Log.d("serial", "反序列化耗时为:" + (endTime - startTime));
        return person;
    }

    public static void saveObject(Context mContext, String key, Object value) {

        try {

            SharedPreferences sp = mContext.getSharedPreferences(TEMPLET_NAME, 0);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(key, serialize(value));
            edit.apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getObject(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(TEMPLET_NAME, 0);
        try {
            return deSerialization(sp.getString(key, null));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }


//    public static void putNevigationItem(Context context, int t) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(context.getString(R.string.nevigation_item), t);
//        editor.commit();
//    }
//
//    public static int getNevigationItem(Context context) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        return sharedPreferences.getInt(context.getResources().getString(R.string.nevigation_item), -1);
//
//    }
}
