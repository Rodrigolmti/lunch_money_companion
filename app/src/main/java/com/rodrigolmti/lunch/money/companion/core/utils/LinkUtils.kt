package com.rodrigolmti.lunch.money.companion.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(intent)
}