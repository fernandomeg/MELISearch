package com.gallardf.melisearch.data.datasource

import kotlinx.serialization.Serializable

@Serializable
data class State(
    val id: String,
    val name: String
)
