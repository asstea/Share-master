package cn.asstea.share.sdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import cn.asstea.share.Share
import cn.asstea.share.ShareOnClickManager
import cn.asstea.share.entity.ShareResultCode
import cn.asstea.share.entity.WeiBoShareInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken

/**
 * author : asstea
 * time   : 2017/06/29
 * desc   : 微博分享工具类
 */
class ShareWeiBoImpl : ShareWeibo, AuthorizeWeibo {

    private val mShareListener = object : WeiboListener.ShareListener {

        override fun onStart(code: Int) {}


        override fun onSuccess() {
            ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.OK)
        }

        override fun onFailure(code: Int, error: WeiboConnectError) {
            if (code == WeiboListener.ACTION_AUTHORIZE) {
                ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.ERROR)
            }
        }

        override fun onCancel(code: Int) {
            ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.ERROR)
        }
    }

    val mSinaSimplyHandler: SinaSimplyHandler by lazy {
        SinaSimplyHandler()
    }

    override fun shareWeibo(weiBoShareInfo: WeiBoShareInfo) {
        weiBoShareInfo.let {
            Share.shareManager().dialog?.let {
                mSinaSimplyHandler.share(it, weiBoShareInfo, mShareListener)
            }
        }
    }

    /**
     * 授权时调用

     * @param activity 当前页面的activity
     */
    override fun authorize(activity: Activity, authorizeListener: WeiboListener.AuthorizeListener) {
        mSinaSimplyHandler.authorize(activity, authorizeListener)
    }

    override fun unAuthorize(context: Context) {
        mSinaSimplyHandler.unAuthorize(context)
    }

    /**
     * 微博分享
     */
    private fun shareWeiBoContent(activity: Activity?, title: String, shareUrl: String, shareBitmap: Bitmap) {
        activity?.let {
            val weiBoShare = WeiBoShareInfo()
            weiBoShare.title = title
            weiBoShare.url = shareUrl
            weiBoShare.bitmap = shareBitmap
            if (mSinaSimplyHandler.isAuthorize(it)) {
                mSinaSimplyHandler.share(it, weiBoShare, mShareListener)
            } else {
                authorize(it, object : WeiboListener.AuthorizeListener {
                    override fun onStart(code: Int) {}

                    override fun onSuccess(mAccessToken: Oauth2AccessToken?) {
                        mSinaSimplyHandler.share(it, weiBoShare, mShareListener)
                    }

                    override fun onFailure(code: Int, error: WeiboConnectError) {
                        ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.ERROR)
                    }

                    override fun onCancel(code: Int) {
                        ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.ERROR)
                    }
                })
            }
        }
    }


    /**
     * 在要分享的activity onActivityResult 中调用
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mSinaSimplyHandler.onActivityResult(requestCode, resultCode, data)
    }

}
