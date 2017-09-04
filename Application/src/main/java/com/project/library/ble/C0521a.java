package com.project.library.ble;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.project.library.share.LibSharedPreferences;
import com.project.library.util.BleScanTool;

class C0521a extends BroadcastReceiver {
    final /* synthetic */ BleConnectService f225a;

    C0521a(BleConnectService bleConnectService) {
        this.f225a = bleConnectService;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
            if (intExtra == 10) {
                if (this.f225a.f222c != null) {
                    this.f225a.f222c.m3086b(false);
                }
            } else if (intExtra == 12 && !LibSharedPreferences.getInstance().isFirwareUpgrade()) {
                if (this.f225a.f222c != null) {
                    this.f225a.f222c.m3086b(true);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                boolean isNeedScanDevice = BleScanTool.getInstance().isNeedScanDevice();
                if (this.f225a.f222c != null) {
                    this.f225a.f222c.m3085a(isNeedScanDevice);
                }
            }
        }
    }
}
