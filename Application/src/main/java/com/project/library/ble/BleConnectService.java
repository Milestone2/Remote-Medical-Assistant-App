package com.project.library.ble;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import com.project.library.util.BleScanTool;
import com.project.library.util.DebugLog;
import java.util.UUID;

public class BleConnectService extends Service {
    public static final UUID f220b = UUID.fromString("00000af0-0000-1000-8000-00805f9b34fb");
    public BleScanTool f221a = BleScanTool.getInstance();
    public C1710c f222c;
    private Handler f223d = new Handler();
    private BroadcastReceiver f224e = new C0521a(this);

    private void m391a() {
        if (this.f222c != null) {
            this.f222c.m3084a();
            this.f222c = null;
        }
    }

    public IBinder onBind(Intent intent) {
        return this.f222c;
    }

    public void onCreate() {
        DebugLog.m415e("onCreate");
        this.f222c = new C1710c(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        registerReceiver(this.f224e, intentFilter);
    }

    public void onDestroy() {
        DebugLog.m415e("onDestroy");
        m391a();
        unregisterReceiver(this.f224e);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    public boolean onUnbind(Intent intent) {
        m391a();
        return false;
    }
}
