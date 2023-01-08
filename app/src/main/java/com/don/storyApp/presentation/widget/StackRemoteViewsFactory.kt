package com.don.storyApp.presentation.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.don.image_loader.getBitmapFromUrl
import com.don.storyApp.R
import com.don.storyApp.data.local.AppPreferences
import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.Constant.Companion.TEXT_BLANK
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Created by gideon on 31 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()
    private val pref = AppPreferences(
        sharedPreferences = mContext.getSharedPreferences(
            "story_app_pref_file",
            Context.MODE_PRIVATE
        )
    )
    private var list = arrayListOf<Story>()
    override fun onCreate() {
        if (pref.listStory != null && pref.listStory != TEXT_BLANK) {
            list = Gson().fromJson(pref.listStory, object : TypeToken<ArrayList<Story>>() {}.type)
        }
    }

    override fun onDataSetChanged() {
        //Ini berfungsi untuk melakukan refresh saat terjadi perubahan.
        for (story in list) {
            mWidgetItems.add(getBitmapFromUrl(mContext, story.photoUrl.orEmpty()))
        }
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])

        val extras = bundleOf(
            ImagesBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}