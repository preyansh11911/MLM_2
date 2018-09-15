package com.support.kotlin

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.example.parth.kotlinpractice_2.support.CoreActivity

fun Int.color(): Int = ContextCompat.getColor(CoreActivity.instance!!, this)
//fun Int.color(activity: FragmentActivity): Int = ContextCompat.getColor(activity, this)

fun Int.string(): String = CoreActivity.instance!!.getResources().getString(this)

fun Int.drawable(): Drawable? = ContextCompat.getDrawable(CoreActivity.instance!!, this)
//fun Int.drawable(activity: FragmentActivity): Drawable? = ContextCompat.getDrawable(activity, this)