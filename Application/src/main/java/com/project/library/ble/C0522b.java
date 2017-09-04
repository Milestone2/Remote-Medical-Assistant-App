package com.project.library.ble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import com.project.library.util.DebugLog;
import java.util.UUID;

public class C0522b {
    public static final UUID f226a = UUID.fromString("00000aF0-0000-1000-8000-00805f9b34fb");
    public static final UUID f227b = UUID.fromString("00000aF6-0000-1000-8000-00805f9b34fb");
    public static final UUID f228c = UUID.fromString("00000aF7-0000-1000-8000-00805f9b34fb");
    public static final UUID f229d = UUID.fromString("00000aF1-0000-1000-8000-00805f9b34fb");
    public static final UUID f230e = UUID.fromString("00000aF2-0000-1000-8000-00805f9b34fb");
    public static final UUID f231f = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID[] f232g = new UUID[]{f226a};

    public static BluetoothGattCharacteristic m392a(BluetoothGatt bluetoothGatt) {
        return C0522b.m393a(bluetoothGatt, f226a, f227b);
    }

    private static BluetoothGattCharacteristic m393a(BluetoothGatt bluetoothGatt, UUID uuid, UUID uuid2) {
        if (bluetoothGatt == null) {
            return null;
        }
        BluetoothGattService service = bluetoothGatt.getService(uuid);
        return service != null ? service.getCharacteristic(uuid2) : null;
    }

    private static boolean m394a(BluetoothGatt bluetoothGatt, UUID uuid, boolean z) {
        BluetoothGattCharacteristic a = C0522b.m393a(bluetoothGatt, f226a, uuid);
        if (a != null && (a.getProperties() | 16) > 0) {
            bluetoothGatt.setCharacteristicNotification(a, z);
            if (uuid.equals(a.getUuid())) {
                BluetoothGattDescriptor descriptor = a.getDescriptor(f231f);
                descriptor.setValue(z ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
                return bluetoothGatt.writeDescriptor(descriptor);
            }
        }
        return false;
    }

    public static boolean m395a(BluetoothGatt bluetoothGatt, boolean z) {
        DebugLog.m415e("enablePeerDeviceNotifyNormal: " + z);
        return C0522b.m394a(bluetoothGatt, f228c, z);
    }

    public static BluetoothGattCharacteristic m396b(BluetoothGatt bluetoothGatt) {
        return C0522b.m393a(bluetoothGatt, f226a, f229d);
    }

    public static boolean m397b(BluetoothGatt bluetoothGatt, boolean z) {
        DebugLog.m415e("enablePeerDeviceNotifyHealth:" + z);
        return C0522b.m394a(bluetoothGatt, f230e, z);
    }
}
