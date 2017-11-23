package cn.asstea.share.sdk

import android.app.Activity
import android.os.Bundle
import cn.asstea.share.Share
import cn.asstea.share.ShareOnClickManager
import cn.asstea.share.entity.QQShareInfo
import cn.asstea.share.entity.ShareResult
import cn.asstea.share.entity.ShareResultCode
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError

class ShareQQImpl : ShareQQ {


    private val QQ_ID: String? = Share.config().QQ_ID

    private val tencent: Tencent by lazy {
        Tencent.createInstance(QQ_ID ?: "", Share.app())
    }

    override fun shareToQQ(qQShareInfo: QQShareInfo) {
        QQ_ID?.let {
            with(qQShareInfo) {
                share(Share.shareManager().dialog, title, description, url, imgUrl)
            }
        }
    }

    private fun share(activity: Activity?, title: String?, desc: String?, shareUrl: String?, imgUrl: String?) {
        val bundle = Bundle()
        //这条分享消息被好友点击后的跳转URL。
        val PARAM_TARGET_URL = "targetUrl"
        bundle.putString(PARAM_TARGET_URL, shareUrl)
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
        val PARAM_TITLE = "title"
        bundle.putString(PARAM_TITLE, title)
        //分享的图片URL
        val PARAM_IMAGE_URL = "imageUrl"
        bundle.putString(PARAM_IMAGE_URL, imgUrl)
        //分享的消息摘要，最长50个字
        val PARAM_SUMMARY = "summary"
        bundle.putString(PARAM_SUMMARY, desc)
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        val PARAM_APPNAME = "appName"
        bundle.putString(PARAM_APPNAME, "")
        //标识该消息的来源应用，值为应用名称+AppId。
        val PARAM_APP_SOURCE = "appSource"
        bundle.putString(PARAM_APP_SOURCE, Share.config().APP_NAME + QQ_ID)
//        bundle.putString(PARAM_APP_SOURCE, Share.config()?.APP_NAME + Share.config()?.QQ_ID)

        tencent.shareToQQ(activity, bundle, object : IUiListener {
            override fun onComplete(o: Any) {
                ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.OK)
            }

            override fun onError(uiError: UiError) {
                ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.ERROR)
            }

            override fun onCancel() {
                ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.ERROR)
            }
        })
    }

}
