package com.electrolytej.main.page.web

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.SpanUtils
import com.electrolytej.main.databinding.ActivityWebSearchBinding
import com.electrolytej.main.util.span.RoundedBackgroundSpan


/**
 * Copyright ® $ 2024
 * All right reserved.
 *
 * @author: electrolyteJ
 * @since: Jun/01/2024  Sat
 */
class WebSearchActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "WebSearchActivity"
        const val REQUEST_CODE_MEDIA_DIALOG: Int = 1001
    }

    val binding: ActivityWebSearchBinding by lazy {
        return@lazy ActivityWebSearchBinding.inflate(layoutInflater)
    }
    var deeplinkUri: String =
        "tbopen://m.taobao.com/tbopen/index.html?action=ali.open.nav&bootImage=0&module=h5&source=alimama&visa=5d429034ad046701&appkey=24540754&bc_fl_src=tanx_df_62934213_ac1295e700000d8b64ec517e1b5c8ca6_100575_535993509_22159511_1697786_94246_2_2098526_6_10065155_0_0_0_0_0_186084_0&backURL=&packageName=com.lingan.seeyou&h5Url=https%3a%2f%2fclick.tanx.com%2ftf%3fe%3dNEKIzUnSj6yMJfbPhIKx63uIYOJ6c5AfcCe%252F7%252FneF2rcWClT%252F9bM33hO%252BK8HCIhHXGUt0k2uG3bG8ly8kaCl1VH%252BVEi%252FINSl0C6wA5z45Mtvnyyg0UF%252BEzBb6TJVGfQ3os%252FmBV4FGxZyJoNXnL1sZru5KVY4PDk29MRtLa8zt3OjhUFh3tM4szUaXo%252Bq5rSuS0lmsWHpBYcFRqPEDtZX%252F9D%252FW86VK9VYUDGpnh5eKL9V6WuRbwXnmSP%252FH9Ph%252FEPbwWbm6%252BubXQsuPIj%252BJeJNdVpnCuxgzPQD5tzoRZYH2%252FDDJgnASJdFeksrMOEDAHL%252Fz59%252F807SXXHIuwlbI4Mikr6Dd%252FKnTB1JBzFMlOpyQIqI762bFJiTIL4x7OgGWR08JZcEg606QObLCDzbyJFSleeEhtoYOl1TaVPoLp88lf4ZZRXnszMKG4ziYLpWqVlGuqRARh8QKM1q0Ywr0zUtJeN9%252B%252FWTnaCevL2FioYLjStHVHQP2Dn0Sebe2RkbAnR2AGtgBbTFFQTUam4VYRDohd%252B%252FKjMmbsgU3CSlHeKWXSm4%252FoGOFbSgxsVvM3mmfsbs6JkEmiyV%252BUQomDNL41ZDvQ%253D%253D%26u%3dhttps%253A%252F%252Fmarket.m.taobao.com%252Fapps%252Fabs%252F5%252F54%252Fkudongcheng%253Fdf_sid%253Dac1295e700000d8b64ec517e1b5c8ca6%2526_wvUseWKWebView%253DYES%2526psId%253D67332%2526pcPsId%253D67333%2526_oneId%253D159134E6F4FCBE3C9F4992F251D0ADF5961F0248087435ED2BFAED5FCCB339A6%2526resource_id%253D100575%26k%3d600%26x%3d1&_tanx_bkts=0.7356566-0.8166650-0.12334132_2098526"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.vAd.setOnClickListener {
            Log.d(TAG, "setOnClickListener")
            openMediaDialog(this,deeplinkUri)
        }
        binding.rvSearchList.layoutManager = layoutManager
        SpanUtils.with(binding.tvAd)
            .append("广告")
            .setFontSize(11, true)
            .setSpans(
                RoundedBackgroundSpan(
                    Color.parseColor(
                        "#FF71A0"
                    ), Color.WHITE, ConvertUtils.dp2px(4f)
                )
            )
            .append("  28元捡漏两套结晶月子服！原价488米错过直拍大腿！入口给你快进群捡漏！")
            .setFontSize(16, true)
            .create()
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //点击搜索按钮会触发
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            //搜索框的文本内容发生变化时会触发
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun openMediaDialog(activity: Activity, path: String?) {
        val intent = Intent()
        intent.setAction(Intent.ACTION_VIEW)
        val uri = Uri.parse(path)
        intent.setData(uri)
        activity.startActivityForResult(intent, REQUEST_CODE_MEDIA_DIALOG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_MEDIA_DIALOG) {
            RESULT_OK
            Log.d(TAG,"onActivityResult:${resultCode}")
//            if (resultCode == RESULT_CANCLE) {
//                //处理取消或失败结果，如果当前Activity在前台，需要调用打开H5的方法，否则不处理
//                if (!isBackground) {
//                    //添加100ms延时策略，解决没有系统启动弹窗场景下因系统生命周期调度问题导致打开京东的同时打开H5页面
//                    Handler(Looper.getMainLooper()).postDelayed({
////                            openH5()
//                    }, 100);
//
//                }
//
//            } else {
//                // 处理活动成功完成的情况
//                Log.d("ActivityResult", "Activity completed successfully");
//            }
        }
    }

}