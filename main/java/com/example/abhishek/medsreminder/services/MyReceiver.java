package com.example.abhishek.medsreminder.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.abhishek.medsreminder.activities.AlarmActivity;

/**
 * Created by Abhishek on 30-Jun-17.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent scheduledIntent = new Intent(context, AlarmActivity.class);
//        scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(scheduledIntent);
        Toast.makeText(context,"started", Toast.LENGTH_SHORT).show();
    }
}
