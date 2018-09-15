package com.preyansh.mlm.register

import android.content.Intent
import android.view.View
import com.example.parth.kotlinpractice_2.support.ActivityViewModel
import com.preyansh.mlm.R
import com.preyansh.mlm.dashboard.MainActivity
import com.support.kotlin.showMsg
import com.support.kotlin.startActivity
import com.support.kotlin.string
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterViewModel(val mActivity : RegisterActivity) : ActivityViewModel(mActivity) {

    val registerClickListener = View.OnClickListener {
        if (isValid()) {
            mActivity.startActivity<MainActivity>(Arrays.asList(Intent.FLAG_ACTIVITY_NEW_TASK,Intent.FLAG_ACTIVITY_CLEAR_TASK))
            mActivity.finish()
        }
    }

    private fun isValid() : Boolean{
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
        if (mActivity.ed_reference_id_registration.text.isBlank()) {
            R.string.empty_reference_id.string().showMsg()
            return false
        }
        return true
    }
}