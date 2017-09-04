package com.project.library.ble;

import com.project.library.util.UartLogUtil;

class C0525g implements Runnable {
    final /* synthetic */ C1710c f237a;

    C0525g(C1710c c1710c) {
        this.f237a = c1710c;
    }

    public void run() {
        UartLogUtil.recordWrite("关闭蓝牙", new byte[1]);
        this.f237a.f1725d.m378a();
    }
}
