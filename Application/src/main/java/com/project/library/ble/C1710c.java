package com.project.library.ble;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import com.project.library.core.ICoreService.Stub;
import com.project.library.core.ICoreServiceCallback;
import com.project.library.p002a.C0514a;
import com.project.library.p002a.C0516b;
import com.project.library.util.DebugLog;
import com.project.library.util.PendingHandler;
import com.project.library.util.UartLogUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class C1710c extends Stub {
    public static final Class[] f1714k = new Class[0];
    public static final Class[] f1715l = new Class[]{Integer.TYPE};
    public static final Class[] f1716m = new Class[]{String.class};
    public static final Class[] f1717n = new Class[]{Byte.TYPE};
    public static final Class[] f1718o = new Class[]{Byte.TYPE, Byte.TYPE};
    public static final Class[] f1719p = new Class[]{byte[].class};
    public static final Class[] f1720q = new Class[]{Byte.TYPE, Boolean.TYPE};
    public static final Class[] f1721r = new Class[]{List.class};
    public Context f1722a;
    public HandlerThread f1723b;
    public PendingHandler f1724c;
    public C0516b f1725d;
    public RemoteCallbackList f1726e;
    public HandlerThread f1727f;
    public Handler f1728g;
    public boolean f1729h;
    public StringBuilder f1730i;
    public SimpleDateFormat f1731j;
    public C0514a f1732s;

    public C1710c(Context context) {
        this.f1729h = false;
        this.f1730i = new StringBuilder();
        this.f1731j = new SimpleDateFormat("HH:mm:ss", Locale.CHINESE);
        this.f1732s = new C1155d(this);
        this.f1729h = false;
        this.f1722a = context;
        this.f1723b = new HandlerThread("BLECore_PendingHandler");
        this.f1723b.start();
        this.f1724c = new PendingHandler(this.f1723b.getLooper());
        this.f1727f = new HandlerThread("OperationHandlerThread");
        this.f1727f.start();
        this.f1728g = new Handler(this.f1727f.getLooper());
        this.f1725d = new C0516b(this.f1722a, this.f1732s);
        this.f1726e = new RemoteCallbackList();
    }

    public void m3076a(String str, Class[] clsArr, Object... objArr) {
        if (!this.f1729h) {
            synchronized (this.f1726e) {
                Method method;
                DebugLog.m415e("broadcast [" + str + "] begin.");
                Method method2 = null;
                try {
                    method = ICoreServiceCallback.class.getMethod(str, clsArr);
                } catch (SecurityException e) {
                    method = method2;
                } catch (NoSuchMethodException e2) {
                    method = method2;
                }
                int beginBroadcast = this.f1726e.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        method.invoke(this.f1726e.getBroadcastItem(i), objArr);
                    } catch (IllegalArgumentException e3) {
                    } catch (IllegalAccessException e4) {
                    } catch (InvocationTargetException e5) {
                    }
                }
                this.f1726e.finishBroadcast();
                DebugLog.m415e("broadcast end.");
            }
        }
    }

    public void m3084a() {
        DebugLog.m415e("closed.");
        this.f1729h = true;
        this.f1723b.quit();
        this.f1727f.quit();
        this.f1726e.kill();
        this.f1725d.m378a();
    }

    public void m3085a(boolean z) {
        this.f1728g.post(new C0523e(this, z));
    }

    public void m3086b(boolean z) {
        this.f1725d.m379a(z);
        String str = "onBlueToothError";
        Class[] clsArr = f1715l;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(z ? -66 : -55);
        m3076a(str, clsArr, objArr);
    }

    public synchronized boolean connect(String str) throws RemoteException {
        this.f1728g.post(new C0524f(this, str));
        return true;
    }

    public synchronized boolean disconnect() throws RemoteException {
        this.f1728g.post(new C0525g(this));
        return true;
    }

    public boolean initBLE(byte b) throws RemoteException {
        if (this.f1725d.m381a(b)) {
            UartLogUtil.recordWrite("蓝牙初始化成功", new byte[]{(byte) 1});
        } else {
            UartLogUtil.recordWrite("蓝牙初始化失败", new byte[1]);
        }
        return this.f1725d.m381a(b);
    }

    public boolean isDeviceConnected() throws RemoteException {
        return this.f1725d.m385c();
    }

    public void registerCallback(ICoreServiceCallback iCoreServiceCallback) throws RemoteException {
        this.f1726e.register(iCoreServiceCallback);
    }

    public void unregisterCallback(ICoreServiceCallback iCoreServiceCallback) throws RemoteException {
        this.f1726e.unregister(iCoreServiceCallback);
    }

    public synchronized boolean write(byte[] bArr) throws RemoteException {
        boolean z = false;
        synchronized (this) {
            if (this.f1729h || !this.f1725d.m386c(bArr) || !this.f1725d.m388d(bArr)) {
                DebugLog.m415e("device closed or can not send next command.");
            } else if (this.f1724c.pending()) {
                DebugLog.m415e("invalid state.(write)");
            } else {
                z = this.f1724c.postT(new C0526h(this, bArr));
            }
        }
        return z;
    }

    public synchronized boolean writeForce(byte[] bArr) throws RemoteException {
        boolean z;
        if (this.f1729h || !this.f1725d.m386c(bArr)) {
            DebugLog.m415e("device closed.");
            z = false;
        } else {
            z = this.f1724c.postF(new C0527i(this, bArr));
        }
        return z;
    }
}
