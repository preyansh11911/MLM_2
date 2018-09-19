package com.preyansh.mlm.dashboard.settings

import android.util.Log
import android.view.View
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.preyansh.mlm.R
import com.preyansh.mlm.login.LoginActivity
import com.support.SharedPrefs
import com.support.builders.ApiBuilder.*
import com.support.builders.ApiBuilder.WebServices.ApiNames.changePassword
import com.support.builders.ApiBuilder.WebServices.ApiNames.logout
import com.support.builders.ApiBuilder.responseModels.ChangePasswordResponseModel
import com.support.builders.ApiBuilder.responseModels.LogoutResponseModel
import com.support.core_utils.FragmentViewModel
import com.support.kotlin.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.util.*

class SettingsViewModel(val mFragment: SettingsFragment) : FragmentViewModel(), SingleCallback {

    val TAG = "SettingViewModel"
    val binding = mFragment.binding
    val view: View = binding.root

    val submitClickListener = View.OnClickListener {
        if (isValid()) {
            (mFragment.activity as CoreActivity<*, *, *>).callApi(
                    changePassword,
                    this,
                    getChangePasswordHeaders()
            )
            {
                ApiBuilder.webServices!!.changePassword(
                        SharedPrefs.getUID(),
                        view.ed_current_password.text.toString(),
                        view.ed_new_password.text.toString()
                )
            }
        }
    }

    private fun getChangePasswordHeaders(): MutableList<Header> {
        return Arrays.asList(Header("token", SharedPrefs.getToken()))
    }

    private fun isValid(): Boolean {
        if (view.ed_current_password.text.isBlank()) {
            R.string.empty_current_password.string().showMsg()
            return false
        }
        if (view.ed_new_password.text.isBlank()) {
            R.string.empty_new_password.string().showMsg()
            return false
        }
        if (view.ed_confirm_password.text.isBlank()) {
            R.string.empty_confirm_password.string().showMsg()
            return false
        }
        if (!view.ed_new_password.text.toString().equals(view.ed_confirm_password.text.toString())) {
            R.string.warning_new_password_mismatch.string().showMsg()
            return false
        }
        return true
    }

    val logoutClickListener = View.OnClickListener {
        (mFragment.activity as CoreActivity<*, *, *>).callApi(logout, this, getLogoutHeaders())
        { ApiBuilder.webServices!!.logout(CoreActivity.deviceID) }
    }

    val deleteClickListener = View.OnClickListener {

        mFragment.activity?.showAlert {
            title(R.string.lbl_delete_ac.string())
            message(R.string.delete_account_alert.string())
            positiveButtonClick("Yes") {
                SharedPrefs.setUID("")
                SharedPrefs.setToken("")
                SharedPrefs.setLoginStatus(false)

                mFragment.activity?.startActivity<LoginActivity>()
                mFragment.activity?.finish()
            }
            negativeButtonClick("NO") {}
            makeCancelable()
            show()
        }
    }

    private fun getLogoutHeaders(): MutableList<Header> {
        return Arrays.asList(Header("Content-Type", "application/x-www-form-urlencoded"))
    }

    private fun clearFields() {
        view.ed_current_password.setText("")
        view.ed_new_password.setText("")
        view.ed_confirm_password.setText("")
    }

    override fun onSuccess(o: Any, apiName: WebServices.ApiNames) {
        when (apiName) {
            logout -> {
                val response = o as LogoutResponseModel
                Log.e(TAG, response.message)
                if (response.success == 1) {
                    SharedPrefs.setUID("")
                    SharedPrefs.setToken("")
                    SharedPrefs.setLoginStatus(false)

                    mFragment.activity?.startActivity<LoginActivity>()
                    mFragment.activity?.finish()
                } else
                    response.message.showMsg()
            }
            changePassword -> {
                val response = o as ChangePasswordResponseModel
                Log.e(TAG, response.message)
                if (response.success == 1) {
                    R.string.change_password_successful_msg.string().showMsg()
                    clearFields()
                } else
                    response.message.showMsg()
            }
        }

    }

    override fun onFailure(throwable: Throwable, apiName: WebServices.ApiNames) {
        Log.e(TAG, "Failure =>> " + throwable.localizedMessage)
        R.string.warning_something_went_wrong.string().showLongMsg()
    }

}