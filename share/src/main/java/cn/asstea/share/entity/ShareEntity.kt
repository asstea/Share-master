package cn.asstea.share.entity

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

/**
 *     author : Holy
 *     time   : 2017/06/27
 *     desc   : 分享数据
 *     version: 1.0
 */

data class ShareInfo(
        var id: Int, //id
        var isSelect: Boolean,
        var unSelectIcon: Int, //未选中图标
        var selectIcon: Int, //图标
        var selectTitle: String, //标题
        var unSelectTitle: String //未选中标题
) : Parcelable {

    constructor(id: Int, selectIcon: Int, selectTitle: String)
            : this(id, false, selectIcon, selectIcon, selectTitle, selectTitle)

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ShareInfo> = object : Parcelable.Creator<ShareInfo> {
            override fun createFromParcel(source: Parcel): ShareInfo = ShareInfo(source)
            override fun newArray(size: Int): Array<ShareInfo?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            1 == source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeInt((if (isSelect) 1 else 0))
        dest.writeInt(unSelectIcon)
        dest.writeInt(selectIcon)
        dest.writeString(selectTitle)
        dest.writeString(unSelectTitle)
    }
}

data class ShareItemData(
        var one: List<ShareInfo>?,
        var two: List<ShareInfo>?) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ShareItemData> = object : Parcelable.Creator<ShareItemData> {
            override fun createFromParcel(source: Parcel): ShareItemData = ShareItemData(source)
            override fun newArray(size: Int): Array<ShareItemData?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.createTypedArrayList(ShareInfo.CREATOR),
            source.createTypedArrayList(ShareInfo.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(one)
        dest.writeTypedList(two)
    }
}

interface ShareOnClickListener {

    fun click(shareInfo: ShareInfo)
}

interface ShareItemOnClickListener<in T> {

    fun click(shareInfo: ShareInfo, data: T)

}

interface ShareResultCallback<in T> {

    fun shareResult(shareInfo: ShareInfo, code: ShareResult.Code, data: T)

}

object ShareResult {

    val KEY_CODE_NAME = "KEY_CODE_NAME"
    val KEY_SHAREINFO_NAME = "KEY_SHAREINFO_NAME"
    val ACTIVITY_REQUEST_CODE: Int = 0x10
    val ACTIVITY_RESULT_CODE: Int = 0x11

    enum class Code {
        OK,
        ERROR
    }

}

class WeixinShareInfo {
    var title: String? = null
    var url: String? = null
    var description: String? = null
    var bitmap: Bitmap? = null
}

class QQShareInfo {
    var title: String? = null
    var url: String? = null
    var description: String? = null
    var imgUrl: String? = null
}

class WeiBoShareInfo {
    var url: String? = null
    var title: String? = null
    var bitmap: Bitmap? = null

    fun isNull() = url.isNullOrBlank() && title.isNullOrBlank() && bitmap != null

}



