package com.support.kotlin;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ToastDisplayDuration {
    public static final int SHORT = 0;
    public static final int LONG = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SHORT, LONG})
    public @interface ToastDuration {
    }
}
