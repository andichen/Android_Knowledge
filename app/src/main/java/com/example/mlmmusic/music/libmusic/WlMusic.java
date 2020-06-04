package com.example.mlmmusic.music.libmusic;

import android.text.TextUtils;


import com.example.mlmmusic.music.bean.TimeBean;
import com.example.mlmmusic.music.libenum.MuteEnum;
import com.example.mlmmusic.music.libenum.SampleRateEnum;
import com.example.mlmmusic.music.listener.OnCompleteListener;
import com.example.mlmmusic.music.listener.OnErrorListener;
import com.example.mlmmusic.music.listener.OnInfoListener;
import com.example.mlmmusic.music.listener.OnLoadListener;
import com.example.mlmmusic.music.listener.OnPauseResumeListener;
import com.example.mlmmusic.music.listener.OnPreparedListener;
import com.example.mlmmusic.music.listener.OnRecordListener;
import com.example.mlmmusic.music.listener.OnShowPcmDataListener;
import com.example.mlmmusic.music.listener.OnVolumeDBListener;

import java.io.File;

/**
 *  Github: https://github.com/wanliyang1990/wlmusic
 *
 * Created by ywl on 2018-1-7.
 */

public class WlMusic {

    /**
     * the path of file or stream
     */
    private String source;
    /**
     * playing time and totaltime bean
     */
    private TimeBean timeBean;
    /**
     * total times
     */
    private int duration = -1;
    /**
     * volume percent (0-100)
     */
    private int volume = 100;
    /**
     * the speed of playing without change pitch
     * default 1 normal (0.25~4 -> 0.25x~4.0x)
     */
    private float playSpeed = 1f;
    /**
     * the pitch of playing without change speed
     * default 1 normal (0.25~4 -> 0.25x~4.0x)
     */
    private float playPitch = 1f;
    /**
     * this mutex of sound
     */
    private MuteEnum mute = MuteEnum.MUTE_CENTER;//0:left 1:right 2:center

    /**
     * will play next
     */
    private boolean playNext = false;
    /**
     * will play still
     */
    private boolean playCircle = false;
    /**
     * play status
     */
    private boolean isPlaying = false;
    /**
     * seek status
     */
    private boolean isSeek = false;
    /**
     * duration seeking showtime callback
     * true:show
     * false:not show
     */
    private boolean seekingShowTime = true;

    /**
     * stop status
     */
    private boolean stopStatus = false;

    private boolean preparedTostart = false;

    private boolean isCallBackPcmData = false;

    private boolean isShowPCMDB = false;

    private SampleRateEnum sampleRateEnum;

    /**
     * prepared callback
     */
    private OnPreparedListener onPreparedListener;
    /**
     * error callback
     */
    private OnErrorListener onErrorListener;
    /**
     * load status callback
     */
    private OnLoadListener onLoadListener;
    /**
     * info callback
     */
    private OnInfoListener onInfoListener;
    /**
     * play complete callback
     */
    private OnCompleteListener onCompleteListener;
    /**
     * pause and resume callback
     */
    private OnPauseResumeListener onPauseResumeListener;
    /**
     * the DB of volume value callback
     */
    private OnVolumeDBListener onVolumeDBListener;

    /**
     * this record time listener
     */
    private OnRecordListener onRecordListener;

    private OnShowPcmDataListener onShowPcmDataListener;

    public static WlMusic instance = null;

    private WlMusic(){}

    public static synchronized WlMusic getInstance()
    {
        if(instance == null)
        {
            synchronized (WlMusic.class)
            {
                if(instance == null)
                {
                    instance = new WlMusic();
                }
            }
        }
        return instance;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource()
    {
        return source;
    }

    public void setCallBackPcmData(boolean callBackPcmData) {
        isCallBackPcmData = callBackPcmData;
    }

    public void setShowPCMDB(boolean showPCMDB) {
        isShowPCMDB = showPCMDB;
    }

    public void setConvertSampleRate(SampleRateEnum sampleRateEnum) {
        this.sampleRateEnum = sampleRateEnum;
    }

    public int getConvertSampleRate() {
        if(sampleRateEnum != null)
        {
            return sampleRateEnum.getValue();
        }
        return 0;
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.onPreparedListener = onPreparedListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.onErrorListener = onErrorListener;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.onInfoListener = onInfoListener;
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public void setOnPauseResumeListener(OnPauseResumeListener onPauseResumeListener) {
        this.onPauseResumeListener = onPauseResumeListener;
    }

    public void setOnVolumeDBListener(OnVolumeDBListener onVolumeDBListener) {
        this.onVolumeDBListener = onVolumeDBListener;
    }

    public void setOnRecordListener(OnRecordListener onRecordListener) {
        this.onRecordListener = onRecordListener;
    }

    public void setOnShowPcmDataListener(OnShowPcmDataListener onShowPcmDataListener) {
        this.onShowPcmDataListener = onShowPcmDataListener;
    }

    public boolean isPlaying() {
        return isPlaying;
    }


    /**
     * prepared to play
     */
    public void prePared()
    {
        if(TextUtils.isEmpty(source))
        {
            return;
        }
        if(stopStatus)
        {
            return;
        }
        playNext = false;
        n_prepared(source);
    }

    /**
     * play next source
     * @param source
     */
    public void playNext(String source)
    {
        playNext = true;
        this.source = source;
        stop();
    }

    /**
     * set play by circle
     * @param playCircle
     */
    public void setPlayCircle(boolean playCircle) {
        this.playCircle = playCircle;
    }

    /**
     * get play circle status
     * @return
     */
    public boolean isPlayCircle()
    {
        return playCircle;
    }

    /**
     * start
     */
    public void start()
    {
        if(isPlaying)
        {
            return;
        }
        if(!preparedTostart)
        {
            if(onErrorListener != null)
            {
                onErrorListener.onError(1009, "please call parpared first");
            }
            return;
        }
        if(timeBean == null)
        {
            timeBean = new TimeBean();
        }
        isPlaying = true;
        setVolume(volume);
        setPlaySpeed(playSpeed);
        setPlayPitch(playPitch);
        setMute(mute);
        n_start();
    }

    /**
     * pause
     */
    public void pause()
    {
        n_pause();
        if(onPauseResumeListener != null)
        {
            onPauseResumeListener.onPause(true);
        }
    }

    /**
     * resume
     */
    public void resume()
    {
        n_resume();
        if(onPauseResumeListener != null)
        {
            onPauseResumeListener.onPause(false);
        }
    }

    /**
     * stop player and release source
     */
    public void stop()
    {
        if(!stopStatus)
        {
            duration = -1;
            preparedTostart = false;
            stopStatus = true;
            timeBean = null;
            n_stop();
            isPlaying = false;
        }
    }

    /**
     * seek to secds
     * @param secds
     * @param seekingfinished
     * @param showTime
     */
    public void seek(final int secds, boolean seekingfinished, boolean showTime)
    {
        seekingShowTime = showTime;
        if(duration <= 0)
        {
            return;
        }
        if(seekingfinished)
        {
            isSeek = seekingfinished;
            n_seek(secds);
        }
    }

    /**
     * get duration
     * @return
     */
    public int getDuration()
    {
        if(duration < 0)
        {
            duration = n_getduration();
        }
        return duration;
    }

    /**
     * set the volume
     * @param percent
     */
    public void setVolume(int percent)
    {
        if(percent <= 0)
        {
            percent = 0;
        }
        else if(percent >= 100)
        {
            percent = 100;
        }
        this.volume = percent;
        n_volume(volume);
    }

    /**
     * get the volume
     * @return
     */
    public int getVolume()
    {
        return volume;
    }

    /**
     * set play speed
     * @param speed
     */
    public void setPlaySpeed(float speed)
    {
        this.playSpeed = speed;
        n_playspeed(playSpeed);
    }

    /**
     * get play speed
     * @return
     */
    public float getPlaySpeed()
    {
        return playSpeed;
    }

    /**
     * get the pitch
     * @return
     */
    public float getPlayPitch() {
        return playPitch;
    }

    /**
     * set the pitch
     * @param pitch
     */
    public void setPlayPitch(float pitch) {
        this.playPitch = pitch;
        n_playpitch(pitch);
    }

    /**
     * set the mute
     * @param mute
     */
    public void setMute(MuteEnum mute)
    {
        this.mute = mute;
        n_mute(mute.getValue());
    }

    /**
     * get the mute of playing
     * @return
     */
    public MuteEnum getMute()
    {
        return mute;
    }


    /**
     * start recording
     * @param recordSavePath
     * @param recordSaveName
     */
    public void startRecordPlaying(String recordSavePath, String recordSaveName)
    {
        if(TextUtils.isEmpty(recordSavePath) || TextUtils.isEmpty(recordSaveName))
        {
            return;
        }
        File file = new File(recordSavePath);
        if(!file.exists())
        {
            file.mkdirs();
        }
        if(!file.exists())
        {
            if(onErrorListener != null)
            {
                onErrorListener.onError(1008, "record path is wrong");
            }
            return;
        }
        n_startPlayRecord(recordSavePath + "/" + recordSaveName + ".aac");
    }

    /**
     * stop recording
     */
    public void stopRecordPlaying()
    {
        n_stopPlayRecord();
    }

    /**
     * pause recording
     */
    public void pauseRecordPlaying()
    {
        n_pauseRecordPlaying();
    }

    /**
     * resume recording
     */
    public void resumeRecordPlaying()
    {
        n_resumeRecordPlaying();
    }

    /**
     * play the time of audio before cutting
     * @param start_secs
     * @param end_secs
     */
    public void playCutAudio(int start_secs, int end_secs)
    {
        if(n_playCutAudio(start_secs, end_secs)== 0){
            start();
        }
        else
        {
            onCallError(1008, "cut audio not work for this url");
        }
    }

    /**
     * cutting audio
     * @param start_secs
     * @param end_secs
     * @param savepath
     * @param filename
     */
    public void cutAudio(int start_secs, int end_secs, String savepath, String filename)
    {
        File file = new File(savepath);
        if(!file.exists())
        {
            onCallError(1011, "the file is not exists");
            return;
        }
        savepath = savepath + "/" + filename.replace(".aac", "") + ".aac";
        if(n_cutAudio(start_secs, end_secs, savepath) == 0)
        {
            start();
        }
        else
        {
            onCallError(1010, "cut audio not work for this url(" + source + ")");
        }
    }

    public void cutAudio(int start_secs, int end_secs)
    {
        if(n_cutAudio_noRecord(start_secs, end_secs) == 0)
        {
            start();
        }
        else
        {
            onCallError(1010, "cut audio not work for this url(" + source + ")");
        }
    }

    /**
     * for native call
     */
    private void onCallParpared()
    {
        if(onPreparedListener != null)
        {
            if(!stopStatus)
            {
                preparedTostart = true;
                onPreparedListener.onPrepared();
            }
        }
    }

    /**
     * for native call
     * @param code
     * @param msg
     */
    private void onCallError(int code, String msg)
    {
        if(onErrorListener != null)
        {
            onErrorListener.onError(code, msg);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }).start();
    }

    /**
     * for native call
     * @param load
     */
    private void onCallLoad(boolean load)
    {
        if(onLoadListener != null)
        {
            onLoadListener.onLoad(load);
        }
    }

    /**
     * for native call
     * @param currSec
     * @param totalSec
     */
    private void onCallInfo(int currSec, int totalSec)
    {
        if(!seekingShowTime)
        {
            return;
        }
        if(onInfoListener != null && timeBean != null)
        {
            if(!isSeek) {
                timeBean.setCurrSecs(currSec);
                timeBean.setTotalSecs(totalSec);
                duration = totalSec;
//                onInfoListener.onInfo(timeBean);
            }
        }
    }

    /**
     * for native call
     */
    private void onCallComplete()
    {
        duration = getDuration();
        onCallInfo(duration, duration);
        if(onCompleteListener != null)
        {
            onCompleteListener.onComplete();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }).start();
    }

    /**
     * for native call
     * @return
     */
    private boolean onCallPlayCircle() {
        return playCircle;
    }

    /**
     * for native call
     * @param db
     */
    private void onCallVolumeDB(int db)
    {
        if(onVolumeDBListener != null)
        {
            onVolumeDBListener.onVolumeDB(db);
        }
    }

    /**
     * stop complete callback
     */
    private void onCallStopComplete()
    {
        stopStatus = false;
        if(playNext)
        {
            prePared();
        }
    }

    /**
     * seek complete callback
     */
    private void onCallSeekComplete()
    {
        isSeek = false;
    }

    /**
     * show record time callback
     * @param secds
     */
    private void onCallRecordTime(int secds)
    {
        if(onRecordListener != null)
        {
            onRecordListener.onRecordTime(secds);
        }
    }

    /**
     * record complete callback
     */
    private void onCallRecordComplete()
    {
        if(onRecordListener != null)
        {
            onRecordListener.onRecordComplete();
        }
    }

    /**
     * record pause and resume callback
     * @param pause
     */
    private void onCallRecordPauseResume(boolean pause)
    {
        if(onRecordListener != null)
        {
            onRecordListener.onRecordPauseResume(pause);
        }
    }


    /**
     * show pcm-data info from music player for developer
     * @param samplerate
     * @param bit
     * @param channels
     */
    private void onCallPcmInfo(int samplerate, int bit, int channels)
    {
        if(onShowPcmDataListener != null)
        {
            onShowPcmDataListener.onPcmInfo(samplerate, bit, channels);
        }
    }

    /**
     * provide the pcm-data for developer
     * @param pcmdata
     * @param datasize
     */
    private void onCallPcmData(byte[] pcmdata, int datasize, long clock)
    {
        if(onShowPcmDataListener != null)
        {
            onShowPcmDataListener.onPcmData(pcmdata, datasize, clock);
        }
    }

    /**
     * native prepared
     * @param source
     */
    private native void n_prepared(String source);

    /**
     * native start
     */
    private native void n_start();

    /**
     * native pause
     */
    private native void n_pause();

    /**
     * native resume
     */
    private native void n_resume();

    /**
     * native stop
     * @return
     */
    private native void n_stop();

    /**
     * native seek to seconeds
     * @param secds
     */
    private native void n_seek(int secds);

    /**
     * native set volume
     * @param percent
     */
    private native void n_volume(int percent);

    /**
     * native set speed
     * @param speed
     */
    private native void n_playspeed(float speed);

    /**
     * native set pitch
     * @param pitch
     */
    private native void n_playpitch(float pitch);

    /**
     * native set mute
     * @param mute
     */
    private native void n_mute(int mute);

    /**
     * start record while playing
     */
    private native void n_startPlayRecord(String aacsavepath);

    /**
     * stop record while playing
     */
    private native void n_stopPlayRecord();

    /**
     * pause the recorder
     */
    private native void n_pauseRecordPlaying();

    /**
     * resume the recorder
     */
    private native void n_resumeRecordPlaying();

    /**
     * play the time of audio before cut
     * @param start
     * @param end
     * @return
     */
    private native int n_playCutAudio(int start, int end);

    /**
     * cuting the time of audio
     * @param start
     * @param end
     * @param savepath
     * @return
     */
    private native int n_cutAudio(int start, int end, String savepath);

    private native int n_cutAudio_noRecord(int start, int end);


    /**
     * get audio duration
     * @return
     */
    private native int n_getduration();

    /**
     * load library
     */
    static {
        System.loadLibrary("avutil-55");
        System.loadLibrary("swresample-2");
        System.loadLibrary("avcodec-57");
        System.loadLibrary("avformat-57");
        System.loadLibrary("swscale-4");
        System.loadLibrary("avfilter-6");
        System.loadLibrary("avdevice-57");
        System.loadLibrary("ssl");
        System.loadLibrary("crypto");
        System.loadLibrary("wlmusic");
    }

}
