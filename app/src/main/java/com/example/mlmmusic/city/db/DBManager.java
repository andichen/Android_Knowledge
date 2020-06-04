package com.example.mlmmusic.city.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.example.mlmmusic.city.model.City;
import com.example.mlmmusic.city.model.Province;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.mlmmusic.city.db.DBConfig.COLUMN_C_CODE;
import static com.example.mlmmusic.city.db.DBConfig.COLUMN_C_NAME;
import static com.example.mlmmusic.city.db.DBConfig.COLUMN_C_PINYIN;
import static com.example.mlmmusic.city.db.DBConfig.COLUMN_C_PROVINCE;
import static com.example.mlmmusic.city.db.DBConfig.COLUMN_P_PINYIN;
import static com.example.mlmmusic.city.db.DBConfig.DB_NAME_V1;
import static com.example.mlmmusic.city.db.DBConfig.LATEST_DB_NAME;
import static com.example.mlmmusic.city.db.DBConfig.TABLE_NAME;


/**
 * Author clf on 2019/1/26.
 */
public class DBManager {
    private static final int BUFFER_SIZE = 1024;

    private String DB_PATH;
    private Context mContext;

    public DBManager(Context context) {
        this.mContext = context;
        DB_PATH = File.separator + "data"
                + Environment.getDataDirectory().getAbsolutePath() + File.separator
                + context.getPackageName() + File.separator + "databases" + File.separator;
        copyDBFile();
    }

    private void copyDBFile(){
        File dir = new File(DB_PATH);
        if (!dir.exists()){
            dir.mkdirs();
        }
        //如果旧版数据库存在，则删除
        File dbV1 = new File(DB_PATH + DB_NAME_V1);
        if (dbV1.exists()){
            dbV1.delete();
        }
        //创建新版本数据库
        File dbFile = new File(DB_PATH + LATEST_DB_NAME);
        if (!dbFile.exists()){
            InputStream is;
            OutputStream os;
            try {
                is = mContext.getResources().getAssets().open(LATEST_DB_NAME);
                os = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = is.read(buffer, 0, buffer.length)) > 0){
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    SQLiteDatabase db;
   public SQLiteDatabase getDB(){
        if(db==null || !db .isOpen()){
           db= SQLiteDatabase.openOrCreateDatabase(DB_PATH + LATEST_DB_NAME, null);
        }
        return db;
    }
    public void CloseDB(){
        if(db!=null){
           db.close();
        }
    }
    public List<City> getAllCities(){
        getDB();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        List<City> result = new ArrayList<>();
        City city;
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_C_NAME));
            String province = cursor.getString(cursor.getColumnIndex(COLUMN_C_PROVINCE));
            String pinyin = cursor.getString(cursor.getColumnIndex(COLUMN_C_PINYIN));
            String code = cursor.getString(cursor.getColumnIndex(COLUMN_C_CODE));
            city = new City(name, province, pinyin, code);
            result.add(city);
        }
        cursor.close();
        Collections.sort(result, new CityComparator());
        return result;
    }
    public List<City> getAllProvince(){

        getDB();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" cities group by c_province" , null);
        List<City> result = new ArrayList<>();
        City city;
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_C_NAME));
            String province = cursor.getString(cursor.getColumnIndex(COLUMN_C_PROVINCE));
            String pinyin = cursor.getString(cursor.getColumnIndex(COLUMN_C_PINYIN));
            String p_pinyin = cursor.getString(cursor.getColumnIndex(COLUMN_P_PINYIN));
            String code = cursor.getString(cursor.getColumnIndex(COLUMN_C_CODE));
            city = new Province(province,code,p_pinyin);
            result.add(city);
        }
        cursor.close();
        Log.i("DBManager","getAllProvince size:"+result.size());
        Collections.sort(result, new ProvinceComparator());
        return result;
    }
    public List<City> getCityByProvinceName(String p){
        List<City> result = new ArrayList<>();
        if(TextUtils.isEmpty(p))  return  result;
        getDB();
        String sql="select * from "+TABLE_NAME+" where c_province = ?";
        Cursor cursor = db.rawQuery( sql, new String[]{p});
        City city;
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_C_NAME));
            String province = cursor.getString(cursor.getColumnIndex(COLUMN_C_PROVINCE));
            String pinyin = cursor.getString(cursor.getColumnIndex(COLUMN_C_PINYIN));
            String code = cursor.getString(cursor.getColumnIndex(COLUMN_C_CODE));
            city = new City(name, province, pinyin, code);
            result.add(city);
        }
        cursor.close();
        Collections.sort(result, new CityComparator());
        return result;
    }
    public List<City> searchCity(final String keyword){
        String sql = "select * from " + TABLE_NAME + " where "
                + COLUMN_C_NAME + " like ? " + "or "
                + COLUMN_C_PINYIN + " like ? ";
         getDB();
        Cursor cursor = db.rawQuery(sql, new String[]{"%"+keyword+"%", keyword+"%"});

        List<City> result = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_C_NAME));
            String province = cursor.getString(cursor.getColumnIndex(COLUMN_C_PROVINCE));
            String pinyin = cursor.getString(cursor.getColumnIndex(COLUMN_C_PINYIN));
            String code = cursor.getString(cursor.getColumnIndex(COLUMN_C_CODE));
            City city = new City(name, province, pinyin, code);
            result.add(city);
        }
        cursor.close();
        CityComparator comparator = new CityComparator();
        Collections.sort(result, comparator);
        return result;
    }

    /**
     * sort by a-z
     */
    private class CityComparator implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            return a.compareTo(b);
        }
    }
    private class ProvinceComparator implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            try {
            Province p_lhs= (Province) lhs;
            Province p_rhs= (Province) rhs;
            String a = p_lhs.getP_pinyin().substring(0, 1);
            String b = p_rhs.getP_pinyin().substring(0, 1);
            return a.compareTo(b);
            }
            catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
    }

}
