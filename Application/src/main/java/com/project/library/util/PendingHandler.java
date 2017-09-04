package com.project.library.util;

import android.os.Handler;
import android.os.Looper;

public class PendingHandler extends Handler {
    private boolean mPending = false;

    public PendingHandler(Looper looper) {
        super(looper);
    }

    private synchronized void pending(boolean z) {
        this.mPending = z;
    }

    public synchronized boolean pending() {
        return this.mPending;
    }

    public synchronized boolean postF(final Runnable runnable) {
        boolean post;
        removeCallbacksAndMessages(null);
        post = post(new Runnable() {
            public void run() {
                runnable.run();
                PendingHandler.this.pending(false);
            }
        });
        if (post) {
            this.mPending = true;
        }
        return post;
    }

    public synchronized boolean postT(final Runnable runnable) {
        boolean z;
        z = false;
        if (!this.mPending) {
            z = post(new Runnable() {
                public void run() {
                    runnable.run();
                    PendingHandler.this.pending(false);
                }
            });
            if (z) {
                this.mPending = true;
            }
        }
        return z;
    }
}
