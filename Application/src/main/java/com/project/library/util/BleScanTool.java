package com.project.library.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.Log;
import com.project.library.entity.BleDevice;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BleScanTool {
    public static final UUID RX_UPDATE_UUID = UUID.fromString("00001530-1212-efde-1523-785feabcd123");
    private static final long SCAN_PERIOD = 8000;
    private static BleScanTool instance = null;
    private boolean filterByService;
    private boolean isScaning = false;
    private ConcurrentHashMap mBleDeviceMap = new ConcurrentHashMap();
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager;
    private Context mCtx;
    private DFUServiceParser mDFUServiceParser = DFUServiceParser.getDFUParser();
    private CopyOnWriteArrayList mFilterNameList = new CopyOnWriteArrayList();
    private Handler mHandler = new Handler();
    private LeScanCallback mLeScanCallback = new C05481();
    private LeScanCallback mLeScanCallbackDFU = new C05492();
    private CopyOnWriteArrayList mScanDeviceListenerList = new CopyOnWriteArrayList();
    private UUID requiredUUID;

    class C05481 implements LeScanCallback {
        C05481() {
        }

        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            try {
                BleScanTool.this.mDFUServiceParser.decodeDFUAdvData(bArr);
                byte[] decodeManufacturer;
                String name;
                if (BleScanTool.this.mDFUServiceParser.isValidDFUSensor()) {
                    decodeManufacturer = ScannerServiceParser.decodeManufacturer(bArr);
                    if (decodeManufacturer != null) {
                        Log.e("BleScanTool", ByteDataConvertUtil.bytesToHexString(decodeManufacturer));
                        BleDevice bleDevice = new BleDevice();
                        bleDevice.mLen = 0;
                        bleDevice.mId = 0;
                        bleDevice.mIs = 0;
                        if (decodeManufacturer.length == 13) {
                            Log.e("BleScanTool", "设备ID---------：" + (decodeManufacturer[10] & MotionEventCompat.ACTION_MASK));
                            Log.e("BleScanTool", "设备IS---------：" + (decodeManufacturer[11] & MotionEventCompat.ACTION_MASK));
                            Log.e("BleScanTool", "设备ID--IS---------：" + String.format("%x", new Object[]{Byte.valueOf(decodeManufacturer[10])}) + "--" + String.format("%x", new Object[]{Byte.valueOf(decodeManufacturer[11])}));
                            int i2 = decodeManufacturer[10] & MotionEventCompat.ACTION_MASK;
                            int i3 = decodeManufacturer[11] & MotionEventCompat.ACTION_MASK;
                            if (i2 == 10 && i3 == 240) {
                                bleDevice.mId = i2;
                                bleDevice.mIs = i3;
                                bleDevice.mLen = decodeManufacturer.length;
                            }
                        }
                        name = bluetoothDevice.getName();
                        if (TextUtils.isEmpty(name)) {
                            name = ScannerServiceParser.decodeDeviceName(bArr);
                            if (TextUtils.isEmpty(name)) {
                                return;
                            }
                        }
                        if (ScannerServiceParser.decodeDeviceAdvData(bArr, BleScanTool.RX_UPDATE_UUID)) {
                            String address = bluetoothDevice.getAddress();
                            Log.e("BleScanTool", "address:" + address);
                            if (!BleScanTool.this.mBleDeviceMap.containsKey(address)) {
                                bleDevice.mDeviceName = name;
                                bleDevice.mDeviceAddress = address;
                                bleDevice.mRssi = i;
                                bleDevice.mDeviceId = (decodeManufacturer[0] & MotionEventCompat.ACTION_MASK) | (decodeManufacturer[1] << 8);
                                BleScanTool.this.mBleDeviceMap.put(address, bleDevice);
                                Iterator it = BleScanTool.this.mScanDeviceListenerList.iterator();
                                while (it.hasNext()) {
                                    ((ScanDeviceListener) it.next()).onFind(bleDevice);
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                boolean decodeDeviceAdvData;
                CharSequence name2 = bluetoothDevice.getName();
                if (TextUtils.isEmpty(name2)) {
                    name2 = ScannerServiceParser.decodeDeviceName(bArr);
                    if (TextUtils.isEmpty(name2)) {
                        return;
                    }
                }
                String charSequence = name2.toString();
                if (BleScanTool.this.filterByService) {
                    decodeDeviceAdvData = ScannerServiceParser.decodeDeviceAdvData(bArr, BleScanTool.this.requiredUUID);
                } else {
                    boolean isEmpty = BleScanTool.this.mFilterNameList.isEmpty();
                    Iterator it2 = BleScanTool.this.mFilterNameList.iterator();
                    while (it2.hasNext()) {
                        if (charSequence.toUpperCase(Locale.US).contains(((String) it2.next()).toUpperCase(Locale.US))) {
                            decodeDeviceAdvData = true;
                            break;
                        }
                    }
                    decodeDeviceAdvData = isEmpty;
                }
                decodeManufacturer = new byte[20];
                if (decodeDeviceAdvData) {
                    name = bluetoothDevice.getAddress();
                    if (!BleScanTool.this.mBleDeviceMap.containsKey(name)) {
                        BleDevice bleDevice2 = new BleDevice();
                        bleDevice2.mDeviceName = charSequence;
                        bleDevice2.mDeviceAddress = name;
                        bleDevice2.mRssi = i;
                        BleScanTool.this.mBleDeviceMap.put(name, bleDevice2);
                        Iterator it3 = BleScanTool.this.mScanDeviceListenerList.iterator();
                        while (it3.hasNext()) {
                            ((ScanDeviceListener) it3.next()).onFind(bleDevice2);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("BleScanTool", "Invalid data in Advertisement packet " + e.toString());
            }
        }
    }

    class C05492 implements LeScanCallback {
        C05492() {
        }

        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
            try {
                BleScanTool.this.mDFUServiceParser.decodeDFUAdvData(bArr);
                if (BleScanTool.this.mDFUServiceParser.isValidDFUSensor()) {
                    byte[] decodeManufacturer = ScannerServiceParser.decodeManufacturer(bArr);
                    if (decodeManufacturer != null) {
                        Log.e("BleScanTool", ByteDataConvertUtil.bytesToHexString(decodeManufacturer));
                        BleDevice bleDevice = new BleDevice();
                        bleDevice.mLen = 0;
                        bleDevice.mId = 0;
                        bleDevice.mIs = 0;
                        if (decodeManufacturer.length == 13) {
                            Log.e("BleScanTool", "设备ID---------：" + (decodeManufacturer[10] & MotionEventCompat.ACTION_MASK));
                            Log.e("BleScanTool", "设备IS---------：" + (decodeManufacturer[11] & MotionEventCompat.ACTION_MASK));
                            Log.e("BleScanTool", "设备ID--IS---------：" + String.format("%x", new Object[]{Byte.valueOf(decodeManufacturer[10])}) + "--" + String.format("%x", new Object[]{Byte.valueOf(decodeManufacturer[11])}));
                            int i2 = decodeManufacturer[10] & MotionEventCompat.ACTION_MASK;
                            int i3 = decodeManufacturer[11] & MotionEventCompat.ACTION_MASK;
                            if (i2 == 10 && i3 == 240) {
                                bleDevice.mId = i2;
                                bleDevice.mIs = i3;
                                bleDevice.mLen = decodeManufacturer.length;
                            }
                        }
                        if (ScannerServiceParser.decodeDeviceAdvData(bArr, BleScanTool.RX_UPDATE_UUID)) {
                            String address = bluetoothDevice.getAddress();
                            Log.e("BleScanTool", "address:" + address);
                            if (!BleScanTool.this.mBleDeviceMap.containsKey(address)) {
                                bleDevice.mDeviceName = bluetoothDevice.getName();
                                bleDevice.mDeviceAddress = address;
                                bleDevice.mRssi = i;
                                bleDevice.mDeviceId = (decodeManufacturer[1] << 8) | decodeManufacturer[0];
                                BleScanTool.this.mBleDeviceMap.put(address, bleDevice);
                                Iterator it = BleScanTool.this.mScanDeviceListenerList.iterator();
                                while (it.hasNext()) {
                                    ((ScanDeviceListener) it.next()).onFind(bleDevice);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("BleScanTool", "Invalid data in Advertisement packet " + e.toString());
            }
        }
    }

    class C05503 implements Runnable {
        C05503() {
        }

        public void run() {
            BleScanTool.this.mBluetoothAdapter.stopLeScan(BleScanTool.this.mLeScanCallback);
            BleScanTool.this.filterByService = false;
            BleScanTool.this.isScaning = false;
            Iterator it = BleScanTool.this.mScanDeviceListenerList.iterator();
            while (it.hasNext()) {
                ((ScanDeviceListener) it.next()).onFinish();
            }
        }
    }

    class C05514 implements Runnable {
        C05514() {
        }

        public void run() {
            BleScanTool.this.mBluetoothAdapter.stopLeScan(BleScanTool.this.mLeScanCallbackDFU);
            BleScanTool.this.filterByService = false;
            BleScanTool.this.isScaning = false;
            Iterator it = BleScanTool.this.mScanDeviceListenerList.iterator();
            while (it.hasNext()) {
                ((ScanDeviceListener) it.next()).onFinish();
            }
        }
    }

    public interface ScanDeviceListener {
        void onFind(BleDevice bleDevice);

        void onFinish();
    }

    private BleScanTool() {
    }

    public static synchronized BleScanTool getInstance() {
        BleScanTool bleScanTool;
        synchronized (BleScanTool.class) {
            if (instance == null) {
                instance = new BleScanTool();
            }
            bleScanTool = instance;
        }
        return bleScanTool;
    }

    public void addFilterName(String str) {
        if (str != null && !this.mFilterNameList.contains(str)) {
            this.mFilterNameList.add(str);
        }
    }

    public void addFilterNameForNum(String str) {
        if (str != null && !ArrayUtils.containsForNum(this.mFilterNameList)) {
            this.mFilterNameList.add(str);
        }
    }

    public void addScanDeviceListener(ScanDeviceListener scanDeviceListener) {
        if (scanDeviceListener != null && !this.mScanDeviceListenerList.contains(scanDeviceListener)) {
            this.mScanDeviceListenerList.add(scanDeviceListener);
        }
    }

    public void clearFilterName() {
        Iterator it = this.mFilterNameList.iterator();
        while (it.hasNext()) {
            this.mFilterNameList.remove((String) it.next());
        }
    }

    public void clearScanDeviceListener() {
        Iterator it = this.mScanDeviceListenerList.iterator();
        while (it.hasNext()) {
            this.mScanDeviceListenerList.remove((ScanDeviceListener) it.next());
        }
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return this.mBluetoothAdapter;
    }

    public void init(Context context) {
        DebugLog.m415e("BleScanUtil init !");
        this.mCtx = context;
        this.mBluetoothManager = (BluetoothManager) context.getSystemService("bluetooth");
        if (this.mBluetoothManager != null && this.mBluetoothAdapter == null) {
            this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        }
    }

    public boolean isBluetoothOpen() {
        return this.mBluetoothAdapter != null && this.mBluetoothAdapter.isEnabled();
    }

    public boolean isNeedScanDevice() {
        String str = Build.MANUFACTURER;
        DebugLog.m415e("phone_type" + str);
        return str.equalsIgnoreCase("xiaomi") || str.equalsIgnoreCase("huawei") || str.equals("samsung");
    }

    public boolean isScanning() {
        return this.isScaning;
    }

    public boolean isSupportBLE() {
        return this.mCtx.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public void removeFilterName(String str) {
        if (str != null && !this.mFilterNameList.isEmpty()) {
            this.mFilterNameList.remove(str);
        }
    }

    public void removeScanDeviceListener(ScanDeviceListener scanDeviceListener) {
        if (scanDeviceListener != null && !this.mScanDeviceListenerList.isEmpty()) {
            this.mScanDeviceListenerList.remove(scanDeviceListener);
        }
    }

    public void scanLeDevice(boolean z) {
        if (this.mBluetoothAdapter != null && this.mBluetoothAdapter.isEnabled()) {
            if (z) {
                if (this.isScaning) {
                    this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
                }
                this.mHandler.postDelayed(new C05503(), SCAN_PERIOD);
                this.isScaning = true;
                this.mBleDeviceMap.clear();
                this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
            } else if (this.isScaning) {
                this.mHandler.removeCallbacksAndMessages(null);
                this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
                this.isScaning = false;
            }
        }
    }

    public void scanLeDeviceByService(boolean z, UUID uuid) {
        this.requiredUUID = uuid;
        this.filterByService = z;
        scanLeDevice(z);
    }

    public void scanLeDeviceDFU(boolean z) {
        if (this.mBluetoothAdapter != null && this.mBluetoothAdapter.isEnabled()) {
            if (z) {
                if (this.isScaning) {
                    this.mBluetoothAdapter.stopLeScan(this.mLeScanCallbackDFU);
                }
                this.mHandler.postDelayed(new C05514(), SCAN_PERIOD);
                this.isScaning = true;
                this.mBleDeviceMap.clear();
                this.mBluetoothAdapter.startLeScan(this.mLeScanCallbackDFU);
            } else if (this.isScaning) {
                this.mHandler.removeCallbacksAndMessages(null);
                this.mBluetoothAdapter.stopLeScan(this.mLeScanCallbackDFU);
                this.isScaning = false;
            }
        }
    }
}
