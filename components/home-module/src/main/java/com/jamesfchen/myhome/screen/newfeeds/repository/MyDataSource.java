package com.jamesfchen.myhome.screen.newfeeds.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;

import kotlin.coroutines.Continuation;

public class MyDataSource extends PagingSource {
    @Nullable
    @Override
    public Object getRefreshKey(@NonNull PagingState pagingState) {
        return null;
    }

    @Nullable
    @Override
    public Object load(@NonNull LoadParams loadParams, @NonNull Continuation continuation) {
//        Result<Object> objectResult = new Result<>();
        //resumeWith返回 耗时任务返回值 并且自动切换线程
//        continuation.resumeWith(objectResult);
        return null;
    }
}
