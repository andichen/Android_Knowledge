package com.example.mlmmusic.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.example.mlmmusic.R;
import com.example.mlmmusic.base.LiveListBean;
import com.example.mlmmusic.beans.PlaceBean;
import com.example.mlmmusic.beans.Translation;
import com.example.mlmmusic.http.httpentity.HttpResult;
import com.example.mlmmusic.http.serviceapi.ApiService;
import com.example.mlmmusic.http.serviceapi.GetRequest_Interface;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaActivity extends AppCompatActivity {

    private static String TAG = "RxJavaActivity";


    // 设置变量
    // 可重试次数
    private int maxConnectCount = 3;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;

    private Integer i = 0;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        understandRxjava(); //    初识rxjava

        //todo  rxjava各种操作符
        /*
         * 1 create 完整创建1个被观察者对象（Observable）
         * 2 just() 快速的创建被观察者对象  注：最多只能发送10个参数
         * 3 fromArray（）直接发送 传入的数组数据  可发送10个以上参数
         * 4 fromIterable() 直接发送 传入的集合List数据
         * 5 defer（) a定时操作：在经过了x秒后，需要自动执行y操作  b周期性操作：每隔x秒后，需要自动执行y操作
         *            c直到有观察者（Observer ）订阅时，才动态创建被观察者对象（Observable） & 发送事件
         * 6 timer() 本质 = 延迟指定时间后，调用一次 onNext(0)
         * 7 interval() 每隔指定时间 就发送 事件  发送的事件序列 = 从0开始、无限递增1的的整数序列
         * 8 intervalRange() 每隔指定时间 就发送 事件，可指定发送的数据的数量
         * 9 range b. 作用类似于intervalRange（），但区别在于：无延迟发送事件(瞬间发送所有的事件)
         * */
        //3.2 快速创建 & 发送事件
//        rxjava_just();
//        rxjava_fromArray();
//        rxjava_fromIterable();

        //3.3 延迟创建
//        rxjava_defer();
//        rxjava_timer();
//        rxjava_interval();
//        rxjava_intervalRange();
//        rxjava_range();

        // todo   rxjava 变换操作符：
        //
        // 对事件序列中的事件 / 整个事件序列 进行加工处理（即变换），使得其转变成不同的事件 / 整个事件序列
        /*
         * 1 map() 数据类型转换，对被观察者发送的每1个事件都通过 指定的函数 处理，从而变换成另外一种事件   即， 将被观察者发送的事件转换为任意的类型事件。
         * 2 FlatMap（）作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
         *             原理：为事件序列中每个事件都创建一个 Observable 对象；
                            将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
                            将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
                            新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
                       注：新合并生成的事件序列顺序是无序的，即 与旧序列发送事件的顺序无关
         * 3 ConcatMap（）类似FlatMap（）操作符
         *                与FlatMap（）的 区别在于：拆分 & 重新合并生成的事件序列 的顺序 = 被观察者旧序列生产的顺序
         *                即： 有序的将被观察者发送的整个事件序列进行变换
         * 4 buffer() 作用：定期从 被观察者（Obervable）需要发送的事件中 获取一定数量的事件 & 放到缓存区中，最终发送  ，，，即： 缓存被观察者发送的事件
         * */
//        rxjava_Map();
//        rxjava_flatMap();   //有序
//        rxjava_concatMap(); //有序
//        rxjava_buffer();

        //todo rxjava 组合 / 合并操作符：
        /*
         *  组合 多个被观察者（Observable） & 合并需要发送的事件
         * 1 concat（） / concatArray（）：组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
         *                       二者区别：组合被观察者的数量，即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个
         * 2 merge（） / mergeArray（）  : 组合多个被观察者一起发送数据，合并后 按时间线并行执行
         *                    二者区别：   组合被观察者的数量，即merge（）组合被观察者数量≤4个，而mergeArray（）则可＞4个
         *                    注：        区别上述concat（）操作符：同样是组合多个被观察者一起发送数据，但concat（）操作符合并后是按发送顺序串行执行
         * 3 concatDelayError（） / mergeDelayError（） :使用concat（）和merge（）操作符时。若其中一个被观察者发出onError事件，则会马上终止其他被观察者继续发送事件
         *                                          若希望onError事件推迟到其他被观察者发送事件结束后才触发，即需要使用对应的操作符，具体使用如下
         * 4 Zip（）    合并 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送 observable 123,observable 456合并成：14，25，36
         *
         * 5 combineLatest（）   当两个Observables中的任何一个发送了数据后，将先发送了数据的Observables 的最新（最后）一个数据 与 另外一个Observable发送的每个数据结合，最终基于该函数的结果发送数据
         *                      与Zip（）的区别：Zip（） = 按个数合并，即1对1合并；CombineLatest（） = 按时间合并，即在同一个时间点上合并
         * 6 combineLatestDelayError（）   作用类似于concatDelayError（） / mergeDelayError（） ，即错误处理，此处不作过多描述
         *
         * 7 reduce（）把被观察者需要发送的事件聚合成1个事件 & 发送       聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推
         *
         * 8 collect（）     将被观察者Observable发送的数据事件收集到一个数据结构里
         *
         * 9 startWith（） / startWithArray（）   在一个被观察者发送事件前，追加发送一些数据 / 一个新的被观察者
         *
         * 10 count() 统计被观察者发送事件的数量
         * */

//        rxjava_concat_concatArray();
//        rxjava_merge();
//        rxjava_concatArrayDelayError();  //mergeDelayError（）操作符同理，此处不作过多演示
//        rxjava_Zip();
//        rxjava_combineLatest();
//        rxjava_reduce();
//        rxjava_collect();
//        rxjava_starWith_startWithArray();
//        rxjava_count();

        //todo rxjava 功能性操作符： 辅助被观察者（Observable） 在发送事件时实现一些功能性需求，如错误处理、线程调度等等
        /*
         * 1 delay()  使得被观察者延迟一段时间再发送事件
         * 2 do()  在某个事件的生命周期中调用
         * 3 onErrorReturn()  遇到错误时，发送1个特殊事件 & 正常终止   可捕获在它之前发生的异常
         * 4 onErrorResumeNext（）  遇到错误时，发送1个新的Observable
         * 5 retry（）   重试，即当出现错误时，让被观察者（Observable）重新发射数据
         *                   <-- 1. retry（） -->
                        // 作用：出现错误时，让被观察者重新发送数据
                        // 注：若一直错误，则一直重新发送

                            <-- 2. retry（long time） -->
                        // 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
                        // 参数 = 重试次数

                            <-- 3. retry（Predicate predicate） -->
                        // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
                        // 参数 = 判断逻辑

                            <--  4. retry（new BiPredicate<Integer, Throwable>） -->
                        // 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
                        // 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）

                            <-- 5. retry（long time,Predicate predicate） -->
                        // 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
                        // 参数 = 设置重试次数 & 判断逻辑
         * 6 retryWhen（）   遇到错误时，将发生的错误传递给一个新的被观察者（Observable），并决定是否需要重新订阅原始被观察者（Observable）& 发送事件
         * */
//        rxjava_delay();
//        rxjava_do();
//        rxjava_onErrorReturn();
//        rxjava_onErrorResumeNext();
//        rxjava_retry();
//        rxjava_retryWhen();

        //todo rxjava 结合列子  retrofit2+rxjava2

        /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         *
         * 串行任务（flatmap,concat）   有些需求是需要任务之间依次执行的，也就是下一个任务是需要基于上一个任务的处理结果才能执行的，这样的任务需求我们称之为串行任务；
         * 并行任务（merge，zip） 而有些需求是要求执行多个任务的，而且任务之间也并无依赖关系，这样的任务需求我们称之为并行任务
         **/
//        rxjava_retrofit_demo1();  //(无条件) 网络请求轮询
//        rxjava_retrofit_demo2();  //(有条件) 网络请求轮询
//        rxjava_retrofit_demo33();
//        rxjava_retrofit_demo3();  //串行请求，模仿三次请求，并且依次1，2，3
//        rxjava_retrofit_demo4();   //从磁盘 / 内存缓存中 获取缓存数据
//        rxjava_retrofit_demo5();   // 网络请求出错重连 功能


    }

    private void rxjava_retrofit_demo5() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pacc.radio.cn/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();
        // 步骤2：创建 网络请求接口 的实例
        ApiService request = retrofit.create(ApiService.class);
        Observable<HttpResult<List<PlaceBean>>> observable = request.getLivePlace("");
        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {

                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        // 输出异常信息
                        Log.d(TAG, "发生异常 = " + throwable.toString());
                        /**
                         * 需求1：根据异常类型选择是否重试
                         * 即，当发生的异常 = 网络异常 = IO异常 才选择重试
                         */
                        if (throwable instanceof IOException) {
                            /**
                             * 需求2：限制重试次数
                             * 即，当已重试次数 < 设置的重试次数，才选择重试
                             */
                            if (currentRetryCount < maxConnectCount) {

                                // 记录重试次数
                                currentRetryCount++;
                                Log.d(TAG, "重试次数 = " + currentRetryCount);

                                /**
                                 * 需求2：实现重试
                                 * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                 *
                                 * 需求3：延迟1段时间再重试
                                 * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                                 *
                                 * 需求4：遇到的异常越多，时间越长
                                 * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                                 */
                                // 设置等待时间
                                waitRetryTime = 1000 + currentRetryCount * 1000;
                                Log.d(TAG, "等待时间 =" + waitRetryTime);
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);


                            } else {
                                // 若重试次数已 > 设置重试次数，则不重试
                                // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                                return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount + "，即 不再重试"));

                            }
                        }
                        // 若发生的异常不属于I/O异常，则不重试
                        // 通过返回的Observable发送的事件 = Error事件 实现（可在观察者的onError（）中获取信息）
                        else {
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }
//                        return null;
                    }

                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<List<PlaceBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<List<PlaceBean>> listHttpResult) {
                        Log.d(TAG, listHttpResult.getMessage() + "");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void rxjava_retrofit_demo4() {
        // 该2变量用于模拟内存缓存 & 磁盘缓存中的数据
        String memoryCache = null;
        String diskCache = null;

        /*
         * 设置第1个Observable：检查内存缓存是否有该数据的缓存
         **/
        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                // 先判断内存缓存有无数据
                if (memoryCache != null) {
                    // 若有该数据，则发送
                    emitter.onNext(memoryCache);
                } else {
                    // 若无该数据，则直接发送结束事件
                    emitter.onComplete();
                }

            }
        });

        /*
         * 设置第2个Observable：检查磁盘缓存是否有该数据的缓存
         **/
        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                // 先判断磁盘缓存有无数据
                if (diskCache != null) {
                    // 若有该数据，则发送
                    emitter.onNext(diskCache);
                } else {
                    // 若无该数据，则直接发送结束事件
                    emitter.onComplete();
                }

            }
        });

        /*
         * 设置第3个Observable：通过网络获取数据
         **/
        Observable<String> network = Observable.just("从网络中获取数据");
        // 此处仅作网络请求的模拟


        /*
         * 通过concat（） 和 firstElement（）操作符实现缓存功能
         **/

        // 1. 通过concat（）合并memory、disk、network 3个被观察者的事件（即检查内存缓存、磁盘缓存 & 发送网络请求）
        //    并将它们按顺序串联成队列
        Observable.concat(memory, disk, network)
                // 2. 通过firstElement()，从串联队列中取出并发送第1个有效事件（Next事件），即依次判断检查memory、disk、network
                .firstElement()
                // 即本例的逻辑为：
                // a. firstElement()取出第1个事件 = memory，即先判断内存缓存中有无数据缓存；由于memoryCache = null，即内存缓存中无数据，所以发送结束事件（视为无效事件）
                // b. firstElement()继续取出第2个事件 = disk，即判断磁盘缓存中有无数据缓存：由于diskCache ≠ null，即磁盘缓存中有数据，所以发送Next事件（有效事件）
                // c. 即firstElement()已发出第1个有效事件（disk事件），所以停止判断。

                // 3. 观察者订阅
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "最终获取的数据来源 =  " + s);
                    }
                });
    }

    private void rxjava_retrofit_demo33() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pacc.radio.cn/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        ApiService request = retrofit.create(ApiService.class);


        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://pacc.radio.cn/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        ApiService request2 = retrofit2.create(ApiService.class);

        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
        Observable<LiveListBean> observable1 = request.getLiveByParam("", "3125", 10, 1);
        Observable<HttpResult<List<PlaceBean>>> observable2 = request2.getLivePlace("");


        observable1.subscribeOn(Schedulers.io())               // （初始被观察者）切换到IO线程进行网络请求1
                .observeOn(AndroidSchedulers.mainThread())  // （新观察者）切换到主线程 处理网络请求1的结果
                .subscribe(new Observer<LiveListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d.isDisposed());

                    }

                    @Override
                    public void onNext(LiveListBean liveListBean) {
                        Log.d(TAG, "onNext: " + liveListBean.getStatus());
                        observable2.subscribeOn(Schedulers.io())               // （初始被观察者）切换到IO线程进行网络请求1
                                .observeOn(AndroidSchedulers.mainThread())  // （初始观察者）切换到主线程 处理网络请求2的结果
                                .subscribe(new Consumer<HttpResult<List<PlaceBean>>>() {
                                    @Override
                                    public void accept(HttpResult<List<PlaceBean>> result) throws Exception {

                                        Log.d(TAG, "第2次网络请求成功");
                                        // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        System.out.println("登录失败");
                                    }
                                });


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                    }
                });
    }

    @SuppressLint("CheckResult")
    private void rxjava_retrofit_demo3() {
        //需要进行嵌套网络请求：即在第1个网络请求成功后，继续再进行一次网络请求，
        // 如 先进行 用户注册 的网络请求, 待注册成功后回再继续发送 用户登录 的网络请求

        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pacc.radio.cn/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();
        // 步骤2：创建 网络请求接口 的实例
        ApiService request = retrofit.create(ApiService.class);


        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://pacc.radio.cn/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();
        ApiService request2 = retrofit2.create(ApiService.class);


        Retrofit retrofit3 = new Retrofit.Builder()
                .baseUrl("http://pacc.radio.cn/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();
        ApiService request3 = retrofit3.create(ApiService.class);

        // 步骤3：采用Observable<...>形式 对 2个网络请求 进行封装
        Observable<LiveListBean> observable1 = request.getLiveByParam("", "3125", 10, 1);
        Observable<HttpResult<List<PlaceBean>>> observable2 = request2.getLivePlace("");
        Observable<LiveListBean> observable3 = request3.getLiveByParam("", "3125", 10, 1);

        observable1.subscribeOn(Schedulers.io())               // （初始被观察者）切换到IO线程进行网络请求1
                .observeOn(AndroidSchedulers.mainThread())  // （新观察者）切换到主线程 处理网络请求1的结果    .这个现场切换是对应doonnext更新UI的操作，
                .doOnNext(new Consumer<LiveListBean>() {
                    @Override
                    public void accept(LiveListBean result) throws Exception {
                        Log.d(TAG, "第1次网络请求成功");
                        //走到这里说明第一次请求成功了，那么如果走到下面的onError，则说明是第二个请求出现了问题
                        //第二步,注册成功,更新注册成功相关UI，如果更新ui则需要切换到主线程，
                        // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
                    }
                })
                .observeOn(Schedulers.io())                 // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
                // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
                // 但对于初始观察者，它则是新的被观察者
                .flatMap(new Function<LiveListBean, ObservableSource<HttpResult<List<PlaceBean>>>>() { // 作变换，即作嵌套网络请求
                    @Override
                    public ObservableSource<HttpResult<List<PlaceBean>>> apply(LiveListBean result) throws Exception {
                        // 将网络请求1转换成网络请求2，即发送网络请求2
//                        Log.d(TAG, "第1次网络请求成功");
                        //这里可以拿到注册的信息返回的信息（result）,然后去执行登录操作

                        return observable2;
//                        return request2.getLivePlace("" + result.getStatus());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<HttpResult<List<PlaceBean>>>() {
                    @Override
                    public void accept(HttpResult<List<PlaceBean>> listHttpResult) throws Exception {
                        Log.d(TAG, "第2次网络请求成功；" + listHttpResult.getMessage());

                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<HttpResult<List<PlaceBean>>, ObservableSource<LiveListBean>>() {
                    @Override
                    public ObservableSource<LiveListBean> apply(HttpResult<List<PlaceBean>> listHttpResult) throws Exception {
                        return observable3;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  // （初始观察者）切换到主线程 处理网络请求2的结果
                .doOnNext(new Consumer<LiveListBean>() {
                    @Override
                    public void accept(LiveListBean liveListBean) throws Exception {
                        Log.d(TAG, "第3次网络请求成功；" + liveListBean.getMessage());
                    }
                })
                .subscribe(new Observer<LiveListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LiveListBean liveListBean) {

                        Log.d(TAG, "三次请求全部成功");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "三个有请求失败都会触发" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        Observable<Integer> observable3 = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onError(new Exception("发生错误了"));
//
//            }
//        });
//        Observable<Integer> observable4 = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//
//                emitter.onNext(4);
//                emitter.onNext(5);
////                emitter.onError(new Exception("发生错误了"));
//            }
//        });
//
//        observable3.flatMap(new Function<Integer, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(Integer integer) throws Exception {
//                return observable4;
//            }
//        }).subscribe(new Observer<Object>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//                .subscribe(new Consumer<HttpResult<List<PlaceBean>>>() {
//                    @Override
//                    public void accept(HttpResult<List<PlaceBean>> result) throws Exception {
//
//                        if (Looper.myLooper() == Looper.getMainLooper()) {
//                            Log.d(TAG, "主线程");
//                        } else {
//                            Log.d(TAG, "子线程");
//                        }
//                        Log.d(TAG, "第2次网络请求成功");
//                        // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        System.out.println("登录失败");
//                    }
//                });

//        Observable<Integer> just = Observable.just(i);
//
//        Observable.concat(observable1, observable2,just)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Object>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                if (o instanceof LiveListBean) {
//                    Log.d(TAG, "第1次网络请求成功");
//                } else if (o instanceof HttpResult) {
//                    Log.d(TAG, "第2次网络请求成功");
//                    i = 33;
//                } else {
//                    //并不能改变i
//                    Log.d(TAG, "just: "+o.toString());
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//                Log.d(TAG, "error");
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });


    }


    private void rxjava_retrofit_demo2() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://pacc.radio.cn/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        ApiService request = retrofit.create(ApiService.class);

        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<LiveListBean> observable = request.getLiveByParam("", "3225", 10, 1);


        // 步骤4：发送网络请求 & 通过repeatWhen（）进行轮询
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (i > 3) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                    }
                });

            }
        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(new Observer<LiveListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(LiveListBean result) {
                        // e.接收服务器返回的数据
                        i++;
                        Log.d(TAG, result.getMessage() + "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取轮询结束信息
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void rxjava_retrofit_demo1() {
        Observable.interval(2, 3, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                /*
                 * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                 * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                 **/
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long integer) throws Exception {
                        Log.d(TAG, "第 " + integer + " 次轮询");

                        /*
                         * 步骤3：通过Retrofit发送网络请求
                         **/
                        // a. 创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://pacc.radio.cn/") // 设置 网络请求 Url
                                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                                .build();

                        // b. 创建 网络请求接口 的实例
                        ApiService request = retrofit.create(ApiService.class);

                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<LiveListBean> observable = request.getLiveByParam("", "3225", 10, 1);

                        // d. 通过线程切换发送网络请求
                        observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                                .subscribe(new Observer<LiveListBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                    }

                                    @Override
                                    public void onNext(LiveListBean result) {
                                        // e.接收服务器返回的数据
                                        Log.d(TAG, "onNext:" + result.getMessage());
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(TAG, "请求失败");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    private void rxjava_retryWhen() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                // 遇到error事件才会回调
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {

                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                        // 返回Observable<?> = 新的被观察者 Observable（任意类型）
                        // 此处有两种情况：
                        // 1. 若 新的被观察者 Observable发送的事件 = Error事件，那么 原始Observable则不重新发送事件：
                        // 2. 若 新的被观察者 Observable发送的事件 = Next事件 ，那么原始的Observable则重新发送事件：
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {

                                // 1. 若返回的Observable发送的事件 = Error事件，则原始的Observable不重新发送事件
                                // 该异常错误信息可在观察者中的onError（）中获得
                                return Observable.error(new Throwable("retryWhen终止啦"));

                                // 2. 若返回的Observable发送的事件 = Next事件，则原始的Observable重新发送事件（若持续遇到错误，则持续重试）
                                // return Observable.just(1);
                            }
                        });

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应" + e.toString());
                        // 获取异常错误信息
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_retry() {
        //        <--1. retry（）-- >
// 作用：出现错误时，让被观察者重新发送数据
// 注：若一直错误，则一直重新发送

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                .retry() // 遇到错误时，让被观察者重新发射数据（若一直错误，则一直重新发送
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


//<--2. retry（long time）-- >
// 作用：出现错误时，让被观察者重新发送数据（具备重试次数限制
// 参数 = 重试次数
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                .retry(3) // 设置重试次数 = 3次
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });

//<--3. retry（Predicate predicate）-- >
// 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送& 持续遇到错误，则持续重试）
// 参数 = 判断逻辑
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                // 拦截错误后，判断是否需要重新发送请求
                .retry(new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        // 捕获异常
                        Log.e(TAG, "retry错误: " + throwable.toString());

                        //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                        //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                        return true;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });

//<--4. retry（new BiPredicate<Integer, Throwable>）-- >
// 作用：出现错误后，判断是否需要重新发送数据（若需要重新发送 & 持续遇到错误，则持续重试
// 参数 =  判断逻辑（传入当前重试次数 & 异常错误信息）
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })

                // 拦截错误后，判断是否需要重新发送请求
                .retry(new BiPredicate<Integer, Throwable>() {
                    @Override
                    public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Exception {
                        // 捕获异常
                        Log.e(TAG, "异常错误 =  " + throwable.toString());

                        // 获取当前重试次数
                        Log.e(TAG, "当前重试次数 =  " + integer);

                        //返回false = 不重新重新发送数据 & 调用观察者的onError结束
                        //返回true = 重新发送请求（若持续遇到错误，就持续重新发送）
                        return true;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


//<-- 5. retry（long time,Predicate predicate） -->
// 作用：出现错误后，判断是否需要重新发送数据（具备重试次数限制
// 参数 = 设置重试次数 & 判断逻辑
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        })
                // 拦截错误后，判断是否需要重新发送请求
                .retry(3, new Predicate<Throwable>() {
                    @Override
                    public boolean test(@NonNull Throwable throwable) throws Exception {
                        // 捕获异常
                        Log.e(TAG, "retry错误: " + throwable.toString());

                        //返回false = 不重新重新发送数据 & 调用观察者的onError（）结束
                        //返回true = 重新发送请求（最多重新发送3次）
                        return true;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_onErrorResumeNext() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
            }
        })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(@NonNull Throwable throwable) throws Exception {

                        // 1. 捕捉错误异常
                        Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());

                        // 2. 发生错误事件后，发送一个新的被观察者 & 发送事件序列
                        return Observable.just(11, 22);

                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_onErrorReturn() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Throwable("发生错误了"));
            }
        })
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(@NonNull Throwable throwable) throws Exception {
                        // 捕捉错误异常
                        Log.e(TAG, "在onErrorReturn处理了错误: " + throwable.toString());

                        return 666;
                        // 发生错误事件后，发送一个"666"事件，最终正常结束
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_do() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Throwable("发生错误了"));
            }
        })
                // 1. 当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        Log.d(TAG, "doOnEach: " + integerNotification.getValue());
                    }
                })
                // 2. 执行Next事件前调用
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doOnNext: " + integer);
                    }
                })
                // 3. 执行Next事件后调用
                .doAfterNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doAfterNext: " + integer);
                    }
                })
                // 4. Observable正常发送事件完毕后调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doOnComplete: ");
                    }
                })
                // 5. Observable发送错误事件时调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "doOnError: " + throwable.getMessage());
                    }
                })
                // 6. 观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.e(TAG, "doOnSubscribe: ");
                    }
                })
                // 7. Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doAfterTerminate: ");
                    }
                })
                // 8. 最后执行
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doFinally: ");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_delay() {
        Observable.just(1, 2, 3)
                .delay(3, TimeUnit.SECONDS) // 延迟3s再发送，由于使用类似，所以此处不作全部展示
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_count() {
        Observable.just(1, 2, 3, 4).count().subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e(TAG, "发送的事件数量 =  " + aLong);

            }
        });
    }

    private void rxjava_starWith_startWithArray() {
        //        <-- 在一个被观察者发送事件前，追加发送一些数据 -->
        // 注：追加数据顺序 = 后调用先追加
        Observable.just(4, 5, 6)
                .startWith(0)  // 追加单个数据 = startWith()
                .startWithArray(1, 2, 3) // 追加多个数据 = startWithArray()
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


//<-- 在一个被观察者发送事件前，追加发送被观察者 & 发送数据 -->
        // 注：追加数据顺序 = 后调用先追加
        Observable.just(4, 5, 6)
                .startWith(Observable.just(1, 2, 3))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_collect() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .collect(
                        // 1. 创建数据结构（容器），用于收集被观察者发送的数据
                        new Callable<ArrayList<Integer>>() {
                            @Override
                            public ArrayList<Integer> call() throws Exception {
                                return new ArrayList<>();
                            }
                            // 2. 对发送的数据进行收集
                        }, new BiConsumer<ArrayList<Integer>, Integer>() {
                            @Override
                            public void accept(ArrayList<Integer> list, Integer integer)
                                    throws Exception {
                                // 参数说明：list = 容器，integer = 后者数据
                                list.add(integer);
                                // 对发送的数据进行收集
                            }
                        }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(@NonNull ArrayList<Integer> s) throws Exception {
                Log.e(TAG, "本次发送的数据是： " + s);

            }
        });
    }

    private void rxjava_reduce() {
        Observable.just(1, 2, 3, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    // 在该复写方法中复写聚合的逻辑
                    @Override
                    public Integer apply(@NonNull Integer s1, @NonNull Integer s2) throws Exception {
                        Log.e(TAG, "本次计算的数据是： " + s1 + " 乘 " + s2);
                        return s1 * s2;
                        // 本次聚合的逻辑是：全部数据相乘起来
                        // 原理：第1次取前2个数据相乘，之后每次获取到的数据 = 返回的数据x原始下1个数据每
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer s) throws Exception {
                Log.e(TAG, "最终计算的结果是： " + s);

            }
        });
    }

    @SuppressLint("CheckResult")
    private void rxjava_combineLatest() {
        Observable.combineLatest(
                Observable.just(1L, 2L, 3L), // 第1个发送数据事件的Observable
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS), // 第2个发送数据事件的Observable：从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                new BiFunction<Long, Long, Long>() {
                    @Override
                    public Long apply(Long o1, Long o2) throws Exception {
                        // o1 = 第1个Observable发送的最新（最后）1个数据
                        // o2 = 第2个Observable发送的每1个数据
                        Log.e(TAG, "合并的数据是： " + o1 + " " + o2);
                        return o1 + o2;
                        // 合并的逻辑 = 相加
                        // 即第1个Observable发送的最后1个数据 与 第2个Observable发送的每1个数据进行相加
                    }
                }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long s) throws Exception {
                Log.e(TAG, "合并的结果是： " + s);
            }
        });
    }

    private void rxjava_Zip() {
        //        <-- 创建第1个被观察者 -->
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "被观察者1发送了事件1");
                emitter.onNext(1);
                // 为了方便展示效果，所以在发送事件后加入2s的延迟
                Thread.sleep(1000);

                Log.d(TAG, "被观察者1发送了事件2");
                emitter.onNext(2);
                Thread.sleep(1000);

                Log.d(TAG, "被观察者1发送了事件3");
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()); // 设置被观察者1在工作线程1中工作

//<-- 创建第2个被观察者 -->
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "被观察者2发送了事件A");
                emitter.onNext("A");
                Thread.sleep(1000);

                Log.d(TAG, "被观察者2发送了事件B");
                emitter.onNext("B");
                Thread.sleep(1000);

                Log.d(TAG, "被观察者2发送了事件C");
                emitter.onNext("C");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());// 设置被观察者2在工作线程2中工作
        // 假设不作线程控制，则该两个被观察者会在同一个线程中工作，即发送事件存在先后顺序，而不是同时发送

//<-- 使用zip变换操作符进行事件合并 -->
// 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String string) throws Exception {
                return integer + string;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "最终接收到的事件 =  " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    private void rxjava_concatArrayDelayError() {
        //        a. 无使用concatDelayError（）的情况
        Observable.concat(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onError(new NullPointerException()); // 发送Error事件，因为无使用concatDelayError，所以第2个Observable将不会发送事件
                        emitter.onComplete();
                    }
                }),
                Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });

//<-- 使用了concatDelayError（）的情况 -->
        Observable.concatArrayDelayError(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onError(new NullPointerException()); // 发送Error事件，因为使用了concatDelayError，所以第2个Observable将会发送事件，等发送完毕后，再发送错误事件
                        emitter.onComplete();
                    }
                }),
                Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应但是我完成了所有的被观察者所需要发送的事件");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_merge() {
        // merge（）：组合多个被观察者（＜4个）一起发送数据
        // 注：合并后按照时间线并行执行,
        Observable.merge(
                Observable.intervalRange(0, 3, 1, 3, TimeUnit.SECONDS), // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                Observable.intervalRange(2, 3, 1, 3, TimeUnit.SECONDS)) // 从2开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "merge接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });

// mergeArray（） = 组合4个以上的被观察者一起发送数据，此处不作过多演示，类似concatArray（）
    }

    private void rxjava_concat_concatArray() {
        //// concat（）：组合多个被观察者（≤4个）一起发送数据（有顺序，根据被观察者）
        Observable.concat(
                Observable.intervalRange(0, 3, 1, 3, TimeUnit.SECONDS), // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                Observable.intervalRange(2, 3, 1, 3, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });

        // concatArray（）：组合多个被观察者一起发送数据（可＞4个）
        // 注：串行执行
//        Observable.concatArray(Observable.just(1, 2, 3),
//                Observable.just(4, 5, 6),
//                Observable.just(7, 8, 9),
//                Observable.just(10, 11, 12),
//                Observable.just(13, 14, 15))
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer value) {
//                        Log.d(TAG, "接收到了事件" + value);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//                });
    }

    private void rxjava_buffer() {
        // 被观察者 需要发送5个数字
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2) // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量,即指针往后移动的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> stringList) {
                        //
                        Log.d(TAG, " 缓存区里的事件数量 = " + stringList.size());
                        for (Integer value : stringList) {
                            Log.d(TAG, " 事件 = " + value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_concatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }

            // 采用concatMap（）变换操作符
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过concatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    @SuppressLint("CheckResult")
    private void rxjava_flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件 " + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String d) throws Exception {
                Log.d(TAG, d);
                //新合并生成的事件序列顺序是无序的，即 与旧序列发送事件的顺序无关
            }
        });
    }

    @SuppressLint("CheckResult")
    private void rxjava_Map() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // // 1. 被观察者发送事件 = 参数为整型 = 1、2、3
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            // 2. 使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
            @Override
            public String apply(Integer integer) throws Exception {
                return "使用 Map变换操作符 将事件" + integer + "的参数从 整型" + integer + " 变换成 字符串类型" + integer;
            }
        }).subscribe(new Consumer<String>() {
            // 3. 观察者接收事件时，是接收到变换后的事件 = 字符串类型
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);

            }
        });

        //从上面可以看出，map() 将参数中的 Integer 类型对象转换成一个 String类型 对象后返回
        //
        //同时，事件的参数类型也由 Integer 类型变成了 String 类型
    }

    private void rxjava_range() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 注：若设置为负数，则会抛出异常
        Observable.range(3, 10)
                // 该例子发送的事件序列特点：从3开始发送，每次发送事件递增1，一共发送10个事件
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    private void rxjava_intervalRange() {
        // 参数说明：
        // 参数1 = 事件序列起始点；
        // 参数2 = 事件数量；
        // 参数3 = 第1次事件延迟发送时间；
        // 参数4 = 间隔时间；
        // 参数5 = 时间单位
        Observable.intervalRange(3, 10, 2, 3, TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：
                // 1. 从3开始，一共发送10个事件；
                // 2. 第1次延迟2s发送，之后每隔2秒产生1个数字（从0开始递增1，无限个）
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    private void rxjava_interval() {
        // 参数说明：
        // 参数1 = 第1次延迟时间；
        // 参数2 = 间隔时间数字；
        // 参数3 = 时间单位；
        Observable.interval(3, 1, TimeUnit.SECONDS)
                // 该例子发送的事件序列特点：延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });

// 注：interval默认在computation调度器上执行
// 也可自定义指定线程调度器（第3个参数）：interval(long,TimeUnit,Scheduler)
    }

    private void rxjava_timer() {
        // 该例子 = 延迟2s后，发送一个long类型数值
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });

// 注：timer操作符默认运行在一个新线程上
// 也可自定义线程调度器（第3个参数）：timer(long,TimeUnit,Scheduler)
    }

    private void rxjava_defer() {
        i = 10; //第一次赋值
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        //第2次对i赋值
        i = 20;
// <-- 3. 观察者开始订阅 ->>
        // 注：此时，才会调用defer（）创建被观察者对象（Observable）
        observable.subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer value) {
                // 接受到的为20
                Log.d(TAG, "接收到的整数是" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    private void rxjava_fromIterable() {
        // 1. 设置一个集合
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

// 2. 通过fromIterable()将集合中的对象 / 数据发送出去
        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    private void rxjava_fromArray() {
        // 1. 设置需要传入的数组
        Integer[] items = {0, 1, 2, 3, 4};
        // 2. 创建被观察者对象（Observable）时传入数组
        // 在创建后就会将该数组转换成Observable & 发送该对象中的所有数据
        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    private void rxjava_just() {
        // 1. 创建时传入整型1、2、3、4
        // 在创建后就会发送这些对象，相当于执行了onNext(1)、onNext(2)、onNext(3)、onNext(4)
        Observable.just(1, 2, 3, 4)
                // 至此，一个Observable对象创建完毕，以下步骤仅为展示一个完整demo，可以忽略
                // 2. 通过通过订阅（subscribe）连接观察者和被观察者
                // 3. 创建观察者 & 定义响应事件的行为
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }
                    // 默认最先调用复写的 onSubscribe（）

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }

                });
    }

    private void understandRxjava() {
        //1 被观察者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1 create() 是 RxJava 最基本的创造事件序列的方法
            // 此处传入了一个 OnSubscribe 对象参数
            // 当 Observable 被订阅时，OnSubscribe 的 call() 方法会自动被调用，即事件序列就会依照设定依次被触发
            // 即观察者会依次调用对应事件的复写方法从而响应事件
            // 从而实现被观察者调用了观察者的回调方法 & 由被观察者向观察者的事件传递，即观察者模式


            // 2. 在复写的subscribe（）里定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 通过 ObservableEmitter类对象产生事件并通知观察者
                // ObservableEmitter类介绍
                // a. 定义：事件发射器
                // b. 作用：定义需要发送的事件 & 向观察者发送事件
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });

//        <--扩展：RxJava 提供了其他方法用于 创建被观察者对象Observable -->
// 方法1：just(T...)：直接将传入的参数依次发送出来
        Observable observable1 = Observable.just("A", "B", "C");
        // 将会依次调用：
        // onNext("A");
        // onNext("B");
        // onNext("C");
        // onCompleted();

// 方法2：from(T[]) / from(Iterable<? extends T>) : 将传入的数组 / Iterable 拆分成具体对象后，依次发送出来
        String[] words = {"A", "B", "C"};
        Observable observable2 = Observable.fromArray(words);
        // 将会依次调用：
        // onNext("A");
        // onNext("B");
        // onNext("C");
        // onCompleted();


        //2观察者

        Observer<Integer> observer = new Observer<Integer>() {
            // 观察者接收事件前，默认最先调用复写 onSubscribe（）
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            // 当被观察者生产Next事件 & 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "对Next事件作出响应" + integer);
            }

            // 当被观察者生产Error事件& 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            // 当被观察者生产Complete事件& 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };


//        <--方式2：采用Subscriber 抽象类 -->
// 说明：Subscriber类 = RxJava 内置的一个实现了 Observer 的抽象类，对 Observer 接口进行了扩展

// 1. 创建观察者 （Observer ）对象
        Subscriber<String> subscriber = new Subscriber<String>() {

            // 2. 创建对象时通过对应复写对应事件方法 从而 响应对应事件
            // 观察者接收事件前，默认最先调用复写 onSubscribe（）
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "开始采用subscribe连接");
            }

            // 当被观察者生产Next事件 & 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onNext(String value) {
                Log.d(TAG, "对Next事件作出响应" + value);
            }


            // 当被观察者生产Error事件& 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            // 当被观察者生产Complete事件& 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };


//<--特别注意：2种方法的区别，即Subscriber 抽象类与Observer 接口的区别 -->
// 相同点：二者基本使用方式完全一致（实质上，在RxJava的 subscribe 过程中，Observer总是会先被转换成Subscriber再使用）
// 不同点：Subscriber抽象类对 Observer 接口进行了扩展，新增了两个方法：
        // 1. onStart()：在还未响应事件前调用，用于做一些初始化工作
        // 2. unsubscribe()：用于取消订阅。在该方法被调用后，观察者将不再接收 & 响应事件
        // 调用该方法前，先使用 isUnsubscribed() 判断状态，确定被观察者Observable是否还持有观察者Subscriber的引用，如果引用不能及时释放，就会出现内存泄露


        //3 通过订阅连接观察者和被观察者
        observable.subscribe(observer);

//        <-- Observable.subscribe(Subscriber) 的内部实现 -->

//        public Subscription subscribe(Subscriber subscriber) {
//            subscriber.onStart();
//            // 步骤1中 观察者  subscriber抽象类复写的方法，用于初始化工作
//            onSubscribe.call(subscriber);
//            // 通过该调用，从而回调观察者中的对应方法从而响应被观察者生产的事件
//            // 从而实现被观察者调用了观察者的回调方法 & 由被观察者向观察者的事件传递，即观察者模式
//            // 同时也看出：Observable只是生产事件，真正的发送事件是在它被订阅的时候，即当 subscribe() 方法执行时
//        }


        //基于链式 ,可采用 Disposable.dispose() 切断观察者 与 被观察者 之间的连接

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "对Next事件作出响应" + integer);
                if (integer == 2) {
                    // 设置在接收到第二个事件后切断观察者和被观察者的连接
                    //但是不影响emitter.onNext(3);的发送
                    disposable.dispose();
                    Log.d(TAG, "已经切断了连接：" + disposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });

        //Consumer ,just
        Observable.just("hellow", "aaaa").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "对Next事件作出响应" + s);
            }
        });
    }
}
