package com.example.parth.kotlinpractice_2.support

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.provider.Settings
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.support.R
import com.support.databinding.ActivityCoreBinding
import com.support.databinding.ActivityDrawerBinding
import com.support.kotlin.showAlert
import com.support.kotlin.string
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.app_bar_drawer.*
import kotlinx.android.synthetic.main.tool_bar.*


abstract class CoreActivity<T : CoreActivity<T, DB, VM>, DB : ViewDataBinding, VM : ActivityViewModel> : AppCompatActivity() {

    val TAG = "CoreActivity"
    lateinit var activity: T
    var layoutRes: Int = 0
    lateinit var coreBinding: ActivityCoreBinding
    lateinit var navigationDrawerBinding: ActivityDrawerBinding
    lateinit var binding: DB
    var vm: VM? = null
        get() {
            if (field == null) field = createViewModel(activity)
            return field
        }
    var coreVM: ActivityViewModel? = null
        get() {
            if (field == null) field = createCoreViewModel()
            return field
        }
    var hasNavigationDrawer: Boolean = false
    var compositeDisposable: CompositeDisposable? = null
        get() {
            if (field == null) field = CompositeDisposable()
            return field
        }

    val deviceID: String
        @SuppressLint("HardwareIds")
        get() = Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)

    companion object {
        var instance: CoreActivity<*, *, *>? = null
    }

    override fun setContentView(childView: View?) {
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        coreBinding.coreContent.addView(childView, lp)
    }

    override fun onBackPressed() {
        drawer_layout?.let {
            if (it.isDrawerOpen(GravityCompat.START)) {
                it.closeDrawer(GravityCompat.START)
                return
            }
        }

        if (isBackEnabled() != null && !isBackEnabled()!!) {
            showAlert {
                icon(R.drawable.ic_app_exit)
                title(R.string.alert_title.string())
                message(R.string.alert_message.string())
                positiveButtonClick(R.string.YES.string()) { super.onBackPressed() }
                negativeButtonClick(R.string.NO.string()) { }
                makeCancelable()
                show()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> this.onBackPressed()
        }
        return true
    }

    fun setNavigationDrawerContentView(childLayoutRes: Int) {
        binding = DataBindingUtil.inflate(layoutInflater, childLayoutRes, null, false)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        lp.weight = 1F
        navigationDrawerBinding.includedAppBar!!.navigationDrawerContent!!.container.addView(binding.root, 0, lp)
    }

    fun setDefaults(activity: T, layoutRes: Int) {
        this.activity = activity
        instance = activity
        this.layoutRes = layoutRes
        navigationDrawer()
        if (!hasNavigationDrawer) {
            setActionBar()
        }
        bottomNavigation()
        workArea(vm!!)
    }

    fun setActionBarTitle(title: String) {
        if (isCustomActionbar())
            coreVM!!.actionBarTitle.set(title)
        else
            activity.title = title
    }

    fun setNavigationDrawer() {
        removeActionBar()
        coreBinding = DataBindingUtil.setContentView(this, R.layout.activity_core)
        navigationDrawerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_drawer, null, false)
        setContentView(navigationDrawerBinding.root)
        setCoreVM()
        setNavDrawerVM()
        coreVM!!.hasNavigationDrawer.set(true)
        activity.setSupportActionBar(toolbar_navigation_drawer)
        activity.title = getActionBarTitle()
        setNavigationDrawerContentView(layoutRes)
        setVM(binding)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar_navigation_drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setNavDrawerVM() {
        navigationDrawerBinding.vm = coreVM!!
    }

    fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    private fun setActionBar() {
        if (hasActionbar()) {
            if (isCustomActionbar()) {
                setCustomActionBar()
            } else {
                setBindings(layoutRes)
                setDefaultActionBarProperties(getActionBarTitle(), isBackEnabled())
            }
        } else {
            removeActionBar()
            setBindings(layoutRes)
        }
    }

    private fun setCustomActionBar() {
        removeActionBar()
        setBindings(layoutRes)
        setCustomActionBarProperties(isCustomActionbar(), getActionBarTitle(), isBackEnabled())
        this.setSupportActionBar(tool_bar_box)
    }

    private fun setDefaultActionBarProperties(actionBarTitle: String, isBackEnabled: Boolean?) {
        setActionBarTitle(actionBarTitle)
        isBackEnabled?.let { activity.supportActionBar?.setDisplayHomeAsUpEnabled(it)
            activity.supportActionBar?.setDisplayShowHomeEnabled(it)
        }
    }

    private fun setCustomActionBarProperties(isCustomActionBar: Boolean, actionBarTitle: String, isBackEnabled: Boolean?) {
        coreVM!!.isCustomActionbar.set(isCustomActionBar)
        setActionBarTitle(actionBarTitle)
        isBackEnabled?.let { coreVM!!.isBackEnabled.set(it) }
    }

    private fun removeActionBar() {
        activity.setTheme(R.style.AppTheme_NoActionBar)
    }

    private fun setBindings(childLayoutRes: Int) {
        coreBinding = DataBindingUtil.setContentView(this, R.layout.activity_core)
        binding = DataBindingUtil.inflate(layoutInflater, childLayoutRes, null, false)
        setContentView(binding.root)
        setCoreVM()
        setVM(binding)
    }

    private fun setCoreVM() {
        coreBinding.vm = coreVM
    }

    private fun createCoreViewModel(): ActivityViewModel {
        return ActivityViewModel(activity)
    }

    abstract fun setVM(binding: DB)

    abstract fun createViewModel(activity: T): VM

    abstract fun getActionBarTitle(): String

    abstract fun workArea(vm: VM)

    open fun hasActionbar(): Boolean {
        return true
    }

    open fun isCustomActionbar(): Boolean {
        return false
    }

    open fun isBackEnabled(): Boolean? {
        return null
    }

    open fun getToolBarID(): Toolbar? {
        return null
    }

    /**
     * Override this method to set BottomNavigation in activity
     */
    open fun bottomNavigation() {}


    /**
     * Override this method to set NavigationDrawer in activity
     */
    open fun navigationDrawer() {}
}