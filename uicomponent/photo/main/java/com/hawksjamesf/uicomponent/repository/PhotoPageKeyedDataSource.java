package com.hawksjamesf.uicomponent.repository;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/30/2019  Sat
 */
public class PhotoPageKeyedDataSource extends PageKeyedDataSource<String, String> {
    NetworkApi api;
    List<String> urlList = new ArrayList<String>() {
        {
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG168.jpeg?alt=media&token=2a28b72e-d8a5-4c0f-b919-853722c850a2");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG173.jpeg?alt=media&token=67c4a264-1ab4-47ca-971c-25e1571ed084");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG175.jpeg?alt=media&token=6596738d-e940-4158-bfa9-2f16d2586865");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG174.jpeg?alt=media&token=331f983e-e69c-4156-ab3b-d82980f6ad91");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
            add("https://firebasestorage.googleapis.com/v0/b/spacecraft-22dc1.appspot.com/o/WechatIMG176.jpeg?alt=media&token=6769a004-5d06-49fd-89d3-e72802399dd4");
        }
    };

    private static final String BASE_URL = "gs://spacecraft-22dc1.appspot.com";

    public PhotoPageKeyedDataSource(NetworkApi api) {
        this.api = api;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, String> callback) {
        Log.d("hawks", "PhotoPageKeyedDataSource:loadInitial:");


        if (!urlList.isEmpty()) {
            callback.onResult(urlList, "23", "asdfsdf");
        } else {
            FirebaseStorage storage = FirebaseStorage.getInstance(BASE_URL);
            StorageReference storageRef = storage.getReference();
//        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
            //        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
            for (int i = 168; i <= 176; ++i) {
                storageRef.child("WechatIMG$i.jpeg").getDownloadUrl()
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Log.d("hawks", "url:" + task.getResult().toString());
//                        itemList.add(task.result.toString())
//                        adapter.notifyDataSetChanged()

                            }
                        });
            }
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, String> callback) {
        Log.d("hawks", "PhotoPageKeyedDataSource:loadBefore:");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, String> callback) {
        Log.d("hawks", "PhotoPageKeyedDataSource:loadAfter:");
//        callback.onResult(urlList,null)
    }
}
