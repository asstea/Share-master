package cn.asstea.share

import android.app.Activity
import cn.asstea.share.activity.ShareActivity
import cn.asstea.share.entity.ShareInfo
import cn.asstea.share.entity.ShareItemData
import cn.asstea.share.entity.ShareItemOnClickListener
import cn.asstea.share.entity.ShareResultCallback
import cn.asstea.share.sdk.*

/**
 * author : asstea
 * time   : 2017/06/29
 * desc   : ShareManager接口
 */
interface ShareManager : ShareWeixin, ShareQQ, ShareWeibo {

    val shareWeixin: ShareWeixinImpl

    val shareQQ: ShareQQImpl

    val shareWeiBo:ShareWeiBoImpl

    var dialog: ShareActivity?

    /**
     * 添加一个分享类型
     */
    fun putShareItemData(key: String, value: ShareItemData)


    /**
     * 修改一个分享类型
     */
    fun postShareItemData(key: String, value: ShareItemData)

    /**
     * 查找一个分享类型
     */
    fun getShareItemData(key: String): ShareItemData?

    /**
     * 清除所有分享类型
     */
    fun clearAllShareItemData()


    /**
     * 分享小格子點擊事件
     */
    fun setShareOnClickListener(shareInfo: ShareInfo)


    /**
     * 展示弹窗
     */
    fun <T> show(
            key: String,
            sourceActivity: Activity,
            data: T,
            onClickListener: ShareItemOnClickListener<T>)


    fun <T> show(key: String,
                 sourceActivity: Activity,
                 data: T, onClickListener: ShareItemOnClickListener<T>,
                 shareResultCallback: ShareResultCallback<T>)

    /**
     * 隐藏弹窗
     */
    fun hide()

}