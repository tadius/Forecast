package com.tadiuzzz.forecast.feature.current

import android.graphics.Typeface
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:textStyle")
fun bindTextStyle(view: TextView, style: String) {
    when (style) {
        "bold" -> view.setTypeface(null, Typeface.BOLD)
        "normal" -> view.setTypeface(null, Typeface.NORMAL)
    }
}
