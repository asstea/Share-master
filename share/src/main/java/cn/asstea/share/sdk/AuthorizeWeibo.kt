package cn.asstea.share.sdk

import android.app.Activity
import android.content.Context

/**
 *     author : Holy
 *     time   : 2017/06/30
 *     desc   :
 *     version: 1.0
 */
interface AuthorizeWeibo {

    fun authorize(activity: Activity, authorizeListener: WeiboListener.AuthorizeListener)

    fun unAuthorize(context: Context)

}