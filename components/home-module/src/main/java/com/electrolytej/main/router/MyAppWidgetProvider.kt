package com.electrolytej.main.router

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.electrolytej.main.R

class MyAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val remoteViews = RemoteViews(context.packageName, R.layout.deep_link_appwidget)

        val args = Bundle()
        args.putString("url", "file:///android_asset/test.html")
        val pendingIntent = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_screen)
                .setDestination(R.id.dest_activity_webview)
                .setArguments(args)
                .createPendingIntent()
        remoteViews.setOnClickPendingIntent(R.id.deep_link_button, pendingIntent)
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }
}
