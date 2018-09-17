package com.support.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.parth.kotlinpractice_2.support.AlertDialogBuilder
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.support.R
import com.support.core_utils.CoreFragment_DataBinding

fun Context.showAlert(alertDialog: AlertDialogBuilder.() -> Unit) = AlertDialogBuilder(this).apply(alertDialog)

//fun CoreActivity<*, *, *>.launchFragment(
//        title: String = "title",
//        addToBackStack: Boolean = false,
//        fragment: BaseFragment,
//        containerId: Int
//) {
//    getSupportFragmentManager().beginTransaction().apply {
//        if (addToBackStack)
//            add(containerId, fragment)
//        else
//            replace(containerId, fragment)
//        commit()
//    }
//    setActionBarTitle(title)
//}
//
//fun CoreActivity<*, *, *>.startFragment(
//        fragment: BaseFragment,
//        title: String = "title",
//        addToBackStack: Boolean = false,
//        containerId: Int = 0
//) {
//
//    fragment.newInstance().let {
//        launchFragment(fragment = it, title = title, containerId = containerId, addToBackStack = addToBackStack)
//    }
//}

inline fun <reified T : CoreFragment_DataBinding<*, *, *>> CoreActivity<*, *, *>.startFrag(
        fragment: T,
        title: String = "title",
        addToBackStack: Boolean = false,
        containerId: Int = 0) {
    getSupportFragmentManager().beginTransaction().apply {
        if (addToBackStack)
            add(containerId, fragment)
        else
            replace(containerId, fragment)
        commit()
    }
    setActionBarTitle(title)
}


inline fun <reified T: Activity> Activity.startActivity(flags : MutableList<Int>? = null) {
    val intent = Intent(this, T::class.java)
    if (flags != null)
        for (i in 0..flags.size-1)
            intent.addFlags(flags[i])
    startActivity(intent)
}

inline fun <reified T : Activity> FragmentActivity.startActivity(flags: MutableList<Int>? = null) {
    val intent = Intent(this, T::class.java)
    if (flags != null)
        for (i in 0..flags.size - 1)
            intent.addFlags(flags[i])
    startActivity(intent)
}

fun Fragment.toast(msg: String, duration: Duration) {
    activity?.toast(msg, duration)
}

fun Context.toast(msg: String, duration: Duration) {
    val dur: Int
    when (duration) {
        Duration.LONG -> dur = Toast.LENGTH_LONG
        Duration.SHORT -> dur = Toast.LENGTH_SHORT
        else -> {
            dur = Toast.LENGTH_SHORT
        }
    }
    Toast.makeText(this, msg, dur).show()
}

fun View.snack(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
}

fun Activity.hideSoftKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null)
        view = View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showSoftKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun String.showMsg() {
    Toast.makeText(CoreActivity.instance, this, Toast.LENGTH_SHORT).show()
}

fun String.showLongMsg() {
    Toast.makeText(CoreActivity.instance, this, Toast.LENGTH_LONG).show()
}

fun Context.showProgress(): AlertDialog {
    var dialog: AlertDialog? = null
    AlertDialogBuilder(this).apply {
        setCustomView(R.layout.loader_dialog_layout)
        show()
        dialog = this.dialog!!
        dialog!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    return dialog!!
}

//fun View.snackWithAction(msg: String) {
//    val snackBar = Snackbar.make(this,msg, Snackbar.LENGTH_INDEFINITE)
//    snackBar.setAction(R.string.OK)
//
//}

enum class Duration {
    SHORT, LONG, INDEFINITE
}

//fun CoreActivity<*, *, *>.setUpBottomNavigation(builder: BottomNavigationBuilder.() -> Unit) = BottomNavigationBuilder(this).apply(builder)
//fun <T : ActivityViewModel> CoreActivity<*, *, *>.setUpNavigationDrawer(viewModel: T, builder: NavigationDrawerBuilder<T>.() -> Unit) = NavigationDrawerBuilder<T>(this, viewModel).apply(builder)
//fun <T : POJOModel, U : ViewDataBinding> RecyclerView.setUpRecyclerView_Binding(itemList: ArrayList<T>, builder: RecyclerViewBuilder_Binding<T, U>.() -> Unit) = RecyclerViewBuilder_Binding<T, U>(this, itemList).apply(builder)
//fun <T : POJOModel> RecyclerView.setUpRecyclerView(itemList: ArrayList<T>, builder: RecyclerViewBuilder<T>.() -> Unit) = RecyclerViewBuilder<T>(this, itemList).apply(builder)
