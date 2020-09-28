package com.example.mlmmusic.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.mlmmusic.R;
import com.example.mlmmusic.util.MySDKConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class JavaKnowledgeActivity extends AppCompatActivity {


    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_knowledge);
        textView = findViewById(R.id.tv_name);

        /*
         * TODO: 2020-08-27  LinkedList  ArrayList
         *
         * LinkedList   集合数据存储的结构是链表结构
         * 1.该类实现了List接口，允许有null（空）元素。主要用于创建链表数据结构，该类没有同步方法，
         * 2.如果多个线程同时访问一个List，则必须自己实现访问同步，解决方法就是在创建List时候构造一个同步的List: List linkedList= Collections.synchronizedList(new LinkedList<>());
         * 3.底层数据结构是链表，查询慢，增删快
         *
         * ArrayList  集合数据存储的结构是数组结构
         * 1.该类也是实现了List的接口，实现了可变大小的数组，随机访问和遍历元素时，提供更好的性能。
         * 2.该类也是非同步的,在多线程的情况下不要使用。ArrayList 增长当前长度的50%，插入删除效率低。
         * 3.底层数据结构是数组，查询快，增删慢
         *
         * */
//        list();


        // TODO: 2020-08-27   HashSet  LinkedHashSet   TreeSet
        /*
         * set:它里面的集合，所存储的元素就是不重复的。
         *
         * HashSet ： 采用哈希表结构存储数据，保证元素唯一性的方式依赖于：hashCode()与equals()方法
         * 该类实现了Set接口，不允许出现重复元素，不保证集合中元素的顺序，允许包含值为null的元素，但最多只能一个
         * 元素唯一，不能重复  底层结构是 哈希表结构   速度最快的
         *
         * LinkedHashSet：
         * 具有可预知迭代顺序的 Set 接口的哈希表和链接列表实现
         * 元素唯一不能重复   底层结构是 哈希表结构 + 链表结构元素的存与取的顺序一致
         *
         * TreeSet
         * 采用树结构实现(红黑树算法)
         * 该类实现了Set接口，可以实现排序等功能
         *
         * */
        set();

        // TODO: 2020-08-27   HashMap   TreeMap   LinkedHashMap
        /*
         * HashMap :
         * HashMap 是一个散列表，它存储的内容是键值对(key-value)映射
         * 该类实现了Map接口，根据键的HashCode值存储数据 ，具有很快的访问速度，最多允许一条记录的键为null，不支持线程同步
         *
         * TreeMap
         * 继承了AbstractMap，并且使用一颗树。
         *
         * LinkedHashMap
         * 继承于HashMap，使用元素的自然顺序对元素进行排序.
         *
         * */
        map();

        int answ = factorial(4, 1);
        System.out.println("factorial方法：" + answ);


        MySDKConfig.getConfig().setDebug(true).setTimeout(2).set();


        handlerKnowledge();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
//            mTextView.setText(""+msg.arg1+"-"+msg.arg2);
            int obj = (int) msg.obj;
            switch (obj) {
                case 0:
                    textView.setText(msg.arg1+"");
                    System.out.println(msg.arg1);
                    break;
                case 1:
                    textView.setText(msg.arg1+"");
                    System.out.println(msg.arg1);
                    break;
            }

        }

        ;
    };


    private void handlerKnowledge() {

        new Thread() {
            @Override
            public void run() {
                try {
                    Looper.loop();
                    Thread.sleep(2000);
                    Message message = new Message();
                    message.obj = 0;
                    message.arg1 = 88;
                    mHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1999);
                    Message message = new Message();
                    message.obj = 1;
                    message.arg1 = 100;
                    mHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    private int na = 1;

    private int factorial(int i, int ans) {
        for (int a = i; a > 1; a--) {

        }
        return na;
    }


    private void map() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");
        map.put("2", "value4");


        //第一种：普遍使用，二次取值
        System.out.println("通过Map.keySet遍历key和value：");
        for (String key : map.keySet()) {
            System.out.println("key= " + key + " and value= " + map.get(key));
        }

        //第二种
        System.out.println("通过Map.entrySet使用iterator遍历key和value：");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        //第三种：推荐，尤其是容量大时
        System.out.println("通过Map.entrySet遍历key和value");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        //第四种
        System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
        for (String v : map.values()) {
            System.out.println("value= " + v);
        }


    }

    private void set() {
        HashSet<String> hashset = new HashSet<String>();
        hashset.add("1");
        hashset.add("2");
        hashset.add("3");
        hashset.add("4");
        hashset.add("5");
        hashset.add("6");
        hashset.add("7");
        hashset.add("8");
        Iterator<String> iterator = hashset.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
//            System.out.println(next);
        }

        TreeSet<String> integers = new TreeSet<>();
        integers.add("a");
        integers.add("b");
        integers.add("d");
        integers.add("c");
        Iterator<String> iterator1 = integers.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());

        }


    }


    private void list() {


        List<String> linkedList = new LinkedList<>();
        linkedList.add("1");
        linkedList.add("2");
        linkedList.set(0, "3");  //替换指标为0的元素
        for (int i = 0; i < linkedList.size(); i++) {
            String string = linkedList.get(i);
            System.out.println(string);
        }
        //采用迭代器的方法，该方法可以不用担心在遍历的过程中会超出集合的长度
        Iterator<String> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        List arrayList = new ArrayList();


        List<String> list = new ArrayList<String>();

//给集合中添加元素

        list.add("abc1");

        list.add("abc2");

        list.add("abc3");

        list.add("abc4");

//迭代集合，当有元素为"abc2"时，集合加入新元素"itcast"

        Iterator<String> it = list.iterator();

        while (it.hasNext()) {

            String str = it.next();

//判断取出的元素是否是"abc2"，是就添加一个新元素

            if ("abc2".equals(str)) {
//                list.add("itcast");// 该操作会导致程序出错
            }

        }

//打印容器中的元素

        System.out.println(list);

    }


}
