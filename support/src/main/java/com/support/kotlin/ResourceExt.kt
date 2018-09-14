package com.support.kotlin

import android.app.Activity
import android.graphics.drawable.Drawable
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat

fun Int.color(activity: Activity): Int = ContextCompat.getColor(activity, this)
fun Int.color(activity: FragmentActivity): Int = ContextCompat.getColor(activity, this)

fun Int.string(activity: Activity): String = activity.resources.getString(this)

fun Int.drawable(activity: Activity): Drawable? = ContextCompat.getDrawable(activity, this)
fun Int.drawable(activity: FragmentActivity): Drawable? = ContextCompat.getDrawable(activity, this)