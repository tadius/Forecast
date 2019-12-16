package com.tadiuzzz.forecast.feature.current

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tadiuzzz.forecast.R


@BindingAdapter("app:textStyle")
fun bindTextStyle(view: TextView, style: String) {
    when (style) {
        "bold" -> view.setTypeface(null, Typeface.BOLD)
        "normal" -> view.setTypeface(null, Typeface.NORMAL)
    }
}

@BindingAdapter("app:weatherIcon")
fun loadImage(view: ImageView, icon: String?) {
//    if (icon.isNullOrEmpty()) return
    val imageRes = when (icon) {
        "01d" -> R.drawable.ic_01d
        "02d" -> R.drawable.ic_02d
        "03d" -> R.drawable.ic_03d
        "04d" -> R.drawable.ic_04d
        "09d" -> R.drawable.ic_09d
        "10d" -> R.drawable.ic_10d
        "11d" -> R.drawable.ic_11d
        "13d" -> R.drawable.ic_13d
        "50d" -> R.drawable.ic_50d

        "01n" -> R.drawable.ic_01n
        "02n" -> R.drawable.ic_02n
        "03n" -> R.drawable.ic_03n
        "04n" -> R.drawable.ic_04n
        "09n" -> R.drawable.ic_09n
        "10n" -> R.drawable.ic_10n
        "11n" -> R.drawable.ic_11n
        "13n" -> R.drawable.ic_13n
        "50n" -> R.drawable.ic_50n

        else -> R.drawable.ic_default_weather
    }
    view.setImageResource(imageRes)

}
