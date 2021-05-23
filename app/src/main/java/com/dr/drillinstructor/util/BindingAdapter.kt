package com.dr.drillinstructor.util


import android.widget.TextView
import androidx.databinding.BindingAdapter

// we need this as TextView does not seem to have antialias in XML
@BindingAdapter("android:antialias")
fun TextView.setAntialias(value: Boolean) {
    paint.isAntiAlias = value
}