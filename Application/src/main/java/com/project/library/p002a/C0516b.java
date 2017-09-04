package com.project.library.p002a;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.project.library.ble.C0522b;
import com.project.library.core.CoreServiceProxy;
import com.project.library.share.LibSharedPreferences;
import com.project.library.util.BleScanTool;
import com.project.library.util.ByteDataConvertUtil;
import com.project.library.util.DebugLog;
import com.project.library.util.UartLogUtil;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class C0516b {
    public static final UUID f191a = UUID.fromString("00001530-1212-efde-1523-785feabcd123");
    public static int f192n = 2;
    public Context f193b;
    public C0514a f194c;
    public BleScanTool f195d = BleScanTool.getInstance();
    public BluetoothAdapter f196e;
    public boolean f197f = false;
    public boolean f198g = true;
    public Handler f199h = new Handler();
    public String f200i;
    public boolean f201j = true;
    public Handler f203l = new Handler();
    public BluetoothGatt f204m;
    public boolean f205o = true;
    public boolean f208r = false;
    public boolean f209s = false;
    public boolean f210t = false;
    public SimpleDateFormat f211u = new SimpleDateFormat("HH:mm:ss|SSS", Locale.CHINESE);
    public boolean f213w = false;
    public Handler f214x;

    public class C0515a extends BluetoothGattCallback {
        final /* synthetic */ C0516b f190a;

        public C0515a(C0516b c0516b) {
            this.f190a = c0516b;
        }

        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            UUID uuid = bluetoothGattCharacteristic.getUuid();
            byte[] bArr = new byte[20];
            StringBuilder stringBuilder = new StringBuilder();
            if (!ByteDataConvertUtil.bytesToHexString(bArr).startsWith("02 a0")) {
                stringBuilder.append(this.f190a.f211u.format(new Date())).append("接收").append(" : receive > ").append("[" + ByteDataConvertUtil.bytesToHexString(bArr) + "]").append("\r\n\r\n");
                UartLogUtil.recordWrite("接收", bArr);
                DebugLog.m415e(stringBuilder.toString());
            }
            if (uuid.equals(C0522b.f230e)) {

            } else if (uuid.equals(C0522b.f228c)) {

            }
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            if (i == 0) {
                byte[] value = bluetoothGattCharacteristic.getValue();
                if (value != null) {
                    DebugLog.m415e("onCharacteristicWrite:" + bluetoothGatt.getDevice().getAddress() + "[" + ByteDataConvertUtil.bytesToHexString(value) + "]");
                    StringBuilder stringBuilder = new StringBuilder();
                    if (!ByteDataConvertUtil.bytesToHexString(value).startsWith("02 a0")) {
                        stringBuilder.append(this.f190a.f211u.format(new Date())).append(" : write > ").append("[" + ByteDataConvertUtil.bytesToHexString(value) + "]").append("\r\n");
                    }
                    if (C0522b.f229d.equals(bluetoothGattCharacteristic.getUuid())) {

                        return;
                    } else if (C0522b.f227b.equals(bluetoothGattCharacteristic.getUuid())) {

                        return;
                    } else {
                        return;
                    }
                }
                DebugLog.m415e("onCharacteristicWrite error, value == null");
            }
        }

        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            DebugLog.m415e("onConnectionStateChange[" + i + "->" + i2 + "]");
            UartLogUtil.recordWrite("蓝牙状态", new byte[]{(byte) i, (byte) i2});
            if (i == 133 || i == 129) {
                C0522b.m395a(bluetoothGatt, false);
                C0522b.m397b(bluetoothGatt, false);
                this.f190a.m361b(false);
                return;
            }
            if (i == 141 && this.f190a.f196e != null && this.f190a.f196e.isEnabled()) {
                this.f190a.f196e.enable();
            }
            if (i2 == 0) {
                C0522b.m395a(bluetoothGatt, false);
                C0522b.m397b(bluetoothGatt, false);
                if (this.f190a.f208r) {
                    this.f190a.m361b(true);
                } else {
                    this.f190a.m361b(false);
                }
            } else if (i2 == 2) {
                this.f190a.m363c(false);
                if (bluetoothGatt.discoverServices()) {
                    this.f190a.m363c(true);
                } else {
                    this.f190a.m361b(false);
                }
            }
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            if (!(i == 0)) {
                this.f190a.m361b(false);
            } else if (C0522b.f231f.equals(bluetoothGattDescriptor.getUuid())) {
                boolean z = bluetoothGattDescriptor.getValue()[0] == (byte) 1;
                if (C0522b.f230e.equals(bluetoothGattDescriptor.getCharacteristic().getUuid())) {


                } else if (C0522b.f228c.equals(bluetoothGattDescriptor.getCharacteristic().getUuid()) && z) {
                    this.f190a.m363c(false);
                    if (!C0522b.m397b(bluetoothGatt, true)) {
                        this.f190a.m361b(false);
                    }
                }
            }
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            DebugLog.m415e("onServicesDiscovered : " + (i == 0));
            if (i == 0) {
                if (LibSharedPreferences.getInstance().isFirwareUpgrade()) {
                    for (BluetoothGattService uuid : bluetoothGatt.getServices()) {
                        if (uuid.getUuid().equals(C0516b.f191a)) {
                            this.f190a.f208r = true;
                            this.f190a.f213w = true;
                            this.f190a.m361b(true);
                            LocalBroadcastManager.getInstance(this.f190a.f193b).sendBroadcast(new Intent("com.veryfit.multi.ACTION_SINGLE_BANK_WARE_UPDATE"));
                        }
                    }
                }
                if (!C0522b.m395a(bluetoothGatt, true)) {
                    this.f190a.m361b(false);
                }
            }
        }
    }

    public C0516b(Context context, C0514a c0514a) {
        this.f193b = context;
        this.f194c = c0514a;
        this.f196e = this.f195d.getBluetoothAdapter();
    }

    public void m356a(BluetoothDevice bluetoothDevice) {
        if (this.f204m != null) {
            this.f204m.close();
        }
        DebugLog.m415e("connect<!samsung or nullgatt>connectGatt>>>>>" + this.f200i);
        m375h();
    }

    public void m357a(BluetoothGatt bluetoothGatt) {
        try {
            Method method = bluetoothGatt.getClass().getMethod("refresh", new Class[0]);
            if (method != null) {
                method.invoke(bluetoothGatt, new Object[0]);
            }
        } catch (Exception e) {
            DebugLog.m415e(e.getMessage());
        }
    }

    public void m361b(boolean z) {
        if (!m387d()) {
            this.f197f = false;
            m370f();
            f192n = 2;
            m363c(false);
            this.f208r = z;
            if (this.f204m != null) {
                m357a(this.f204m);
                this.f204m.close();
                this.f204m = null;
            }
            if (!this.f208r) {
                if (this.f194c != null) {
                    this.f194c.mo3452a(this.f200i);
                }
                if (this.f205o) {
                    m383b();
                }
            } else if (this.f209s) {
                if (this.f194c != null) {
                    this.f194c.mo3448a((byte) 0);
                }
                this.f209s = false;
            } else if (this.f210t) {
                if (this.f194c != null) {
                }
                this.f210t = false;
            } else {
                if (this.f194c != null) {
                    this.f194c.mo3452a(this.f200i);
                }
                this.f200i = null;
            }
            this.f208r = false;
        }
    }

    public void m363c(boolean z) {
        this.f203l.removeCallbacksAndMessages(null);
        if (z) {
            this.f203l.postDelayed(new C0518e(this), 10000);
        }
    }

    public void m370f() {

    }

    public boolean m373g() {
        BluetoothDevice remoteDevice = this.f196e.getRemoteDevice(this.f200i);
        if (remoteDevice == null) {
            return false;
        }
        if (this.f194c != null) {
            this.f194c.mo3447a();
        }
        f192n = 1;
        String str = Build.MANUFACTURER;
        if ((str.equalsIgnoreCase("ZTE") || str.equalsIgnoreCase("samsung")) && this.f204m != null) {
            Log.e("BleProxy", "connect<samsung>connect>>>>>" + this.f200i);
            this.f204m.connect();
            m375h();
        } else {
            Log.e("BleProxy", "connectGatt" + this.f200i);
            m356a(remoteDevice);
        }
        if (this.f201j) {
            m363c(true);
            return true;
        }
        this.f201j = true;
        return true;
    }

    public void m375h() {

    }

    public boolean m376i() {
        if (this.f195d.isSupportBLE()) {
            if (this.f195d.isBluetoothOpen()) {
                return true;
            }
            if (this.f194c == null) {
                return false;
            }
            this.f194c.mo3451a(-88);
            return false;
        } else if (this.f194c == null) {
            return false;
        } else {
            this.f194c.mo3451a(-99);
            return false;
        }
    }

    public boolean m377j() {
        if (this.f195d.isBluetoothOpen()) {
            return true;
        }
        if (this.f194c != null) {
            this.f194c.mo3451a(-88);
        }
        return false;
    }

    public void m378a() {
        this.f197f = false;
        this.f209s = false;
        this.f210t = false;
        this.f200i = null;
        m361b(true);
    }

    public void m379a(boolean z) {
        this.f205o = z;
    }

    public void m380a(byte[] bArr) {
        if (m386c(bArr)) {

            m384b(bArr);
        }
    }

    public boolean m381a(byte b) {
        if (!m376i()) {
            return false;
        }

        this.f197f = true;
        return this.f197f;
    }

    public boolean m382a(String str) {
        if (!this.f197f) {
            Log.e("BleProxy", "proxy not init yet !");
            UartLogUtil.recordWrite("proxy not init yet !", new byte[1]);
            m381a((byte) 0);
        }
        if (!m387d()) {
            UartLogUtil.recordWrite("device connected or connecting..", new byte[1]);
            DebugLog.m415e("device connected or connecting..");
            return false;
        } else if (m376i()) {
            if (!(m377j() || this.f196e.isEnabled())) {
                this.f196e.enable();
            }
            if (TextUtils.isEmpty(str)) {
                Log.e("BleProxy", "address is null");
                UartLogUtil.recordWrite("address is null", new byte[1]);
                return false;
            }
            this.f200i = str;
            boolean isNeedScanDevice = BleScanTool.getInstance().isNeedScanDevice();
            if (this.f198g && isNeedScanDevice) {
                this.f198g = false;
                m389e();
                Log.e("BleProxy", "needScanBeforeConnect");
                return true;
            }
            this.f198g = false;
            return m373g();
        } else {
            Log.e("BleProxy", "!isBluetoothAvailable()");
            UartLogUtil.recordWrite("!isBluetoothAvailable()", new byte[1]);
            return false;
        }
    }

    public void m383b() {
        if (this.f200i != null && m376i() && !this.f195d.isScanning()) {
            this.f199h.removeCallbacksAndMessages(null);
            this.f199h.postDelayed(new C0517d(this), 2000);
        }
    }

    public void m384b(byte[] bArr) {
        if (!m386c(bArr)) {
            return;
        }

    }

    public boolean m385c() {
        return f192n == 0;
    }

    public boolean m386c(byte[] bArr) {
        return false;
    }

    public boolean m387d() {
        return f192n == 2;
    }

    public boolean m388d(byte[] bArr) {
        return false;
    }

    public void m389e() {
        if (this.f200i != null && m376i() && !this.f195d.isScanning()) {
            LeScanCallback c0519f = new C0519f(this);
            if (this.f214x == null) {
                this.f214x = new Handler();
            }
            this.f214x.removeCallbacksAndMessages(null);
            this.f214x.postDelayed(new C0520g(this, c0519f), 2000);
            if (this.f213w) {
                this.f196e.stopLeScan(c0519f);
            }
            this.f213w = true;
            this.f196e.startLeScan(c0519f);
        }
    }
}
