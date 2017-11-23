package cn.asstea.share.sdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import cn.asstea.share.Share
import cn.asstea.share.entity.WeiBoShareInfo
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.api.*
import com.sina.weibo.sdk.auth.*
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.sina.weibo.sdk.share.WbShareCallback
import com.sina.weibo.sdk.share.WbShareHandler
import java.util.*

/**
 * author : asstea
 * time   : 2017/04/19
 * desc   :微博分享，授权回调
 */
class SinaSimplyHandler {

    private var wbShareHandler: WbShareHandler? = null
    private var mSsoHandler: SsoHandler? = null
    private var mShareListener: WeiboListener.ShareListener? = null

    init {
        WbSdk.install(Share.app(), AuthInfo(Share.app(), Share.config().WEIBO_KEY, Share.config().WEIBO_REDIRECT_URL, Share.config().WEIBO_SCOPE))
    }

    /**
     * 授权

     * @param weiboListener 回调
     */
    fun authorize(activity: Activity?, weiboListener: WeiboListener.AuthorizeListener) {
        if (activity == null) {
            weiboListener.onFailure(WeiboListener.ACTION_AUTHORIZE, WeiboConnectError("授权失败"))
            return
        }
        mSsoHandler = SsoHandler(activity)
        mSsoHandler?.authorize(object : WbAuthListener {

            override fun onFailure(p0: WbConnectErrorMessage?) {
                weiboListener.onFailure(WeiboListener.ACTION_AUTHORIZE, WeiboConnectError(p0?.errorMessage, p0?.errorCode))
            }

            override fun cancel() {
                weiboListener.onCancel(WeiboListener.ACTION_AUTHORIZE)
            }

            override fun onSuccess(mAccessToken: Oauth2AccessToken?) {
                AccessTokenKeeper.writeAccessToken(activity, mAccessToken)
                weiboListener.onSuccess(mAccessToken)
            }

        })
    }

    /**
     * 用户登出
     */
    fun unAuthorize(context: Context) {
        AccessTokenKeeper.clear(context)
    }


    fun isAuthorize(activity: Activity): Boolean {
        val mAccessToken = AccessTokenKeeper.readAccessToken(activity)
        return mAccessToken.isSessionValid
    }

    fun share(activity: Activity, weiBoShare: WeiBoShareInfo, shareListener: WeiboListener.ShareListener) {
        if (weiBoShare.isNull()) {
            shareListener.onFailure(WeiboListener.ACTION_SHARE, WeiboConnectError("分享内容为空"))
            return
        }
        wbShareHandler = WbShareHandler(activity)
        wbShareHandler?.registerApp()
        wbShareHandler?.setProgressColor(weiBoShare.progressColor)
        mShareListener = shareListener


        if (shareMultiImage(activity, weiBoShare.imagePath)) {
            return
        }

        if (shareVideoSource(weiBoShare.videoPath)) {
            return
        }

        shareWeiBoText(weiBoShare)


    }

    private fun shareVideoSource(videoPath: Uri?): Boolean {
        videoPath?.let {
            val weiboMessage = WeiboMultiMessage()
            val videoSourceObject = VideoSourceObject()
            videoSourceObject.videoPath = it
            weiboMessage.videoSourceObject = videoSourceObject
            wbShareHandler?.shareMessage(weiboMessage, false)
            return true
        }
        return false
    }

    private fun shareWeiBoText(weiBoShare: WeiBoShareInfo) {
        val weiboMessage = WeiboMultiMessage()
        if (weiBoShare.title != null || weiBoShare.url != null) {
            val textObject = TextObject()
            if (weiBoShare.title != null) {
                textObject.text = weiBoShare.title
            }
            if (weiBoShare.url != null) {
                textObject.text = weiBoShare.title + weiBoShare.url
            }
            weiboMessage.textObject = textObject
        }
        if (weiBoShare.bitmap != null) {
            val imageObject = ImageObject()
            imageObject.setImageObject(weiBoShare.bitmap)
            weiboMessage.imageObject = imageObject
        }
        wbShareHandler?.shareMessage(weiboMessage, false)
    }

    private fun shareMultiImage(activity: Activity, imagePath: ArrayList<Uri>): Boolean {
        if (WbSdk.supportMultiImage(activity)) {
            imagePath.let {
                if (it.isNotEmpty()) {
                    val weiboMessage = WeiboMultiMessage()
                    val multiImageObject = MultiImageObject()
                    multiImageObject.imageList = imagePath
                    weiboMessage.multiImageObject = multiImageObject
                    wbShareHandler?.shareMessage(weiboMessage, false)
                    return true
                }
            }
        }
        return false
    }

    /**
     * 微博登录页授权时在activity onActivityResult调用此方法
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mSsoHandler?.authorizeCallBack(requestCode, resultCode, data)
    }


    /**
     * 微博分享回调可能会执行的代码
     */
    fun onNewIntent(intent: Intent?) {
        wbShareHandler?.doResultIntent(intent, object : WbShareCallback {

            override fun onWbShareFail() {
                mShareListener?.onFailure(WeiboListener.ACTION_SHARE, WeiboConnectError("分享失败"))
            }

            override fun onWbShareCancel() {
                mShareListener?.onCancel(WeiboListener.ACTION_SHARE)
            }

            override fun onWbShareSuccess() {
                mShareListener?.onSuccess()
            }

        })
    }

}
