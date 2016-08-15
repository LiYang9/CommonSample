package com.ly723.common.utils;

import android.util.Log;

/**
 * @Description 日志打印
 * @Author LiYang
 */
public class LogUtil {
    //控制是否调试程序，在上线时应该关闭
    private static final boolean DEBUG = true;
    private static final String TAG = "common";

    public static void debug(String msg) {
        if (DEBUG) {
            showLogCat(msg, Log.DEBUG);
        }
    }

    public static void info(String msg) {
        if (DEBUG) {
            showLogCat(msg, Log.INFO);
        }
    }

    public static void error(String msg) {
        if (DEBUG) {
            showLogCat(msg, Log.ERROR);
        }
    }

    public static void error(Throwable tr) {
        if (DEBUG) {
            showLogCat(handlerErrorMsg(tr), Log.ERROR);
        }
    }

    public static void error(String msg, Throwable tr) {
        if (DEBUG) {
            showLogCat(msg.concat("\n").concat(handlerErrorMsg(tr)), Log.ERROR);
        }
    }

    public static String handlerErrorMsg(Throwable tr) {
        String[] exs = Log.getStackTraceString(tr).split("\n");
        if (exs != null) {
            return exs[0].concat("\noccurred ").concat(exs[1].trim());
        } else {
            return Log.getStackTraceString(tr);
        }
    }

    /**
     * 获取调用此方法的类名
     */
    private static String getCurrentClassTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass
                        .lastIndexOf('.') + 1);
                break;
            }
        }
        return callingClass;
    }

    private static void showLogCat(String msg, int level) {
        String content;
        switch (level) {
            case Log.DEBUG:
                content = postion("【debug】").concat(":\n").concat(msg);
                Log.d(TAG.concat(":").concat(getCurrentClassTag()), content);
                break;
            case Log.INFO:
                content = postion("【info】").concat(":\n").concat(msg);
                Log.i(TAG.concat(":").concat(getCurrentClassTag()), content);
                break;
            case Log.ERROR:
                content = postion("【error】").concat(":\n").concat(msg);
                Log.e(TAG.concat(":").concat(getCurrentClassTag()), content);
                break;
        }


    }


    /**
     * 超链接，显示调用打印方法的位置
     */
    private static String postion(String prefix) {
        StackTraceElement[] stackTraceElement = Thread.currentThread()
                .getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].toString().contains(getCurrentClassTag())) {
                currentIndex = i + 3;
                break;
            }
        }
        String fullClassName = stackTraceElement[currentIndex].getClassName();
        String className = fullClassName.substring(fullClassName
                .lastIndexOf(".") + 1);
        String methodName = stackTraceElement[currentIndex].getMethodName();
        String lineNumber = String.valueOf(stackTraceElement[currentIndex].getLineNumber());
        return prefix + " at " + className + "." + methodName + "(" + className + ".java:" + lineNumber + ")";
    }
}
