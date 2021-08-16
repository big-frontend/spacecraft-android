package com.jamesfchen.myhome.screen.photo.repository

import android.util.Log
import androidx.paging.PagingSource
import java.util.ArrayList
import androidx.paging.PagingState
import com.jamesfchen.myhome.screen.photo.model.Item
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.Continuation

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/30/2019  Sat
 */
class PhotoPageKeyedDataSource(var api: NetworkApi) : PagingSource<String, Item>() {
    companion object {
        private const val BASE_URL = "gs://spacecraft-22dc1.appspot.com"
    }

    var nextPageNumber =0
    override fun getRefreshKey(state: PagingState<String, Item>): String? {
        return if (state.anchorPosition != null) state.closestPageToPosition(state.anchorPosition!!)!!.nextKey else null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Item> {
        Log.d("cjf", "PhotoPageKeyedDataSource:load:")
        val uriList = StockImages.uriList
        if (uriList.isNotEmpty()) {
            val items = ArrayList<Item>()
            items.add(
                Item(
                    uriList.subList(
                        uriList.size - 2,
                        uriList.size
                    )
                )
            )
            items.add(Item(uriList.subList(0, 3)))
            items.add(Item(uriList.subList(3, 4)))
            for (i in 0..19) {
                items.add(Item(uriList.subList(3, 4)))
                items.add(Item(uriList.subList(4, 7)))
            }
            items.add(
                Item(
                    uriList.subList(
                        0,
                        uriList.size
                    )
                )
            )
            items.add(
                Item(
                    uriList.subList(
                        uriList.size - 8,
                        uriList.size - 2
                    )
                )
            )
            items.add(
                Item(
                    uriList.subList(
                        uriList.size - 2,
                        uriList.size
                    )
                )
            )
            nextPageNumber +=items.size
            return LoadResult.Page(items, null, "nextKey-${nextPageNumber}")
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
        return LoadResult.Page(mutableListOf(), null, null)
    }
}