package com.support.builders.ApiBuilder.responseModels

import com.google.gson.annotations.SerializedName
import com.support.POJOModel

data class UserListResponseModel(@SerializedName("data")
                                 val data: ArrayList<UserListDataItem>?,
                                 @SerializedName("success")
                                 val success: Int = 0,
                                 @SerializedName("count")
                                 val count: Int = 0,
                                 @SerializedName("status")
                                 val status: Int = 0) : POJOModel() {

    data class UserListDataItem(@SerializedName("firstname")
                                val firstname: String = "",
                                @SerializedName("refid")
                                val refid: String = "",
                                @SerializedName("email")
                                val email: String = "",
                                @SerializedName("lastname")
                                val lastname: String = "",
                                @SerializedName("number")
                                val number: String = "") : POJOModel() {

//        init {
//            UserListDataItem.id = refid.toLong()
//            this.id=UserListDataItem.id
//        }
//        companion object {
//            var id: Long = 0
//        }

    }
}