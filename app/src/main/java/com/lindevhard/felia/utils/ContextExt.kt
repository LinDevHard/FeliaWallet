package com.lindevhard.felia.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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

fun Context.shareText(text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)

    }

    val shareIntent = Intent.createChooser(intent, null)
    startActivity(shareIntent)
}

fun Context.goAppPermissions() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse("package:${applicationContext.packageName}")
    }
    startActivity(intent)
}