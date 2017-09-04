package com.project.library.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.Set;

public class CommonPreferences {
    protected SharedPreferences mSharePre;

    protected float getValue(String str, float f) {
        return this.mSharePre.getFloat(str, f);
    }

    protected int getValue(String str, int i) {
        return this.mSharePre.getInt(str, i);
    }

    protected long getValue(String str, long j) {
        return this.mSharePre.getLong(str, j);
    }

    protected String getValue(String str, String str2) {
        return this.mSharePre.getString(str, str2);
    }

    protected Set getValue(String str, Set set) {
        return this.mSharePre.getStringSet(str, set);
    }

    protected boolean getValue(String str, boolean z) {
        return this.mSharePre.getBoolean(str, z);
    }

    public void init(Context context, String str) {
        Log.i("CommonPreferences", "context  = :" + context + "   spName : " + str);
        this.mSharePre = context.getSharedPreferences(str, 0);
    }

    public boolean remove(String str) {
        return this.mSharePre.edit().remove(str).commit();
    }

    protected void setValue(String str, float f) {
        this.mSharePre.edit().putFloat(str, f).commit();
    }

    protected void setValue(String str, int i) {
        this.mSharePre.edit().putInt(str, i).commit();
    }

    protected void setValue(String str, long j) {
        this.mSharePre.edit().putLong(str, j).commit();
    }

    protected void setValue(String str, String str2) {
        this.mSharePre.edit().putString(str, str2).commit();
    }

    protected void setValue(String str, Set set) {
        this.mSharePre.edit().putStringSet(str, set).commit();
    }

    protected void setValue(String str, boolean z) {
        this.mSharePre.edit().putBoolean(str, z).commit();
    }
}
