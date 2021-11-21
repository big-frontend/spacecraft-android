package com.jamesfchen.map

import android.content.Context
import android.graphics.Color
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.maps.AMap
import com.blankj.utilcode.util.SpanUtils
import com.jamesfchen.map.model.LBS
import java.util.*

class MapAdapter(context: Context, val map: AMap) : PagedListAdapter<LBS, MapAdapter.LbsViewHolder>(diffCallback) {
    var geocoder: Geocoder = Geocoder(context, Locale.CHINESE)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<LBS>() {
            override fun areItemsTheSame(oldItem: LBS, newItem: LBS): Boolean =
                    oldItem.index == newItem.index

            override fun areContentsTheSame(oldItem: LBS, newItem: LBS): Boolean =
                    oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LbsViewHolder = LbsViewHolder(parent)


    override fun onBindViewHolder(holder: LbsViewHolder, position: Int) {
        val item = getItem(position)
        val info = item?.appCellInfo
        val appLocation = item?.appLocation
        val lat = appLocation?.lat ?: 0.0
        val lon = appLocation?.lon ?: 0.0
        val lac = info?.lac ?: 0
        val cid = info?.cid ?: 0
        val add = geocoder.reverseGeocode2String(lat, lon)
        SpanUtils.with(holder.tv_block)
                .append(">>>> index:$position  ")
                .setBackgroundColor(Color.parseColor("#ffa86a"))
                .setFontSize(10, true)
                .setBoldItalic()
                .append("\n")
                .append("lat,lon:${lat.format()},${lon.format()}")
                .append(" ${add}")
                .append("\n")
                .append("radio: ${info?.radio_type} lac,cid: ${info?.lac},${info?.cid}")
                .create()
        holder.itemView.setOnClickListener { view ->
            map.move(lat, lon,lac,cid)
        }
    }

    fun Double?.format(): String {
        if (this == null || this == 0.0) return ""
        val s = this.toString()
        return if (s.contains('.') && s.length > 6) {
            s.substring(0, s.indexOf('.') + 5)
        } else {
            s
        }
    }


    class LbsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.map_item_location, parent, false)) {
        val tv_block = itemView.findViewById<TextView>(R.id.tv_block)
    }

}
