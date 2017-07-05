package cn.asstea.share.activity


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.asstea.share.Share
import com.sina.weibo.sdk.api.share.BaseResponse
import com.sina.weibo.sdk.api.share.IWeiboHandler

/**
 * author : asstea
 * time   : 2017/04/19
 * desc   : 微博回调用
 */
class WeiboShareActivity : AppCompatActivity(), IWeiboHandler.Response {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        Share.ShareManager().shareWeiBo.mSinaSimplyHandler.handleWeiboResponse(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        Share.ShareManager().shareWeiBo.mSinaSimplyHandler.handleWeiboResponse(intent, this)
    }

    override fun onResponse(baseResponse: BaseResponse) {
        Share.ShareManager().shareWeiBo.mSinaSimplyHandler.onResponse(baseResponse)
        finish()
    }

}
