package com.example.mlmmusic.http.serviceapi;


import com.example.mlmmusic.beans.LiveChannelBean;
import com.example.mlmmusic.beans.PlaceBean;
import com.example.mlmmusic.http.service.BaseApi;
import com.example.mlmmusic.http.service.HttpMethod;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class SubjectApi extends BaseApi {
    public static SubjectApi subjectApi;
    public ApiService apiService;

    public SubjectApi() {
        apiService = HttpMethod.getInstance().createApi(ApiService.class);
    }

    public static SubjectApi getInstance() {
        if (subjectApi == null) {
            subjectApi = new SubjectApi();
        }
        return subjectApi;
    }




    /*------------下面都是网络请求的方法------------*/


    public void getLiveList(String token, String channelPlaceId, int pageSize, int currentPage, Observer<List<LiveChannelBean>> subscriber) {

        Observable observable = apiService.getLiveByParam2(token, channelPlaceId, pageSize, currentPage)
                .map(new HttpResultFunc<List<LiveChannelBean>>());

        toSubscribe(observable, subscriber);

    }


    public void getLivePlace(String token, Observer<List<PlaceBean>> subscriber) {

        Observable observable = apiService.getLivePlace(token)
                .map(new HttpResultFunc<List<PlaceBean>>());

        toSubscribe(observable, subscriber);

    }


}
