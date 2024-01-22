package com.gallardf.melisearch.utils

import com.gallardf.melisearch.core.Constants.PRODUCT_THUMBNAIL_REPLACE_COUNT_OF_DIGITS
import com.gallardf.melisearch.core.Constants.PRODUCT_THUMBNAIL_REPLACE_TO_PREFIX

fun String.parseUrl(): String =
    this.replaceRange(0..PRODUCT_THUMBNAIL_REPLACE_COUNT_OF_DIGITS, PRODUCT_THUMBNAIL_REPLACE_TO_PREFIX)

