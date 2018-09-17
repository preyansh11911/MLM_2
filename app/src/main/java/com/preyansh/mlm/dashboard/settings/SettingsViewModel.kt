package com.preyansh.mlm.dashboard.settings

import android.view.View
import com.preyansh.mlm.R
import com.preyansh.mlm.login.LoginActivity
import com.support.SharedPrefs
import com.support.core_utils.FragmentViewModel
import com.support.kotlin.showAlert
import com.support.kotlin.startActivity
import com.support.kotlin.string

class SettingsViewModel(val mFragment: SettingsFragment) : FragmentViewModel() {
    val binding = mFragment.binding
    val view: View = binding.root

    val logoutClickListener = View.OnClickListener {
        SharedPrefs.setUID("")
        SharedPrefs.setToken("")
        SharedPrefs.setLoginStatus(false)

        mFragment.activity?.startActivity<LoginActivity>()
        mFragment.activity?.finish()
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
}