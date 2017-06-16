package com.example.abhishek.safetyapp;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    boolean isLoginSuccess;
    private LocalBroadcastManager mManager;
    public static final int WAITING_TIME = 5000;
    public static final int ITERATION_TIME = 1000;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mManager = LocalBroadcastManager.getInstance(this);
        mManager.registerReceiver(mReceiver, new IntentFilter("login"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mManager.unregisterReceiver(mReceiver);
    }

    //Receiver to check if login was successful from activity
    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isLoginSuccess = intent.getBooleanExtra("login_status", false);
            if (isLoginSuccess){
                resetLogin();
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scheduleTask();

        return START_STICKY;
    }

    //Method which runs per second to continuously checks for required apps unless login was successful
    private void scheduleTask() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(isLoginSuccess){
                    stopForSomeTime();
                }else{
                    findForegroundApp();
                }
            }
        }, 0, ITERATION_TIME, TimeUnit.MILLISECONDS);
    }

    //Method to find the top activity to popup our security
    public void findForegroundApp() {
        String currentApp = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (appList != null && appList.size() > 0) {
                TreeMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (!mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }

        if (currentApp.contains("whatsapp")){ // Can use an array here to handle many pkgs
            isLoginSuccess = true;
            Intent intent = new Intent(this, PopUpActivity.class);
            startActivity(intent);
        }

    }

    public void stopForSomeTime() {
        if (!isLoginSuccess){
            resetLogin();
        }
    }

    public void resetLogin(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isLoginSuccess = false;
            }
        }, WAITING_TIME);
    }
}
