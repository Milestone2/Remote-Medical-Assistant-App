package com.project.library.ble;

import com.project.library.util.UartLogUtil;

class C0524f implements Runnable {
    final /* synthetic */ C1710c f235a;
    private final /* synthetic */ String f236b;

    C0524f(C1710c c1710c, String str) {
        this.f235a = c1710c;
        this.f236b = str;
    }

    public void run() {
        UartLogUtil.recordWrite("连接蓝牙", new byte[1]);
        this.f235a.f1725d.m382a(this.f236b);
    }
}
