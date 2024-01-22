package com.gallardf.melisearch.core

object Constants {

    const val TAG = "MELI Search"
    /*** Code to Mexico ***/
    private const val SITE_ID = "MLM"
    /*** endpoints to search products and view detail product ***/
    const val GET_PRODUCTS = "sites/$SITE_ID/search"
    const val GET_DETAILS = "items/{productId}"
    const val GET_DESCRIPTION = "items/{productId}/description"

    /*** constants to format imageUrl ***/
    const val PRODUCT_THUMBNAIL_REPLACE_COUNT_OF_DIGITS = 3
    const val PRODUCT_THUMBNAIL_REPLACE_TO_PREFIX = "https"
}

