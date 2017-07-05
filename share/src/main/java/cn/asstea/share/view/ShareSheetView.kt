package cn.asstea.share.view

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import cn.asstea.share.R
import cn.asstea.share.entity.ShareInfo
import cn.asstea.share.entity.ShareItemData
import cn.asstea.share.entity.ShareOnClickListener
import kotlinx.android.synthetic.main.view_share.view.*

/**
 * author : Holy
 * time   : 2017/06/27
 * desc   : 分享控件
 * version: 1.0
 */
class ShareSheetView(context: Context) : RelativeLayout(context) {

    init {
        iniView()
    }

    fun setShareData(mShareData: ShareItemData, onShareItemClick: ShareOnClickListener) {
        val listOnes = mShareData.one
        listOnes?.let {
            if (it.isNotEmpty()) {
                iniRecyclerView(share_lv_one, onShareItemClick, it)
            }
        }
        val listTwos = mShareData.two
        listTwos?.let {
            if (it.isNotEmpty()) {
                share_view_line.visibility = View.VISIBLE
                iniRecyclerView(share_lv_two, onShareItemClick, it)
            }
        }
    }

    private fun iniRecyclerView(lv: RecyclerView, onShareItemClick: ShareOnClickListener, listShares: List<ShareInfo>) {
        val shareAdapter = ShareAdapter(listShares, onShareItemClick)
        lv.layoutManager = linearLayoutManager
        lv.adapter = shareAdapter
    }


    private inner class ClickListener : View.OnClickListener {

        override fun onClick(v: View) {

            (context as Activity).finish()

        }
    }

    private val linearLayoutManager: LinearLayoutManager
        get() {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayout.HORIZONTAL
            return linearLayoutManager
        }

    private fun iniView() {
        View.inflate(context, R.layout.view_share, this)
        findViewById(R.id.share_tv_cancel).setOnClickListener(ClickListener())
        setBackgroundColor(ContextCompat.getColor(context, R.color.color_e7e5e8))
        setPadding(0, dip2px(context, 18f), 0, 0)
    }

    /**
     * 分享适配
     */
    private inner class ShareAdapter internal constructor(private val data: List<ShareInfo>, private val onClickListener: ShareOnClickListener?) : RecyclerView.Adapter<ShareAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewHolder = MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_share, parent, false))
            return viewHolder
        }

        override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
            val item = data[position]
            var title = ""
            var icon = -1
            when (item.isSelect) {
                true->{
                    item.selectTitle?.let { title = item.selectTitle }
                    item.selectIcon?.let { icon = item.selectIcon }
                }
                false ->{
                    item.unSelectTitle?.let { title = item.unSelectTitle }
                    item.unSelectIcon?.let { icon = item.unSelectIcon }
                }
            }
            viewHolder.tvTitle.text = title
            viewHolder.ivIcon.setImageResource(icon)
            viewHolder.itemView.setOnClickListener {
                onClickListener?.click(item)
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var ivIcon: ImageView = view.findViewById(R.id.itemShare_iv_icon) as ImageView
            var tvTitle: TextView = view.findViewById(R.id.itemShare_tv_title) as TextView
        }
    }

    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}