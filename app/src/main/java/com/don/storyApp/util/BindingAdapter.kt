package com.don.storyApp.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.don.storyApp.R


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@BindingAdapter("imageSrcFromUrl")
fun ImageView.loadImageFromUrl(
    imageUrl: String?
) {
    imageUrl?.let {
        Glide
            .with(this.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder_square)
            .into(this)
    }
}
