package com.project.library.util;

import android.os.Environment;
import com.project.library.share.LibSharedPreferences;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UartLogUtil {
    private static final int BUFFER = 1;
    private static final int BUFFER_SIZE = 0;
    private static final String LOG_PATH = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/veryfit2").append("/log/dataLog").toString();
    private static final String REAL_PATH = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/veryfit2").append("/log/realLog").toString();
    private static StringBuffer buffer;
    private static FileWriter fw;
    private static StringBuffer realffer;
    private static StringBuffer sbffer;

    public static void close() {
        if (fw != null) {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fw = null;
        }
    }

    private static void deleteOldFile() {
        File file = new File(LOG_PATH);
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 10) {
                int length = listFiles.length - 10;
                for (int i = 0; i < length; i++) {
                    listFiles[i].delete();
                }
            }
        }
    }

    public static void open() {
        if (fw == null) {
            try {
                fw = new FileWriter(new File(Environment.getExternalStorageDirectory() + "/log_" + new SimpleDateFormat("yyyyMMdd", Locale.CHINESE).format(new Date()) + ".txt"), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void record(String str) throws IOException {
        FileWriter fileWriter;
        IOException e;
        Throwable th;
        synchronized (UartLogUtil.class) {
            if (LibSharedPreferences.getInstance().getDebug()) {
                File file = new File(LOG_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                deleteOldFile();
                if (buffer == null) {
                    buffer = new StringBuffer(0);
                }
                if (buffer.length() + str.length() > 0) {
                    try {
                        fileWriter = new FileWriter(new File(LOG_PATH + "/dataLog_" + new SimpleDateFormat("yyyyMMdd", Locale.CHINESE).format(new Date()) + ".txt"), true);
                        try {
                            fileWriter.append(buffer.toString());
                            fileWriter.flush();
                            if (fileWriter != null) {
                                try {
                                    fileWriter.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        } catch (IOException e3) {
                            try {
                                e3.printStackTrace();
                                if (fileWriter != null) {
                                    try {
                                        fileWriter.close();
                                    } catch (IOException e22) {
                                        e22.printStackTrace();
                                    }
                                }
                                buffer.setLength(0);
                                buffer.append(str);
                            } catch (Throwable th2) {
                                th = th2;
                                if (fileWriter != null) {
                                    try {
                                        fileWriter.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (IOException e5) {
                        fileWriter = null;
                        e5.printStackTrace();
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        buffer.setLength(0);
                        buffer.append(str);
                    } catch (Throwable th3) {
                        fileWriter = null;
                        if (fileWriter != null) {
                            fileWriter.close();
                        }
                        try {
                            throw th3;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                    buffer.setLength(0);
                    buffer.append(str);
                } else {
                    buffer.append(str);
                }
            }
        }
    }

    public static synchronized void recordRealTime(String str) {
        synchronized (UartLogUtil.class) {
        }
    }

    public static synchronized void recordRecieve(String str, byte[] bArr) {
        FileWriter fileWriter;
        IOException e;
        Throwable th;
        synchronized (UartLogUtil.class) {
            if (LibSharedPreferences.getInstance().getDebug()) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss|SSS", Locale.CHINESE);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(simpleDateFormat.format(new Date())).append(str).append(" : receive > ").append("[" + ByteDataConvertUtil.bytesToHexString(bArr) + "]").append("\r\n");
                String stringBuilder2 = stringBuilder.toString();
                File file = new File(LOG_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                deleteOldFile();
                if (buffer == null) {
                    buffer = new StringBuffer(0);
                }
                if (buffer.length() + stringBuilder2.length() > 0) {
                    try {
                        fileWriter = new FileWriter(new File(LOG_PATH + "/dataLog_" + new SimpleDateFormat("yyyyMMdd", Locale.CHINESE).format(new Date()) + ".txt"), true);
                        try {
                            fileWriter.append(buffer.toString());
                            fileWriter.flush();
                            if (fileWriter != null) {
                                try {
                                    fileWriter.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        } catch (IOException e3) {
                            try {
                                e3.printStackTrace();
                                if (fileWriter != null) {
                                    try {
                                        fileWriter.close();
                                    } catch (IOException e22) {
                                        e22.printStackTrace();
                                    }
                                }
                                buffer.setLength(0);
                                buffer.append(stringBuilder2);
                            } catch (Throwable th2) {
                                th = th2;
                                if (fileWriter != null) {
                                    try {
                                        fileWriter.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (IOException e5) {
                        fileWriter = null;
                        e5.printStackTrace();
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        buffer.setLength(0);
                        buffer.append(stringBuilder2);
                    } catch (Throwable th3) {
                        fileWriter = null;
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        try {
                            throw th3;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                    buffer.setLength(0);
                    buffer.append(stringBuilder2);
                } else {
                    buffer.append(stringBuilder2);
                }
            }
        }
    }

    public static synchronized void recordWrite(String str, byte[] bArr) {
        FileWriter fileWriter;
        IOException e;
        Throwable th;
        synchronized (UartLogUtil.class) {
            if (LibSharedPreferences.getInstance().getDebug()) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss|SSS", Locale.CHINESE);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(simpleDateFormat.format(new Date())).append(str).append(" -> ").append("[" + ByteDataConvertUtil.bytesToHexString(bArr) + "]").append("\r\n");
                String stringBuilder2 = stringBuilder.toString();
                File file = new File(LOG_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                deleteOldFile();
                if (buffer == null) {
                    buffer = new StringBuffer(0);
                }
                if (buffer.length() + stringBuilder2.length() > 0) {
                    try {
                        fileWriter = new FileWriter(new File(LOG_PATH + "/dataLog_" + new SimpleDateFormat("yyyyMMdd", Locale.CHINESE).format(new Date()) + ".txt"), true);
                        try {
                            fileWriter.append(buffer.toString());
                            fileWriter.flush();
                            if (fileWriter != null) {
                                try {
                                    fileWriter.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        } catch (IOException e3) {
                            try {
                                e3.printStackTrace();
                                if (fileWriter != null) {
                                    try {
                                        fileWriter.close();
                                    } catch (IOException e22) {
                                        e22.printStackTrace();
                                    }
                                }
                                buffer.setLength(0);
                                buffer.append(stringBuilder2);
                            } catch (Throwable th2) {
                                th = th2;
                                if (fileWriter != null) {
                                    try {
                                        fileWriter.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        }
                    } catch (IOException e5) {
                        fileWriter = null;
                        e5.printStackTrace();
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        buffer.setLength(0);
                        buffer.append(stringBuilder2);
                    } catch (Throwable th3) {
                        fileWriter = null;
                        if (fileWriter != null) {
                            try {
                                fileWriter.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        try {
                            throw th3;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                    buffer.setLength(0);
                    buffer.append(stringBuilder2);
                } else {
                    buffer.append(stringBuilder2);
                }
            }
        }
    }

    public static void write(String str) {
        if (fw != null) {
            try {
                fw.append(str);
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
