package com.hawksjamesf.av

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.hardware.display.DisplayManager
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.*
import android.util.Log
import android.widget.Toast
import java.io.File

class RecorderService : Service() {
    lateinit var notificationManager: NotificationManager
    lateinit var mediaProjectionManager: MediaProjectionManager
    lateinit var mediaProjection: MediaProjection

    val recoder = Recorder
    private val mProjectionCallback: MediaProjection.Callback = object : MediaProjection.Callback() {
        override fun onStop() {
        }
    }
    val handlerThread = HandlerThread("handler_thread")

    inner class WorkHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_START -> {
                    Log.d("cjf","start...")
                    recoder.start()
                }
                MSG_STOP -> {
                    Log.d("cjf","stop...")
                    recoder.stop()
                    recoder.release()
                    mediaProjection?.stop()
                    mediaProjection?.unregisterCallback(mProjectionCallback)
                }

            }
        }
    }

    var workHandler: WorkHandler

    init {
        handlerThread.start()
        workHandler = WorkHandler(handlerThread.looper)
    }

    fun start() = workHandler.sendEmptyMessage(MSG_START)
    fun stop() = workHandler.sendEmptyMessage(MSG_STOP)


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent) {
        super.onRebind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        mediaProjectionManager = applicationContext.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val notificationIntent = Intent(this, RecorderActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            notificationManager!!.createNotificationChannel(chan)
            val notification = Notification.Builder(this, channelId)
                    .setContentTitle("this is title")
                    .setContentText("this is text")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setTicker("this is ticker")
                    .build()
            startForeground(ONGOING_NOTIFICATION_ID, notification)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val resultCode = intent.getIntExtra("code", -1)
        val data = intent.getParcelableExtra<Intent>("data")
        val mediaProjection: MediaProjection? = mediaProjectionManager.getMediaProjection(resultCode, data)
        if (mediaProjection != null) {

            this.mediaProjection = mediaProjection
            this.mediaProjection?.registerCallback(mProjectionCallback, Handler())
            val display = this.mediaProjection?.createVirtualDisplay("jfc", 1080, 1920, 1, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, null, null, null)
            recoder.setOutput(File(filesDir.absolutePath, "video.mp4"))
            recoder.setDisplay(display)
            start()
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        stop()
        Toast.makeText(this, "stop & unbind service", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun startService(activity: Activity, dataIntent: Intent? = null) {
            startAndBindService(activity, null, dataIntent)
        }

        fun startAndBindService(activity: Activity, connection: ServiceConnection?, dataIntent: Intent? = null) {
            val intent = Intent(dataIntent)
            intent.component = ComponentName(activity, RecorderService::class.java)
            if (connection != null) {
                activity.bindService(intent, connection, Context.BIND_AUTO_CREATE or Context.BIND_IMPORTANT)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity.startForegroundService(intent)
                Toast.makeText(activity, "start &  bind  foreground service", Toast.LENGTH_SHORT).show()
            } else {
                activity.startService(intent)
                Toast.makeText(activity, "start &  bind  service", Toast.LENGTH_SHORT).show()
            }
        }

        fun stopService(activity: Activity) {
            stopAndUnbindService(activity, null)

        }

        fun stopAndUnbindService(activity: Activity, connection: ServiceConnection?) {
            val intent = Intent(activity, RecorderService::class.java)
            activity.stopService(intent)
            if (connection != null) activity.unbindService(connection)
        }

        private const val ONGOING_NOTIFICATION_ID = 100
        private const val channelId = "channelId"
        private const val channelName = "channelName"
        private const val MSG_START = 0
        private const val MSG_STOP = 1
    }
}