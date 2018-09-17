package com.preyansh.mlm.login

import android.util.Log
import android.view.View.OnClickListener
import com.example.parth.kotlinpractice_2.support.ActivityViewModel
import com.preyansh.mlm.R
import com.preyansh.mlm.dashboard.MainActivity
import com.preyansh.mlm.register.RegisterActivity
import com.support.SharedPrefs
import com.support.builders.ApiBuilder.*
import com.support.builders.ApiBuilder.WebServices.ApiNames.login
import com.support.builders.ApiBuilder.responseModels.LoginResponseModel
import com.support.kotlin.showLongMsg
import com.support.kotlin.showMsg
import com.support.kotlin.startActivity
import com.support.kotlin.string
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginViewModel(val mActivity: LoginActivity) : ActivityViewModel(mActivity), SingleCallback {

    val TAG = "LoginActivity"
    val registerClickListener = OnClickListener { mActivity.startActivity<RegisterActivity>() }

    val signInClickListener = OnClickListener {

        if (isValid()) {
            mActivity.callApi(login, this, getHeaders())
            {
                ApiBuilder.webServices!!.login(
                        mActivity.ed_reference_id_login.text.toString(),
                        mActivity.ed_password_login.text.toString(),
                        mActivity.deviceID
                )
            }
        }
    }

    private fun getHeaders(): MutableList<Header> {
        return Arrays.asList(Header("Content-Type", "application/x-www-form-urlencoded"))
    }

    private fun isValid(): Boolean {
        if (mActivity.ed_reference_id_login.text.isBlank()) {
            R.string.empty_reference_id.string().showMsg()
            return false
        }
        if (mActivity.ed_password_login.text.isBlank()) {
            R.string.empty_password.string().showMsg()
            return false
        }
        return true
    }

    override fun onSuccess(o: Any, apiName: WebServices.ApiNames) {
        val response = o as LoginResponseModel
        Log.e(TAG, "Success =>> " + response.message)
        if (response.success == 1) {
            R.string.msg_login_successful.string().showMsg()
            SharedPrefs.setToken(response.token)
            SharedPrefs.setUID(response.uid)
            SharedPrefs.setLoginStatus(true)
            mActivity.startActivity<MainActivity>()
            mActivity.finish()
        } else {
            R.string.invalide_user_credentials.string().showMsg()
        }
    }

    override fun onFailure(throwable: Throwable, apiName: WebServices.ApiNames) {
        Log.e(TAG, "Failure =>> " + throwable.localizedMessage)
        R.string.warning_something_went_wrong.string().showLongMsg()
    }
}
