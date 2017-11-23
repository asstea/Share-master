package cn.asstea.share.sdk

import cn.asstea.share.entity.ShareResultCallback1
import cn.asstea.share.entity.WeixinShareInfo

/**
 * author : asstea
 * time   : 2017/06/30
 * desc   :
 */
interface ShareWeixin {

    fun shareToWeixinFriends(weixinShareInfo: WeixinShareInfo)

    fun shareToWeixinFriends(weixinShareInfo: WeixinShareInfo, shareResultCallback: ShareResultCallback1){}

    fun shareToWeixinFriendsQueue(weixinShareInfo: WeixinShareInfo)

    fun shareToWeixinFriendsQueue(weixinShareInfo: WeixinShareInfo, shareResultCallback: ShareResultCallback1){}

}