package com.hawksjamesf.spacecraft

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import androidx.appcompat.app.AppCompatActivity
import com.facebook.stetho.InspectorModulesProvider
import com.facebook.stetho.Stetho
import com.facebook.stetho.inspector.database.ContentProviderDatabaseDriver
import com.facebook.stetho.inspector.database.ContentProviderSchema
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain
import com.hawksjamesf.storage.DBDumperPlugin


/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Aug/03/2019  Sat
 */
class StethoActivity : AppCompatActivity() {
    companion object {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        UETool.showUETMenu();
//        Stetho.initializeWithDefaults(this)
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp {
                    Stetho.DefaultDumperPluginsBuilder(this)
                            .provide(DBDumperPlugin(contentResolver))
                            .finish()
                }
//                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableWebKitInspector(ExtInspectorModulesProvider(this))
                .build());
    }

    private class ExtInspectorModulesProvider internal constructor(private val mContext: Context) : InspectorModulesProvider {

        override fun get(): Iterable<ChromeDevtoolsDomain> {
            return Stetho.DefaultInspectorModulesBuilder(mContext)
                    .provideDatabaseDriver(createContentProviderDatabaseDriver(mContext))
                    .finish()
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        private fun createContentProviderDatabaseDriver(context: Context): ContentProviderDatabaseDriver {
            val calendarsSchema = ContentProviderSchema.Builder()
                    .table(ContentProviderSchema.Table.Builder()
                            .uri(CalendarContract.Calendars.CONTENT_URI)
                            .projection(arrayOf<String>(CalendarContract.Calendars._ID, CalendarContract.Calendars.NAME, CalendarContract.Calendars.ACCOUNT_NAME, CalendarContract.Calendars.IS_PRIMARY))
                            .build())
                    .build()

            // sample events content provider we want to support
            val eventsSchema = ContentProviderSchema.Builder()
                    .table(ContentProviderSchema.Table.Builder()
                            .uri(CalendarContract.Events.CONTENT_URI)
                            .projection(arrayOf<String>(CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.ACCOUNT_NAME, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.CALENDAR_ID))
                            .build())
                    .build()
            return ContentProviderDatabaseDriver(context, calendarsSchema, eventsSchema)
        }
    }
}