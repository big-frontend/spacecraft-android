package com.electrolytej.ad.page.shake

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.Vibrator
import android.text.Editable
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.electrolytej.ad.Constants
import com.electrolytej.ad.Constants.sk_by_angle
import com.electrolytej.ad.Constants.sk_by_angle2
import com.electrolytej.ad.Constants.sk_by_angle2_y
import com.electrolytej.ad.Constants.sk_by_angle2_z
import com.electrolytej.ad.Constants.sk_by_angle_acceleration
import com.electrolytej.ad.Constants.sk_by_angle_acceleration2
import com.electrolytej.ad.Constants.sk_by_angle_acceleration2_y
import com.electrolytej.ad.Constants.sk_by_angle_acceleration2_z
import com.electrolytej.ad.Constants.sk_by_angle_acceleration_y
import com.electrolytej.ad.Constants.sk_by_angle_acceleration_z
import com.electrolytej.ad.Constants.sk_by_angle_y
import com.electrolytej.ad.Constants.sk_by_angle_z
import com.electrolytej.ad.Constants.sk_by_gap_duration
import com.electrolytej.ad.R
import com.electrolytej.ad.page.shake.sensor.ShakeSensorHandler
import com.electrolytej.ad.util.ShakeTraceUtil
import com.electrolytej.util.component6
import com.electrolytej.util.component7
import com.electrolytej.util.component8
import com.electrolytej.util.component9
import com.electrolytej.widget.LineChartView
import com.electrolytej.sensor.ISensorHandler
import com.electrolytej.sensor.SensorDetector
import com.electrolytej.ad.widget.SimpleTextWatcher
import com.electrolytej.util.NumberUtil
import com.electrolytej.widget.recyclerview.addDividerItemDecoration
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.max


class ShakeActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ShakeActivity"
    }

    lateinit var rvLogs: RecyclerView
    lateinit var realtimeAdapter: ContentShakeAdapter
    lateinit var rvLogs2: RecyclerView
    lateinit var hitShakeAdapter: LeftShakeAdapter

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var accelerometerChart: LineChartView
    lateinit var rationChart: LineChartView
    lateinit var aAndmChart: LineChartView
    lateinit var tvAccelerometer: TextView
    lateinit var tvRation: TextView
    lateinit var tvAm: TextView
    lateinit var btnClear: Button
    private  val mDetector: SensorDetector by lazy {
         val d = SensorDetector(this)
        d.samplingPeriodUs  = 20_000
        return@lazy d
    }
    val vibrator by lazy {
        getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
    private val handler2 = object : Handler() {
        override fun handleMessage(msg: Message) {
            vibrator.vibrate(300)
            stopSensor()
        }
    }
    private var isRecordStream = true
    private var isPause = false
    private var maxAx = 0.0
    private var maxAy = 0.0
    private var maxAz = 0.0
    private var maxRationDegreeDx = 0.0
    private var maxRationDegreeDy = 0.0
    private var maxRationDegreeDz = 0.0
    private var maxAmDegreeDx = 0.0
    private var maxAmDegreeDy = 0.0
    private var maxAmDegreeDz = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        drawerLayout = findViewById(R.id.drawer_layout)!!;
        coordinatorLayout = findViewById(R.id.coordinatorLayout)!!
        val llLogs: LinearLayout = findViewById(R.id.ll_logs)
        rvLogs = findViewById(R.id.rv_logs)
        realtimeAdapter = ContentShakeAdapter()
        rvLogs.adapter = realtimeAdapter
        rvLogs.addDividerItemDecoration()
        rvLogs2 = findViewById(R.id.rv_logs2)
        hitShakeAdapter = LeftShakeAdapter()
        rvLogs2.adapter = hitShakeAdapter
        rvLogs2.addDividerItemDecoration()
        accelerometerChart = findViewById(R.id.accelerometerChart)
        rationChart = findViewById(R.id.rationChart)
        aAndmChart = findViewById(R.id.aAndmChart)
        tvAccelerometer = findViewById(R.id.tv_accelerometer)
        tvRation = findViewById(R.id.tv_ration)
        tvAm = findViewById(R.id.tv_av)

        //配置
        val etSampleThreshold: EditText = findViewById(R.id.et_sample_threshold)
        etSampleThreshold.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                try {
                    val text: String = etSampleThreshold.text.toString()
                    realtimeAdapter.sampleThreshold = text.toLong()
                } catch (e: Exception) {
                    Log.d(TAG, Log.getStackTraceString(e))
                    realtimeAdapter.sampleThreshold = 1000L
                }
            }
        })

        val etAcceleration = findViewById(R.id.et_acceleration) as EditText
        etAcceleration.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                try {
                    val text: String = etAcceleration.text.toString()
                    val split =
                        text.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    Constants.accelerationX1 = split[0].toFloat()
                    Constants.accelerationY1 = split[1].toFloat()
                    Constants.accelerationZ1 = split[2].toFloat()
                } catch (e: Exception) {
                    Constants.accelerationX1 = sk_by_angle_acceleration
                    Constants.accelerationY1 = sk_by_angle_acceleration_y
                    Constants.accelerationZ1 = sk_by_angle_acceleration_z
                }
            }
        })
        etAcceleration.hint =
            "加速度1 ${sk_by_angle_acceleration}/${sk_by_angle_acceleration_y}/${sk_by_angle_acceleration_z}"
        val etDegree = findViewById(R.id.et_degree) as EditText
        etDegree.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                try {
                    val text: String = etDegree.text.toString()
                    val split =
                        text.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    Constants.degreesX1 = split[0].toFloat()
                    Constants.degreesY1 = split[1].toFloat()
                    Constants.degreesZ1 = split[2].toFloat()
                } catch (e: Exception) {
                    Constants.degreesX1 = sk_by_angle
                    Constants.degreesY1 = sk_by_angle_y
                    Constants.degreesZ1 = sk_by_angle_z
                }
            }
        })
        etDegree.hint = "角度1 ${sk_by_angle}/${sk_by_angle_y}/${sk_by_angle_z}"
        val etAcceleration2 = findViewById(R.id.et_acceleration2) as EditText
        etAcceleration2.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                try {
                    val text: String = etAcceleration2.text.toString()
                    val split =
                        text.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    Constants.accelerationX2 = split[0].toFloat()
                    Constants.accelerationY2 = split[1].toFloat()
                    Constants.accelerationZ2 = split[2].toFloat()
                } catch (e: Exception) {
                    Constants.accelerationX2 = sk_by_angle_acceleration2
                    Constants.accelerationY2 = sk_by_angle_acceleration2_y
                    Constants.accelerationZ2 = sk_by_angle_acceleration2_z
                }
            }
        })
        etAcceleration2.hint =
            "加速度2 ${sk_by_angle_acceleration2}/${sk_by_angle_acceleration2_y}/${sk_by_angle_acceleration2_z}"
        val etDegree2 = findViewById(R.id.et_degree2) as EditText
        etDegree2.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                try {
                    val text: String = etDegree2.text.toString()
                    val split =
                        text.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    Constants.degreesX2 = split[0].toFloat()
                    Constants.degreesY2 = split[1].toFloat()
                    Constants.degreesZ2 = split[2].toFloat()
                } catch (e: Exception) {
                    Constants.degreesX2 = sk_by_angle2
                    Constants.degreesY2 = sk_by_angle2_y
                    Constants.degreesZ2 = sk_by_angle2_z
                }
            }
        })
        etDegree2.hint = "角度2 ${sk_by_angle2}/${sk_by_angle2_y}/${sk_by_angle2_z}"
        val etDuratioin: EditText = findViewById(R.id.et_duration)
        etDuratioin.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                try {
                    val text: String = etDuratioin.text.toString()
                    Constants.duration = text.toInt()
                } catch (e: Exception) {
                    Constants.duration = sk_by_gap_duration
                }
            }
        })
        etDuratioin.hint = "间隔时间$sk_by_gap_duration"


        val btnPause: Button = findViewById(R.id.btn_pause)
        btnPause.setOnClickListener {
            isPause = !isPause
        }
        val btnRecordStream: Button = findViewById(R.id.btn_record_stream)
        btnRecordStream.setOnClickListener {
            isRecordStream = true
            clearRealTimeData()
            hitShakeAdapter.clear()
            realtimeAdapter.refresh = true
            startSensor()

        }
        val btnRecordSample: Button = findViewById(R.id.btn_record_sample)
        btnRecordSample.setOnClickListener {
            isRecordStream = false
            clearRealTimeData()
            realtimeAdapter.refresh = true
            startSensor()
            handler2.removeMessages(1)
            handler2.sendEmptyMessageDelayed(1, 5000)
        }
        val btnCheckShake: Button = findViewById(R.id.btn_check_shake)
        btnCheckShake.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val btnSwitchList: Button = findViewById(R.id.btn_switch_list)
        val llChart: LinearLayout = findViewById(R.id.ll_chart)
        btnSwitchList.setOnClickListener {
            llLogs.visibility = if (llLogs.visibility == ViewGroup.VISIBLE) {
                ViewGroup.GONE
            } else {
                ViewGroup.VISIBLE
            }

            llChart.visibility = if (llLogs.visibility == ViewGroup.VISIBLE) {
                ViewGroup.GONE
            } else {
                ViewGroup.VISIBLE
            }
        }

        btnClear = findViewById(R.id.btn_clear)
        btnClear.setOnClickListener {
            hitShakeAdapter.clear()
        }
        startSensor()

    }

    private fun createSensorHandler(): ISensorHandler {
        val startTime = System.currentTimeMillis()
        val handler = ShakeSensorHandler()
        handler.setOnShakeListener( object : ShakeSensorHandler.OnShakeListener {
            override fun onTrace(resultValues: DoubleArray) {
                if (isPause || mDetector.isStop) {
                    return
                }
                val (ax, ay, az, rationDegreeDx, rationDegreeDy, rationDegreeDz,amDegreeDx,amDegreeDy,amDegreeDz) = resultValues
                val endTime = System.currentTimeMillis() - startTime
                accelerometerChart.addDataPoint(
                    LineChartView.DataPoint(
                        endTime, ax, ay, az
                    )
                )
                rationChart.addDataPoint(
                    LineChartView.DataPoint(
                        endTime, rationDegreeDx, rationDegreeDy,
//                        rationDegreeDz
                    )
                )
                aAndmChart.addDataPoint(
                    LineChartView.DataPoint(
                        endTime, amDegreeDx,
//                        amDegreeDy, amDegreeDz
                    )
                )
                computeMax(
                    NumberUtil.round(abs(ax), 1),
                    NumberUtil.round(abs(ay), 1),
                    NumberUtil.round(abs(az), 1),
                    NumberUtil.round(abs(rationDegreeDx), 1),
                    NumberUtil.round(abs(rationDegreeDy), 1),
                    NumberUtil.round(abs(rationDegreeDz), 1),
                    NumberUtil.round(abs(amDegreeDx), 1),
                    NumberUtil.round(abs(amDegreeDy), 1),
                    NumberUtil.round(abs(amDegreeDz), 1),
                )
                val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                val p = "加速度:" + ShakeTraceUtil.accelerationTrace(
                    ax,
                    ay,
                    az
                ) + " 角度差:" + ShakeTraceUtil.degreeTrace(rationDegreeDx, rationDegreeDy, rationDegreeDz)
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val currentTime = sdf.format(Date())
                val pp = "${p}\n$currentTime"
                if (realtimeAdapter.add(pp)) {
                    rvLogs.smoothScrollToPosition(realtimeAdapter.itemCount - 1)
                    //            rvLogs.scrollToPosition(shakeList.size - 1)
                }
                Log.d(TAG, "onShakeTrace:$pp")
            }


//            override fun onShake(p: String) {
//                if (isPause) {
//                    return
//                }
//                if (isRecordStream) {
//                    return
//                }
//                handler2.removeMessages(1)
//                stopSensor()
//                vibrator.vibrate(300)
//                realtimeAdapter.refresh = false
//                Log.i(TAG, "onShake: $p")
//                SnackBarUtil.show(coordinatorLayout, p)
//                if (hitShakeAdapter.add(p)) {
//                    rvLogs2.scrollToPosition(hitShakeAdapter.itemCount - 1)
//                }
//            }

        })
        return handler
    }

    fun computeMax(
        ax: Double,
        ay: Double,
        az: Double,
        rationDegreeDx: Double,
        rationDegreeDy: Double,
        rationDegreeDz: Double,
        amDegreeDx :Double,
        amDegreeDy:Double,
        amDegreeDz:Double
    ) {
        maxAx = max(ax,maxAx)
        maxAy = max(ay,maxAy)
        maxAz = max(az,maxAz)

        maxRationDegreeDx = max(rationDegreeDx,maxRationDegreeDx)
        maxRationDegreeDy = max(rationDegreeDy,maxRationDegreeDy)
        maxRationDegreeDz = max(rationDegreeDz,maxRationDegreeDz)

        maxAmDegreeDx = max(amDegreeDx,maxAmDegreeDx)
        maxAmDegreeDy = max(amDegreeDy,maxAmDegreeDy)
        maxAmDegreeDz = max(amDegreeDz,maxAmDegreeDz)

        tvAccelerometer.text = "加速度\n最大x/y/z ${maxAx}/${maxAy}/${maxAz}"
        tvRation.text = "旋转矢量\n最大x/y/z ${maxRationDegreeDx}/${maxRationDegreeDy}/${maxRationDegreeDz}"
        tvAm.text = "加速度+磁力计\n最大x/y/z ${maxAmDegreeDx}/${maxAmDegreeDy}/${maxAmDegreeDz}"
    }

    private fun clearRealTimeData() {
        stopSensor()
        realtimeAdapter.clear()
        accelerometerChart.clearData()
        rationChart.clearData()
        aAndmChart.clearData()

        maxAy = 0.0
        maxAy = 0.0
        maxAz = 0.0
        maxRationDegreeDx = 0.0
        maxRationDegreeDy = 0.0
        maxRationDegreeDz = 0.0

        maxAmDegreeDx = 0.0
        maxAmDegreeDy = 0.0
        maxAmDegreeDz = 0.0
    }


    fun startSensor() {
        mDetector.addHandler(createSensorHandler())
//        mDetector.addHandler(DoubleShakeSensorHandler())
//        mDetector.addHandler(TwoStageShakeSensorHandler())
        mDetector.start()
    }

    fun stopSensor() {
        mDetector.stop()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSensor()
    }

}
