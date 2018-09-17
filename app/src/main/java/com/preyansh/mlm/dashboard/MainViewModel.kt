package com.preyansh.mlm.dashboard

import android.view.MenuItem
import com.example.parth.kotlinpractice_2.support.ActivityViewModel
import com.preyansh.mlm.R
import com.preyansh.mlm.dashboard.profile.ProfileFragment
import com.preyansh.mlm.dashboard.settings.SettingsFragment
import com.preyansh.mlm.dashboard.userList.UserListFragment
import com.support.kotlin.startFrag

class MainViewModel(val mActivity: MainActivity) : ActivityViewModel(mActivity) {

//    fun createUsersList(recView : RecyclerView, @LayoutRes layoutResID : Int) {
//        recView.setUp(layoutResID, makeArrayList(), LINEAR, VERTICAL) {
//            contentBinder { users, view ->
//                view.txt_user_name.text = users.name
//                view.txt_mobile_number.text = users.number
//            }
//        }
//    }

//    private fun makeArrayList() : ArrayList<Users>{
//        val arrList = ArrayList<Users> ()
//        arrList.add(Users("Preyansh", "8866511911"))
//        arrList.add(Users("Ronak", "8490913488"))
//        arrList.add(Users("Jignesh", "7016786731"))
//        arrList.add(Users("Preyansh", "8866511911"))
//        return arrList
//    }


    fun onItemSelectedListener(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.action_home ->
                mActivity.startFrag(UserListFragment(), "User List", false, R.id.box_dashboard)
            R.id.action_profile ->
                mActivity.startFrag(ProfileFragment(), "User Profile", false, R.id.box_dashboard)
            R.id.action_settings ->
                mActivity.startFrag(SettingsFragment(), "Change Password", false, R.id.box_dashboard)
        }
    }
}