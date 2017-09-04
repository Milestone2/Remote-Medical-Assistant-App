package com.project.library.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import com.project.library.ble.BleConnectService;
import com.project.library.core.ICoreServiceCallback.Stub;
import com.project.library.util.DebugLog;
import com.project.library.util.UartLogUtil;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CoreServiceProxy {
    private static CoreServiceProxy sInstance = null;
    private final Stub mCallback = new C17111();
    private final ServiceConnection mConnection = new C05372();
    private final Context mContext;
    private ICoreService mCoreService;
    private final Handler mHandler;
    private CopyOnWriteArrayList mListeners = new CopyOnWriteArrayList();

    class C05372 implements ServiceConnection {
        C05372() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            DebugLog.m415e("service connected.");
            CoreServiceProxy.this.mCoreService = ICoreService.Stub.asInterface(iBinder);
            try {
                CoreServiceProxy.this.mCoreService.registerCallback(CoreServiceProxy.this.mCallback);
            } catch (RemoteException e) {
                DebugLog.m415e("remote call failed. (registerCallback)");
            }
            Iterator it = CoreServiceProxy.this.mListeners.iterator();
            while (it.hasNext()) {
                ((CoreServiceListener) it.next()).onCoreServiceConnected();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            DebugLog.m415e("service disconnected.");
            Iterator it = CoreServiceProxy.this.mListeners.iterator();
            while (it.hasNext()) {
                ((CoreServiceListener) it.next()).onCoreServiceDisconnected();
            }
            CoreServiceProxy.this.mCoreService = null;
        }
    }

    class C17111 extends Stub {

        class C05292 implements Runnable {
            C05292() {
            }

            public void run() {
                DebugLog.m415e("onBLEConnecting.");
                Iterator it = CoreServiceProxy.this.mListeners.iterator();
                while (it.hasNext()) {
                    ((CoreServiceListener) it.next()).onBLEConnecting();
                }
            }
        }

        class C05303 implements Runnable {
            C05303() {
            }

            public void run() {
                DebugLog.m415e("onBLEConnected.");
                Iterator it = CoreServiceProxy.this.mListeners.iterator();
                while (it.hasNext()) {
                    ((CoreServiceListener) it.next()).onBLEConnected();
                }
            }
        }

        class C05325 implements Runnable {
            C05325() {
            }

            public void run() {
                DebugLog.m415e("onBLEConnectTimeOut.");
                Iterator it = CoreServiceProxy.this.mListeners.iterator();
                while (it.hasNext()) {
                    ((CoreServiceListener) it.next()).onBLEConnectTimeOut();
                }
            }
        }

        C17111() {
        }

        public void onAppControlSuccess(final byte b, final boolean z) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onAppControlSuccess.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onAppControlSuccess(b, z);
                    }
                }
            });
        }

        public void onBLEConnectTimeOut() throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new C05325());
        }

        public void onBLEConnected() throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new C05303());
        }

        public void onBLEConnecting() throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new C05292());
        }

        public void onBLEDisConnected(final String str) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onBLEDisConnected.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onBLEDisConnected(str);
                    }
                }
            });
        }

        public void onBindUnbind(final byte b) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onBindUnbind.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onBindUnbind(b);
                    }
                }
            });
        }

        public void onBleControlReceive(final byte b, final byte b2) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onBleControlReceive.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onBleControlReceive(b, b2);
                    }
                }
            });
        }

        public void onBlueToothError(final int i) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onBlueToothError.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onBlueToothError(i);
                    }
                }
            });
        }

        public void onDataChanged(final List list) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onDataChanged.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onDataChanged(list);
                    }
                }
            });
        }

        public void onDataReceived(final byte[] bArr) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onDataReceived.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onDataReceived(bArr);
                    }
                }
            });
        }

        public void onDataSendTimeOut(final byte[] bArr) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onDataSendTimeOut.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onDataSendTimeOut(bArr);
                    }
                }
            });
        }

        public void onGetInfo(final byte b) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onGetInfo.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onGetInfo(b);
                    }
                }
            });
        }

        public void onOtherDataReceive(final byte[] bArr) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onOtherDataReceive.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onOtherDataReceive(bArr);
                    }
                }
            });
        }

        public void onSettingsSuccess(final byte b, final boolean z) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onSettingsSuccess.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onSettingsSuccess(b, z);
                    }
                }
            });
        }

        public void onSyncData(final int i) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onSyncData." + i);
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onSyncData(i);
                    }
                }
            });
        }

        public void onWareUpdate(final byte b) throws RemoteException {
            CoreServiceProxy.this.mHandler.post(new Runnable() {
                public void run() {
                    DebugLog.m415e("onWareUpdate.");
                    Iterator it = CoreServiceProxy.this.mListeners.iterator();
                    while (it.hasNext()) {
                        ((CoreServiceListener) it.next()).onWareUpdate(b);
                    }
                }
            });
        }
    }

    private CoreServiceProxy(Context context) {
        DebugLog.m415e("construction.");
        this.mContext = context;
        this.mHandler = new Handler();
        this.mCoreService = null;
        Intent intent = new Intent(this.mContext, BleConnectService.class);
        intent.setAction("com.project.library.ble.ConnectService");
        if (this.mContext.bindService(intent, this.mConnection, 1)) {
            DebugLog.m415e("bind core service success.");
        } else {
            DebugLog.m415e("bind core service failed.");
        }
    }

    private void clearListener() {
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            this.mListeners.remove((CoreServiceListener) it.next());
        }
    }

    private void close() {
        DebugLog.m415e("proxy closed.");
        clearListener();
        this.mContext.unbindService(this.mConnection);
    }

    public static void fini() {
        if (sInstance != null) {
            sInstance.close();
            sInstance = null;
        }
    }

    public static CoreServiceProxy getInstance() {
        return sInstance;
    }

    public static void init(Context context) {
        sInstance = new CoreServiceProxy(context);
    }

    public void addListener(CoreServiceListener coreServiceListener) {
        if (coreServiceListener != null && !this.mListeners.contains(coreServiceListener)) {
            this.mListeners.add(coreServiceListener);
        }
    }

    public boolean connect(String str) {
        boolean z = false;
        if (isAvailable()) {
            try {
                z = this.mCoreService.connect(str);
            } catch (RemoteException e) {
                DebugLog.m415e("remote call failed. (connect)");
                UartLogUtil.recordWrite("remote call failed. (connect)", new byte[1]);
            }
        } else {
            DebugLog.m415e("Service is unAvailable");
            UartLogUtil.recordWrite("Service is unAvailable", new byte[1]);
        }
        return z;
    }

    public boolean disconnect() {
        boolean z = false;
        if (isAvailable()) {
            try {
                z = this.mCoreService.disconnect();
            } catch (RemoteException e) {
                DebugLog.m415e("remote call failed. (disconnect)");
            }
        } else {
            DebugLog.m415e("Service is unAvailable");
        }
        return z;
    }

    public boolean initBLE(byte b) {
        boolean z = false;
        if (isAvailable()) {
            try {
                z = this.mCoreService.initBLE(b);
            } catch (RemoteException e) {
                DebugLog.m415e("remote call failed. (initBLE)");
            }
        } else {
            DebugLog.m415e("Service is unAvailable");
        }
        return z;
    }

    public boolean isAvailable() {
        return this.mCoreService != null;
    }

    public boolean isDeviceConnected() {
        boolean z = false;
        if (isAvailable()) {
            try {
                z = this.mCoreService.isDeviceConnected();
            } catch (RemoteException e) {
                DebugLog.m415e("remote call failed. (isDeviceConnected)");
            }
        } else {
            DebugLog.m415e("Service is unAvailable");
        }
        return z;
    }

    public void removeListener(CoreServiceListener coreServiceListener) {
        if (coreServiceListener != null && !this.mListeners.isEmpty()) {
            this.mListeners.remove(coreServiceListener);
        }
    }

    public boolean write(byte[] bArr) {
        boolean z = false;
        if (isAvailable()) {
            try {
                z = this.mCoreService.write(bArr);
            } catch (RemoteException e) {
                DebugLog.m415e("remote call failed. (write)");
            }
        } else {
            DebugLog.m415e("Service is unAvailable");
        }
        return z;
    }

    public boolean writeForce(byte[] bArr) {
        boolean z = false;
        if (isAvailable()) {
            try {
                z = this.mCoreService.writeForce(bArr);
            } catch (RemoteException e) {
                DebugLog.m415e("remote call failed. (writeForce)");
            }
        } else {
            DebugLog.m415e("Service is unAvailable");
        }
        return z;
    }
}
