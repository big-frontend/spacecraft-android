package com.jamesfchen.myhome.page.newfeeds.repository

import androidx.paging.*
import com.jamesfchen.myhome.network.api.NewFeedsApi
import com.jamesfchen.myhome.page.newfeeds.model.Item
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/24/2019  Sun
 */
interface Repository {
    val flow: Flow<PagingData<Item>>
}

class NewFeedsRepositoryImpl(private val region: CacheRegion) : Repository {
    companion object {
        private val DISK_IO = Executors.newSingleThreadExecutor()
        private val NETWORK_IO = Executors.newFixedThreadPool(3)
    }
    var executor: Executor = when (region) {
        CacheRegion.IN_MEMORY_BY_PAGE -> {
            NETWORK_IO
        }
        CacheRegion.IN_MEMORY_BY_ITEM -> {
            NETWORK_IO
        }
        CacheRegion.IN_DB -> {
            DISK_IO
        }
    }
    private val api by lazy {
        NewFeedsApi.create()
    }
    @ExperimentalPagingApi
    override val flow: Flow<PagingData<Item>>
        get(){
            return when (region) {
                CacheRegion.IN_MEMORY_BY_ITEM -> {
                    Pager(
                        config = PagingConfig(
                            pageSize = 30,
                            enablePlaceholders = false,
                            initialLoadSize = 30 * 2
                        ),
                        pagingSourceFactory = {
                            PhotoItemKeyedDataSource(api)
                        }
                    ).flow
                }
                CacheRegion.IN_MEMORY_BY_PAGE -> {
                    Pager(
                        config = PagingConfig(
                            pageSize = 30,
                            enablePlaceholders = false,
                            initialLoadSize = 30 * 2
                        ),
                        pagingSourceFactory = {
                            PhotoPageKeyedDataSource(api)

                        }
                    ).flow
                }
                else -> {
                    Pager(
                        config = PagingConfig(
                            pageSize = 30,
                            enablePlaceholders = false,
                            initialLoadSize = 30 * 2
                        ), remoteMediator = PageKeyedRemoteMediator(api),
                        pagingSourceFactory = {
//                        db.posts().postsBySubreddit(subReddit)
                            PhotoPageKeyedDataSource(
                                api
                            )
                        }
                    ).flow
                }
            }
        }

}