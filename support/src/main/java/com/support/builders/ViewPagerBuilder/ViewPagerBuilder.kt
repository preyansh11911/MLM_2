package com.support.builders.ViewPagerBuilder

import android.support.annotation.DrawableRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.Gravity
import com.support.POJOModel
import com.support.kotlin.drawable

inline fun <T : POJOModel> ViewPager.setUp(
        mFragment: Fragment,
        mItems: ArrayList<T>,
        builder: ViewPagerBuilder<T>.() -> Unit) = ViewPagerBuilder(mFragment, this, mItems).apply(builder)

class ViewPagerBuilder<T : POJOModel>(
        val mFragment: Fragment,
        val viewPager: ViewPager,
        val mItems: ArrayList<T>) : FragmentPagerAdapter(mFragment.childFragmentManager) {

    var fragmentInstanceListener: ((T) -> Fragment)? = null
    var animation: ViewPager.PageTransformer? = null
        set(value) {
            viewPager.setPageTransformer(true, value)
        }

    override fun getItem(position: Int): Fragment {
        return fragmentInstanceListener?.invoke(mItems[position])!!
    }

    override fun getCount(): Int = mItems.size

    init {
        viewPager.adapter = this
    }

    fun getFragmentInstance(l: (T) -> Fragment) {
        fragmentInstanceListener = l
    }

    fun setIndicator(tabs: TabLayout, @DrawableRes drawableResID: Int) {
        tabs.tabGravity = Gravity.CENTER
        tabs.setBackgroundResource(drawableResID)
//        tabs.setBackgroundDrawable(drawableResID.drawable(mFragment.activity!!))
    }
}