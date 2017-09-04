package com.veryfit.multi.util;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;
import com.project.library.core.CoreServiceProxy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private Context mCtx;

    class C07221 extends Thread {
        C07221() {
        }

        public void run() {
            Looper.prepare();
            //Toast.makeText(CrashHandler.this.mCtx, CrashHandler.this.mCtx.getResources().getString(C0597R.string.app_error_exit), 0).show();
            CoreServiceProxy mCoreService = CoreServiceProxy.getInstance();
            if (mCoreService != null && mCoreService.isAvailable()) {
                mCoreService.disconnect();
            }
            CoreServiceProxy.fini();
            Looper.loop();
        }
    }

    private static class CrashHandlerFactory {
        private static CrashHandler instance = new CrashHandler();

        private CrashHandlerFactory() {
        }
    }

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return CrashHandlerFactory.instance;
    }

    public CrashHandler init(Context context) {
        this.mCtx = context;
        return this;
    }

    public synchronized void uncaughtException(Thread thread, Throwable ex) {
        new C07221().start();
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        StackTraceElement[] trace = ex.getStackTrace();
        StackTraceElement[] trace2 = new StackTraceElement[(trace.length + 3)];
        System.arraycopy(trace, 0, trace2, 0, trace.length);
        trace2[trace.length + 0] = new StackTraceElement("Android", "MODEL", Build.MODEL, -1);
        trace2[trace.length + 1] = new StackTraceElement("Android", "VERSION", VERSION.RELEASE, -1);
        trace2[trace.length + 2] = new StackTraceElement("Android", "FINGERPRINT", Build.FINGERPRINT, -1);
        ex.setStackTrace(trace2);
        ex.printStackTrace(printWriter);
        String stacktrace = result.toString();
        printWriter.close();
        Log.e(TAG, stacktrace);
        if (Environment.getExternalStorageState().equals("mounted")) {
            String path = Constant.LOG_PATH;
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            writeLog(stacktrace, new StringBuilder(String.valueOf(path)).append("/project_crash").toString());
            writeLogcat(new StringBuilder(String.valueOf(path)).append("/project_logcat").toString());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Process.killProcess(Process.myPid());
        System.exit(-1);
    }

    private void writeLog(String log, String name) {
        try {
            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(new StringBuilder(String.valueOf(name)).append("_").append(DateFormat.format("yyyyMMdd_kkmmss", System.currentTimeMillis())).append(".log").toString()));
            BufferedWriter bw = new BufferedWriter(output);
            bw.write(log);
            bw.newLine();
            bw.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeLogcat(String name) {
        try {
            Logcat.writeLogcat(new StringBuilder(String.valueOf(name)).append("_").append(DateFormat.format("yyyyMMdd_kkmmss", System.currentTimeMillis())).append(".log").toString());
        } catch (IOException e) {
            Log.e(TAG, "Cannot write logcat to disk");
        }
    }
}
