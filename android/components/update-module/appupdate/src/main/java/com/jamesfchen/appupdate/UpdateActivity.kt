package com.jamesfchen.appupdate

import android.app.ActionBar
import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import com.pgyersdk.update.PgyUpdateManager;

class UpdateActivity :Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bt = Button(this)
        bt.setText("update")
        setContentView(bt, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT))

//        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
//        PgyUpdateManager.register(this);
        PgyUpdateManager.register(this@UpdateActivity,
            object : UpdateManagerListener() {
                fun onUpdateAvailable(result: String?) {

                    // 将新版本信息封装到AppBean中
                    val appBean: AppBean = getAppBeanFromString(result)
                    Builder(this@UpdateActivity)
                        .setTitle("更新")
                        .setMessage("")
                        .setNegativeButton(
                            "确定",
                            object : OnClickListener() {
                                fun onClick(
                                    dialog: DialogInterface?,
                                    which: Int
                                ) {
                                    startDownloadTask(
                                        this@UpdateActivity,
                                        appBean.getDownloadURL()
                                    )
                                }
                            }).show()
                }

                fun onNoUpdateAvailable() {}
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        PgyUpdateManager.unregister();
    }
}