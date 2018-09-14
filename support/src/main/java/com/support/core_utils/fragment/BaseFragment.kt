package com.example.parth.kotlinpractice_2.support


import android.os.Bundle
import android.support.v4.app.Fragment
import com.support.POJOModel

open class BaseFragment : Fragment() {
    fun newInstance() = this
    fun newInstance(mItem: POJOModel): BaseFragment {
        val frag = BaseFragment()
        val args = Bundle()
        args.putParcelable("single_item", mItem)
        frag.arguments = args
        return frag
    }
}
