package com.jamesfchen.mockserver

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.orhanobut.logger.Logger

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/27/2019  Fri
 */
class MockServerContentProvider : ContentProvider() {
    companion object{
        const val TAG="${Constants.TAG}/MockServerProvider"
    }

    override fun onCreate(): Boolean {
        Logger.t(TAG).e( "onCreate")

        return true
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null

    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        return null
    }


    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return -1
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return -1
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}