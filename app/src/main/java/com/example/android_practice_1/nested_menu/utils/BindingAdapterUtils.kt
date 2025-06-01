package com.example.android_practice_1.nested_menu.utils

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

object BindingAdapterUtils {

    @BindingAdapter("android:textTheme")
    @JvmStatic
    fun setImageTint(imageView: ImageView, @ColorRes colorRes: Int) {
        imageView.setColorFilter(ContextCompat.getColor(imageView.context, colorRes))
    }

}