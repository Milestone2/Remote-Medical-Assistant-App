package com.project.library.util;

import android.util.Log;

public class DebugLog {
    static String className;
    static int lineNumber;
    static String methodName;

    private DebugLog() {
    }

    private static String createLog(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append(methodName);
        stringBuffer.append(":");
        stringBuffer.append(lineNumber);
        stringBuffer.append("]");
        stringBuffer.append(str);
        return stringBuffer.toString();
    }

    public static void m414d(String str) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            Log.d(className, createLog(str));
        }
    }

    public static void m415e(String str) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(className, createLog(str));
        }
    }

    private static void getMethodNames(StackTraceElement[] stackTraceElementArr) {
        className = stackTraceElementArr[1].getFileName();
        methodName = stackTraceElementArr[1].getMethodName();
        lineNumber = stackTraceElementArr[1].getLineNumber();
    }

    public static void m416i(String str) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            Log.i(className, createLog(str));
        }
    }

    public static boolean isDebuggable() {
        return true;
    }

    public static void m417v(String str) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            Log.v(className, createLog(str));
        }
    }

    public static void m418w(String str) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            Log.w(className, createLog(str));
        }
    }

    public static void wtf(String str) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            Log.wtf(className, createLog(str));
        }
    }
}
