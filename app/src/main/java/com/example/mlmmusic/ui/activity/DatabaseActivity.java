package com.example.mlmmusic.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;


import com.example.mlmmusic.R;
import com.example.mlmmusic.base.BaseActivity;
import com.example.mlmmusic.city.db.DBManager;
import com.example.mlmmusic.city.model.City;
import com.example.mlmmusic.database.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DatabaseActivity extends BaseActivity {

    private List<City> mAllCities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        ButterKnife.bind(this);
        setTitleTrans(R.color.color_trans);
        setTitleLine(R.color.color_trans);
        setTitle("数据库");
        setTitleColor(R.color.color_222222);
        setBackView();








    }

    @OnClick({R.id.instablish, R.id.upgrade, R.id.insert, R.id.modify, R.id.query, R.id.delete, R.id.delete_database,R.id.btn_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.instablish:
                // 创建SQLiteOpenHelper子类对象
                MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(this,"test_carson");
                //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
                SQLiteDatabase sqliteDatabase = dbHelper.getWritableDatabase();
                // SQLiteDatabase  sqliteDatabase = dbHelper.getReadbleDatabase();

                break;
            case R.id.upgrade:
                // 创建SQLiteOpenHelper子类对象
                MySQLiteOpenHelper dbHelper_upgrade = new MySQLiteOpenHelper(this,"test_carson",2);
                // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                SQLiteDatabase  sqliteDatabase_upgrade = dbHelper_upgrade.getWritableDatabase();
                // SQLiteDatabase  sqliteDatabase = dbHelper.getReadbleDatabase();

                break;
            case R.id.insert:

                MySQLiteOpenHelper dbHelper1 = new MySQLiteOpenHelper(this, "test_carson", 2);
                SQLiteDatabase sqliteDatabase1 = dbHelper1.getWritableDatabase();
                System.out.println("插入数据");
                ContentValues values1 = new ContentValues();
                values1.put("id", 1);
                values1.put("name", "chenlongfei");
                values1.put("sex", "男");
//                values1.put("id", 2);
//                values1.put("name", "chenhao");
                sqliteDatabase1.insert(MySQLiteOpenHelper.My_Table, null, values1);

//                sqliteDatabase1.execSQL("insert into user(id,name) values (2,'陈浩')");

                sqliteDatabase1.close(); //关闭数据库
                break;
            case R.id.modify:
                // 创建一个DatabaseHelper对象
                // 将数据库的版本升级为2
                // 传入版本号为2，大于旧版本（1），所以会调用onUpgrade()升级数据库
                MySQLiteOpenHelper dbHelper2 = new MySQLiteOpenHelper(this,"test_carson", 2);


                // 调用getWritableDatabase()得到一个可写的SQLiteDatabase对象
                SQLiteDatabase sqliteDatabase2 = dbHelper2.getWritableDatabase();

                System.out.println("修改数据");
                // 创建一个ContentValues对象
                ContentValues values2 = new ContentValues();
                values2.put("name", "zhangsan");

                // 调用update方法修改数据库
                sqliteDatabase2.update(MySQLiteOpenHelper.My_Table, values2, "id=?", new String[]{"1"});

                //关闭数据库
                sqliteDatabase2.close();
                break;
            case R.id.query:


                // 创建DatabaseHelper对象
                MySQLiteOpenHelper dbHelper4 = new MySQLiteOpenHelper(this,"test_carson",2);

                // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                SQLiteDatabase sqliteDatabase4 = dbHelper4.getReadableDatabase();

                System.out.println("查询数据");
                // 调用SQLiteDatabase对象的query方法进行查询
                // 返回一个Cursor对象：由数据库查询返回的结果集对象
                Cursor cursor = sqliteDatabase4.query(MySQLiteOpenHelper.My_Table, new String[] { "id",
                        "name","sex" }, "id=?", new String[] { "1" }, null, null, null);

                String id = null;
                String name = null;
                String sex = null;

                //将光标移动到下一行，从而判断该结果集是否还有下一条数据
                //如果有则返回true，没有则返回false
                while (cursor.moveToNext()) {
                    id = cursor.getString(cursor.getColumnIndex("id"));
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    sex = cursor.getString(cursor.getColumnIndex("sex"));
                    //输出查询结果
                    System.out.println("查询到的数据是:"+"id: "+id+"  "+"name: "+name+"  "+"sex: "+sex);

                }
                //关闭数据库
                sqliteDatabase4.close();
                break;
            case R.id.delete:
                // 创建DatabaseHelper对象
                MySQLiteOpenHelper dbHelper3 = new MySQLiteOpenHelper(this,"test_carson",2);

                // 调用getWritableDatabase()方法创建或打开一个可以读的数据库
                SQLiteDatabase sqliteDatabase3 = dbHelper3.getWritableDatabase();
                System.out.println("删除数据");
                //删除数据
                sqliteDatabase3.delete(MySQLiteOpenHelper.My_Table, "id=?", new String[]{"1"});

                //关闭数据库
                sqliteDatabase3.close();

                break;
            case R.id.delete_database:
                MySQLiteOpenHelper dbHelper5 = new MySQLiteOpenHelper(this,
                        "test_carson",2);

                // 调用getReadableDatabase()方法创建或打开一个可以读的数据库
                SQLiteDatabase sqliteDatabase5 = dbHelper5.getReadableDatabase();
                System.out.println("删除数据库");
                //删除名为test.db数据库
                deleteDatabase("test_carson");
                break;
            case R.id.btn_city:

                DBManager dbManager = new DBManager(this);
                mAllCities = dbManager.getAllProvince();

                break;
        }
    }
}
