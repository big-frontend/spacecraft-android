package com.jamesfchen.myhome.screen.profile

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jamesfchen.myhome.R

open class ChangeIconActivity : Activity() {

    internal class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = RecyclerView(this)
        val layoutManager = GridLayoutManager(this, 3)
        val adapter= object : RecyclerView.Adapter<MyViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val iv = ImageView(parent.context)
                iv.layoutParams = ViewGroup.LayoutParams(60,60)
                return MyViewHolder(iv)
            }

            override fun getItemCount() = 2

            override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                (holder.itemView as ImageView).setImageResource(R.drawable.ic_stat_name)
                (holder.itemView as ImageView).setColorFilter(android.R.color.black)
                holder.itemView.setOnClickListener {
                    when (position) {
                        1 -> {
                            changeLogo("com.jamesfchen.myhome.aMainActivity")
                        }
                        2 -> {
                            changeLogo("com.xzh.demo.cheese")
                        }
                        3 -> {
                            changeLogo("com.xzh.demo.chocolate")
                        }
                        else -> {
                            changeLogo("com.xzh.demo.default")
                        }
                    }
                }
            }

        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        setContentView(recyclerView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT))

    }
    fun changeLogo(name: String) {
        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
        packageManager.setComponentEnabledSetting(ComponentName(this, name), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
        val am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val resolveInfos = packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfos) {
            if (resolveInfo.activityInfo != null) {
                am.killBackgroundProcesses(resolveInfo.activityInfo.packageName)
            }
        }
    }
}
