package com.example.mlmmusic.music.listener;

/**
 * Created by ywl on 2018-4-5.
 */

public interface OnRecordListener {

    void onRecordTime(int scds);

    void onRecordComplete();

    void onRecordPauseResume(boolean pause);

}
