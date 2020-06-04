package com.example.mlmmusic.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Trace.I("aaaaa", intent.getAction() + "");
    }
}
