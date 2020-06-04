package com.example.mlmmusic.music.listener;

public interface OnShowPcmDataListener {

    void onPcmInfo(int samplerate, int bit, int channels);

    void onPcmData(byte[] pcmdata, int size, long clock);

}
