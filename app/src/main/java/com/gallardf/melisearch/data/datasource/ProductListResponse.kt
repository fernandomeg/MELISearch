package com.gallardf.melisearch.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    @SerializedName("results")
    val results: List<ProductResponse>
)

@Serializable
data class ProductResponse(
    val id: String?,
    val price: Float?,
    val title: String?,
    val thumbnail: String?,
    @SerializedName("accepts_mercadopago")
    val acceptsMercadoPago: Boolean?
)
