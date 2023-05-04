package com.mangbaam.mvitemplate.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(
    message: CharSequence,
    duration: Int? = null,
    action: CharSequence? = null,
    listener: View.OnClickListener? = null,
) {
    Snackbar
        .make(this, message, duration ?: Snackbar.LENGTH_SHORT)
        .setAction(action, listener)
        .show()
}
