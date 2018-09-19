package com.preyansh.mlm.dashboard.userList

import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.preyansh.mlm.R
import com.preyansh.mlm.databinding.FragmentUserListBinding
import com.support.core_utils.CoreFragment_DataBinding
import com.support.kotlin.showMsg

class UserListFragment : CoreFragment_DataBinding<UserListFragment, FragmentUserListBinding, UserListViewModel>() {
    override fun workArea() {
        setHasOptionsMenu(true)
        activity?.invalidateOptionsMenu()
//        vm?.let {
//            it.createUsersList(it.view.rec_user_list, R.layout.user_list_single_item)
//        }
    }

    override fun getLayoutView() = R.layout.fragment_user_list

    override fun getFragmentContext() = this@UserListFragment

    override fun createViewModel() = UserListViewModel(this)

    override fun setVM(binding: FragmentUserListBinding) {
        binding.vm = vm
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.home_options_menu, menu)

        var queryText: String
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.showMsg()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                queryText = newText!!
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> {
                "Filter".showMsg()
                return true
            }
            else -> {
                return false
            }
        }
    }

}
