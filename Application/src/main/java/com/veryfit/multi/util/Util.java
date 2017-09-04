package com.veryfit.multi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
//import p004u.aly.bq;

public class Util {
    private static long mLastTime = 0;
    private static Toast mToast = null;

    /*public static String getMaxLenthString(String[] strs) {
        String maxLen = bq.f411b;
        if (strs != null) {
            for (String string : strs) {
                if (string != null && maxLen.length() <= string.length()) {
                    maxLen = string;
                }
            }
        }
        return maxLen;
    }*/

    public static String formatTime(int hour, int min) {
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(hour), Integer.valueOf(min)});
    }

    public static int format24To12(int hour) {
        int h = hour % 12;
        if (h == 0) {
            return 12;
        }
        return h;
    }

    public static boolean isAM(int hour) {
        return hour < 12;
    }

    public static int format12To24(int hour, boolean isAm) {
        int h = 12;
        if (!isAm) {
            if (hour != 12) {
                h = hour + 12;
            }
            return h;
        } else if (hour == 12) {
            return 0;
        } else {
            return hour;
        }
    }

    public static boolean isSameDay(Calendar c1, Calendar c2) {
        return c1.get(1) == c2.get(1) && c1.get(2) == c2.get(2) && c1.get(5) == c2.get(5);
    }

    public static String formatToMonthAndDate(Calendar c) {
        return new StringBuilder(String.valueOf(c.get(2) + 1)).append("/").append(c.get(5)).toString();
    }

    public static String formatToMonthAndDate(Calendar c, int dateOffset) {
        c.add(5, dateOffset);
        String time = new StringBuilder(String.valueOf(c.get(2) + 1)).append("/").append(c.get(5)).toString();
        c.add(5, -dateOffset);
        return time;
    }

    public static String ss(Calendar c) {
        return c.get(1) + "/" + (c.get(2) + 1) + "/" + c.get(5);
    }

    public static String timeFormatter(int mins, boolean is24) {
        if (mins < 0 || mins >= 1440) {
            Log.e("Util", "timeFormatter Error : mins is out of range [0 , 1440).");
            return "--:--";
        }
        int h = getHourAndMin(mins, is24)[0];
        int min = mins % 60;
        if (is24) {
            String str = "%1$02d:%2$02d";
            Object[] objArr = new Object[2];
            if (h == 24) {
                h = 0;
            }
            objArr[0] = Integer.valueOf(h);
            objArr[1] = Integer.valueOf(min);
            return String.format(str, objArr);
        }
        String m = mins < 720 ? " am" : " pm";
        String str2 = "%1$02d:%2$02d";
        Object[] objArr2 = new Object[2];
        if (h == 24) {
            h = 0;
        }
        objArr2[0] = Integer.valueOf(h);
        objArr2[1] = Integer.valueOf(min);
        return String.format(str2, objArr2) + m;
    }

    public static int[] getHourAndMin(int mins, boolean is24) {
        int h = mins / 60;
        if (!is24) {
            if (h % 12 == 0) {
                h = 12;
            } else if (h > 12) {
                h -= 12;
            }
        }
        return new int[]{h, mins % 60};
    }

    public static String getTimeStr(int[] hourAndMin) {
        return String.format("%1$02d : %2$02d", new Object[]{Integer.valueOf(hourAndMin[0]), Integer.valueOf(hourAndMin[1])});
    }

    public static Bitmap geByFilePath(String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }

    public static String getSyncTimeStr(Calendar c) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d/%02d/%02d ", new Object[]{Integer.valueOf(c.get(1) % 1000), Integer.valueOf(c.get(2) + 1), Integer.valueOf(c.get(5))}));
        sb.append(String.format("%02d:%02d", new Object[]{Integer.valueOf(c.get(11)), Integer.valueOf(c.get(12))}));
        return sb.toString();
    }

    public static long getCurrentTime(Calendar c) {
        return (long) (((c.get(1) * 10000) + ((c.get(2) + 1) * 1000)) + c.get(5));
    }

    public static long getCurrentMinute(Calendar c) {
        return (long) ((c.get(10) * 60) + c.get(12));
    }

    public static String getStrCurrDate(Calendar c) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%04d%02d%02d", new Object[]{Integer.valueOf(c.get(1)), Integer.valueOf(c.get(2) + 1), Integer.valueOf(c.get(5))}));
        return sb.toString();
    }

    public static String getStrCurrDateWithSign(Calendar c) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%04d-%02d-%02d", new Object[]{Integer.valueOf(c.get(1)), Integer.valueOf(c.get(2) + 1), Integer.valueOf(c.get(5)), Integer.valueOf(c.get(11)), Integer.valueOf(c.get(12))}));
        return sb.toString();
    }

    public static String getStrDelayDate(Calendar c, int days) {
        c.add(5, days);
        return getStrCurrDate(c);
    }

    public static long getLongCurrDate() {
        return Long.parseLong(getStrCurrDate(Calendar.getInstance()));
    }

    public static long getLongDelayDate(int days) {
        return Long.parseLong(getStrDelayDate(Calendar.getInstance(), days));
    }

    public static String getApartDate(String aDate, int days) {
        try {
            Date date = DateFormat.getDateInstance().parse(aDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(5, days);
            return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isEmail(String email) {
        return Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$").matcher(email).matches();
    }

    public static boolean isMobileNO(String mobiles) {
        return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(mobiles).matches();
    }

    public static void showToast(Context context, int resid, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resid, duration);
        } else {
            mToast.setText(resid);
            mToast.setDuration(duration);
        }
        if (System.currentTimeMillis() - mLastTime > 1000) {
            mToast.show();
            mLastTime = System.currentTimeMillis();
        }
    }
}
