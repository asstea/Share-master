package cn.asstea.share.util

import android.content.Context

object SharedPreferencesUtil {

    private const val SHAREDPREFERENCE_NAME = "share_android_cache"

    fun putLong(context: Context, key: String, value: Long): Boolean {
        val mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE)
        return mSharedPreferences.edit().putLong(key, value).commit()
    }

    fun getLong(context: Context, key: String, value: Long): Long {
        val mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE)
        return mSharedPreferences.getLong(key, value)
    }

    fun remove(context: Context, key: String): Boolean {
        val mSharedPreferences = context.getSharedPreferences(SHAREDPREFERENCE_NAME, Context.MODE_PRIVATE)
        return mSharedPreferences.edit().remove(key).commit()
    }
}
