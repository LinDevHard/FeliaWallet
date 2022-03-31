package com.lindevhard.felia.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.ContextCompat


fun Context.toast(message: String) {
    toast(context = this, message = message)
}

fun Context.copyText(text: String) {
    val clipboard: ClipboardManager? =
        ContextCompat.getSystemService(this, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("~!", text)
    clipboard?.setPrimaryClip(clip)
}