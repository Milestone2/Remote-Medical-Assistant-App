package com.project.library.util;

import android.support.v4.view.MotionEventCompat;
import java.lang.reflect.Array;

public class ByteDataConvertUtil {
    public static int BinToInt(byte[] bArr, int i, int i2) {
        int i3 = 0;
        int i4 = i2 - 1;
        while (i4 >= 0) {
            i3 = (i3 << 8) | (bArr[i] & MotionEventCompat.ACTION_MASK);
            i4--;
            i++;
        }
        return i3;
    }

    public static long BinToLong(byte[] bArr, int i, int i2) {
        long j = 0;
        int i3 = i2 - 1;
        while (i3 >= 0) {
            j = (j << 8) | ((long) (bArr[i] & MotionEventCompat.ACTION_MASK));
            i3--;
            i++;
        }
        return j;
    }

    public static void BinnCat(byte[] bArr, byte b, int i, int i2) {
        int i3 = i + i2;
        int i4 = 0;
        while (i < i3) {
            byte b2 = bArr[i4];
            i++;
            i4++;
        }
    }

    public static void BinnCat(byte[] bArr, byte[] bArr2, int i, int i2) {
        int i3 = 0;
        while (i3 < i2) {
            bArr2[i3] = bArr[i];
            i3++;
            i++;
        }
    }

    public static int Bit8Array2Int(byte[] bArr) {
        int length = bArr.length;
        int i = 0;
        for (int i2 = length - 1; i2 >= 0; i2--) {
            i += bArr[i2] << ((length - 1) - i2);
        }
        return i;
    }

    public static int Byte2Int(byte b) {
        return b >= (byte) 0 ? b : (b + 128) + 128;
    }

    public static byte[] Int2Bit8(int i) {
        int i2 = (byte) i;
        byte[] bArr = new byte[8];
        for (int i3 = 0; i3 <= 7; i3++) {
            bArr[i3] = (byte) (i2 & 1);
            i2 = (byte) (i2 >> 1);
        }
        return bArr;
    }

    public static byte Int2Byte(int i) {
        return (byte) (i & MotionEventCompat.ACTION_MASK);
    }

    public static byte[] IntToBin(int i, int i2) {
        byte[] bArr = new byte[i2];
        int i3 = i2 - 1;
        int i4 = 0;
        while (i3 >= 0) {
            bArr[i4] = (byte) (i >> (i3 * 8));
            i3--;
            i4++;
        }
        return bArr;
    }

    public static byte[] IntToBin(int i, byte[] bArr, int i2, int i3) {
        int i4 = i3 - 1;
        while (i4 >= 0) {
            bArr[i2] = (byte) (i >> (i4 * 8));
            i4--;
            i2++;
        }
        return bArr;
    }

    public static void LongToBin(long j, byte[] bArr, int i, int i2) {
        int i3 = i2 - 1;
        while (i3 >= 0) {
            bArr[i] = (byte) ((int) (j >> (i3 * 8)));
            i3--;
            i++;
        }
    }

    public static byte[] LongToBin(long j, int i) {
        byte[] bArr = new byte[i];
        int i2 = i - 1;
        int i3 = 0;
        while (i2 >= 0) {
            bArr[i3] = (byte) ((int) (j >> (i2 * 8)));
            i2--;
            i3++;
        }
        return bArr;
    }

    public static byte[] byteMerger(byte[] bArr, byte[] bArr2) {
        Object obj = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, obj, 0, bArr.length);
        System.arraycopy(bArr2, 0, obj, bArr.length, bArr2.length);
        return (byte[]) obj;
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & MotionEventCompat.ACTION_MASK);
            if (toHexString.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(toHexString);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public static String[] bytesToHexStrings(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        String[] strArr = new String[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            String toHexString = Integer.toHexString(bArr[i] & MotionEventCompat.ACTION_MASK);
            if (toHexString.length() == 1) {
                toHexString = new StringBuilder(String.valueOf(0)).append(toHexString).toString();
            }
            strArr[i] = toHexString;
        }
        return strArr;
    }

    public static byte[] getMacBytes(String str) {
        byte[] bArr = new byte[6];
        String[] split = str.split(":");
        for (int i = 0; i < split.length; i++) {
            bArr[i] = (byte) Integer.parseInt(split[i], 16);
        }
        return bArr;
    }


    public static byte i2b(int i) {
        return (byte) Integer.toHexString(i).charAt(0);
    }

    public static Object invertArray(Object obj) {
        int i = 0;
        int length = Array.getLength(obj);
        Object newInstance = Array.newInstance(obj.getClass().getComponentType(), length);
        System.arraycopy(obj, 0, newInstance, 0, length);
        while (i < length / 2) {
            Object obj2 = Array.get(newInstance, i);
            Array.set(newInstance, i, Array.get(newInstance, (length - i) - 1));
            Array.set(newInstance, (length - i) - 1, obj2);
            i++;
        }
        return newInstance;
    }

    public static int toRevInt(byte[] bArr, int i, int i2) {
        int i3 = 0;
        int i4 = (i + i2) - 1;
        int i5 = 0;
        while (i3 < i2) {
            i5 = (i5 << 8) | (bArr[i4] & MotionEventCompat.ACTION_MASK);
            i3++;
            i4--;
        }
        return i5;
    }
}
