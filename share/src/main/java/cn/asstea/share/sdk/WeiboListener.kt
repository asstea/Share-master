package cn.asstea.share.sdk

import com.sina.weibo.sdk.auth.Oauth2AccessToken

/**
 * author : asstea
 * time   : 2017/04/19
 * desc   : 微博分享监听
 */
interface WeiboListener {

    fun onStart(code: Int)

    fun onFailure(code: Int, error: WeiboConnectError)

    fun onCancel(code: Int)

    interface ShareListener : WeiboListener {
        /**
         * 分享成功时调用
         */
        fun onSuccess()
    }

    interface AuthorizeListener : WeiboListener {
        /**
         * 授权成功时调用

         * @param mAccessToken 授权信息
         */
        fun onSuccess(mAccessToken: Oauth2AccessToken?)
    }

    companion object {

        val ACTION_AUTHORIZE = 0
        val ACTION_DELETE = 1
        val ACTION_SHARE = 2
    }

}
