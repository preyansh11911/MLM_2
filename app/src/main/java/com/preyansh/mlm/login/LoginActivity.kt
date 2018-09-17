package com.preyansh.mlm.login

import android.os.Bundle
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.preyansh.mlm.R
import com.preyansh.mlm.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : CoreActivity<LoginActivity, ActivityLoginBinding, LoginViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaults(this@LoginActivity, R.layout.activity_login)
    }

    override fun setVM(binding: ActivityLoginBinding) {
        binding.vm = vm
    }

    override fun createViewModel(activity: LoginActivity) = LoginViewModel(this@LoginActivity)

    override fun getActionBarTitle() = "Login"

    override fun hasActionbar() = false

    override fun isBackEnabled(): Boolean? {
        return false
    }

    override fun workArea(vm: LoginViewModel) {
        btn_register_login.setOnClickListener(vm.registerClickListener)
        btn_sign_in.setOnClickListener(vm.signInClickListener)
    }
}
