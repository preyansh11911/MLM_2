package com.support.builders.ApiBuilder

import com.support.builders.ApiBuilder.responseModels.*
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

    @FormUrlEncoded
    @POST("logout.php")
    fun logout(@Field("deviceid") deviceid: String): Observable<LogoutResponseModel>

    @FormUrlEncoded
    @POST("changepassword.php")
    fun changePassword(
            @Field("uid") uid: String,
            @Field("oldpassword") oldpassword: String,
            @Field("newpassword") newpassword: String
    ): Observable<ChangePasswordResponseModel>

    @FormUrlEncoded
    @POST("getuserdetail.php")
    fun userProfile(
            @Field("uid") uid: String,
            @Field("userid") userid: String
    ): Observable<UserProfileResponseModel>

    enum class ApiNames {
        sample, movieList, login, registration, userList, logout, changePassword, userProfile
    }
}