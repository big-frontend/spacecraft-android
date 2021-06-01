package com.jamesfchen.image

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_gif.*

class GifActivity : AppCompatActivity() {


    lateinit var player: GifPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif)
//        iv_gif.setSource("tenor.gif")
//        iv_gif.setSource("tenor.gif",assets)
//
        player= GifPlayer.createAndBind(this,iv_gif,"tenor.gif", assets)
        var i = 0
        bt_start.setOnClickListener {
//            player.start()
            (bt_start as Button).text = "start ${++i}"
        }


    }
}