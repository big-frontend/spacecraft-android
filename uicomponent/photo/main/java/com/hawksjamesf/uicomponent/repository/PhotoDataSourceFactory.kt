package com.hawksjamesf.uicomponent.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/24/2019  Sun
 */
class PhotoDataSourceFactory(
        private val region: CacheRegion,
        private val api: NetworkApi
) : DataSource.Factory<String, String>() {

    val sourceLiveData = MutableLiveData<DataSource<String, String>>()
    override fun create(): DataSource<String, String> {
        val datasource:DataSource<String, String> = when (region) {
            CacheRegion.IN_MEMORY_BY_ITEM -> {
                PhotoItemKeyedDataSource(api)
            }
            CacheRegion.IN_MEMORY_BY_PAGE -> {
                PhotoPageKeyedDataSource(api)
            }
            else -> {
                PhotoPageKeyedDataSource(api)
            }
        }
        sourceLiveData.postValue(datasource)
        return datasource
    }
}