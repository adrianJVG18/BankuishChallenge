package com.adrian.bankuishcodechallenge.framework.utils

import android.content.res.Resources

fun Int.toDp(): Int =
    (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
