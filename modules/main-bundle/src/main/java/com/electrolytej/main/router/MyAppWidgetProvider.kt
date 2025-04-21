package com.electrolytej.main.router

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.electrolytej.main.R
import com.electrolytej.util.getAssetsPath

class MyAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val remoteViews = RemoteViews(context.packageName, R.layout.appwidget_deep_link)

        val args = Bundle()
        args.putString("url", getAssetsPath("test.html"))
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_main)
            .setDestination(R.id.dest_activity_webview)
//            .setDestination(R.id.dest_fragment_webview)
//            .setComponentName(WebActivity::class.java)
            .setArguments(args)
            .createPendingIntent()
        remoteViews.setOnClickPendingIntent(R.id.deep_link_button, pendingIntent)
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }
}
