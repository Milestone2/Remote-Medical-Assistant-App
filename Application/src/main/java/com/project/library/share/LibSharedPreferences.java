package com.project.library.share;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;

public class LibSharedPreferences extends CommonPreferences {
    private static final String DEBUG = "debug";
    private static final String DEVICE_ALARM_MAX_COUNT = "device_alarm_max_count";
    private static final String DEVICE_ENERGE = "device_energe";
    private static final String DEVICE_FIRM_WARE_VERSION = "device_firm_ware_version";
    private static final String DEVICE_FUN_ALARM_NOTIFY = "DEVICE_FUN_ALARM_NOTIFY";
    private static final String DEVICE_FUN_CALL_NOTIFY = "DEVICE_FUN_CALL_NOTIFY";
    private static final String DEVICE_FUN_CONTROL = "DEVICE_FUN_CONTROL";
    private static final String DEVICE_FUN_MAIN = "DEVICE_FUN_MAIN";
    private static final String DEVICE_FUN_MSG_NOTIFY = "DEVICE_FUN_MSG_NOTIFY";
    private static final String DEVICE_FUN_MSG_NOTIFY2 = "DEVICE_FUN_MSG_NOTIFY2";
    private static final String DEVICE_FUN_OTHER2_DISPLAYMODE = "DEVICE_FUN_OTHER2_DISPLAYMODE";
    private static final String DEVICE_FUN_OTHER2_DISTURBMODE = "DEVICE_FUN_OTHER2_DISTURBMODE";
    private static final String DEVICE_FUN_OTHER2_LEFTRIGHTMODE = "DEVICE_FUN_OTHER2_LEFTRIGHTMODE";
    private static final String DEVICE_FUN_OTHER2_STATICHEART = "DEVICE_FUN_OTHER2_STATICHEART";
    private static final String DEVICE_FUN_OTHER_NOTIFY = "DEVICE_FUN_OTHER_NOTIFY";
    private static final String DEVICE_FUN_TIP_INFO_NOTIFY = "DEVICE_FUN_TIP_INFO_NOTIFY";
    private static final String DEVICE_HEART_RATE = "device_heart_rate";
    private static final String DEVICE_ID = "device_id";
    private static final String DEVICE_REAL_TIME = "device_real_time";
    private static final String DEVICE_REBOOT = "device_reboot";
    private static final String DEVICE_SYNC_DATA = "device_need_sync_data";
    private static final String HEART_RATE = "heart_rate";
    private static final String HEART_RATE_MAX = "heart_rate_max";
    private static final String HEART_RATE_MIN = "heart_rate_min";
    private static final String IS_FIRWARE_UPGRADE = "IS_FIRWARE_UPGRADE";
    private static final String REAL_CALORIES = "real_calories";
    private static final String REAL_DISTANCES = "real_distances";
    private static final String REAL_STEP = "real_step";
    private static final String SET_MAIN_SYNC_DATA = "SET_MAIN_SYNC_DATA";
    private static final String SET_UNITS = "SET_UNITS";
    private static final String SP_NAME = "veryfit_multi_app_lib";
    private static final String SUPPORT_ALL_NOTIFY = "SUPPORT_ALL_NOTIFY";
    private static final String SUPPORT_ROTA = "SUPPORT_ROTA";
    private static LibSharedPreferences instance;

    private LibSharedPreferences() {
    }

    public static final LibSharedPreferences getInstance() {
        if (instance == null) {
            instance = new LibSharedPreferences();
        }
        return instance;
    }

    public boolean getAllNotify() {
        return getValue(SUPPORT_ALL_NOTIFY, false);
    }

    public boolean getDebug() {
        return getValue(DEBUG, false);
    }

    public int getDeviceAlarmMaxCount() {
        return getValue(DEVICE_ALARM_MAX_COUNT, -1);
    }

    public int getDeviceCallNotify() {
        return getValue(DEVICE_FUN_CALL_NOTIFY, -1);
    }

    public int getDeviceEnerge() {
        return getValue(DEVICE_ENERGE, 0);
    }

    public int getDeviceFirmwareVersion() {
        return getValue(DEVICE_FIRM_WARE_VERSION, 0);
    }

    public int getDeviceFunAlarmNotify() {
        return getValue(DEVICE_FUN_ALARM_NOTIFY, -1);
    }

    public int getDeviceFunControl() {
        return getValue(DEVICE_FUN_CONTROL, -1);
    }

    public int getDeviceFunMain() {
        return getValue(DEVICE_FUN_MAIN, -1);
    }

    public int getDeviceFunMsgNotify() {
        return getValue(DEVICE_FUN_MSG_NOTIFY, -1);
    }

    public int getDeviceFunMsgNotify2() {
        return getValue(DEVICE_FUN_MSG_NOTIFY2, -1);
    }

    public boolean getDeviceFunOther2DisplayMode() {
        return getValue(DEVICE_FUN_OTHER2_DISPLAYMODE, false);
    }

    public boolean getDeviceFunOther2DisturbMode() {
        return getValue(DEVICE_FUN_OTHER2_DISTURBMODE, false);
    }

    public boolean getDeviceFunOther2LeftRightMode() {
        return getValue(DEVICE_FUN_OTHER2_LEFTRIGHTMODE, false);
    }

    public boolean getDeviceFunOther2Staticheart() {
        return getValue(DEVICE_FUN_OTHER2_STATICHEART, false);
    }

    public int getDeviceFunOtherNotify() {
        return getValue(DEVICE_FUN_OTHER_NOTIFY, -1);
    }

    public int getDeviceFunTipInfoNotify() {
        return getValue(DEVICE_FUN_TIP_INFO_NOTIFY, -1);
    }

    public boolean getDeviceHeartRate() {
        return getValue(DEVICE_HEART_RATE, false);
    }

    public int getDeviceId() {
        return getValue(DEVICE_ID, 0);
    }

    public boolean getDeviceRealTime() {
        return getValue(DEVICE_REAL_TIME, false);
    }

    public int getHeartRate() {
        return getValue(HEART_RATE, 0);
    }

    public int getHeartRateMax() {
        return getValue(HEART_RATE_MAX, (int) MotionEventCompat.ACTION_MASK);
    }

    public int getHeartRateMin() {
        return getValue(HEART_RATE_MIN, 30);
    }

    public int getReBoot() {
        return getValue(DEVICE_REBOOT, 0);
    }

    public int getRealCalories() {
        return getValue(REAL_CALORIES, 0);
    }

    public int getRealDistances() {
        return getValue(REAL_DISTANCES, 0);
    }

    public int getRealStep() {
        return getValue(REAL_STEP, 0);
    }

    public boolean getRota() {
        return getValue(SUPPORT_ROTA, false);
    }

    public boolean getUnits() {
        return getValue(SET_UNITS, false);
    }

    public void init(Context context) {
        super.init(context, SP_NAME);
    }

    public boolean isFirwareUpgrade() {
        return getValue(IS_FIRWARE_UPGRADE, false);
    }

    public boolean isSyncData() {
        return getValue(DEVICE_SYNC_DATA, true);
    }

    public void setAllNotify(boolean z) {
        setValue(SUPPORT_ALL_NOTIFY, z);
    }

    public void setDebug(boolean z) {
        setValue(DEBUG, z);
    }

    public void setDeviceAlarmMaxCount(int i) {
        setValue(DEVICE_ALARM_MAX_COUNT, i);
    }

    public void setDeviceEnerge(int i) {
        setValue(DEVICE_ENERGE, i);
    }

    public void setDeviceFirmwareVersion(int i) {
        setValue(DEVICE_FIRM_WARE_VERSION, i);
    }

    public void setDeviceFunAlarmNotify(int i) {
        setValue(DEVICE_FUN_ALARM_NOTIFY, i);
    }

    public void setDeviceFunCallNotify(int i) {
        setValue(DEVICE_FUN_CALL_NOTIFY, i);
    }

    public void setDeviceFunControl(int i) {
        setValue(DEVICE_FUN_CONTROL, i);
    }

    public void setDeviceFunMain(int i) {
        setValue(DEVICE_FUN_MAIN, i);
    }

    public void setDeviceFunMsgNotify(int i) {
        setValue(DEVICE_FUN_MSG_NOTIFY, i);
    }

    public void setDeviceFunMsgNotify2(int i) {
        setValue(DEVICE_FUN_MSG_NOTIFY2, i);
    }

    public void setDeviceFunOther2DisplayMode(boolean z) {
        setValue(DEVICE_FUN_OTHER2_DISPLAYMODE, z);
    }

    public void setDeviceFunOther2DisturbMode(boolean z) {
        setValue(DEVICE_FUN_OTHER2_DISTURBMODE, z);
    }

    public void setDeviceFunOther2LeftRightMode(boolean z) {
        setValue(DEVICE_FUN_OTHER2_LEFTRIGHTMODE, z);
    }

    public void setDeviceFunOther2Staticheart(boolean z) {
        setValue(DEVICE_FUN_OTHER2_STATICHEART, z);
    }

    public void setDeviceFunOtherNotify(int i) {
        setValue(DEVICE_FUN_OTHER_NOTIFY, i);
    }

    public void setDeviceFunTipInfoNotify(int i) {
        setValue(DEVICE_FUN_TIP_INFO_NOTIFY, i);
    }

    public void setDeviceHeartRate(boolean z) {
        setValue(DEVICE_HEART_RATE, z);
    }

    public void setDeviceId(int i) {
        setValue(DEVICE_ID, i);
    }

    public void setDeviceRealTime(boolean z) {
        setValue(DEVICE_REAL_TIME, z);
    }

    public void setHeartRate(int i) {
        setValue(HEART_RATE, i);
    }

    public void setHeartRateMax(int i) {
        setValue(HEART_RATE_MAX, i);
    }

    public void setHeartRateMin(int i) {
        setValue(HEART_RATE_MIN, i);
    }

    public void setIsFirwareUpgrade(boolean z) {
        setValue(IS_FIRWARE_UPGRADE, z);
    }

    public void setReBoot(int i) {
        setValue(DEVICE_REBOOT, i);
    }

    public void setRealCalories(int i) {
        setValue(REAL_CALORIES, i);
    }

    public void setRealDistances(int i) {
        setValue(REAL_DISTANCES, i);
    }

    public void setRealStep(int i) {
        setValue(REAL_STEP, i);
    }

    public void setRota(boolean z) {
        setValue(SUPPORT_ROTA, z);
    }

    public void setSyncData(boolean z) {
        setValue(DEVICE_SYNC_DATA, z);
    }

    public void setUnits(boolean z) {
        setValue(SET_UNITS, z);
    }
}
