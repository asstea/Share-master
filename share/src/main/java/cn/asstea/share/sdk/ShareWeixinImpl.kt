package cn.asstea.share.sdk

import android.graphics.Bitmap
import cn.asstea.share.Share
import cn.asstea.share.entity.WeixinShareInfo
import com.tencent.mm.sdk.openapi.*
import com.tencent.mm.sdk.platformtools.Util

/**
 * 微信分享工具类
 *
 *
 * Created by lp on 2016/9/24.
 */

class ShareWeixinImpl : ShareWeixin {


    var iwxapi: IWXAPI? = null

    init {
        //获取应用Manifest中的wx_api_id
        val config = Share.config()
        config?.let {
            val wxKey = it.WX_ID
            if (wxKey.isEmpty()) {
                print("wx key in null")
            } else {
                iwxapi = WXAPIFactory.createWXAPI(Share.app(), wxKey)
                iwxapi?.registerApp(wxKey)
            }
        }
    }

    override fun shareToWeixinFriends(weixinShareInfo: WeixinShareInfo) {
        shareWeixin(weixinShareInfo, SendMessageToWX.Req.WXSceneSession)
    }

    override fun shareToWeixinFriendsQueue(weixinShareInfo: WeixinShareInfo) {
        shareWeixin(weixinShareInfo, SendMessageToWX.Req.WXSceneTimeline)
    }

    /**
     * 分享
     */
    private fun shareWeixin(weixinShareInfo: WeixinShareInfo, scene: Int) {
        weixinShareInfo.title?.let {
            val title = it
            weixinShareInfo.url?.let {
                val url = it
                weixinShareInfo.description?.let {
                    val description = it
                    weixinShareInfo.bitmap?.let {
                        shareUrlToWx(url, title, description, it, scene)
                        return
                    }
                }
            }
            shareTextToWx(it, scene)
            return
        }
        weixinShareInfo.bitmap?.let {
            shareImgToWx(it, scene)
        }
    }


    /**
     * 分享文字到微信

     * @param text
     * *
     * @param scene
     */
    private fun shareTextToWx(text: String, scene: Int) {
        val textObject = WXTextObject()
        textObject.text = text

        val msg = WXMediaMessage()
        msg.mediaObject = textObject
        msg.description = text

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("text")
        req.message = msg
        req.scene = scene
        iwxapi?.sendReq(req)
    }

    /**
     * 分享图片到微信

     * @param bitmap
     * *
     * @param scene
     */
    private fun shareImgToWx(bitmap: Bitmap, scene: Int) {
        val imgObject = WXImageObject(bitmap)
        val msg = WXMediaMessage()
        msg.mediaObject = imgObject

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("imgM")
        req.message = msg
        req.scene = scene
        iwxapi?.sendReq(req)
    }

    /**
     * 分享网页到微信

     * @param url
     * *
     * @param title
     * *
     * @param description
     * *
     * @param bitmap
     * *
     * @param scene
     */
    private fun shareUrlToWx(url: String, title: String, description: String, bitmap: Bitmap, scene: Int) {
        val webpage = WXWebpageObject()
        webpage.webpageUrl = url

        val msg = WXMediaMessage(webpage)
        msg.title = title
        msg.description = description
        msg.thumbData = Util.bmpToByteArray(bitmap, true)

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("webpage")
        req.message = msg
        req.scene = scene
        iwxapi?.sendReq(req)
    }

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }

}
