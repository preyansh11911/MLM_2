package com.example.parth.kotlinpractice_2.support

import android.content.Context
import android.content.DialogInterface
import android.support.annotation.LayoutRes
import android.support.v7.app.AlertDialog

class AlertDialogBuilder(context: Context) {

    var builder: AlertDialog.Builder
    var dialog: AlertDialog? = null

    init {
        builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
    }

    fun title(title: String) {
        builder.setTitle(title)
    }

    fun icon(iconRes: Int) {
        builder.setIcon(iconRes)
    }

    fun message(msg: String) {
        builder.setMessage(msg)
    }

    fun positiveButtonClick(s: String, listener: DialogInterface.() -> Unit) {
        builder.setPositiveButton(s) { dialog, which -> dialog.listener() }
    }

    fun negativeButtonClick(s: String, listener: DialogInterface.() -> Unit) {
        builder.setNegativeButton(s) { dialog, which -> dialog.listener() }
    }

    fun show() {
        dialog = builder.create()
        dialog!!.show()
        dialog!!.setCanceledOnTouchOutside(false)
    }

    fun makeCancelable() {
        builder.setCancelable(true)
    }

    fun makeCancelableOnTouchOutSide() {
        dialog!!.setCanceledOnTouchOutside(true)
    }

    fun setCustomView(@LayoutRes layoutResId: Int) {
        builder.setView(layoutResId)
    }
}
