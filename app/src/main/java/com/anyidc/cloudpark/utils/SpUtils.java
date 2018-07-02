package com.anyidc.cloudpark.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.anyidc.cloudpark.BaseApplication;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by Administrator on 2018/2/1.
 */

public class SpUtils {

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = BaseApplication.appContext.getPackageName() + ".preference";
    public static final String VERSION_CODE = "versionCode";
    public static final String VERSION_NAME = "versionName";
    public static final String TIME = "time";
    public static final String DID = "did";
    public static final String SEARCHLIST = "searchlist";
    public static final String USERINFO = "userinfo";
    public static final String MYLATITUDE = "latitude";
    public static final String MYLONGITUDE = "longitude";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public static void set(String key, Object object) {
        SharedPreferences sp = BaseApplication.appContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * 保存Model（Model里面可以嵌套Model与List）
     * 说明：建议在线程中调用此函数
     *
     * @param key
     * @param object
     * @return
     */
    public static void setObject(String key, Object object) {
        String jsonValue = "";
        if (object != null) {
            Gson gson = new Gson();
            jsonValue = gson.toJson(object);
        }
        SharedPreferences sp = BaseApplication.appContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, jsonValue);
        editor.apply();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = BaseApplication.appContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 通过key获取Model
     * 说明：建议在线程中调用此函数
     *
     * @param key
     * @param clazz
     * @return
     */
    public static <E> E getObject(String key, Class<E> clazz) {
        SharedPreferences sp = BaseApplication.appContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        String jsonValue = sp.getString(key, null);
        if (!TextUtils.isEmpty(jsonValue)) {
            Gson gson = new Gson();
            E value = gson.fromJson(jsonValue, clazz);
            return value;
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = BaseApplication.appContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = BaseApplication.appContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = BaseApplication.appContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = BaseApplication.appContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }
}
