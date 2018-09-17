package com.preyansh.mlm.dashboard.settings

import com.preyansh.mlm.R
import com.preyansh.mlm.databinding.FragmentSettingsBinding
import com.support.core_utils.CoreFragment_DataBinding
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : CoreFragment_DataBinding<SettingsFragment, FragmentSettingsBinding, SettingsViewModel>() {
    override fun workArea() {
        vm?.let {
            it.view.btn_logout.setOnClickListener(it.logoutClickListener)
            it.view.btn_delete_ac.setOnClickListener(it.deleteClickListener)
        }
    }

    override fun getLayoutView() = R.layout.fragment_settings

    override fun getFragmentContext() = this@SettingsFragment

    override fun createViewModel() = SettingsViewModel(getFragmentContext())

    override fun setVM(binding: FragmentSettingsBinding) {
        binding.vm = vm
    }
}
