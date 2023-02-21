package com.adrian.bankuishcodechallenge.framework.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun String.formatTo(format: String) : String {
    val formatter = DateTimeFormatter.ofPattern(format)
    val odt = OffsetDateTime.parse(this, formatter)
    return odt.toString()
}