package com.example.mlmmusic.beans;


import com.example.mlmmusic.base.BaseBean;

/**
 * Created by clf on 2019-7-26.
 */

public class StreamsBean extends BaseBean {

    private String bitstreamType;
    private String resolution;
    private String url;

    public String getBitstreamType() {
        return bitstreamType;
    }

    public void setBitstreamType(String bitstreamType) {
        this.bitstreamType = bitstreamType;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
