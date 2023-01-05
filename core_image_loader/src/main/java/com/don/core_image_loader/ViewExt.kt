package com.don.core_image_loader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.IOException
import java.net.URL


/**
 * Created by gideon on 05 January 2023
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
fun getBitmap(context: Context, imageUrl: String): Bitmap {
    var bitmapImage: Bitmap? = null
    Glide.with(context)
        .asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapImage = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // this is called when imageView is cleared on lifecycle call or for
                // some other reason.
                // if you are referencing the bitmap somewhere else too other than this imageView
                // clear it here as you can no longer have the bitmap
            }
        })

    val defaultBitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sample)
    return bitmapImage ?: defaultBitmap
}

fun getBitmapFromUrl(context: Context, imageUrl: String): Bitmap {
    var image: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sample)
    try {
        val url = URL(imageUrl)
        image = BitmapFactory.decodeStream(url.openConnection().getInputStream())

    } catch (e: IOException) {
    }
    return image
}