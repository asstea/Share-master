package cn.asstea.share

import android.content.Intent
import cn.asstea.share.activity.ShareActivity
import cn.asstea.share.entity.ShareInfo
import cn.asstea.share.entity.ShareItemOnClickListener
import cn.asstea.share.entity.ShareResult
import cn.asstea.share.entity.ShareResultCallback

/**
 * author : asstea
 * time   : 2017/06/29
 * desc   :
 */
internal class ShareOnClickManager<T> private constructor(
        private val shareResultCallback: ShareResultCallback<T>? = null,
        private val tShareItemOnClickListener: ShareItemOnClickListener<T>,
        private val data: T) {

    private var shareActivity: ShareActivity? = null
    private var shareInfo: ShareInfo? = null

    fun execute(shareActivity: ShareActivity?, shareInfo: ShareInfo) {
        this.shareActivity = shareActivity
        this.shareInfo = shareInfo
        if (shareOnClickManager != null) {
            tShareItemOnClickListener.click(shareInfo, data)
        }
    }

    fun setSourceActivityResult(code: ShareResult.Code) {
        val intent = Intent()
        intent.putExtra(ShareResult.KEY_CODE_NAME, code)
        intent.putExtra(ShareResult.KEY_SHAREINFO_NAME, shareInfo)
        shareActivity?.setResult(ShareResult.ACTIVITY_RESULT_CODE, intent)
        shareInfo?.let {
            shareResultCallback?.shareResult(it, code, data)
        }

    }

    companion object {

        @Volatile var shareOnClickManager: ShareOnClickManager<*>? = null
            private set

        fun <T> registerInstance(shareResultCallback: ShareResultCallback<T>? = null, onClickListener: ShareItemOnClickListener<T>, data: T) {
            if (shareOnClickManager == null) {
                synchronized(ShareOnClickManager::class.java) {
                    if (shareOnClickManager == null) {
                        shareOnClickManager = ShareOnClickManager(shareResultCallback, onClickListener, data)
                    }
                }
            }
        }

        fun unRegisterInstance() {
            shareOnClickManager?.shareActivity = null
            shareOnClickManager?.shareInfo = null
            shareOnClickManager = null
        }
    }

}
