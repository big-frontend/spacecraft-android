package com.electrolytej.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.SPUtils
import com.electrolytej.main.databinding.ActivityMainBinding
import com.electrolytej.util.CryptoUtil
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    val navController by lazy {
//        binding.fragmentNavHost.findFragment<NavHostFragment>().navController
        (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
//        navController.handleDeepLink(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //native  crash
//        Crypto.getClientKey("", 0L)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        if (!SPUtils.getInstance().getBoolean(Constants.KEY_WELCOME_SPLASH)) {
            navGraph.setStartDestination(R.id.dest_welcome_splash)
        } else if (!SPUtils.getInstance().getBoolean(Constants.KEY_AD_SPLASH)) {
            navGraph.setStartDestination(R.id.dest_ad_splash)
        } else {
            navGraph.setStartDestination(R.id.dest_blank_splash)
        }
        navController.graph = navGraph
        binding.bnv.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d(
                TAG,
                "addOnDestinationChangedListener ${destination} ${destination.id} ${destination.route} ${destination.navigatorName} ${destination.displayName}"
            )
            if (destination.id == R.id.dest_home) binding.bnv.visibility = View.VISIBLE

        }
        binding.bnv.setOnItemSelectedListener { item ->
            return@setOnItemSelectedListener when (item.itemId) {
                R.id.dest_home -> {
                    navController.navigate(R.id.dest_home)
                    true
                }

                R.id.dest_feeds -> {
                    navController.navigate(R.id.dest_new_feeds)
                    true
                }

                R.id.dest_infos -> {
                    navController.navigate(R.id.dest_infos)
                    true
                }

                R.id.dest_profile -> {
                    navController.navigate(R.id.dest_profile)
                    true
                }

                R.id.dest_bundle2 -> {
                    navController.navigate(R.id.dest_bundle2)
                    true
                }

                else -> false
            }
        }
        getAllPackage()
        getMyPackage()
    }
    fun getMyPackage(){
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val firstInstallTime = packageInfo.firstInstallTime;
        val installDate =  Date(firstInstallTime);
        val lastUpdateTime = packageInfo.lastUpdateTime;
        val updateDate =  Date(lastUpdateTime);
        val dateFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val installDateString = dateFormat.format(installDate);
        val updateDateString = dateFormat.format(updateDate);
        Log.d(TAG,"getMyPackage ${packageName} ${updateDateString} ${installDateString}")
    }
    fun getAllPackage(){
        val installedPackages = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES or PackageManager.GET_SIGNING_CERTIFICATES)
        for (i in 0 until installedPackages.size) {
            val info = installedPackages[i]
            val packagename = info.packageName
            val lastUpdateTime = info.lastUpdateTime
            val firstInstallTime = info.firstInstallTime
            val activities = info.activities
            val applicationInfo = info.applicationInfo
            val signingInfo = info.signingInfo

            val installDate =  Date(firstInstallTime);
            val updateDate =  Date(lastUpdateTime);
            val dateFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            val installDateString = dateFormat.format(installDate);
            val updateDateString = dateFormat.format(updateDate);
            Log.d(TAG,"getAllPackage ${packagename}   ${updateDateString} ${installDateString}")
            val messageDigest: String = CryptoUtil.encryptMD5ToString(getRawSignature(this, info.packageName)?.get(0)?.toByteArray())
            val sb = StringBuffer()
            if (activities != null) {
                for (activityinfo in activities) {
                    sb.append(activityinfo.name)
                        .append('\t')
                }
            }
            val sb2 = StringBuffer()
            val sb3 = StringBuffer()
            if (signingInfo != null) {
                for (signature in signingInfo.apkContentsSigners) {
                    sb2.append(CryptoUtil.encryptMD5ToString(signature.toByteArray()))
                        .append('\t')

                }
                for (signingCertificateHistory in signingInfo.signingCertificateHistory) {
                    sb3.append(CryptoUtil.encryptMD5ToString(signingCertificateHistory.toByteArray()))
                        .append('\t')
                }
            }
        }
    }

    fun getRawSignature(ctx: Context?, packageName: String?): Array<Signature?>? {
        if (ctx == null || packageName.isNullOrEmpty()) return null
        val packageManager = ctx.packageManager
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES or PackageManager.GET_SIGNING_CERTIFICATES)
            packageInfo.signatures
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

}