/*
 * Created by Saeedus Salehin on 20/7/25, 1:48 AM.
 */

package com.sazim.teebay.products.domain.utils

import android.content.Context
import android.net.Uri

fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.use { it.readBytes() }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}