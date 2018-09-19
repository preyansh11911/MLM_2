package com.preyansh.mlm.register

import android.content.Intent
import android.util.Log
import android.view.View
import com.example.parth.kotlinpractice_2.support.ActivityViewModel
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.preyansh.mlm.R
import com.preyansh.mlm.dashboard.MainActivity
import com.support.SharedPrefs
import com.support.builders.ApiBuilder.*
import com.support.builders.ApiBuilder.responseModels.RegistrationResponseModel
import com.support.kotlin.showLongMsg
import com.support.kotlin.showMsg
import com.support.kotlin.startActivity
import com.support.kotlin.string
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterViewModel(val mActivity: RegisterActivity) : ActivityViewModel(mActivity), SingleCallback {

    val TAG = "REGISTRATION"

    val registerClickListener = View.OnClickListener {
        if (isValid()) {

            mActivity.callApi(WebServices.ApiNames.registration, this, getHeaders())
            {
                ApiBuilder.webServices!!.register(
                        mActivity.ed_fname.text.toString(),
                        mActivity.ed_lname.text.toString(),
                        mActivity.ed_adhar_card.text.toString(),
                        mActivity.ed_email.text.toString(),
                        mActivity.ed_password_registration.text.toString(),
                        mActivity.ed_reference_id_registration.text.toString(),
                        CoreActivity.deviceID,
                        mActivity.ed_mobile_number.text.toString()
                )
            }
        }
    }

    private fun getHeaders(): MutableList<Header> {
        return Arrays.asList(Header("Content-Type", "application/x-www-form-urlencoded"))
    }

    private fun isValid(): Boolean {
        if (mActivity.ed_fname.text.isBlank()) {
            R.string.empty_first_name.string().showMsg()
            return false
        }
        if (mActivity.ed_lname.text.isBlank()) {
            R.string.empty_last_name.string().showMsg()
            return false
        }
        if (mActivity.ed_mobile_number.text.isBlank()) {
            R.string.empty_mobile_number.string().showMsg()
            return false
        }
        if (mActivity.ed_adhar_card.text.isBlank()) {
            R.string.empty_adhar_card_number.string().showMsg()
            return false
        }
        if (mActivity.ed_email.text.isBlank()) {
            R.string.empty_email.string().showMsg()
            return false
        }
        if (mActivity.ed_password_registration.text.isBlank()) {
            R.string.empty_password.string().showMsg()
            return false
        }
        if (mActivity.ed_confirm_password_registration.text.isBlank()) {
            R.string.empty_confirm_password.string().showMsg()
            return false
        }
        if (!mActivity.ed_password_registration.text.toString().equals(mActivity.ed_confirm_password_registration.text.toString())) {
            R.string.warning_password_mismatch.string().showMsg()
            return false
        }
        if (mActivity.ed_reference_id_registration.text.isBlank()) {
            R.string.empty_reference_id.string().showMsg()
            return false
        }
        return true
    }

    override fun onSuccess(o: Any, apiName: WebServices.ApiNames) {
        val response = o as RegistrationResponseModel
        Log.e(TAG, "Success =>> " + response.message)
        if (response.success == 1) {
            R.string.msg_registration_successful.string().showMsg()
            SharedPrefs.setLoginStatus(true)
            SharedPrefs.setUID(response.uid)
            mActivity.startActivity<MainActivity>(Arrays.asList(Intent.FLAG_ACTIVITY_NEW_TASK, Intent.FLAG_ACTIVITY_CLEAR_TASK))
            mActivity.finish()
        } else {
            R.string.warning_not_registered.string().showMsg()
        }
    }

    override fun onFailure(throwable: Throwable, apiName: WebServices.ApiNames) {
        Log.e(TAG, "Failure =>> " + throwable.localizedMessage)
        R.string.warning_something_went_wrong.string().showLongMsg()
    }

}