package com.ys.administrator.ysinteriortest.util;

import android.util.Log;

/**
 * Created by amos 2017/7/31.
 * Log管理类
 */
public class LogXmc {
    private static String TAG = "LogXmc";
    private static int logLevel = Log.VERBOSE;
    private static boolean isDebug = true;

    public static void enableDebug(boolean isEnableDebug) {
        isDebug = isEnableDebug;
    }

    public static void setTag(String tag) {
        TAG = tag;
    }

    public static void setLogLevel(int xmcLogLevel) {
        logLevel = xmcLogLevel;
    }

    public static void v(Object str) {
        v(TAG, str);
    }

    public static void v(String tag, Object str) {
        if (isDebug && logLevel <= Log.VERBOSE) {
            Log.v(tag, formatLog(str));
        }
    }

    public static void d(Object str) {
        d(TAG, str);
    }

    public static void d(String tag, Object str) {
        if (isDebug && logLevel <= Log.DEBUG) {
            Log.d(tag, formatLog(str));
        }
    }

    public static void i(Object str) {
        i(TAG, str);
    }

    public static void i(String tag, Object str) {
        if (isDebug && logLevel <= Log.INFO) {
            Log.i(tag, formatLog(str));
        }
    }

    public static void w(Object str) {
        w(TAG, str);
    }

    public static void w(String tag, Object str) {
        if (isDebug && logLevel <= Log.WARN) {
            Log.w(tag, formatLog(str));
        }
    }

    public static void e(Object str) {
        e(TAG, str);
    }

    public static void e(String tag, Object str) {
        if (isDebug && logLevel <= Log.ERROR) {
            Log.e(tag, formatLog(str));
        }
    }

    public static void e(Exception exception) {
        e(TAG, exception);
    }

    public static void e(String tag, Exception exception) {
        if (isDebug && logLevel <= Log.ERROR) {
            StringBuilder sb = new StringBuilder();
            String methodInfo = getMethodInfo();
            StackTraceElement[] sts = exception.getStackTrace();
            if (methodInfo != null) {
                sb.append(methodInfo + " : " + exception + "\r\n");
            } else {
                sb.append(exception + "\r\n");
            }
            if (sts != null && sts.length > 0) {
                for (StackTraceElement st : sts) {
                    if (st != null) {
                        sb.append("[ at " + st.getClassName() + "." + st.getMethodName() + "(" + st.getFileName() + ":" + st.getLineNumber() + ")" + " ]\r\n");
                    }
                }
            }
            Log.e(tag, sb.toString());
        }
    }

    private static String formatLog(Object str) {
        String formatLog = "";
        if(null != str) {
            String methodInfo = getMethodInfo();
            formatLog = (null == methodInfo ? str.toString() : (methodInfo + " : " + str));
        }
        return formatLog;
    }

    private static String getMethodInfo() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(LogXmc.class.getName())) {
                continue;
            }
            return "[" + Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + " " + st.getFileName() + ":"
                    + st.getLineNumber() + " " + st.getMethodName() + "()]";
        }
        return null;
    }

}
