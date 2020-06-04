package com.example.mlmmusic.beans;

import java.util.List;

public class RXBusBean {
    private int flagId;
    private String nickName;

    private StreamsBean streams;

    public RXBusBean(int flagId, String nickName,StreamsBean streamsBean) {
        this.flagId = flagId;
        this.nickName = nickName;
        this.streams = streamsBean;
    }

    public int getFlagId() {
        return flagId;
    }

    public void setFlagId(int userId) {
        this.flagId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public StreamsBean getStreams() {
        return streams;
    }

    public void setStreams(StreamsBean streams) {
        this.streams = streams;
    }
}
