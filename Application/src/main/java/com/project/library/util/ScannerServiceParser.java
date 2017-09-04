package com.project.library.util;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import java.util.UUID;

public class ScannerServiceParser {
    private static final int COMPLETE_LOCAL_NAME = 9;
    private static final int COMPLETE_MANUFACTURER = -1;
    private static final int FLAGS_BIT = 1;
    private static final byte LE_GENERAL_DISCOVERABLE_MODE = (byte) 2;
    private static final byte LE_LIMITED_DISCOVERABLE_MODE = (byte) 1;
    private static final int SERVICES_COMPLETE_LIST_128_BIT = 7;
    private static final int SERVICES_COMPLETE_LIST_16_BIT = 3;
    private static final int SERVICES_COMPLETE_LIST_32_BIT = 5;
    private static final int SERVICES_MORE_AVAILABLE_128_BIT = 6;
    private static final int SERVICES_MORE_AVAILABLE_16_BIT = 2;
    private static final int SERVICES_MORE_AVAILABLE_32_BIT = 4;
    private static final int SHORTENED_LOCAL_NAME = 8;
    private static final String TAG = "ScannerServiceParser";

    public static boolean decodeDeviceAdvData(byte[] bArr, UUID uuid) {
        String uuid2 = uuid != null ? uuid.toString() : null;
        if (bArr == null) {
            return false;
        }
        boolean z = uuid2 == null;
        int length = bArr.length;
        int i = 0;
        boolean z2 = false;
        while (i < length) {
            byte b = bArr[i];
            if (b == (byte) 0) {
                return z2 && z;
            } else {
                boolean z3;
                int i2 = i + 1;
                byte b2 = bArr[i2];
                if (uuid2 != null) {
                    if (b2 == (byte) 2 || b2 == (byte) 3) {
                        for (i = i2 + 1; i < (i2 + b) - 1; i += 2) {
                            z = z || decodeService16BitUUID(uuid2, bArr, i, 2);
                        }
                        z3 = z;
                        z = b2 == (byte) 1 ? (bArr[i2 + 1] & 3) > 0 : z2;
                        z2 = z;
                        z = z3;
                        i = ((b - 1) + i2) + 1;
                    } else if (b2 == (byte) 4 || b2 == (byte) 5) {
                        for (i = i2 + 1; i < (i2 + b) - 1; i += 4) {
                            z = z || decodeService32BitUUID(uuid2, bArr, i, 4);
                        }
                        z3 = z;
                        if (b2 == (byte) 1) {
                        }
                        z2 = z;
                        z = z3;
                        i = ((b - 1) + i2) + 1;
                    } else if (b2 == (byte) 6 || b2 == (byte) 7) {
                        for (i = i2 + 1; i < (i2 + b) - 1; i += 16) {
                            z = z || decodeService128BitUUID(uuid2, bArr, i, 16);
                        }
                    }
                }
                z3 = z;
                if (b2 == (byte) 1) {
                    if ((bArr[i2 + 1] & 3) > 0) {
                    }
                }
                z2 = z;
                z = z3;
                i = ((b - 1) + i2) + 1;
            }
        }
        return z2 && z;
    }

    public static String decodeDeviceName(byte[] bArr) {
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            byte b = bArr[i];
            if (b == (byte) 0) {
                return null;
            }
            i++;
            byte b2 = bArr[i];
            if (b2 == (byte) 9 || b2 == (byte) 8) {
                return decodeLocalName(bArr, i + 1, b - 1);
            }
            i = (i + (b - 1)) + 1;
        }
        return null;
    }

    public static String decodeLocalName(byte[] bArr, int i, int i2) {
        try {
            return new String(bArr, i, i2, "UTF-8");
        } catch (Throwable e) {
            Log.e(TAG, "Unable to convert the complete local name to UTF-8", e);
            return null;
        }
    }

    public static byte[] decodeManufacturer(byte[] bArr) {
        byte[] bArr2 = new byte[62];
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            byte b = bArr[i];
            if (b == (byte) 0) {
                return null;
            }
            i++;
            if (bArr[i] == (byte) -1) {
                ByteDataConvertUtil.BinnCat(bArr, bArr2, i + 1, b - 1);
                byte[] bArr3 = new byte[(b - 1)];
                ByteDataConvertUtil.BinnCat(bArr2, bArr3, 0, b - 1);
                return bArr3;
            }
            i = (i + (b - 1)) + 1;
        }
        return null;
    }

    private static boolean decodeService128BitUUID(String str, byte[] bArr, int i, int i2) {
        return String.format("%04x", new Object[]{Integer.valueOf(decodeUuid16(bArr, (i + i2) - 4))}).equalsIgnoreCase(str.substring(4, 8));
    }

    private static boolean decodeService16BitUUID(String str, byte[] bArr, int i, int i2) {
        String format = String.format("%04x", new Object[]{Integer.valueOf(decodeUuid16(bArr, i))});
        String substring = str.substring(4, 8);
        Log.v(TAG, new StringBuilder(String.valueOf(substring)).append("--16--").append(format).toString());
        return format.equalsIgnoreCase(substring);
    }

    private static boolean decodeService32BitUUID(String str, byte[] bArr, int i, int i2) {
        String format = String.format("%04x", new Object[]{Integer.valueOf(decodeUuid16(bArr, (i + i2) - 4))});
        String substring = str.substring(4, 8);
        Log.v(TAG, new StringBuilder(String.valueOf(substring)).append("--32--").append(format).toString());
        return format.equalsIgnoreCase(substring);
    }

    private static int decodeUuid16(byte[] bArr, int i) {
        return ((bArr[i] & MotionEventCompat.ACTION_MASK) << 0) | ((bArr[i + 1] & MotionEventCompat.ACTION_MASK) << 8);
    }
}
