package com.project.library.util;

import com.veryfit.multi.util.Constant;
import java.util.concurrent.CopyOnWriteArrayList;

public final class ArrayUtils {
    public static boolean contains(byte[] bArr, byte b) {
        for (byte b2 : bArr) {
            if (b2 == b) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsForNum(CopyOnWriteArrayList copyOnWriteArrayList) {
        for (int i = 0; i < copyOnWriteArrayList.size(); i++) {
            if (((String) copyOnWriteArrayList.get(i)).contains(Constant.DEVICE_NAME5)) {
                return true;
            }
        }
        return false;
    }
}
