package cn.asstea.share.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.LinearLayout
import cn.asstea.share.Share
import cn.asstea.share.entity.ShareInfo
import cn.asstea.share.entity.ShareItemData
import cn.asstea.share.entity.ShareOnClickListener
import cn.asstea.share.entity.ShareResult
import cn.asstea.share.view.ShareSheetView
import com.tencent.tauth.Tencent


/**
 *     author : Holy
 *     time   : 2017/06/27
 *     desc   : 分享页面
 *     version: 1.0
 */

class ShareActivity : AppCompatActivity() {

    private val mShareSheetView by lazy {
        ShareSheetView(this)
    }

    private val mShareItemData: ShareItemData by lazy {
        intent.getParcelableExtra<ShareItemData>(SHARE_ITEM_DATA)
    }

    companion object {

        const val SHARE_ITEM_DATA = "SHARE_ITEM_DATA"

        fun startShareActivity(packageContext: Activity, shareItemData: ShareItemData): Unit {
            val intent = Intent(packageContext, ShareActivity::class.java)
            intent.putExtra(SHARE_ITEM_DATA, shareItemData)
            packageContext.startActivityForResult(intent, ShareResult.ACTIVITY_REQUEST_CODE)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mShareSheetView)
        Share.Ext.shareManager.dialog = this
        if (savedInstanceState == null) {
            initView()
        }
        initData()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //QQ回调->官方文档没没没没没没这句代码, 但是很很很很很很重要, 不然不不不不不不会回调
        Tencent.onActivityResultData(requestCode, resultCode, data, null)
        //微博回调
        data?.let {
            Share.ShareManager().shareWeiBo.onActivityResult(requestCode, resultCode, it)
        }
    }

    private fun initView() {
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.BOTTOM)
        setFinishOnTouchOutside(true)
    }


    private fun initData() {
        mShareSheetView.setShareData(mShareItemData, object : ShareOnClickListener {
            override fun click(shareInfo: ShareInfo) {
                Share.Ext.shareManager.setShareOnClickListener(shareInfo)
            }

        })
    }

    override fun finish() {
        Share.Ext.shareManager.dialog = null
        Share.Ext.shareManager.hide()
        super.finish()
        overridePendingTransition(0, 0)
    }

}