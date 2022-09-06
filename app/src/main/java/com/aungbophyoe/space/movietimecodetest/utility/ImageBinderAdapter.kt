package com.aungbophyoe.space.movietimecodetest.utility

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.aungbophyoe.space.movietimecodetest.BuildConfig
import com.aungbophyoe.space.movietimecodetest.R

object ImageBinderAdapter {
    fun setImageUrl(imageView: ImageView, url: String) {
        if (url == null) {
            imageView.load(R.drawable.ic_default_image)
        } else {
            imageView.load("${BuildConfig.IMAGE_URL}$url"){
                crossfade(true)
                placeholder(R.drawable.ic_default_image)
            }
        }
    }
}