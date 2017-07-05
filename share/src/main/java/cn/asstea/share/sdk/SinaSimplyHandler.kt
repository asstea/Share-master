package cn.asstea.share.sdk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import cn.asstea.share.Share
import cn.asstea.share.entity.WeiBoShareInfo
import cn.asstea.share.util.SharedPreferencesUtil
import com.sina.weibo.sdk.api.ImageObject
import com.sina.weibo.sdk.api.TextObject
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.sina.weibo.sdk.api.share.*
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WeiboAuthListener
import com.sina.weibo.sdk.auth.sso.SsoHandler
import com.sina.weibo.sdk.constant.WBConstants
import com.sina.weibo.sdk.exception.WeiboException
import java.lang.ref.WeakReference

/**
 * author : asstea
 * time   : 2017/04/19
 * desc   :
 */
class SinaSimplyHandler {

    private val iWeiboShareAPI: IWeiboShareAPI = WeiboShareSDK.createWeiboAPI(Share.app(), Share.config()!!.WEIBO_KEY)
    private val WEI_BO_EXPIRESTIME = "wei_bo_expires_time"
    private var mMSsoHandler: SsoHandler? = null
    private var mShareListener: WeiboListener.ShareListener? = null

    init {
        iWeiboShareAPI.registerApp()
    }

    /**
     * 授权

     * @param weiboListener 回调
     */
    fun authorize(activity: Activity, weiBoAuthInfo: WeiBoAuthInfo, weiboListener: WeiboListener.AuthorizeListener) {
        val weakAct = WeakReference(activity)
        if (weakAct.get() == null) {
            weiboListener.onError(WeiboListener.ACTION_AUTHORIZE, Exception("授权失败"))
            return
        }
        val mAuthInfo = AuthInfo(activity, weiBoAuthInfo.mAppKey, weiBoAuthInfo.mRedirectUrl, weiBoAuthInfo.mScope)
        mMSsoHandler = SsoHandler(weakAct.get(), mAuthInfo)
        mMSsoHandler?.authorize(object : WeiboAuthListener {
            override fun onComplete(bundle: Bundle) {
                val token = Oauth2AccessToken.parseAccessToken(bundle)
                SharedPreferencesUtil.putLong(activity, WEI_BO_EXPIRESTIME, token.expiresTime)
                weiboListener.onComplete(bundle)
            }

            override fun onWeiboException(e: WeiboException) {
                val exception = Exception(e.message, e.cause)
                weiboListener.onError(WeiboListener.ACTION_AUTHORIZE, exception)
            }

            override fun onCancel() {
                weiboListener.onCancel(WeiboListener.ACTION_AUTHORIZE)
            }
        })
    }

    fun isAuthorize(activity: Activity): Boolean {
        val expires_time = SharedPreferencesUtil.getLong(activity, WEI_BO_EXPIRESTIME, -1)
        if (expires_time.toInt() == -1) {
            return false
        } else {
            if (expires_time < System.currentTimeMillis()) {
                return false
            }
        }
        return true
    }

    fun share(activity: Activity, weiBoShare: WeiBoShareInfo, shareListener: WeiboListener.ShareListener) {
        this.mShareListener = shareListener
        if (weiBoShare.isNull()) {
            shareListener.onError(WeiboListener.ACTION_SHARE, Exception("分享内容为空"))
            return
        }
        val request = SendMultiMessageToWeiboRequest()
        request.transaction = System.currentTimeMillis().toString()
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
            weiboMessage.imageObject = imageObject
        }
        request.multiMessage = weiboMessage
        iWeiboShareAPI.sendRequest(activity, request)
    }

    /**
     * 微博登录页授权时在activity onActivityResult调用此方法
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mMSsoHandler?.authorizeCallBack(requestCode, resultCode, data)
    }

    fun handleWeiboResponse(intent: Intent, response: IWeiboHandler.Response): Boolean {
        return iWeiboShareAPI.handleWeiboResponse(intent, response)
    }

    fun onResponse(baseResponse: BaseResponse?) {
        baseResponse?.let {
            val mBaseResponse = it
            mShareListener?.let {
                when (mBaseResponse.errCode) {
                    WBConstants.ErrorCode.ERR_OK -> it.onSuccess()
                    WBConstants.ErrorCode.ERR_CANCEL -> it.onCancel(WeiboListener.ACTION_SHARE)
                    WBConstants.ErrorCode.ERR_FAIL -> it.onError(WeiboListener.ACTION_SHARE, Exception(mBaseResponse.errMsg))
                }
            }
        }
    }

    /**
     * 签名信息
     */
    class WeiBoAuthInfo(var mAppKey: String, var mRedirectUrl: String, var mScope: String)
}
