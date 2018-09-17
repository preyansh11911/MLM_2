package com.preyansh.mlm.dashboard.profile

import com.preyansh.mlm.R
import com.preyansh.mlm.databinding.FragmentProfileBinding
import com.support.core_utils.CoreFragment_DataBinding

class ProfileFragment : CoreFragment_DataBinding<ProfileFragment, FragmentProfileBinding, ProfileViewModel>() {
    override fun workArea() {

    }

    override fun getLayoutView() = R.layout.fragment_profile

    override fun getFragmentContext() = this@ProfileFragment

    override fun createViewModel() = ProfileViewModel(getFragmentContext())

    override fun setVM(binding: FragmentProfileBinding) {
        binding.vm = vm
    }
}
