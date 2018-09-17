package com.preyansh.mlm.dashboard.profile

import android.view.View
import com.support.core_utils.FragmentViewModel

class ProfileViewModel(val mFragment: ProfileFragment) : FragmentViewModel() {
    val binding = mFragment.binding
    val view: View = binding.root

}