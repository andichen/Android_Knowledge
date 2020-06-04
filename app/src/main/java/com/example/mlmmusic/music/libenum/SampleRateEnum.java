package com.example.mlmmusic.music.libenum;

/**
 *
 * sample_rate enum
 * Created by ywl on 2018-10-19.
 */

public enum SampleRateEnum {

    RATE_8000("RATE_8000", 8000),
    RATE_11025("RATE_11025", 11025),
    RATE_12000("RATE_12000", 12000),
    RATE_16000("RATE_16000", 16000),
    RATE_22050("RATE_22050", 22050),
    RATE_24000("RATE_24000", 24000),
    RATE_32000("RATE_32000", 32000),
    RATE_44100("RATE_44100", 44100),
    RATE_48000("RATE_48000", 48000);

    private String rate;
    private int value;

    SampleRateEnum(String rate, int value)
    {
        this.rate = rate;
        this.value = value;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
