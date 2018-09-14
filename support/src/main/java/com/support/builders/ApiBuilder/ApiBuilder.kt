package com.support.builders.ApiBuilder

import com.example.parth.kotlinpractice_2.support.CoreActivity
import com.google.gson.GsonBuilder
import com.support.builders.ApiBuilder.WebServices.ApiNames
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun <T> CoreActivity<*, *, *>.callApi(
        apiName: ApiNames,
        singleCallback: SingleCallback,
        api: () -> Observable<T>
) = ApiBuilder(
        this,
        apiName,
        singleCallback,
        api
)/*.apply(builder)*/
fun setBaseURL(baseURL: String) {
    ApiBuilder.BASE_URL = baseURL
}

class ApiBuilder<T>(
        mActivity: CoreActivity<*, *, *>,
        apiName: ApiNames,
        singleCallback: SingleCallback,
        api: () -> Observable<T>
) {

    companion object {
        val OKHTTP_TIMEOUT = 30.toLong()
        val TAG = "ApiClient"
        var BASE_URL = ""
        var retrofit: Retrofit? = null
        var webServices: WebServices? = null
            get() {
                return retrofit!!.create(WebServices::class.java)
            }
    }

    var observableApi: Observable<T>

    init {
        val gson = with(GsonBuilder()) {
            setLenient()
            setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            create()
        }

        try {
            if (BASE_URL.isNotBlank())
                retrofit = with(Retrofit.Builder()) {
                    baseUrl(BASE_URL)
                    client(okHttpClient())
                    addConverterFactory(GsonConverterFactory.create(gson))
                    addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    build()
                }
            else
                throw IllegalArgumentException("Base URL can not be empty")
        } catch (e: IllegalArgumentException) {
            print(e.localizedMessage)
        }

        observableApi = api.invoke()
        subscribeToSingle(observableApi, mActivity.compositeDisposable!!, apiName, singleCallback)
    }

    fun okHttpClient(): OkHttpClient {
        var client: OkHttpClient? = null
        if (client == null) {
            val clientBuilder = with(OkHttpClient.Builder()) {
                retryOnConnectionFailure(true)
                connectTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
            }

            clientBuilder.addInterceptor { chain ->
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                chain.proceed(requestBuilder.build())
            }

            client = clientBuilder.build()
        }
        return client!!
    }

    fun subscribeToSingle(
            observable: Observable<T>,
            compositeDisposable: CompositeDisposable,
            apiName: ApiNames,
            singleCallback: SingleCallback
    ) {
        makeSingle(observable).subscribe(getSingleObserver(compositeDisposable, apiName, singleCallback))
    }

    fun makeSingle(observable: Observable<T>): Single<T> {
        return Single.fromObservable(observable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

    }

    fun getSingleObserver(
            compositeDisposable: CompositeDisposable,
            apiName: ApiNames,
            singleCallback: SingleCallback
    ): SingleObserver<T> {
        return object : SingleObserver<T> {
            override fun onSuccess(t: T) {
                singleCallback.onSuccess(t as Any, apiName)
            }

            override fun onSubscribe(d: Disposable) {
                if (compositeDisposable != null) compositeDisposable.add(d)
            }

            override fun onError(e: Throwable) {
                singleCallback.onFailure(e, apiName)
            }

        }
    }
}