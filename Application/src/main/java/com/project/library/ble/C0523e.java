package com.project.library.ble;

import com.project.library.util.DebugLog;

class C0523e implements Runnable {
    final /* synthetic */ C1710c f233a;
    private final /* synthetic */ boolean f234b;

    C0523e(C1710c c1710c, boolean z) {
        this.f233a = c1710c;
        this.f234b = z;
    }

    public void run() {
        if (this.f234b) {
            this.f233a.f1725d.m389e();
            DebugLog.m415e("自动连接设备扫描。。。");
            return;
        }
        //this.f233a.f1725d.m383b();
        DebugLog.m415e("自动连接设备延时。。。");
    }
}
