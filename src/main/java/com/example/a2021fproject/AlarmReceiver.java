package com.example.a2021fproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String ACTION_RESTART_SERVICE = "Restart";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_RESTART_SERVICE)) {
            Intent in = new Intent(context, com.example.a2021fproject.AlarmService.class);
            context.startService(in);
        }
    }
}
