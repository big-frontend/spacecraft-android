package com.jamesfchen.uicomponent.repository;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jamesfchen.uicomponent.model.Item;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/30/2019  Sat
 */
public class PhotoPageKeyedDataSource extends PageKeyedDataSource<String, Item> {
    NetworkApi api;
    private static final String BASE_URL = "gs://spacecraft-22dc1.appspot.com";

    public PhotoPageKeyedDataSource(NetworkApi api) {
        this.api = api;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Item> callback) {
        Log.d("hawks", "PhotoPageKeyedDataSource:loadInitial:");

        List<Uri> uriList = StockImages.uriList;
        if (!uriList.isEmpty()) {
            ArrayList<Item> items = new ArrayList<>();
            items.add(new Item(uriList.subList(uriList.size()-2,uriList.size())));
            items.add(new Item(uriList.subList(0, 3)));
            items.add(new Item(uriList.subList(3,4)));
            for (int i = 0; i < 20; i++) {
                items.add(new Item(uriList.subList(3,4)));
                items.add(new Item(uriList.subList(4,7)));
            }
            items.add(new Item(uriList.subList(0,uriList.size())));
            items.add(new Item(uriList.subList(uriList.size()-8,uriList.size()-2)));
            items.add(new Item(uriList.subList(uriList.size()-2,uriList.size())));
            callback.onResult(items, "23", "asdfsdf");
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
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Item> callback) {
        Log.d("hawks", "PhotoPageKeyedDataSource:loadBefore:");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Item> callback) {
        Log.d("hawks", "PhotoPageKeyedDataSource:loadAfter:");
//        callback.onResult(urlList,null)
    }
}
