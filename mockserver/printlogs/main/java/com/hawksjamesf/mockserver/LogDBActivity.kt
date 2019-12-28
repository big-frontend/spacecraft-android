package com.hawksjamesf.mockserver

import android.annotation.TargetApi
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.SPUtils
import com.facebook.stetho.InspectorModulesProvider
import com.facebook.stetho.Stetho
import com.facebook.stetho.inspector.database.ContentProviderDatabaseDriver
import com.facebook.stetho.inspector.database.ContentProviderSchema
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain
import com.hawksjamesf.mockserver.platform.LogDBContract
import com.hawksjamesf.mockserver.platform.LogDBHelper
import com.hawksjamesf.mockserver.platform.save
import com.orhanobut.logger.Logger
import okhttp3.*
import java.io.IOException

class LogDBActivity : AppCompatActivity() {
    //    private RecyclerView mrvHome;
    val logDB = LogDBHelper.getDB(this)

    companion object {
        const val TAG = "LogDBActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        textView.text = "log db activity"
        textView.id = R.id.tv_text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        setContentView(textView)
//
//        Stetho.initializeWithDefaults(this)
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp {
                    Stetho.DefaultDumperPluginsBuilder(this)
                            .provide(DBDumperPlugin(contentResolver))
                            .finish()
                }
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                .enableWebKitInspector(ExtInspectorModulesProvider(this))
                .build())
        repeat(20) {
            val values = ContentValues()
            values.put(LogDBContract.LogEntity.COLUMNS_SERVICE_CODE, it)
            if (it % 2 == 0) {
                values.put(LogDBContract.LogEntity.COLUMNS_ENTITY_TYPE, "request")
            } else {
                values.put(LogDBContract.LogEntity.COLUMNS_ENTITY_TYPE, "response")
            }
            logDB?.save(values)
        }
//        logDB?.query()

        textView.setOnClickListener {
            val url = "${SPUtils.getInstance().getString(Constants.PRE_BASE_URL)}data/2.5/weather"
            Logger.t(TAG).i(url)
            OkHttpClient.Builder()
//                    .dns(Dns.SYSTEM)//spacecraft.com
                    .connectionSpecs(arrayListOf(ConnectionSpec.CLEARTEXT))
                    .build()
                    .newCall(Request.Builder()
                            .url(url)
                            .build()
                    ).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Logger.t("$TAG---onFailure").i(e.message)
                        }

                        override fun onResponse(call: Call, response: Response) {
                            Logger.t("$TAG---onResponse").i(response.isSuccessful.toString())
                        }

                    })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logDB?.close()
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