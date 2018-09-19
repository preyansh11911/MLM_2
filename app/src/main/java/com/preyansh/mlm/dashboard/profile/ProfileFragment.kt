package com.preyansh.mlm.dashboard.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.preyansh.mlm.Constants
import com.preyansh.mlm.R
import com.preyansh.mlm.databinding.FragmentProfileBinding
import com.support.core_utils.CoreFragment_DataBinding

class ProfileFragment : CoreFragment_DataBinding<ProfileFragment, FragmentProfileBinding, ProfileViewModel>() {

    companion object {
        fun newInstance(fromValue: String): ProfileFragment {
            val fragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putString(Constants.FROM, fromValue)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun workArea() {

    }

    override fun getLayoutView() = R.layout.fragment_profile

    override fun getFragmentContext() = this@ProfileFragment

    override fun createViewModel() = ProfileViewModel(getFragmentContext())

    override fun setVM(binding: FragmentProfileBinding) {
        binding.vm = vm
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.profile_option_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_edit -> {
                vm!!.makeProfileEditable()
                return true
            }
            else -> {
                return false
            }
        }
    }
}
