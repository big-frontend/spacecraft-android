package com.electrolytej.main.page.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.electrolytej.main.R
import com.electrolytej.main.base.AbsPermissionsFragment
import com.electrolytej.main.databinding.FragmentSplashBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.Random
import java.util.concurrent.TimeUnit

class SplashFragment : AbsPermissionsFragment() {
    companion object {

        const val TAG = "SplashActivity"

        // Remote Config keys
        private const val LOADING_PHRASE_CONFIG_KEY = "loading_phrase"
        private const val WELCOME_MESSAGE_KEY = "welcome_message"
        //    private void fetchWelcome() {
        //        Log.i(TAG, "LOADING_PHRASE_CONFIG_KEY:" + mFirebaseRemoteConfig.getString(LOADING_PHRASE_CONFIG_KEY));
        //        mFirebaseRemoteConfig.fetchAndActivate()
        //                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
        //                    @Override
        //                    public void onComplete(@NonNull Task<Boolean> task) {
        //                        if (task.isSuccessful()) {
        //                            boolean updated = task.getResult();
        //                            Log.i(TAG, "Config params updated: " + updated);
        //                            Toast.makeText(SplashActivity.this, "Fetch and activate succeeded",
        //                                    Toast.LENGTH_SHORT).show();
        //
        //                        } else {
        //                            Toast.makeText(SplashActivity.this, "Fetch failed",
        //                                    Toast.LENGTH_SHORT).show();
        //                        }
        //
        //                        String welcomeMessage = mFirebaseRemoteConfig.getString(WELCOME_MESSAGE_KEY);
        //                        Log.i(TAG, "WELCOME_MESSAGE_KEY: " + welcomeMessage);
        //
        //                    }
        //                });
        //    }
    }
    private val random = Random()
    lateinit var binding: FragmentSplashBinding
    val navController by lazy {
//        (childFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
    }

    //    private FirebaseAnalytics mFirebaseAnalytics;
    //    private Trace myTrace;
    //    private HttpMetric mHttpMetric;
    //    private FirebaseRemoteConfig mFirebaseRemoteConfig = App.getFirebaseRemoteConfig();
    //    @AddTrace(name = "SplashActivity#onCreate", enabled = true /* optional */)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onRequestPermissionsResult() {
        Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
            .subscribe { aLong: Long? ->
                val index = random.nextInt(20)
                when (index % 4) {
                    0 -> {
                    }
                }
            }
    }
//        myTrace = FirebasePerformance.getInstance().newTrace("loadData");
//        myTrace.start();
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        mHttpMetric = FirebasePerformance.getInstance().newHttpMetric("https://www.google.com", FirebasePerformance.HttpMethod.GET);
//        mHttpMetric.start();
//        onDestroyDisposable.add(Observable.timer(1, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        //todo:需要通过refresh token来判断进入那个界面
//                        myTrace.incrementMetric("started activity", 1);
//                        mHttpMetric.setHttpResponseCode(200);
//                        mHttpMetric.setResponseContentType("application/x-protobuf");
//
//                        Bundle bundle = new Bundle();
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
//                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name");
//                        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//                        ActivityUtil.startActivity(LocationActivity.class, ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle());
////                        TransitionForActivityActivity.startActivity(SplashActivity.this);
//                        finish();
//                    }
//                }));
//        fetchWelcome();
//        myTrace.stop();
//        mHttpMetric.stop();
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event);
}