package com.preyansh.mlm.dashboard.settings

import android.util.Log
import android.view.View
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.preyansh.mlm.R
import com.preyansh.mlm.login.LoginActivity
import com.support.SharedPrefs
import com.support.builders.ApiBuilder.*
import com.support.builders.ApiBuilder.WebServices.ApiNames.logout
import com.support.builders.ApiBuilder.responseModels.LogoutResponseModel
import com.support.core_utils.FragmentViewModel
import com.support.kotlin.showAlert
import com.support.kotlin.showLongMsg
import com.support.kotlin.startActivity
import com.support.kotlin.string
import java.util.*

class SettingsViewModel(val mFragment: SettingsFragment) : FragmentViewModel(), SingleCallback {

    val TAG = "SettingViewModel"
    val binding = mFragment.binding
    val view: View = binding.root

    val logoutClickListener = View.OnClickListener {
        (mFragment.activity as CoreActivity<*, *, *>).callApi(logout, this, getHeaders())
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

    private fun getHeaders(): MutableList<Header> {
        return Arrays.asList(Header("Content-Type", "application/x-www-form-urlencoded"))
    }

    override fun onSuccess(o: Any, apiName: WebServices.ApiNames) {
        val response = o as LogoutResponseModel
        Log.e(TAG, response.message)
        if (response.success == 1) {
            SharedPrefs.setUID("")
            SharedPrefs.setToken("")
            SharedPrefs.setLoginStatus(false)

            mFragment.activity?.startActivity<LoginActivity>()
            mFragment.activity?.finish()
        }
    }

    override fun onFailure(throwable: Throwable, apiName: WebServices.ApiNames) {
        Log.e(TAG, "Failure =>> " + throwable.localizedMessage)
        R.string.warning_something_went_wrong.string().showLongMsg()
    }

}