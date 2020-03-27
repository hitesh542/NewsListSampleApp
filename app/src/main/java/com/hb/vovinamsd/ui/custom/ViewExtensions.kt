package com.hb.vovinamsd.ui.custom

import android.text.TextUtils
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("displayFromUtcDate")
fun setDisplayDateFromUTC(@Nullable textView: TextView, @Nullable dateString: String?) {
    if (!TextUtils.isEmpty(dateString)) {
        textView.text = dateString?.getUTCDate()
    } else
        textView.text = ""
}

/** Converting UTC date to redable date**/
fun String.getUTCDate(): String? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    return try {
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = dateFormat.parse(this)
        val displayDateFormat = SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.US)
        displayDateFormat.format(date!!)
    } catch (e: ParseException) {
        this
    }
}