package com.sazim.teebay.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.toIso8601String(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(date)
}


fun String.toFormattedDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val date = inputFormat.parse(this)
        val outputFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        this
    }
}
