package com.project.library.core;

import java.util.List;

public interface CoreServiceListener {
    void onAppControlSuccess(byte b, boolean z);

    void onBLEConnectTimeOut();

    void onBLEConnected();

    void onBLEConnecting();

    void onBLEDisConnected(String str);

    void onBindUnbind(byte b);

    void onBleControlReceive(byte b, byte b2);

    void onBlueToothError(int i);

    void onCoreServiceConnected();

    void onCoreServiceDisconnected();

    void onDataChanged(List list);

    void onDataReceived(byte[] bArr);

    void onDataSendTimeOut(byte[] bArr);

    void onGetInfo(byte b);

    void onOtherDataReceive(byte[] bArr);

    void onSettingsSuccess(byte b, boolean z);

    void onSyncData(int i);

    void onWareUpdate(byte b);
}
