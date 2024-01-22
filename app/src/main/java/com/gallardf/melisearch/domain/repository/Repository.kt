package com.gallardf.melisearch.domain.repository

import com.gallardf.melisearch.data.Resource

abstract class Repository {

    protected suspend fun <R> networkCall(
        callFunction: suspend () -> R
    ) : Resource<R> {
        return try {
            val response= callFunction.invoke()
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}