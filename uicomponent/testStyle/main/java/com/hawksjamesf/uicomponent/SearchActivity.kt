package com.hawksjamesf.uicomponent

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Dec/12/2019  Thu
 */
class SearchActivity : AppCompatActivity(), TextView.OnEditorActionListener {
    companion object{
        const val TAG="SearchActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        tv_content.text = json_string
        et_search.setOnEditorActionListener(this)
        et_search.imeOptions = EditorInfo.IME_ACTION_SEARCH
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

        if (v != null) {
            Log.d(TAG,tv_content.text.toString())
            search(v!!.text,et_search.text.toString())
        }
        return true

    }

    private fun search(keyword: CharSequence, content: String) {
        sv_content_container.smoothScrollBy(0,0)
        sv_content_container.smoothScrollTo(0,0)

    }
}

const val json_string = "{\n" +
        "  \"coord\": {\n" +
        "    \"lon\": -0.13,\n" +
        "    \"lat\": 51.51\n" +
        "  },\n" +
        "  \"weather\": [\n" +
        "    {\n" +
        "      \"id\": 741,\n" +
        "      \"main\": \"Fog\",\n" +
        "      \"description\": \"fog\",\n" +
        "      \"icon\": \"50n\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"base\": \"stations\",\n" +
        "  \"main\": {\n" +
        "    \"temp\": 275.44,\n" +
        "    \"pressure\": 1038,\n" +
        "    \"humidity\": 100,\n" +
        "    \"temp_min\": 273.15,\n" +
        "    \"temp_max\": 277.15\n" +
        "  },\n" +
        "  \"visibility\": 10000,\n" +
        "  \"wind\": {\n" +
        "    \"speed\": 0.5,\n" +
        "    \"deg\": 270\n" +
        "  },\n" +
        "  \"clouds\": {\n" +
        "    \"all\": 0\n" +
        "  },\n" +
        "  \"dt\": 1537854600,\n" +
        "  \"sys\": {\n" +
        "    \"type\": 1,\n" +
        "    \"id\": 5168,\n" +
        "    \"message\": 0.005,\n" +
        "    \"country\": \"GB\",\n" +
        "    \"sunrise\": 1537854698,\n" +
        "    \"sunset\": 1537897898\n" +
        "  },\n" +
        "  \"id\": 2643743,\n" +
        "  \"name\": \"London\",\n" +
        "  \"cod\": 200\n" +
        "  \"coord\": {\n" +
        "    \"lon\": -0.13,\n" +
        "    \"lat\": 51.51\n" +
        "  },\n" +
        "  \"weather\": [\n" +
        "    {\n" +
        "      \"id\": 741,\n" +
        "      \"main\": \"Fog\",\n" +
        "      \"description\": \"fog\",\n" +
        "      \"icon\": \"50n\"\n" +
        "    }\n" +
        "  ],\n" +
        "  \"base\": \"stations\",\n" +
        "  \"main\": {\n" +
        "    \"temp\": 275.44,\n" +
        "    \"pressure\": 1038,\n" +
        "    \"humidity\": 100,\n" +
        "    \"temp_min\": 273.15,\n" +
        "    \"temp_max\": 277.15\n" +
        "  },\n" +
        "  \"visibility\": 10000,\n" +
        "  \"wind\": {\n" +
        "    \"speed\": 0.5,\n" +
        "    \"deg\": 270\n" +
        "  },\n" +
        "  \"clouds\": {\n" +
        "    \"all\": 0\n" +
        "  },\n" +
        "  \"dt\": 1537854600,\n" +
        "  \"sys\": {\n" +
        "    \"type\": 1,\n" +
        "    \"id\": 5168,\n" +
        "    \"message\": 0.005,\n" +
        "    \"country\": \"GB\",\n" +
        "    \"sunrise\": 1537854698,\n" +
        "    \"sunset\": 1537897898\n" +
        "  },\n" +
        "  \"id\": 2643743,\n" +
        "  \"name\": \"London\",\n" +
        "  \"cod\": 200\n" +
        "}"