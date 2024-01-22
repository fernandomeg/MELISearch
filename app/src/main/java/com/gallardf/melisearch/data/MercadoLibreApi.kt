package com.gallardf.melisearch.data

import com.gallardf.melisearch.core.Constants.GET_DESCRIPTION
import com.gallardf.melisearch.core.Constants.GET_DETAILS
import com.gallardf.melisearch.core.Constants.GET_PRODUCTS
import com.gallardf.melisearch.data.datasource.ProductDescriptionResponse
import com.gallardf.melisearch.data.datasource.ProductDetailResponse
import com.gallardf.melisearch.data.datasource.ProductListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreApi {

    @GET(GET_PRODUCTS)
    suspend fun getProductsByQuery(
        @Query("q") productQuery: String
    ): ProductListResponse

    @GET(GET_DETAILS)
    suspend fun getDetailsById(
        @Path("productId") productId: String
    ): ProductDetailResponse

    @GET(GET_DESCRIPTION)
    suspend fun getDescriptionById(
        @Path("productId") productId: String
    ): ProductDescriptionResponse

}