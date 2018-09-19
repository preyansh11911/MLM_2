package com.preyansh.mlm.dashboard.profile

import android.databinding.ObservableField
import android.util.Log
import android.view.View
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.preyansh.mlm.Constants
import com.preyansh.mlm.R
import com.support.SharedPrefs
import com.support.builders.ApiBuilder.*
import com.support.builders.ApiBuilder.responseModels.UserProfileResponseModel
import com.support.core_utils.FragmentViewModel
import com.support.kotlin.showLongMsg
import com.support.kotlin.string
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.*

class ProfileViewModel(val mFragment: ProfileFragment) : FragmentViewModel(), SingleCallback {

    var fullname: ObservableField<String> = ObservableField()
    val TAG = "ProfileViewModel"
    val binding = mFragment.binding
    val view: View = binding.root

    init {
        if (mFragment.arguments!!.get(Constants.FROM).equals(Constants.USER_LIST)) {
            mFragment.setHasOptionsMenu(false)
        } else
            mFragment.setHasOptionsMenu(true)
        (mFragment.activity as CoreActivity<*, *, *>).callApi(
                WebServices.ApiNames.userProfile,
                this,
                getHeaders()
        )
        { ApiBuilder.webServices!!.userProfile(SharedPrefs.getUID(), SharedPrefs.getUID()) }
    }

    private fun getHeaders(): MutableList<Header> {
        return Arrays.asList(Header("Content-Type", "application/x-www-form-urlencoded"),
                Header("token", SharedPrefs.getToken()))
    }

    fun makeProfileEditable() {
        view.ed_full_name.makeEditable()
        view.ed_email_profile.makeEditable()
        view.ed_number_profile.makeEditable()
        view.btn_submit_profile.visibility = View.VISIBLE
    }

    fun View.makeEditable() {
        this.isClickable = true
        this.isFocusable = true
    }

    override fun onSuccess(o: Any, apiName: WebServices.ApiNames) {
        val response = o as UserProfileResponseModel
        Log.e(TAG, "Success =>> " + response.data)
        if (response.success == 1) {
            fullname.set(response.data!!.get(0).firstname + " " + response.data!!.get(0).lastname)
            mFragment.binding.profile = response.data!!.get(0)
        }
    }

    override fun onFailure(throwable: Throwable, apiName: WebServices.ApiNames) {
        Log.e(TAG, "Failure =>> " + throwable.localizedMessage)
        R.string.warning_something_went_wrong.string().showLongMsg()
    }

}