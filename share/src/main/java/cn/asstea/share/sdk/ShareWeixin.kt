package cn.asstea.share.sdk

import cn.asstea.share.entity.WeixinShareInfo

/**
 * author : asstea
 * time   : 2017/06/30
 * desc   :
 */
interface ShareWeixin {

    fun shareToWeixinFriends(weixinShareInfo: WeixinShareInfo)

    fun shareToWeixinFriendsQueue(weixinShareInfo: WeixinShareInfo)

}