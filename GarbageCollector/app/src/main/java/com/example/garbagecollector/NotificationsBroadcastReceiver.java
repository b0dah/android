package com.example.garbagecollector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class NotificationsBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION_PUSH_RECEIVED = "com.example.garbagecollector.action.pushReceived";
    public static final IntentFilter BROADCAST_INTENT_FILTER = new IntentFilter(ACTION_PUSH_RECEIVED);

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentToSend = new Intent(ACTION_PUSH_RECEIVED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intentToSend);
    }


}
