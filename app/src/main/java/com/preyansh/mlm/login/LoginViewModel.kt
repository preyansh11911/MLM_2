package com.preyansh.mlm.login

import android.view.View
import android.widget.Toast
import com.example.parth.kotlinpractice_2.support.ActivityViewModel
import com.preyansh.mlm.R
import com.preyansh.mlm.dashboard.MainActivity
import com.preyansh.mlm.register.RegisterActivity
import com.support.kotlin.showError
import com.support.kotlin.startActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginViewModel(val mActivity: LoginActivity) : ActivityViewModel(mActivity) {

    val registerClickListener = View.OnClickListener { mActivity.startActivity<RegisterActivity>() }
    val signInClickListener = View.OnClickListener {
        if (isValid()) {
            mActivity.startActivity<MainActivity>()
            mActivity.finish()
        }
    }

    private fun isValid() : Boolean {
        if (mActivity.ed_reference_id_login.text.isBlank()) {
            mActivity.getString(R.string.empty_reference_id).showError(mActivity)
            return false
        }
        if (mActivity.ed_password_login.text.isBlank()) {
            mActivity.getString(R.string.empty_password).showError(mActivity)
            return false
        }
        return true
    }
}