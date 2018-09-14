package com.preyansh.mlm.dashboard

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import com.example.parth.kotlinpractice_2.support.ActivityViewModel
import com.support.builders.RecyckerViewBuilder.RecyclerViewLayoutManager.LINEAR
import com.support.builders.RecyckerViewBuilder.RecyclerViewLinearLayout.VERTICAL
import com.support.builders.RecyckerViewBuilder.setUp
import kotlinx.android.synthetic.main.user_list_single_item.view.*

class MainViewModel(mActivity : MainActivity) : ActivityViewModel(mActivity) {

    fun createUsersList(recView : RecyclerView, @LayoutRes layoutResID : Int) {
        recView.setUp(layoutResID, makeArrayList(), LINEAR, VERTICAL) {
            contentBinder { users, view ->
                view.txt_user_name.text = users.name
                view.txt_mobile_number.text = users.number
            }
        }
    }

    private fun makeArrayList() : ArrayList<Users>{
        val arrList = ArrayList<Users> ()
        arrList.add(Users("Preyansh", "8866511911"))
        arrList.add(Users("Ronak", "8490913488"))
        arrList.add(Users("Jignesh", "7016786731"))
        arrList.add(Users("Preyansh", "8866511911"))
        return arrList
    }

}