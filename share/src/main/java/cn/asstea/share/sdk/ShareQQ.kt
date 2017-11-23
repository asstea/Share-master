package cn.asstea.share.sdk

import cn.asstea.share.entity.QQShareInfo
import cn.asstea.share.entity.ShareResultCallback1

/**
 *     author : Holy
 *     time   : 2017/06/30
 *     desc   :
 *     version: 1.0
 */
interface ShareQQ {

    fun shareToQQ(qQShareInfo: QQShareInfo)

    fun shareToQQ(qQShareInfo: QQShareInfo, shareResultCallback: ShareResultCallback1){}

}