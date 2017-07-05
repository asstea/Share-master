package cn.asstea.share

import android.app.Activity
import cn.asstea.share.activity.ShareActivity
import cn.asstea.share.entity.*
import cn.asstea.share.sdk.ShareQQImpl
import cn.asstea.share.sdk.ShareWeiBoImpl
import cn.asstea.share.sdk.ShareWeixinImpl

/**
 * author : asstea
 * time   : 2017/06/29
 * desc   : ShareManager 实现
 */
internal class ShareManagerImpl private constructor() : ShareManager {

    override val shareWeiBo: ShareWeiBoImpl by lazy {
        ShareWeiBoImpl()
    }


    override val shareWeixin: ShareWeixinImpl by lazy {
        ShareWeixinImpl()
    }

    override val shareQQ: ShareQQImpl by lazy {
        ShareQQImpl()
    }

    override var dialog: ShareActivity? = null

    override fun shareToQQ(qQShareInfo: QQShareInfo) {
        shareQQ.shareToQQ(qQShareInfo)
    }

    override fun shareToWeixinFriends(weixinShareInfo: WeixinShareInfo) {
        shareWeixin.shareToWeixinFriends(weixinShareInfo)
    }

    override fun shareToWeixinFriendsQueue(weixinShareInfo: WeixinShareInfo) {
        shareWeixin.shareToWeixinFriendsQueue(weixinShareInfo)
    }

    override fun shareWeibo(weiBoShareInfo: WeiBoShareInfo) {
        shareWeiBo.shareWeibo(weiBoShareInfo)
    }

    override fun <T> show(key: String, sourceActivity: Activity, data: T, onClickListener: ShareItemOnClickListener<T>) {
        val shareItemData = getShareItemData(key)
        shareItemData?.let {
            ShareActivity.startShareActivity(sourceActivity, it)
        }
        ShareOnClickManager.registerInstance(onClickListener = onClickListener, data = data)
    }

    override fun <T> show(key: String, sourceActivity: Activity, data: T, onClickListener: ShareItemOnClickListener<T>, shareResultCallback: ShareResultCallback<T>) {
        val shareItemData = getShareItemData(key)
        shareItemData?.let {
            ShareActivity.startShareActivity(sourceActivity, it)
        }
        ShareOnClickManager.registerInstance(shareResultCallback, onClickListener, data)
    }

    override fun setShareOnClickListener(shareInfo: ShareInfo) {
        ShareOnClickManager.shareOnClickManager?.execute(dialog, shareInfo)
    }

    override fun putShareItemData(key: String, value: ShareItemData) {
        SHARE_TABLE.put(key, value)
    }

    override fun postShareItemData(key: String, value: ShareItemData) {
        if (SHARE_TABLE.containsKey(key)) {
            SHARE_TABLE[key] = value
        }
    }

    override fun getShareItemData(key: String): ShareItemData? {
        if (SHARE_TABLE.containsKey(key)) {
            return SHARE_TABLE[key]
        } else {
            return null
        }
    }

    override fun clearAllShareItemData() {
        SHARE_TABLE.clear()
    }

    override fun hide() {
        dialog?.finish()
        ShareOnClickManager.unRegisterInstance()
    }

    companion object {

        //分享配置表
        private val SHARE_TABLE: HashMap<String, ShareItemData> = hashMapOf()

        /**
         * 单例
         */
        fun registerInstance(): ShareManager {
            return ShareMangerInnerClass.INSTANCE
        }
    }

    /**
     * 保证线程安全
     */
    private object ShareMangerInnerClass {
        var INSTANCE = ShareManagerImpl()
    }

}