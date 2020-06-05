package com.example.keepalivelibrary;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;

import com.example.keepalivelibrary.config.ForegroundNotification;
import com.example.keepalivelibrary.config.KeepLiveService;
import com.example.keepalivelibrary.service.JobHandlerService;
import com.example.keepalivelibrary.service.LocalService;
import com.example.keepalivelibrary.service.RemoteService;


/**
 * 保活工具
 */
public final class KeepLive {
    public static ForegroundNotification foregroundNotification = null;
    public static KeepLiveService keepLiveService = null;
    public static RunMode runMode = null;

    /**
     * 启动保活
     *
     * @param application            your application
     * @param foregroundNotification 前台服务
     * @param keepLiveService        保活业务
     */
    public static void startWork(@NonNull Context application, @NonNull RunMode runMode, ForegroundNotification foregroundNotification, @NonNull KeepLiveService keepLiveService) {
        if (isMain(application)) {
            KeepLive.foregroundNotification = foregroundNotification;
            KeepLive.keepLiveService = keepLiveService;
            KeepLive.runMode = runMode;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //启动定时器，在定时器中启动本地服务和守护进程
                Intent intent = new Intent(application, JobHandlerService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    application.startForegroundService(intent);
                } else {
                    application.startService(intent);
                }
            } else {
                //启动本地服务
                Intent localIntent = new Intent(application, LocalService.class);
                //启动守护进程
                Intent guardIntent = new Intent(application, RemoteService.class);
                application.startService(localIntent);
                application.startService(guardIntent);
            }
        }
    }

    private static boolean isMain(Context application) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager mActivityManager = (ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        String packageName = application.getPackageName();
        if (processName.equals(packageName)) {
            return true;
        }
        return false;
    }

    /**
     * 运行模式
     */
    public static enum RunMode {
        /**
         * 省电模式
         * 省电一些，但保活效果会差一点
         */
        ENERGY,
        /**
         * 流氓模式
         * 相对耗电，但可造就不死之身
         */
        ROGUE
    }
}
