package com.lindevhard.felia.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun toast(context: Context,message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun toast(context: Context, @StringRes message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

