package com.support.builders.ApiBuilder

import com.google.gson.annotations.SerializedName
import com.support.POJOModel


data class MoviesItem(@SerializedName("createdby")
                      val createdby: String = "",
                      @SerializedName("firstappearance")
                      val firstappearance: String = "",
                      @SerializedName("imageurl")
                      val imageurl: String = "",
                      @SerializedName("name")
                      val name: String = "",
                      @SerializedName("publisher")
                      val publisher: String = "",
                      @SerializedName("bio")
                      val bio: String = "",
                      @SerializedName("team")
                      val team: String = "",
                      @SerializedName("realname")
                      val realname: String = "") : POJOModel()
