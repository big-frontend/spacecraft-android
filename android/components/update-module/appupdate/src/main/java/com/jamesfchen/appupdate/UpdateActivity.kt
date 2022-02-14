package com.jamesfchen.appupdate

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.DownloadFileListener

import com.pgyersdk.update.javabean.AppBean
import java.io.File
import java.lang.Exception


class UpdateActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bt = Button(this)
        bt.text = "update app"
        bt.isAllCaps = false
        setContentView(
            bt,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        PgyUpdateManager.Builder()
            .setForced(true) //设置是否强制提示更新,非自定义回调更新接口此方法有用
            .setUserCanRetry(false) //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
            .setDeleteHistroyApk(false) // 检查更新前是否删除本地历史 Apk， 默认为true
            .setUpdateManagerListener(object : UpdateManagerListener {
                override fun onNoUpdateAvailable() {
                    //没有更新是回调此方法
                    Log.d("pgyer", "there is no new version")
                }

                override fun onUpdateAvailable(appBean: AppBean) {
                    //有更新回调此方法
                    Log.d(
                        "pgyer", "there is new version can update"
                                + "new versionCode is " + appBean.versionCode
                    )
                    //调用以下方法，DownloadFileListener 才有效；
                    //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                    PgyUpdateManager.downLoadApk(appBean.downloadURL)
                }

                override fun checkUpdateFailed(e: Exception) {
                    //更新检测失败回调
                    //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
                    Log.e("pgyer", "check update failed ", e)
                }
            }) //注意 ：
            //下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
            //此方法是方便用户自己实现下载进度和状态的 UI 提供的回调
            //想要使用蒲公英的默认下载进度的UI则不设置此方法
            .setDownloadFileListener(object : DownloadFileListener {
                override fun downloadFailed() {
                    //下载失败
                    Log.e("pgyer", "download apk failed")
                }

                override fun downloadSuccessful(file: File?) {
                    Log.e("pgyer", "download apk success")
                    // 使用蒲公英提供的安装方法提示用户 安装apk
                    PgyUpdateManager.installApk(file)
                }

                override fun onProgressUpdate(vararg integers: Int?) {
                    Log.e("pgyer", "update download apk progress$integers")

                }

            })
            .register()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}