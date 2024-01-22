package com.gallardf.melisearch.data.datasource


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDescriptionResponse(
    @SerializedName("plain_text")
    val text: String?
)