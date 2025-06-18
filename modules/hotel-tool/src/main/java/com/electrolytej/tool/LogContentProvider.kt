package com.electrolytej.tool

import android.app.ActivityManager
import android.content.ContentProvider
import android.content.ContentProviderOperation
import android.content.ContentProviderResult
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Process
import android.util.Log
import com.electrolytej.tool.platform.LogDBContract
import com.electrolytej.tool.platform.LogDBHelper
import java.util.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/16/2019  Mon
 */
class LogContentProvider : ContentProvider() {
    var logHelper: LogDBHelper? = null
    var logDB: SQLiteDatabase? = null

    companion object {
        const val TAG = "LogProvider"
    }

    override fun onCreate(): Boolean {
        logHelper = LogDBHelper.getInstance(context)
        logDB = LogDBHelper.getDB(context)
        Log.d(
            "cjf",
            "onCreate--->databaseName:${logHelper?.databaseName} process:${getProcessName(context)}"
        )

        return true
    }

    override fun getType(uri: Uri): String? {
        Log.d(TAG, "getType")
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "insert")
        if (logDB == null) return null
        val id: Long = logDB!!.insert(LogDBContract.TABLE_NAME, null /* nullColumnHack */, values)
        notifyChange()
        return uri.buildUpon().appendEncodedPath(id.toString()).build()
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d(TAG, "query")
        val cursor = logDB?.query(
            LogDBContract.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null /* groupBy */,
            null /* having */,
            sortOrder,
            null /* limit */
        )
        cursor?.setNotificationUri(context!!.contentResolver, LogDBContract.CONTENT_URI)
        return cursor
//        notifyChange()
        return null
    }


    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        Log.d(TAG, "update")
        notifyChange()
        return -1
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "delete")
        notifyChange()
        return -1
    }

    /**
     * example:
     * ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
     *  operations.add(
    ContentProviderOperation.newDelete(APODContract.CONTENT_URI)
    .build());
     * mContentResolver.applyBatch(AUTHORITY, operations);
     */
    override fun applyBatch(operations: ArrayList<ContentProviderOperation>): Array<ContentProviderResult> {
        Log.d(TAG, "applyBatch:1 param")
        logDB?.beginTransaction()
        val results = super.applyBatch(operations)
        logDB?.setTransactionSuccessful()
        logDB?.endTransaction()
        notifyChange()
        return results
    }

    override fun applyBatch(
        authority: String,
        operations: ArrayList<ContentProviderOperation>
    ): Array<ContentProviderResult> {
        Log.d(TAG, "applyBatch:2 params")
        logDB?.beginTransaction()
        val results = super.applyBatch(authority, operations)
        logDB?.setTransactionSuccessful()
        logDB?.endTransaction()
        logDB?.endTransaction()
        notifyChange()
        return results
    }

    private fun notifyChange() {
        context!!.contentResolver.notifyChange(LogDBContract.CONTENT_URI, null /* observer */)
    }

    fun getProcessName(context: Context?): String {
        val am = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = am.runningAppProcesses ?: return ""

        for (processInfo in runningAppProcesses) {
            if (processInfo.pid == Process.myPid() && processInfo.processName != null) {
                return processInfo.processName
            }
        }

        return ""
    }
}