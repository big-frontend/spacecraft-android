package com.jamesfchen.image.gif

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jamesfchen.image.databinding.ActivityGifBinding

class GifActivity : AppCompatActivity() {


    lateinit var player: GifPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGifBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        iv_gif.setSource("tenor.gif")
//        iv_gif.setSource("tenor.gif",assets)
//
        player= GifPlayer.createAndBind(this, binding.ivGif, "tenor.gif", assets)
        var i = 0
        binding.btStart.setOnClickListener {
//            player.start()
            (binding.btStart as Button).text = "start ${++i}"
        }


    }
}