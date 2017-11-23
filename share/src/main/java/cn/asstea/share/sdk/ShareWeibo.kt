package cn.asstea.share.sdk

import cn.asstea.share.entity.ShareResultCallback1
import cn.asstea.share.entity.WeiBoShareInfo

/**
 *     author : Holy
 *     time   : 2017/06/30
 *     desc   :
 *     version: 1.0
 */
interface ShareWeibo {

    /**
     * 分享到微博
     */
    fun shareWeibo(weiBoShareInfo: WeiBoShareInfo)

    /**
     * 分享到微博，九宫格专用
     */
    fun shareWeibo(weiBoShareInfo: WeiBoShareInfo, shareResultCallback: ShareResultCallback1) {}

}