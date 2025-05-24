package com.electrolytej.bundle2.page.customview.testStyle

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.util.SparseIntArray
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.core.util.isNotEmpty
import com.google.android.material.snackbar.Snackbar
import com.electrolytej.bundle2.R
import com.electrolytej.bundle2.databinding.ActivitySearchBinding
import com.electrolytej.widget.span.DrawableBackgroundSpan
import org.json.JSONObject
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

    var json_string = """{
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

    //    var lines: Int? = -1
    var map = SparseIntArray()

    @Volatile
    var curindex = 0
    val PAGE_LIMIT_LINE = 20
    var lineHeight: Int = -1
    lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        json_string = resources.openRawResource(R.raw.data).reader().buffered().readText()
        Log.d("hawks", "file length: ${json_string.toByteArray().size / 1024f} kb")
        binding.tvContent.text = JSONObject(json_string).toString(4)
        lineHeight = binding.tvContent.lineHeight
        binding.etSearch.setOnEditorActionListener(this)
        binding.tilSearchContainer.addOnEndIconChangedListener { textInputLayout, previousIcon ->

        }


    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        Log.d(
            TAG,
            "keyword:${v?.text?.toString()} / actionId $actionId / event action ${event?.action}"
        )
        if (v != null && actionId == EditorInfo.IME_ACTION_SEARCH) {
            reset()
            search(v!!.text.toString(), binding.tvContent.text.toString())
        }
        return false
    }

    fun onNext(v: View?) {
        if (map.isNotEmpty()) {
            next(map, binding.tvContent.text.toString())
        } else {
//            Snackbar.make(et_search, "心里能不能有点。。。没搜索哪来结果", Snackbar.LENGTH_SHORT).show()
            Snackbar.make(binding.fabNext, "没搜索结果", Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.fabNext)
                .show()
        }
    }

    fun reset() {
        map.clear()
        curindex = 0
    }

    private fun search(keyword: String, content: String) {
        val p = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE)
        val matcher = p.matcher(content)
        // java.util.regex.PatternSyntaxException: Syntax error in regexp pattern near index 1
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()


            map.put(start, end)
        }
        next(map, content)
    }

    @Synchronized
    fun next(map: SparseIntArray, content: String) {

        var contentSpan = SpannableStringBuilder(content)
        if (curindex >= map.size()) {
            curindex = 0
            return
        }
        map.forEach { key, value ->
            contentSpan.setSpan(
                ForegroundColorSpan(Color.CYAN),
                key,
                value,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        val start = map.keyAt(curindex)
        val end = map.valueAt(curindex)
        binding.tvContentShadow.text = content.substring(0, start)
        binding.tvContentShadow.post {
            //由于setText的数据为 content.substring(0, start)之后在post调用smoothScrollTo会scroll不准确，故lineCount的计算放在另外一个TextView中做
            var curLine = binding.tvContentShadow.lineCount //51
            runOnUiThread {
                val pagePosition = curLine / PAGE_LIMIT_LINE //1
                val pageLineIndex = curLine % PAGE_LIMIT_LINE //11
                val pageHeight = lineHeight * PAGE_LIMIT_LINE
                contentSpan.setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                contentSpan.setSpan(
                    StyleSpan(Typeface.BOLD_ITALIC),
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
//        contentSpan.setSpan(AbsoluteSizeSpan(15,true),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                contentSpan.setSpan(
                    RelativeSizeSpan(1.5f),
                    start,
                    end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                contentSpan.setSpan(
                    DrawableBackgroundSpan(
                        strokeColor = getColor(com.electrolytej.base.R.color.apricot),
                        strokeWith = 2,
                        dashWidth = 2,
                        dashGap = 1,
                        textColor = Color.RED
                    ), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.tvContent.text = contentSpan
                binding.svContentContainer.smoothScrollTo(0, pageHeight * pagePosition)
                val msg =
                    "curindex:$curindex / curLine:$curLine  / preScrollY:${binding.svContentContainer.scrollY} / curScrollY:${pageHeight * pagePosition}\n" +
                            "pagePosition:$pagePosition / pageLineIndex:$pageLineIndex / pageHeight:${pageHeight}\n" +
                            "start:$start / end:$end / map size:${map.size()}"
                Log.d("hawks", msg)
                if (map.size() - 1 == map.indexOfKey(start)) {
                    Toast.makeText(this, "这是最后一个啦", Toast.LENGTH_SHORT)
                        .show()
                    Snackbar.make(binding.fabNext, "last word", Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.fabNext)
                        .show()
                }
                curindex++
            }
        }
    }


}