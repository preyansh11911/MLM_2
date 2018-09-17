package com.support.builders.ApiBuilder

import com.support.builders.ApiBuilder.responseModels.LoginResponseModel
import com.support.builders.ApiBuilder.responseModels.RegistrationResponseModel
import com.support.builders.ApiBuilder.responseModels.UserListResponseModel
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

    @FormUrlEncoded
    @POST("register.php")
    fun register(
            @Field("firstname") firstname: String,
            @Field("lastname") lastname: String,
            @Field("adhdarcard") adhdarcard: String,
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("refralid") refralid: String,
            @Field("deviceid") deviceid: String,
            @Field("number") number: String
    ): Observable<RegistrationResponseModel>

    @FormUrlEncoded
    @POST("getuser.php")
    fun getUserList(@Field("uid") uid: String): Observable<UserListResponseModel>

    enum class ApiNames {
        sample, movieList, login, registration, userList
    }
}