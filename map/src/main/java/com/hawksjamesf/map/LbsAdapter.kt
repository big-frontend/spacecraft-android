package com.hawksjamesf.map

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hawksjamesf.map.model.LBS

class LbsAdapter : PagedListAdapter<LBS, LbsAdapter.LbsViewHolder>(diffCallback) {

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
        if ("gsm".equals(info?.radio_type, ignoreCase = true)
                || "gsm_lte".equals(info?.radio_type, ignoreCase = true)
                || "gsm_wcdma".equals(info?.radio_type, ignoreCase = true)) {
            holder.tv_radio_type.text = "index:${position}\nradio_type:\n" + info?.radio_type
            holder.tv_lac.text = "lac:" + info?.lac
            holder.tv_cid.text = "cid:" + info?.cid
        } else if ("cdma".equals(info?.radio_type, ignoreCase = true)) {
            holder.tv_radio_type.text = "index:${position}\nradio_type:\n" + info?.radio_type
            holder.tv_lac.text = "lat:" + info?.cdmalat
            holder.tv_cid.text = "lon:" + info?.cdmalon
        }
        val appLocation = item?.appLocation
        holder.tv_lat.text = "lat: " + appLocation?.lat
        holder.tv_lon.text = "lon:" + appLocation?.lon

    }


    class LbsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cell_info, parent, false)) {
        val tv_radio_type = itemView.findViewById<TextView>(R.id.tv_radio_type)
        val tv_lac = itemView.findViewById<TextView>(R.id.tv_lac)
        val tv_cid = itemView.findViewById<TextView>(R.id.tv_cid)
        val tv_lat = itemView.findViewById<TextView>(R.id.tv_lat)
        val tv_lon = itemView.findViewById<TextView>(R.id.tv_lon)
    }

}
