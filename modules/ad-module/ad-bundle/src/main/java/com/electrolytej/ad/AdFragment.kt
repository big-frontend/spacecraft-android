package com.electrolytej.ad

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.EncryptUtils
import com.electrolytej.ad.databinding.FragmentAdBinding
import java.text.SimpleDateFormat
import java.util.Date


class AdFragment : Fragment() {
    companion object {
        private const val TAG = "AdFragment"
    }

    private val viewModel by viewModels<AdViewModel>()
    lateinit var binding: FragmentAdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.svShiny.setOnShinyListener {
//            BarUtil.setBarsFullscreen(requireActivity(), BarUtil.IMMERSIVE_STICKY)
            findNavController().navigateUp()
//            SPUtils.getInstance().put(Constants.KEY_AD_SPLASH, true)
        }
        getAllPackage()
        getMyPackage()
    }

    fun getMyPackage() {
        val packageInfo =
            requireActivity().packageManager.getPackageInfo(requireActivity().packageName, 0)
        val firstInstallTime = packageInfo.firstInstallTime;
        val installDate = Date(firstInstallTime);
        val lastUpdateTime = packageInfo.lastUpdateTime;
        val updateDate = Date(lastUpdateTime);
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val installDateString = dateFormat.format(installDate);
        val updateDateString = dateFormat.format(updateDate);
        Log.d(
            TAG,
            "getMyPackage ${requireActivity().packageName} ${updateDateString} ${installDateString}"
        )
    }

    fun getAllPackage() {
        val installedPackages =
            requireActivity().packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES or PackageManager.GET_SIGNING_CERTIFICATES)
        for (i in 0 until installedPackages.size) {
            val info = installedPackages[i]
            val packagename = info.packageName
            val lastUpdateTime = info.lastUpdateTime
            val firstInstallTime = info.firstInstallTime
            val activities = info.activities
            val applicationInfo = info.applicationInfo
            val signingInfo = info.signingInfo

            val installDate = Date(firstInstallTime);
            val updateDate = Date(lastUpdateTime);
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            val installDateString = dateFormat.format(installDate);
            val updateDateString = dateFormat.format(updateDate);
            Log.d(TAG, "getAllPackage ${packagename}   ${updateDateString} ${installDateString}")
            val messageDigest: String = EncryptUtils.encryptMD5ToString(
                getRawSignature(requireActivity(), info.packageName)?.get(0)?.toByteArray()
            )
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
                    sb2.append(EncryptUtils.encryptMD5ToString(signature.toByteArray()))
                        .append('\t')

                }
                for (signingCertificateHistory in signingInfo.signingCertificateHistory) {
                    sb3.append(EncryptUtils.encryptMD5ToString(signingCertificateHistory.toByteArray()))
                        .append('\t')
                }
            }
        }
    }

    fun getRawSignature(ctx: Context?, packageName: String?): Array<Signature?>? {
        if (ctx == null || packageName.isNullOrEmpty()) return null
        val packageManager = ctx.packageManager
        return try {
            val packageInfo = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES or PackageManager.GET_SIGNING_CERTIFICATES
            )
            packageInfo.signatures
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}