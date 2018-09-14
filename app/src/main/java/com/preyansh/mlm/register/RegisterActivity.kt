package com.preyansh.mlm.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.preyansh.mlm.R
import com.preyansh.mlm.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : CoreActivity<RegisterActivity, ActivityRegisterBinding, RegisterViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaults(this@RegisterActivity, R.layout.activity_register)
    }

    override fun setVM(binding: ActivityRegisterBinding) {
        binding.vm = vm
    }

    override fun createViewModel(activity: RegisterActivity) = RegisterViewModel(this@RegisterActivity)

    override fun getActionBarTitle() = "Registration"

    override fun isBackEnabled() = true

    override fun workArea(vm: RegisterViewModel) {
        btn_registration.setOnClickListener(vm.registerClickListener)
    }
}
