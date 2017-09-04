package com.project.library.util;

import android.os.Handler;
import java.lang.ref.WeakReference;

public abstract class WeakHandler extends Handler {
    private WeakReference mOwner;

    public WeakHandler(Object obj) {
        this.mOwner = new WeakReference(obj);
    }

    public Object getOwner() {
        return this.mOwner.get();
    }
}
