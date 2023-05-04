package com.mangbaam.mvitemplate.extension

import android.content.Context
import android.widget.Toast

fun Context.toast(message: CharSequence, duration: Int? = null) {
    Toast.makeText(this, message, duration ?: Toast.LENGTH_SHORT).show()
}
