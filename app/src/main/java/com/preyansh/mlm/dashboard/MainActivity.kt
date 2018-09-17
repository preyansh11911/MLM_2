package com.preyansh.mlm.dashboard

import android.os.Bundle
import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.example.parth.kotlinpractice_2.support.setUpBottomNavigation
import com.preyansh.mlm.R
import com.preyansh.mlm.databinding.ActivityMainBinding

class MainActivity : CoreActivity<MainActivity,ActivityMainBinding,MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaults(this@MainActivity,R.layout.activity_main)
    }

    override fun setVM(binding: ActivityMainBinding) {
        binding.vm = vm
    }

    override fun createViewModel(activity: MainActivity) = MainViewModel(this@MainActivity)

    override fun getActionBarTitle() = "DashBoard"

    override fun workArea(vm: MainViewModel) {
//        vm.createUsersList(rec_user_list, R.layout.user_list_single_item)
    }

    override fun isBackEnabled(): Boolean? {
        return false
    }

    override fun bottomNavigation() {
        setUpBottomNavigation {
            setMenu(R.menu.user_dashboard_menu)
            setBackgroundColor(R.color.gray)
            setItemColor(R.color.colorPrimary, R.color.darkGray)
            itemSelectionHandler {
                vm!!.onItemSelectedListener(it)
            }
            selectedItem(R.id.action_home)
        }
    }
}
