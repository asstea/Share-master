package cn.asstea.share.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.asstea.share.Share
import cn.asstea.share.ShareOnClickManager
import cn.asstea.share.entity.ShareResultCode
import com.tencent.mm.sdk.openapi.BaseReq
import com.tencent.mm.sdk.openapi.BaseResp
import com.tencent.mm.sdk.openapi.ConstantsAPI
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler

/**
 * author : asstea
 * time   : 2017/06/30
 * desc   :
 */
open class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Share.shareManager().shareWeixin.iwxapi?.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        Share.shareManager().shareWeixin.iwxapi?.handleIntent(intent, this)
    }

    override fun onResp(p0: BaseResp?) {
        when (p0?.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.OK)
            }
            BaseResp.ErrCode.ERR_USER_CANCEL,
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.ERROR)
            }
            else -> {
                ShareOnClickManager.shareOnClickManager?.setSourceActivityResult(ShareResultCode.ERROR)
            }
        }
        this.finish()
    }

    override fun onReq(p0: BaseReq?) {
        when (p0?.type) {
            ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX -> {
            }
            ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX -> {
            }
            else -> {
            }
        }
    }

}