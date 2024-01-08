package com.jamesfchen.loader.androidxstartup

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.AddTrace
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class StorageInitializer : Initializer<Unit> {
    companion object {
        const val TAG = "StorageInitializer"
    }

    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    @AddTrace(name = "StorageInitializer#create", enabled = true)
    override fun create(context: Context) {
//        Log.d(TAG, "StorageInitializer#create")
//        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
//        val configSettings = FirebaseRemoteConfigSettings.Builder()
//            .setMinimumFetchIntervalInSeconds(3600)
//            .setFetchTimeoutInSeconds((60 * 60).toLong())
//            .build()
//        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
//        firebaseRemoteConfig.ensureInitialized()
//        firebaseRemoteConfig.fetchAndActivate()
//            .addOnCompleteListener {
//                Log.i(
//                    TAG,
//                    "onComplete-perf_enable:" + firebaseRemoteConfig.getBoolean("perf_enable")
//                )
//                FirebasePerformance.getInstance().isPerformanceCollectionEnabled =
//                    firebaseRemoteConfig.getBoolean("perf_enable")
//            }
//            .addOnFailureListener({ Log.i(TAG, "onFailure") })
//            .addOnSuccessListener { aBoolean ->
//                Log.i(TAG, "onSuccess:$aBoolean")
//            }
//            .addOnCanceledListener { Log.i(TAG, "onCanceled") }
    }

    fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {
        return firebaseRemoteConfig;
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}