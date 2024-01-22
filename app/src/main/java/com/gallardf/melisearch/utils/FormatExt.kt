package com.gallardf.melisearch.utils

import java.text.NumberFormat
import java.util.Locale

fun Float.formatToCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    formatter.minimumFractionDigits = 2
    formatter.maximumFractionDigits = 2
    return formatter.format(this)
}