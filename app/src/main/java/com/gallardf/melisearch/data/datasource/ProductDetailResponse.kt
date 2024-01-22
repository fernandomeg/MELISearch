package com.gallardf.melisearch.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDetailResponse(
    val title: String?,
    @SerializedName("accepts_mercadopago")
    val acceptsMercadopago: Boolean?,
    val condition: String?,
    val shipping: Shipping?,
    val id: String?,
    val pictures: List<Picture?>?,
    val price: Float?,
    @SerializedName("secure_thumbnail")
    val secureThumbnail: String?,
    @SerializedName("seller_address")
    val sellerAddress: SellerAddress?
)
