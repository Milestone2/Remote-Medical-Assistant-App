package com.project.library.p002a;

import android.bluetooth.BluetoothAdapter.LeScanCallback;

class C0520g implements Runnable {
    final /* synthetic */ C0516b f218a;
    private final /* synthetic */ LeScanCallback f219b;

    C0520g(C0516b c0516b, LeScanCallback leScanCallback) {
        this.f218a = c0516b;
        this.f219b = leScanCallback;
    }

    public void run() {
        this.f218a.f196e.stopLeScan(this.f219b);
        this.f218a.f213w = false;
        this.f218a.m383b();
    }
}
