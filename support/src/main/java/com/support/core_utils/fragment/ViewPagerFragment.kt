package com.support.core_utils.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.support.core_utils.FragmentViewModel

abstract class ViewPagerFragment<T : ViewPagerFragment<T, DB, VM>, DB : ViewDataBinding, VM : FragmentViewModel> : Fragment() {

    lateinit var coreFragment: T
    lateinit var binding: DB
    var vm: VM? = null
        get() {
            if (field == null) field = createViewModel()
            return field
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getRoot()
    }

    fun getRoot(): View {
        coreFragment = getFragmentContext()
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutView(), null, false)
        setVM(binding)
        workArea()
        return binding.root
    }

    abstract fun workArea()

    @LayoutRes
    abstract fun getLayoutView(): Int

    abstract fun getFragmentContext(): T

    abstract fun createViewModel(): VM

    abstract fun setVM(binding: DB)
}