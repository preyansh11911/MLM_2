package com.support.core_utils

import android.arch.lifecycle.ViewModel
import com.example.parth.kotlinpractice_2.support.CoreActivity

open class FragmentViewModel : ViewModel() {
    var activity: CoreActivity<*, *, *>? = null
}
