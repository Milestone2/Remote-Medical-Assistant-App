package com.project.library.util;

import android.util.Log;

public class DFUServiceParser {
    private static DFUServiceParser mParserInstance;
    private final String DFU_SERVICE_UUID = "2148";
    private final int SERVICE_CLASS_128BIT_UUID = 6;
    private final String TAG = "DFUServiceParser";
    private boolean isValidDFUSensor = false;
    private int packetLength = 0;

    private void decodeService128BitUUID(byte[] bArr, int i, int i2) throws Exception {
        Log.e("DFUServiceParser", "StartPosition: " + i + " Data length: " + i2);
        if (new StringBuilder(String.valueOf(Byte.toString(bArr[(i + i2) - 3]))).append(Byte.toString(bArr[(i + i2) - 4])).toString().equals("2148")) {
            this.isValidDFUSensor = true;
        }
    }

    public static synchronized DFUServiceParser getDFUParser() {
        DFUServiceParser dFUServiceParser;
        synchronized (DFUServiceParser.class) {
            if (mParserInstance == null) {
                mParserInstance = new DFUServiceParser();
            }
            dFUServiceParser = mParserInstance;
        }
        return dFUServiceParser;
    }

    public void decodeDFUAdvData(byte[] bArr) throws Exception {
        int i = 0;
        this.isValidDFUSensor = false;
        if (bArr != null) {
            this.packetLength = bArr.length;
            while (i < this.packetLength) {
                byte b = bArr[i];
                if (b != (byte) 0) {
                    i++;
                    if (bArr[i] == (byte) 6) {
                        decodeService128BitUUID(bArr, i + 1, b - 1);
                        i += b - 1;
                    } else {
                        i += b - 1;
                    }
                    i++;
                } else {
                    return;
                }
            }
            return;
        }
        Log.e("DFUServiceParser", "data is null!");
    }

    public boolean isValidDFUSensor() {
        return this.isValidDFUSensor;
    }
}
