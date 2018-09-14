package com.support

import android.os.Parcel
import android.os.Parcelable

open class POJOModel() : Parcelable {
    var id: Long = 0

    var isLoader: Boolean = false

    constructor(parcel: Parcel) : this()

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<POJOModel> {
        override fun createFromParcel(parcel: Parcel): POJOModel {
            return POJOModel(parcel)
        }

        override fun newArray(size: Int): Array<POJOModel?> {
            return arrayOfNulls(size)
        }
    }
}