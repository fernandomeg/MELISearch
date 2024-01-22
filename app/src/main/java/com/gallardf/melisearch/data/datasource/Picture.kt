package com.gallardf.melisearch.data.datasource

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    @SerializedName("secure_url")
    val secureUrl: String?,
)