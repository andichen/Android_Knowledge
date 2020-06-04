package com.example.mlmmusic.util;

import android.text.TextUtils;
import android.util.Log;


import java.util.List;

public class Trace {
    public static boolean isOpen = true;

    public static void I(String tag, String msg) {
        if (isOpen) {
            Log.i("clf_" + tag, msg);
        }
    }

    public static void T() {
        if (isOpen) {
            T("", 2);
        }

    }

    public static void T2() {
        if (isOpen) {
            T("<---", 3);
            T("", 2);
        }
    }

    public static void T2(String l) {
        if (isOpen) {
            T(l, 2);
            T("<---", 3);
        }
    }

    public static void T(String l) {
        if (isOpen) {
            T(l, 2);
        }
    }

    public static void T(long l) {
        if (isOpen) {

            T(Long.toString(l), 2);
        }
    }

    ////////////// type
    public static void F() {
        if (isOpen) {

            T("", 2);
        }
    }

    public static void F2() {
        if (isOpen) {

            T("", 2);
            T("<---", 3);
        }
    }

    public static void F2(String l) {
        if (isOpen) {

            T(l, 2);
            T("<---", 3);
        }
    }

    public static void F(String l) {
        if (isOpen) {

            T(l, 2);
        }
    }

    public static void F(long l) {
        if (isOpen) {

            T(Long.toString(l), 2);
        }
    }

    private static String listToString(List<Long> members) {
        if (members == null) {
            return "";
        }
        StringBuffer toStringBuffer = new StringBuffer();
        for (Long m : members) {
            toStringBuffer.append(m);
            toStringBuffer.append(" ");
        }
        return toStringBuffer.toString();

    }

    public static void T(List<Long> members) {
        if (isOpen) {

            T(listToString(members), 2);
        }
    }

    public static void T(String l, int level) {
        if (isOpen) {
            level += 2;
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            if (stacks == null || stacks.length <= level) {
                logDetail(null, null, l);
                return;
            }
            StackTraceElement traceElement = stacks[level];
            if (traceElement == null) {
                logDetail(null, null, l);
                return;
            }

            String method = traceElement.getMethodName();
            if (method == null) {
                logDetail(null, null, l);
                return;
            }

            String cls = traceElement.getFileName();
            if (cls == null || cls.length() <= 5) {
                logDetail(cls, method, l);
                return;
            }

            StringBuffer toStringBuffer = new StringBuffer("[").append(method).append("]").append(
                    " ").append(l == null ? "" : l);
            toStringBuffer.append(" ");
            Log.i(cls.substring(0, cls.length() - 5), toStringBuffer.toString());
        }

    }

    private static void logDetail(String cls, String method, String l) {
        if (TextUtils.isEmpty(cls)) {
            cls = "unknown";
        }
        StringBuffer toStringBuffer = new StringBuffer("[").append(method == null ? "" : method).append("]").append(
                " ").append(l == null ? "" : l);
        toStringBuffer.append(" ");
        Log.i(cls, toStringBuffer.toString());
    }

    public static boolean isOpen() {
        return isOpen;
    }

    public static void setOpen(boolean isOpen) {
        Trace.isOpen = isOpen;
    }
}
