package com.hawksjamesf.uicomponent

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.FrameMetrics
import android.view.View
import android.view.ViewGroup
import android.view.Window.OnFrameMetricsAvailableListener
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

class ConstraintPerformanceActivity : AppCompatActivity() {

    private val frameMetricsHandler = Handler()
    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("LongLogTag")
    private val frameMetricsAvailableListener = OnFrameMetricsAvailableListener { _, frameMetrics, _ ->
        val frameMetricsCopy = FrameMetrics(frameMetrics)
        // Layout measure duration in Nano seconds
        val layoutMeasureDurationNs = frameMetricsCopy.getMetric(FrameMetrics.LAYOUT_MEASURE_DURATION)

        Log.d(TAG, "layoutMeasureDurationMs: ${layoutMeasureDurationNs/Math.pow(10.0,6.0)}")
//        val layoutMeasureDurationMs = TimeUnit.MILLISECONDS.convert(layoutMeasureDurationNs, TimeUnit.NANOSECONDS)
//        Log.d(TAG, "layoutMeasureDurationMs: $layoutMeasureDurationMs")
    }

    companion object {

        private val TAG = "ConstraintPerformanceActivity"

        private val TOTAL = 100

        private val WIDTH = 1920

        private val HEIGHT = 1080
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_for_test)

        val traditionalCalcButton = findViewById<Button>(R.id.button_start_calc_traditional)
        val constraintCalcButton = findViewById<Button>(R.id.button_start_calc_constraint)
        val textViewFinish = findViewById<TextView>(R.id.textview_finish)
        traditionalCalcButton.setOnClickListener {
            @SuppressLint("InflateParams")
            constraintCalcButton.visibility = View.INVISIBLE
            val container = layoutInflater
                    .inflate(R.layout.activity_traditional, null) as ViewGroup
            val asyncTask = MeasureLayoutAsyncTask(
                    getString(R.string.executing_nth_iteration),
                    WeakReference(traditionalCalcButton),
                    WeakReference(textViewFinish),
                    WeakReference(container))
            asyncTask.execute()
        }

        constraintCalcButton.setOnClickListener {
            @SuppressLint("InflateParams")
            traditionalCalcButton.visibility = View.INVISIBLE
            val container = layoutInflater
                    .inflate(R.layout.activity_constraintlayout, null) as ViewGroup
            val asyncTask = MeasureLayoutAsyncTask(
                    getString(R.string.executing_nth_iteration),
                    WeakReference(constraintCalcButton),
                    WeakReference(textViewFinish),
                    WeakReference(container))
            asyncTask.execute()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        window.addOnFrameMetricsAvailableListener(frameMetricsAvailableListener, frameMetricsHandler)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onPause() {
        super.onPause()
        window.removeOnFrameMetricsAvailableListener(frameMetricsAvailableListener)
    }

    /**
     * AsyncTask that triggers measure/layout in the background. Not to leak the Context of the
     * Views, take the View instances as WeakReferences.
     */
    private class MeasureLayoutAsyncTask(val executingNthIteration: String,
                                         val startButtonRef: WeakReference<Button>,
                                         val finishTextViewRef: WeakReference<TextView>,
                                         val containerRef: WeakReference<ViewGroup>) : AsyncTask<Void?, Int, Void?>() {

        override fun doInBackground(vararg voids: Void?): Void? {
            for (i in 0 until TOTAL) {
                publishProgress(i)
                try {
                    Thread.sleep(100)
                } catch (ignore: InterruptedException) {
                    // No op
                }

            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            val startButton = startButtonRef.get() ?: return
            startButton.text = String.format(executingNthIteration, values[0], TOTAL)
            val container = containerRef.get() ?: return
            // Not to use the view cache in the View class, use the different measureSpecs
            // for each calculation. (Switching the
            // View.MeasureSpec.EXACT and View.MeasureSpec.AT_MOST alternately)
            measureAndLayoutExactLength(container)
            measureAndLayoutWrapLength(container)
        }

        override fun onPostExecute(aVoid: Void?) {
            val finishTextView = finishTextViewRef.get() ?: return
            finishTextView.visibility = View.VISIBLE
            val startButton = startButtonRef.get() ?: return
            startButton.visibility = View.GONE
        }

        private fun measureAndLayoutWrapLength(container: ViewGroup) {
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(WIDTH,
                    View.MeasureSpec.AT_MOST)
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(HEIGHT,
                    View.MeasureSpec.AT_MOST)
            container.measure(widthMeasureSpec, heightMeasureSpec)
            container.layout(0, 0, container.measuredWidth,
                    container.measuredHeight)
        }

        private fun measureAndLayoutExactLength(container: ViewGroup) {
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(WIDTH,
                    View.MeasureSpec.EXACTLY)
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(HEIGHT,
                    View.MeasureSpec.EXACTLY)
            container.measure(widthMeasureSpec, heightMeasureSpec)
            container.layout(0, 0, container.measuredWidth,
                    container.measuredHeight)
        }
    }
}
