package com.support.builders.ViewPagerBuilder;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ViewPagerTypes {
    public static final int DEFAULT = 10210;
    public static final int FRAGMENT = 10220;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DEFAULT, FRAGMENT})
    public @interface ViewPagerType {
    }
}
