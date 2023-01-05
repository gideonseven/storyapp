package com.don.core_image_loader

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


/**
 * Created by gideon on 05 January 2023
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