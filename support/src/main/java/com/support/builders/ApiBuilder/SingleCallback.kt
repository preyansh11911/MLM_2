package com.support.builders.ApiBuilder

import com.support.builders.ApiBuilder.WebServices.ApiNames

interface SingleCallback {
    fun onSuccess(o: Any, apiName: ApiNames)
    fun onFailure(throwable: Throwable, apiName: ApiNames)
}