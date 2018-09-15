package com.preyansh.mlm.splash

import android.os.Bundle
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.preyansh.mlm.R
import com.preyansh.mlm.dashboard.MainActivity
import com.preyansh.mlm.databinding.ActivitySplashBinding
import com.preyansh.mlm.login.LoginActivity
import com.support.SharedPrefs
import com.support.kotlin.startActivity
import com.support.kotlin.string

class SplashActivity : CoreActivity<SplashActivity, ActivitySplashBinding, SplashViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaults(this@SplashActivity, R.layout.activity_splash)
    }

    override fun setVM(binding: ActivitySplashBinding) {
        binding.vm = vm
    }

    override fun createViewModel(activity: SplashActivity) = SplashViewModel(this@SplashActivity)

    override fun getActionBarTitle() = R.string.app_name.string()

    override fun workArea(vm: SplashViewModel) {
        if (SharedPrefs.isLoggedIn()) {
            startActivity<MainActivity>()
            finish()
        } else {
            startActivity<LoginActivity>()
            finish()
        }
    }
}
