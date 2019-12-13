package com.hawksjamesf.uicomponent

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search.*
import java.util.regex.Pattern

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/12/2019  Thu
 */
class SearchActivity : AppCompatActivity(), TextView.OnEditorActionListener {
    companion object {
        const val TAG = "SearchActivity"
    }

    var contentSpan: SpannableStringBuilder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
//        json_string = resources.openRawResource(R.raw.data).reader().buffered().readText()
        tv_content.text = json_string
        et_search.setOnEditorActionListener(this)
        et_search.imeOptions = EditorInfo.IME_ACTION_SEARCH

        contentSpan = SpannableStringBuilder.valueOf(json_string)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        Log.d(TAG,"keyword:${v?.text?.toString()}")
        if (v != null) {
            Log.d(TAG, "content:"+tv_content.text.toString())
            search(v!!.text.toString(), json_string)
        }
        return true

    }

    internal var matchTime = 0
    private fun search(keyword: String, content: String) {

        val p = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE)
        val matcher =p.matcher(content)
        // java.util.regex.PatternSyntaxException: Syntax error in regexp pattern near index 1

        var matchCount = 0
        var firstStart = 0
        while (matcher.find()) {
            if (matchCount > 10) {
                break
            }
            val start = matcher.start()
            val end = matcher.end()
            Log.d(TAG, "start:$start / end:$end  content length:${content.length} / contentSpan:${contentSpan?.length}")
            contentSpan?.setSpan(ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            if (matchCount == matchTime && firstStart == 0) {
                firstStart = start
            }
            matchCount++
        }
        tv_content.text = contentSpan
        if (firstStart > 0) {
            val indexLine = content.substring(0, firstStart).split("\n").toTypedArray().size
            sv_content_container.smoothScrollTo(0, et_search.lineHeight * indexLine)
//        sv_content_container.smoothScrollBy(0,0)
        } else {
            Toast.makeText(this, "这是最后一个啦", Toast.LENGTH_SHORT).show()
        }


    }

    var json_string = """
        {
          "coord": {
            "lon": -0.13,
            "lat": 51.51
          },
          "weather": [
            {
              "id": 741,
              "main": "Fog",
              "description": "fog",
              "icon": "50n"
            }
          ],
          "base": "stations",
          "main": {
            "temp": 275.44,
            "pressure": 1038,
            "humidity": 100,
            "temp_min": 273.15,
            "temp_max": 277.15
          },
          "visibility": 10000,
          "wind": {
            "speed": 0.5,
            "deg": 270
          },
          "clouds": {
            "all": 0
          },
          "dt": 1537854600,
          "sys": {
            "type": 1,
            "id": 5168,
            "message": 0.005,
            "country": "GB",
            "sunrise": 1537854698,
            "sunset": 1537897898
          },
          "id": 2643743,
          "name": "London",
          "cod": 200,
          "coord": {
            "lon": -0.13,
            "lat": 51.51
          },
          "weather": [
            {
              "id": 741,
              "main": "Fog",
              "description": "fog",
              "icon": "50n"
            }
          ],
          "base": "stations",
          "main": {
            "temp": 275.44,
            "pressure": 1038,
            "humidity": 100,
            "temp_min": 273.15,
            "temp_max": 277.15
          },
          "visibility": 10000,
          "wind": {
            "speed": 0.5,
            "deg": 270
          },
          "clouds": {
            "all": 0
          },
          "dt": 1537854600,
          "sys": {
            "type": 1,
            "id": 5168,
            "message": 0.005,
            "country": "GB",
            "sunrise": 1537854698,
            "sunset": 1537897898
          },
          "id": 2643743,
          "name": "London",
          "cod": 200
        }"""
}