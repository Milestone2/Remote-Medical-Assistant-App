package com.project.library.entity;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class BleDevice implements Serializable, Comparable {
    private static final long serialVersionUID = -5217710157640312976L;
    public String mDeviceAddress;
    public int mDeviceId;
    public String mDeviceName;
    public int mId;
    public int mIs;
    public int mLen;
    public int mRssi;

    public int compareTo(BleDevice bleDevice) {
        return this.mRssi > bleDevice.mRssi ? -1 : this.mRssi < bleDevice.mRssi ? 1 : 0;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
