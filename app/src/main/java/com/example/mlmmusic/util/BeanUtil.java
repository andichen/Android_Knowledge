package com.example.mlmmusic.util;


import android.content.Context;

import com.example.mlmmusic.log.MyLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by ywl on 2016/1/30.
 */
public class BeanUtil {
    public static String bean2string(Object obj) {
        String rtn_str = "<========="+ obj.getClass().getSimpleName() +"=========>\n";

        try {
            Class<?> obj_class = obj.getClass();

            Method[] methods = obj_class.getDeclaredMethods();

            for (Method method : methods) {
                String method_name = method.getName();
                if (method_name.startsWith("get") && !method_name.contains("getClass")) {
                    Object method_resul = method.invoke(obj);
                    rtn_str += method_name.substring(3).toLowerCase(Locale.CHINA) + " : " + method_resul + "\n";
                }
            }

            rtn_str += "<========================>\n";
        } catch (Exception e) {
            MyLog.e(e.getMessage(), e);
        }

        return rtn_str;
    }

    /**
     * 对象copy只拷贝非空属性
     *
     * @param from
     * @param to
     */
    public static void copyBeanWithOutNull(Object from, Object to) {
        Class<?> beanClass = from.getClass();
        Field[] fields = beanClass.getFields();
        for (Field field:fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(from);
                if (value != null) {
                    field.set(to, value);
                }
            } catch (Exception e) {
            }
        }
    }
    /**
     * 对象copy
     *
     * @param from
     * @param to
     */
    public static void copyBean(Object from, Object to) {
        Class<?> beanClass = from.getClass();
        Field[] fields = beanClass.getFields();
        for (Field field:fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(from);
                field.set(to, value);
            } catch (Exception e) {
            }
        }
    }




    public static Field getDeclaredField(@SuppressWarnings("rawtypes") Class clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取属性
     *
     * @param o
     * @param field
     * @return
     */
    public static Object getProperty(Object o, String field) {
        try {
            Field f = o.getClass().getDeclaredField(field);
            return f.get(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加屬性
     *
     * @param o
     * @param field
     * @param value
     */
    public static void setProperty(Object o, String field, Object value) {
        try {
            Field f = o.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(o, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTxt(Context context, String fileName){
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName+".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }
}
