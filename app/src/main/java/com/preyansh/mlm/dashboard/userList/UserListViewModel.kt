package com.preyansh.mlm.dashboard.userList

import android.annotation.SuppressLint
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.preyansh.mlm.Constants
import com.preyansh.mlm.R
import com.preyansh.mlm.dashboard.MainViewModel
import com.preyansh.mlm.dashboard.profile.ProfileFragment
import com.support.SharedPrefs
import com.support.builders.ApiBuilder.*
import com.support.builders.ApiBuilder.WebServices.ApiNames.userList
import com.support.builders.ApiBuilder.responseModels.UserListResponseModel
import com.support.builders.RecyckerViewBuilder.RecyclerViewLayoutManager.LINEAR
import com.support.builders.RecyckerViewBuilder.RecyclerViewLinearLayout.VERTICAL
import com.support.builders.RecyckerViewBuilder.setUp
import com.support.core_utils.FragmentViewModel
import com.support.kotlin.showLongMsg
import com.support.kotlin.startFrag
import com.support.kotlin.string
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import kotlinx.android.synthetic.main.user_list_single_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class UserListViewModel(mFragment: UserListFragment) : FragmentViewModel(), SingleCallback {
    val binding = mFragment.binding
    val view: View = binding.root
    @SuppressLint("StaticFieldLeak")
    val activity: CoreActivity<*, *, *> = mFragment.activity as CoreActivity<*, *, *>
    var users: ArrayList<UserListResponseModel.UserListDataItem> = ArrayList()

    init {
        activity.callApi(userList, this, getHeaders())
        { ApiBuilder.webServices!!.getUserList(SharedPrefs.getUID()) }
    }

    private fun getHeaders(): MutableList<Header> {
        return Arrays.asList(Header("Content-Type", "application/x-www-form-urlencoded"),
                Header("token", SharedPrefs.getToken()))
    }

    fun createUsersList(recView: RecyclerView, @LayoutRes layoutResID: Int) {
        recView.setUp(layoutResID, users, LINEAR, VERTICAL) {
            contentBinder { users, view ->
                view.txt_user_name.text = "${users.firstname} ${users.lastname}"
                view.txt_email.text = users.email
                view.txt_reference_id.text = users.refid
                view.setOnClickListener {
                    activity.startFrag(ProfileFragment.newInstance(Constants.USER_LIST), "User Profile", false, R.id.box_dashboard)
                    (activity.vm as MainViewModel).setCheckedMenuItem(1)
                }
            }
        }
    }

    override fun onSuccess(o: Any, apiName: WebServices.ApiNames) {
        val response = o as UserListResponseModel
        if (response.success == 1) {
            if (response.count > 0)
                users = response.data!!
            createUsersList(view.rec_user_list, R.layout.user_list_single_item)
        }
    }

    override fun onFailure(throwable: Throwable, apiName: WebServices.ApiNames) {
        Log.e("UserList", "Failure =>> " + throwable.localizedMessage)
        R.string.warning_something_went_wrong.string().showLongMsg()
    }

}