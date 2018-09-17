package com.preyansh.mlm.dashboard.userList

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.preyansh.mlm.R
import com.support.SharedPrefs
import com.support.builders.ApiBuilder.*
import com.support.builders.ApiBuilder.WebServices.ApiNames.userList
import com.support.builders.ApiBuilder.responseModels.UserListResponseModel
import com.support.builders.RecyckerViewBuilder.RecyclerViewLayoutManager.LINEAR
import com.support.builders.RecyckerViewBuilder.RecyclerViewLinearLayout.VERTICAL
import com.support.builders.RecyckerViewBuilder.setUp
import com.support.core_utils.FragmentViewModel
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import kotlinx.android.synthetic.main.user_list_single_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class UserListViewModel(mFragment: UserListFragment) : FragmentViewModel(), SingleCallback {
    val binding = mFragment.binding
    val view: View = binding.root
    var users: ArrayList<UserListResponseModel.UserListDataItem> = ArrayList()

    init {
        (mFragment.activity as CoreActivity<*, *, *>).callApi(userList, this, getHeaders())
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
            }
        }
    }

//    private fun makeArrayList(): ArrayList<Users> {
//        val arrList = ArrayList<Users>()
//        arrList.add(Users("Preyansh", "8866511911"))
//        arrList.add(Users("Ronak", "8490913488"))
//        arrList.add(Users("Jignesh", "7016786731"))
//        arrList.add(Users("Preyansh", "8866511911"))
//        return arrList
//    }

    override fun onSuccess(o: Any, apiName: WebServices.ApiNames) {
        val response = o as UserListResponseModel
        if (response.success == 1) {
            if (response.count > 0)
                users = response.data!!
            createUsersList(view.rec_user_list, R.layout.user_list_single_item)
        }
    }

    override fun onFailure(throwable: Throwable, apiName: WebServices.ApiNames) {

    }

}