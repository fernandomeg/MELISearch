package com.gallardf.melisearch.domain.models

data class ProductDetailModel(
    val id: String?,
    val title: String?,
    val acceptsMercadopago: Boolean?,
    val condition: String?,
    val shipping: Boolean?,
    val pictures: List<String>?,
    val price: Float?,
    val secureThumbnail: String?,
    val generalAddress: String?
)
