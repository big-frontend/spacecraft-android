package com.jamesfchen.myhome.screen.photo.repository;

import android.net.Uri;
import android.util.Log;

import com.jamesfchen.myhome.screen.photo.model.Item;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;

import kotlin.coroutines.Continuation;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/30/2019  Sat
 */

public class PhotoPageKeyedDataSource extends PagingSource<String, Item> {
    NetworkApi api;
    private static final String BASE_URL = "gs://spacecraft-22dc1.appspot.com";

    public PhotoPageKeyedDataSource(NetworkApi api) {
        this.api = api;
    }

    @Nullable
    @Override
    public Object load(@NonNull LoadParams<String> params, @NonNull Continuation<? super LoadResult<String, Item>> continuation) {
        Log.d("cjf", "PhotoPageKeyedDataSource:load:");

        List<Uri> uriList = StockImages.uriList;
        if (!uriList.isEmpty()) {
            ArrayList<Item> items = new ArrayList<>();
            items.add(new Item(uriList.subList(uriList.size() - 2, uriList.size())));
            items.add(new Item(uriList.subList(0, 3)));
            items.add(new Item(uriList.subList(3, 4)));
            for (int i = 0; i < 20; i++) {
                items.add(new Item(uriList.subList(3, 4)));
                items.add(new Item(uriList.subList(4, 7)));
            }
            items.add(new Item(uriList.subList(0, uriList.size())));
            items.add(new Item(uriList.subList(uriList.size() - 8, uriList.size() - 2)));
            items.add(new Item(uriList.subList(uriList.size() - 2, uriList.size())));
            LoadResult.Page<String, Item> stringItemPage = new LoadResult.Page<>(items, "prevKey", "nextKey");
            continuation.resumeWith(stringItemPage);
            return stringItemPage;

        } else {
//            FirebaseStorage storage = FirebaseStorage.getInstance(BASE_URL);
//            StorageReference storageRef = storage.getReference();
//        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
            //        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
//            for (int i = 168; i <= 176; ++i) {
//                storageRef.child("WechatIMG$i.jpeg").getDownloadUrl()
//                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Uri> task) {
//                                Log.d("hawks", "url:" + task.getResult().toString());
//                        itemList.add(task.result.toString())
//                        adapter.notifyDataSetChanged()

//                            }
//                        });
//            }
        }

        return new Object();
    }

    @Nullable
    @Override
    public String getRefreshKey(@NonNull PagingState<String, Item> pagingState) {
        return pagingState.getAnchorPosition() != null ? pagingState.closestPageToPosition(pagingState.getAnchorPosition()).getNextKey() : null;
    }
}
