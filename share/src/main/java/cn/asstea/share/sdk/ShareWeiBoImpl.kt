package cn.asstea.share.sdk

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import cn.asstea.share.Share
import cn.asstea.share.ShareOnClickManager
import cn.asstea.share.entity.ShareResult
import cn.asstea.share.entity.WeiBoShareInfo

/**
 * author : asstea
 * time   : 2017/06/29
 * desc   : 微博分享工具类
 */
class ShareWeiBoImpl : ShareWeibo {


    companion object {
        private const val REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"
        private const val SCOPE =
                "email,direct_messages_read,direct_messages_write," +
                        "friendships_groups_read,friendships_groups_write,statuses_to_me_read," +
                        "follow_app_official_microblog," + "invitation_write"
    }

    private val mShareListener = object : WeiboListener.ShareListener {

        override fun onStart(code: Int) {}


        override fun onSuccess() {
            ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResult.Code.OK)
        }

        override fun onError(code: Int, throwable: Exception) {
            if (code == WeiboListener.ACTION_AUTHORIZE) {
                ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResult.Code.ERROR)
            }
        }

        override fun onCancel(code: Int) {
            ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResult.Code.ERROR)
        }
    }

    val mSinaSimplyHandler: SinaSimplyHandler by lazy {
        SinaSimplyHandler()
    }

    fun shareWeiBoText(activity: Activity, text: String) {
        val weiBoShare = WeiBoShareInfo()
        weiBoShare.title = text
        mSinaSimplyHandler.share(activity, weiBoShare, mShareListener)
    }

    fun shareWeiBoImg(activity: Activity, bitmap: Bitmap) {
        val weiBoShare = WeiBoShareInfo()
        weiBoShare.bitmap = bitmap
        mSinaSimplyHandler.share(activity, weiBoShare, mShareListener)
    }

    override fun shareWeibo(weiBoShareInfo: WeiBoShareInfo) {
        weiBoShareInfo.let {
            Share.ShareManager().dialog?.let {
                val shareActivity = it
                weiBoShareInfo.title?.let {
                    val title = it
                    weiBoShareInfo.url?.let {
                        val url = it
                        weiBoShareInfo.bitmap?.let {
                            shareWeiBoContent(shareActivity, title, url, it)
                            return
                        }
                    }
                    shareWeiBoText(shareActivity, it)
                    return
                }
                weiBoShareInfo.bitmap?.let {
                    shareWeiBoImg(shareActivity, it)
                }
            }
        }
    }

    /**
     * 微博分享
     */
    fun shareWeiBoContent(activity: Activity?, title: String, shareUrl: String, shareBitmap: Bitmap) {
        activity?.let {
            val weiBoShare = WeiBoShareInfo()
            weiBoShare.title = title
            weiBoShare.url = shareUrl
            weiBoShare.bitmap = shareBitmap
            if (mSinaSimplyHandler.isAuthorize(it)) {
                mSinaSimplyHandler.share(it, weiBoShare, mShareListener)
            } else {
                doOauthVerify(it, object : WeiboListener.AuthorizeListener {
                    override fun onStart(code: Int) {}

                    override fun onComplete(bundle: Bundle) {
                        mSinaSimplyHandler.share(it, weiBoShare, mShareListener)
                    }

                    override fun onError(code: Int, throwable: Exception) {
                        ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResult.Code.ERROR)
                    }

                    override fun onCancel(code: Int) {
                        ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResult.Code.ERROR)
                    }
                })
            }
        }
    }

    /**
     * 授权时调用

     * @param activity 当前页面的activity
     */
    fun doOauthVerify(activity: Activity, authorizeListener: WeiboListener.AuthorizeListener) {
        val config = Share.config()
        mSinaSimplyHandler.authorize(activity, SinaSimplyHandler.WeiBoAuthInfo(config?.WEIBO_KEY ?: "", REDIRECT_URL, SCOPE), authorizeListener)
    }

    /**
     * 在要分享的activity onActivityResult 中调用
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mSinaSimplyHandler.onActivityResult(requestCode, resultCode, data)
    }

}
