package com.gallardf.melisearch.data.datasource

import kotlinx.serialization.Serializable

@Serializable
data class SellerAddress(
    val city: City?,
    val state: State?
)
