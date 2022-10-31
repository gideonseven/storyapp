package com.don.storyApp.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.don.storyApp.util.Constant
import javax.inject.Singleton


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@Singleton
class AppPreferences(val sharedPreferences: SharedPreferences) {
    /**
     * Puts a value for the given [key]. Using [edit] extension function make it easier.
     */
    operator fun SharedPreferences.set(key: String, value: Any?) = when (value) {
        is String? -> edit { putString(key, value) }
        is Int -> edit { putInt(key, value) }
        is Boolean -> edit { putBoolean(key, value) }
        is Float -> edit { putFloat(key, value) }
        is Long -> edit { putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

    /**
     * Finds a preference based on the given [key]. [T] is the type of value
     *
     * @param defaultValue optional defaultValue - will take a default defaultValue if it is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T =
        when (T::class) {
            String::class -> getString(key, defaultValue as? String ?: "") as T
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
            else -> throw UnsupportedOperationException("Not yet implemented")
        }

    companion object {
        private const val AUTH_KEY = "auth_key"
        private const val LIST_STORY = "list_story"
    }

    fun setValue(key: String, value: Any?) {
        sharedPreferences[key] = value
    }

    inline fun <reified T : Any> getValue(key: String, defaultValue: T? = null): T =
        sharedPreferences[key, defaultValue]

    fun clear() = sharedPreferences.edit().clear().apply()

    var accessToken: String?
        get() = getValue(AUTH_KEY, Constant.TEXT_BLANK)
        set(value) = setValue(AUTH_KEY, value)

    var listStory: String?
        get() = getValue(LIST_STORY, Constant.TEXT_BLANK)
        set(value) = setValue(LIST_STORY, value)

}