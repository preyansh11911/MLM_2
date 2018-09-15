package com.support.builders.ApiBuilder

import com.support.builders.ApiBuilder.responseModels.LoginResponseModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WebServices {

//    @GET("")
//    fun <T : POJOModel> sampleWebService(): Observable<T>
//
//    @GET("demos/marvel/")
//    fun movieList(): Observable<ArrayList<MoviesItem>>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("deviceid") deviceid: String
    ): Observable<LoginResponseModel>

    enum class ApiNames {
        sample, movieList, login
    }
}