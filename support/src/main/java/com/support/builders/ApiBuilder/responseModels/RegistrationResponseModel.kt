package com.support.builders.ApiBuilder.responseModels

import com.google.gson.annotations.SerializedName

data class RegistrationResponseModel(@SerializedName("success")
                                     val success: Int = 0,
                                     @SerializedName("message")
                                     val message: String = "",
                                     @SerializedName("status")
                                     val status: Int = 0)