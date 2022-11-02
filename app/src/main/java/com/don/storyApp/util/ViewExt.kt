package com.don.storyApp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.don.storyApp.R
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.io.IOException
import java.net.URL


/**
 * Created by gideon on 28 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

fun showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
        setTextColor(
            ContextCompat.getColor(
                this.context,
                R.color.md_white_1000
            )
        )
        show()
    }
}

fun getBitmap(context: Context, imageUrl: String): Bitmap {
    var bitmapImage: Bitmap? = null
    val defaultBitmap: Bitmap
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

    defaultBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sample)
    return bitmapImage ?: defaultBitmap
}

fun getBitmapFromUrl(context: Context, imageUrl: String): Bitmap {
    var image: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sample)
    try {
        val url = URL(imageUrl)
        image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        Timber.e("==== try getBitmapFromUrl $imageUrl ")

    } catch (e: IOException) {
        Timber.e("==== ERROR getBitmapFromUrl $e ")
    }
    return image
}