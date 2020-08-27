package com.example.mlmmusic.http.serviceapi;


import com.example.mlmmusic.base.LiveListBean;
import com.example.mlmmusic.beans.LiveChannelBean;
import com.example.mlmmusic.beans.PlaceBean;
import com.example.mlmmusic.http.httpentity.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 根据Id获取直播列表
     *
     * @param token
     * @param channelPlaceId
     * @param limit
     * @param offset
     * @return
     */
    @GET("channels/getlivebyparam")
    Observable<LiveListBean> getLiveByParam(@Query("token") String token, @Query("channelPlaceId") String channelPlaceId, @Query("limit") int limit, @Query("offset") int offset);


    /**
     * 根据Id获取直播列表
     *
     * @param token
     * @param channelPlaceId
     * @param limit
     * @param offset
     * @return
     */
    @GET("channels/getlivebyparam")
    Observable<HttpResult<List<LiveChannelBean>>> getLiveByParam2(@Query("token") String token, @Query("channelPlaceId") String channelPlaceId, @Query("limit") int limit, @Query("offset") int offset);

    /**
     * 获取省市台编号
     * @param token
     * @return
     */
    @GET("channels/getliveplace")
    Observable<HttpResult<List<PlaceBean>>> getLivePlace(@Query("token") String token);


    //特殊API接口单独加入请求头
    @Headers({ "Accept: application/vnd.github.v3.full+json", "User-Agent: Retrofit-your-App"})
    @GET("users/{username}")
    Call<Object>   getUser(@Path("username") String username);


}
