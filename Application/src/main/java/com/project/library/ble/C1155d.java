package com.project.library.ble;

import com.project.library.p002a.C0514a;

class C1155d implements C0514a {
    final /* synthetic */ C1710c f805a;

    C1155d(C1710c c1710c) {
        this.f805a = c1710c;
    }

    public void mo3447a() {
        this.f805a.m3076a("onBLEConnecting", C1710c.f1714k, new Object[0]);
    }

    public void mo3448a(byte b) {
        this.f805a.m3076a("onWareUpdate", C1710c.f1717n, Byte.valueOf(b));
    }

    public void mo3449a(byte b, byte b2) {
        this.f805a.m3076a("onBleControlReceive", C1710c.f1718o, Byte.valueOf(b), Byte.valueOf(b2));
    }

    public void mo3450a(byte b, boolean z) {
        this.f805a.m3076a("onSettingsSuccess", C1710c.f1720q, Byte.valueOf(b), Boolean.valueOf(z));
    }

    public void mo3451a(int i) {
        this.f805a.m3076a("onBlueToothError", C1710c.f1715l, Integer.valueOf(i));
    }

    public void mo3452a(String str) {
        this.f805a.m3076a("onBLEDisConnected", C1710c.f1716m, str);
        this.f805a.f1729h = true;
    }

    public void mo3453a(byte[] bArr) {
        this.f805a.m3076a("onDataSendTimeOut", C1710c.f1719p, bArr);
    }

    public void mo3454b() {
        this.f805a.f1729h = false;
        this.f805a.m3076a("onBLEConnected", C1710c.f1714k, new Object[0]);
    }

    public void mo3455b(byte b) {
        this.f805a.m3076a("onBindUnbind", C1710c.f1717n, Byte.valueOf(b));
    }

    public void mo3456b(byte b, boolean z) {
        this.f805a.m3076a("onAppControlSuccess", C1710c.f1720q, Byte.valueOf(b), Boolean.valueOf(z));
    }

    public void mo3457b(int i) {
        this.f805a.m3076a("onSyncData", C1710c.f1715l, Integer.valueOf(i));
    }

    public void mo3458b(byte[] bArr) {
        this.f805a.m3076a("onOtherDataReceive", C1710c.f1719p, bArr);
    }

    public void mo3459c(byte b) {
        this.f805a.m3076a("onGetInfo", C1710c.f1717n, Byte.valueOf(b));
    }
}
