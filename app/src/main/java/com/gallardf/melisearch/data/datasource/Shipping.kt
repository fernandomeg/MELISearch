package com.gallardf.melisearch.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Shipping(
    @SerializedName("free_shipping")
    val freeShipping: Boolean?,
)