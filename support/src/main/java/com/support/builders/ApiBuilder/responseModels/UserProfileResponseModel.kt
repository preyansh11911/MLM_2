package com.support.builders.ApiBuilder.responseModels

import com.google.gson.annotations.SerializedName

data class UserProfileResponseModel(@SerializedName("data")
                                    val data: List<DataItem>?,
                                    @SerializedName("success")
                                    val success: Int = 0,
                                    @SerializedName("count")
                                    val count: Int = 0,
                                    @SerializedName("status")
                                    val status: Int = 0) {
    data class DataItem(@SerializedName("number")
                        val number: String = "",
                        @SerializedName("firstname")
                        val firstname: String = "",
                        @SerializedName("refid")
                        val refid: String = "",
                        @SerializedName("email")
                        val email: String = "",
                        @SerializedName("lastname")
                        val lastname: String = "")
}