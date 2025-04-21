package com.electrolytej.bundle2.page.customview.newLayoutExprimental

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.electrolytej.bundle2.R
import java.lang.ref.WeakReference

class ConstraintPerformanceActivity : AppCompatActivity() {
    companion object {
        private const val TOTAL = 100
        private const val WIDTH = 1920
        private const val HEIGHT = 1080
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
            val container = layoutInflater.inflate(R.layout.activity_traditional, null,false) as ViewGroup
            val asyncTask = MeasureLayoutAsyncTask(
                getString(R.string.executing_nth_iteration),
                WeakReference(traditionalCalcButton),
                WeakReference(textViewFinish),
                WeakReference(container)
            )
            asyncTask.execute()
        }

        constraintCalcButton.setOnClickListener {
            @SuppressLint("InflateParams")
            traditionalCalcButton.visibility = View.INVISIBLE
            val container =
                layoutInflater.inflate(R.layout.activity_constraintlayout, null) as ViewGroup
            val asyncTask = MeasureLayoutAsyncTask(
                getString(R.string.executing_nth_iteration),
                WeakReference(constraintCalcButton),
                WeakReference(textViewFinish),
                WeakReference(container)
            )
            asyncTask.execute()
        }
        textViewFinish.setOnClickListener {
            textViewFinish.visibility  = View.INVISIBLE
            constraintCalcButton.visibility = View.VISIBLE
            traditionalCalcButton.visibility = View.VISIBLE
        }
    }

    /**
     * AsyncTask that triggers measure/layout in the background. Not to leak the Context of the
     * Views, take the View instances as WeakReferences.
     */
    private class MeasureLayoutAsyncTask(
        val executingNthIteration: String,
        val startButtonRef: WeakReference<Button>,
        val finishTextViewRef: WeakReference<TextView>,
        val containerRef: WeakReference<ViewGroup>
    ) : AsyncTask<Void?, Int, Void?>() {

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
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                WIDTH,
                View.MeasureSpec.AT_MOST
            )
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                HEIGHT,
                View.MeasureSpec.AT_MOST
            )
            container.measure(widthMeasureSpec, heightMeasureSpec)
            container.layout(
                0, 0, container.measuredWidth,
                container.measuredHeight
            )
        }

        private fun measureAndLayoutExactLength(container: ViewGroup) {
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                WIDTH,
                View.MeasureSpec.EXACTLY
            )
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                HEIGHT,
                View.MeasureSpec.EXACTLY
            )
            container.measure(widthMeasureSpec, heightMeasureSpec)
            container.layout(
                0, 0, container.measuredWidth,
                container.measuredHeight
            )
        }
    }
}
